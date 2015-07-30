package uk.co.ribot.androidboilerplate.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;

import uk.co.ribot.androidboilerplate.R;
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

        Log.w(TAG, "creating subscribe fragment");
        this.subscribeFragment = new SubscribeFragment();
        final SearchActivity context = this;

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.action_search:
                        getSupportFragmentManager().beginTransaction()
                                .replace(getFragmentMountPoint(), context.searchFragment).commit();
                        break;
                    case R.id.action_subscribe:
                        getSupportFragmentManager().beginTransaction()
                                .replace(getFragmentMountPoint(), context.subscribeFragment).commit();
                        break;
                }
                mDrawer.closeDrawers();
                return false;
            }
        });
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
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
