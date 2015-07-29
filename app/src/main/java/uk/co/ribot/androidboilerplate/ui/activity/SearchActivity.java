package uk.co.ribot.androidboilerplate.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.ui.fragment.SearchFragment;

public class SearchActivity extends SingleFragmentActivity {

    public static boolean isForeground = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected Fragment createFragment() {
        SearchFragment searchFragment = new SearchFragment();
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
