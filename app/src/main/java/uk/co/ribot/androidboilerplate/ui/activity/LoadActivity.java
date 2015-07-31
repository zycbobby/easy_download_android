package uk.co.ribot.androidboilerplate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import uk.co.ribot.androidboilerplate.AndroidBoilerplateApplication;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.SyncService;
import uk.co.ribot.androidboilerplate.data.model.User;

public class LoadActivity extends AppCompatActivity {

    private DataManager mDataManager;
    private CompositeSubscription mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(SyncService.getStartIntent(this));
        setContentView(R.layout.activity_load);
        ButterKnife.bind(this);
        mSubscriptions = new CompositeSubscription();
        mDataManager = AndroidBoilerplateApplication.get().getDataManager();

        // mSubscriptions.add(bindActivity(this, loadUserObservable()).subscribeOn(mDataManager.getScheduler()).subscribe(onUserLoaded()));

        final Context self = this;


        // start a new handler and looper to do this job
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                String registrationID = JPushInterface.getRegistrationID(AndroidBoilerplateApplication.get());

                while (null == registrationID) {
                    try {
                        System.out.println("going to sleep");
                        Thread.sleep(500);
                        registrationID = JPushInterface.getRegistrationID(AndroidBoilerplateApplication.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // self register
                System.out.println("can be register now : " + registrationID);
                Observable<User> userObservable = selfRegisterToEasyDownloadServer(registrationID).observeOn(AndroidSchedulers.mainThread());
                userObservable.subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mDataManager.getRuntimeData().setUser(user);

                        Intent i = new Intent(self, SearchActivity.class );
                        // refer https://stackoverflow.com/questions/3473168/clear-the-entire-history-stack-and-start-a-new-activity-on-android
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        self.startActivity(i);
                    }
                });
            }
        });

    }


    private Observable<User> selfRegisterToEasyDownloadServer(String regId) {
        // get user name somewhere
        String myName = "unknow";
        String[] myTags = {"nike", "adidas"};
        User zuozuo = new User(myName, regId, myTags);
        return mDataManager.createOrUpdate(zuozuo);
    }

    @Override
    protected void onDestroy() {
        mSubscriptions.unsubscribe();
        super.onDestroy();
    }
}
