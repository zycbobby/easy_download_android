package uk.co.ribot.androidboilerplate.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;

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

    public abstract int getLayout();

    /**
     * this method can be overriden
     *
     * @return fragment container
     */
    public int getFragmentMountPoint() {
        return R.id.fragmentContainer;
    }

    ;


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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                System.out.println(menuItem.toString());
                return false;
            }
        });

        /**
         * This part is key for showing the hummer icon
         */
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // Drawer Toggle Object Made
        mDrawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();

    }

    protected abstract Fragment createFragment();
}
