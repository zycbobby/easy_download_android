package uk.co.ribot.androidboilerplate.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import uk.co.ribot.androidboilerplate.AndroidBoilerplateApplication;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.Thing;
import uk.co.ribot.androidboilerplate.data.model.User;
import uk.co.ribot.androidboilerplate.ui.adapter.ThingItemViewHolder;
import uk.co.ribot.androidboilerplate.util.ViewUtil;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

import static rx.android.app.AppObservable.bindActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private static final String TAG = "SEARCH FRAGMENT";

    @Bind(R.id.editText)
    EditText searchBar;

    @Bind(R.id.searchResult)
    RecyclerView searchResultListView;

    private CompositeSubscription mSubscriptions;
    private EasyRecyclerAdapter<Thing> adapter;

    DataManager dataManager;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);


        adapter = new EasyRecyclerAdapter<>(this.getActivity(), ThingItemViewHolder.class);
        searchResultListView.setAdapter(adapter);
        searchResultListView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        dataManager = AndroidBoilerplateApplication.get().getDataManager();
        mSubscriptions = new CompositeSubscription();
        mSubscriptions.add(bindActivity(this.getActivity(), createSearchObservable(searchBar)).subscribeOn(dataManager.getScheduler()).subscribe(onQueryEntered()));

        return view;
    }

    private Observable<String> createSearchObservable(final EditText searchBar) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            ViewUtil.hideKeyboard(getActivity());
                            String word = textView.getText().toString();
                            subscriber.onNext(word);
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }

    private Action1<String> onQueryEntered() {
        return new Action1<String>() {
            @Override
            public void call(String word) {

                dataManager.getRuntimeData().getUser().addTags(word);
                dataManager.createOrUpdate(dataManager.getRuntimeData().getUser()).subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        System.out.println(user + " updated tags");
                    }
                });

                dataManager.searchThings(word)
                        .subscribeOn(dataManager.getScheduler())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<Thing>>() {
                            @Override
                            public void call(List<Thing> things) {
                                adapter.setItems(things);
                            }

                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });
            }
        };
    }


    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        mSubscriptions.unsubscribe();
        super.onDestroyView();
    }
}
