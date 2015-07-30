package uk.co.ribot.androidboilerplate.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.ui.adapter.MyAdapter;

/**
 * Created by zuo on 15-7-21.
 */
public abstract class SingleFragmentActivity extends ActionBarActivity {

    private Toolbar toolbar;

    String TITLES[] = {"Home","Events","Mail","Shop","Travel"};
    int ICONS[] = {R.drawable.abc_ic_clear_mtrl_alpha
            ,R.drawable.abc_ic_clear_mtrl_alpha
            ,R.drawable.abc_ic_clear_mtrl_alpha
            ,R.drawable.abc_ic_clear_mtrl_alpha
            ,R.drawable.abc_ic_clear_mtrl_alpha};

    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    String NAME = "Akash Bangad";
    String EMAIL = "akash.bangad@android4devs.com";
    int PROFILE = R.drawable.abc_ic_clear_mtrl_alpha;

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;

    public abstract int getLayout();

    /**
     * this method can be overriden
     * @return fragment container
     */
    public int getFragmentMountPoint() {
        return R.id.fragmentContainer;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayout());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(this.getFragmentMountPoint());
        if (fragment == null) {
            fragment = this.createFragment();
            fm.beginTransaction().add(this.getFragmentMountPoint(), fragment).commit();
        }

        /**
         * Replace the acitonbar with toolbar
         */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.nav_menu); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new MyAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){

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
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();


    }

    protected abstract Fragment createFragment();
}
