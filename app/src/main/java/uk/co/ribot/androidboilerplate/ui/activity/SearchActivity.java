package uk.co.ribot.androidboilerplate.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;

import uk.co.ribot.androidboilerplate.AndroidBoilerplateApplication;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.event.UserUpdateEvent;
import uk.co.ribot.androidboilerplate.ui.fragment.SearchFragment;
import uk.co.ribot.androidboilerplate.ui.fragment.SubscribeFragment;

public class SearchActivity extends SingleFragmentActivity {

    public static String TAG = "SEARCH ACTIVITY";

    public static boolean isForeground = false;

    Fragment searchFragment;
    Fragment subscribeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.subscribeFragment = new SubscribeFragment();
        final SearchActivity context = this;

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.action_search:
                        replaceFragment(context.searchFragment);
                        break;
                    case R.id.action_subscribe:
                        replaceFragment(context.subscribeFragment);
                        break;
                }
                mDrawer.closeDrawers();
                return false;
            }
        });

        mDataManager = AndroidBoilerplateApplication.get().getDataManager();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected Fragment createFragment() {
        if (this.searchFragment == null) {
            this.searchFragment = new SearchFragment();
        }
        return searchFragment;
    }

    @Override
    protected void onResume() {
        isForeground = true;
        mDataManager.getBus().register(this);
        System.out.println("register");
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        mDataManager.getBus().unregister(this);
        System.out.println("unregister");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe
    public void onRuntimeUserFix(UserUpdateEvent userUpdateEvent) {
        String name = userUpdateEvent.getUser().getName() == null? userUpdateEvent.getUser().getRegisterId():userUpdateEvent.getUser().getName();
        nameTextView.setText(name);
    }
}
