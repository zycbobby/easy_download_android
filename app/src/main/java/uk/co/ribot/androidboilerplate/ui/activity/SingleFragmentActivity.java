package uk.co.ribot.androidboilerplate.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import uk.co.ribot.androidboilerplate.R;

/**
 * Created by zuo on 15-7-21.
 */
public abstract class SingleFragmentActivity extends ActionBarActivity {

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
    }

    protected abstract Fragment createFragment();
}
