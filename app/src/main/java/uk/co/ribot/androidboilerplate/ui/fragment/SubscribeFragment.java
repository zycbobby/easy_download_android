package uk.co.ribot.androidboilerplate.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import uk.co.ribot.androidboilerplate.AndroidBoilerplateApplication;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.Thing;
import uk.co.ribot.androidboilerplate.data.model.User;
import uk.co.ribot.androidboilerplate.ui.adapter.TagItemViewHolder;
import uk.co.ribot.androidboilerplate.ui.adapter.ThingItemViewHolder;
import uk.co.ribot.androidboilerplate.util.DialogFactory;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

import static rx.android.app.AppObservable.bindActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubscribeFragment extends Fragment {

    private static final String TAG = "SUBSCRIBE FRAGMENT";

    @Bind(R.id.tags_list)
    RecyclerView tagsList;

    DataManager dataManager;

    String[] tags;

    private CompositeSubscription mSubscriptions;
    private EasyRecyclerAdapter<String> adapter;

    public SubscribeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subscribe, container, false);
        ButterKnife.bind(this, view);
        mSubscriptions = new CompositeSubscription();
        dataManager = AndroidBoilerplateApplication.get().getDataManager();
        adapter = new EasyRecyclerAdapter<>(getActivity(), TagItemViewHolder.class);
        tagsList.setAdapter(adapter);
        tagsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSubscriptions.add(bindActivity(this.getActivity(), createTagsObservable()).subscribeOn(dataManager.getScheduler()).subscribe(onUserGet()));
        return view;
    }

    private Observable<User> createTagsObservable() {
        return dataManager.getUser(JPushInterface.getRegistrationID(this.getActivity()));
    }

    public Action1<User> onUserGet() {
        return new Action1<User>() {
            @Override
            public void call(User user) {
                tags = user.getTags();
                List<String> strings = Arrays.asList(tags);
                adapter.setItems(strings);
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
