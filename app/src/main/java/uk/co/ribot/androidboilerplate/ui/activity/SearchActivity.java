package uk.co.ribot.androidboilerplate.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.ui.fragment.DetailFragment;
import uk.co.ribot.androidboilerplate.ui.fragment.SearchFragment;
import uk.co.ribot.androidboilerplate.ui.fragment.SubscribeFragment;

public class SearchActivity extends SingleFragmentActivity {

    public static String TAG = "SEARCH ACTIVITY";

    public static boolean isForeground = false;

    @Bind(R.id.tool_bar)
    Toolbar toolbar;

    @Bind(R.id.drawer_container)
    DrawerLayout mDrawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    @Bind(R.id.name)
    TextView nameTextView;

    Fragment searchFragment;
    Fragment subscribeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.subscribeFragment = new SubscribeFragment();

        /**
         * Replace the acitonbar with toolbar
         */
        setSupportActionBar(toolbar);

        /**
         * This part is key for showing the hummer icon
         */
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        mDrawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.action_search:
                        replaceFragment(searchFragment);
                        break;
                    case R.id.action_subscribe:
                        replaceFragment(subscribeFragment);
                        break;
                }
                mDrawer.closeDrawers();
                return false;
            }
        });

        nameTextView.setText(mDataManager.getRuntimeData().getUser().getName());
    }

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected Fragment createFragment(Bundle savedInstanceState, Intent intent) {
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
