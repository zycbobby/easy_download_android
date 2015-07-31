package uk.co.ribot.androidboilerplate.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.AndroidBoilerplateApplication;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.DataManager;

/**
 * Created by zuo on 15-7-21.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    @Bind(R.id.tool_bar)
    Toolbar toolbar;

    @Bind(R.id.drawer_container)
    DrawerLayout mDrawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    @Bind(R.id.name)
    TextView nameTextView;


    DataManager mDataManager;

    public abstract int getLayout();

    /**
     * this method can be overriden
     *
     * @return fragment container
     */
    public int getFragmentMountPoint() {
        return R.id.fragmentContainer;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayout());
        ButterKnife.bind(this);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(this.getFragmentMountPoint());
        if (fragment == null) {
            fragment = this.createFragment();
            fm.beginTransaction().add(this.getFragmentMountPoint(), fragment).commit();
        }

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
        mDataManager = AndroidBoilerplateApplication.get().getDataManager();
    }

    protected abstract Fragment createFragment();

    public void replaceFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction()
                .replace(getFragmentMountPoint(), f).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
