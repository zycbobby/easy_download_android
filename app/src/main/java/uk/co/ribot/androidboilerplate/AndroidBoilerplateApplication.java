package uk.co.ribot.androidboilerplate;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.jpush.android.api.JPushInterface;
import uk.co.ribot.androidboilerplate.data.DataManager;

import rx.schedulers.Schedulers;

public class AndroidBoilerplateApplication extends Application {

    private static AndroidBoilerplateApplication sAndroidBoilerplateApplication;
    private DataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO uncomment after replacing Crashlytics API Key in AndroidManifest.xml
        //if (!BuildConfig.DEBUG) Crashlytics.start(this);

        setApplicationStaticInstance(this);
        mDataManager = new DataManager(this, Schedulers.io());

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        JPushInterface.init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        setApplicationStaticInstance(null);
        super.onTerminate();
    }

    public static AndroidBoilerplateApplication get() {
        return sAndroidBoilerplateApplication;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    // Method necessary to bypass a findbugs warning for this specific case.
    // http://stackoverflow.com/a/21136731/1847449
    private static void setApplicationStaticInstance(AndroidBoilerplateApplication application) {
        sAndroidBoilerplateApplication = application;
    }
}

