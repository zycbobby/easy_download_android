package uk.co.ribot.androidboilerplate.data;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.local.RuntimeData;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.data.model.Thing;
import uk.co.ribot.androidboilerplate.data.model.User;
import uk.co.ribot.androidboilerplate.data.remote.ElasticSearchHelper;
import uk.co.ribot.androidboilerplate.data.remote.RetrofitHelper;
import uk.co.ribot.androidboilerplate.data.remote.RibotsService;
import uk.co.ribot.androidboilerplate.data.remote.UserService;
import uk.co.ribot.androidboilerplate.event.UserUpdateEvent;

public class DataManager {

    private RibotsService mRibotsService;
    private UserService mUserService;
    private DatabaseHelper mDatabaseHelper;
    private PreferencesHelper mPreferencesHelper;
    private Scheduler mScheduler;
    private Bus mBus;
    private RuntimeData runtimeData;

    public DataManager(Context context, Scheduler scheduler) {
        mRibotsService = new RetrofitHelper().setupRibotsService();
        mUserService = new RetrofitHelper().setupUserService();
        mDatabaseHelper = new DatabaseHelper(context);
        mPreferencesHelper = new PreferencesHelper(context);
        mBus = new Bus();
        mScheduler = scheduler;
        runtimeData = RuntimeData.INSTANCE;
    }

    public void setRibotsService(RibotsService ribotsService) {
        mRibotsService = ribotsService;
    }

    public void setScheduler(Scheduler scheduler) {
        mScheduler = scheduler;
    }

    public DatabaseHelper getDatabaseHelper() {
        return mDatabaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Scheduler getScheduler() {
        return mScheduler;
    }

    public Bus getBus() {
        return mBus;
    }

    public RuntimeData getRuntimeData() {
        return runtimeData;
    }

    public Observable<Ribot> syncRibots() {
        return mRibotsService.getRibots()
                .concatMap(new Func1<List<Ribot>, Observable<Ribot>>() {
                    @Override
                    public Observable<Ribot> call(List<Ribot> ribots) {
                        return mDatabaseHelper.setRibots(ribots);
                    }
                });
    }

    public Observable<List<Thing>> searchThings(String keyword) {
        return ElasticSearchHelper.search(keyword);
    }

    public Observable<List<Ribot>> getRibots() {
        return mDatabaseHelper.getRibots().distinct();
    }

    /// Helper method to post events from doOnCompleted.
    private Action0 postEventAction(final Object event) {
        return new Action0() {
            @Override
            public void call() {
                postEventSafely(event);
            }
        };
    }

    // Helper method to post an event from a different thread to the main one. That's why this function is private, we need to make sure it is in the Main Looper
    private void postEventSafely(final Object event) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mBus.post(event);
            }
        });
    }

    /***
     * user part
     */
    public Observable<User> createOrUpdate(User user) {
        return mUserService.createOrUpdate(user);
    }

    /**
     * it is very important to post the event here, because if you post in {@class uk.co.ribot.androidboilerplate.ui.receiver.MyReceiver},
     * the looper is another looper
     * @return
     */
    public Action1<User> getOnUserUpdateAction() {
        return new Action1<User>() {
            @Override
            public void call(User user) {
                runtimeData.setUser(user);
                postEventSafely(new UserUpdateEvent(user));
            }
        };
    }
}
