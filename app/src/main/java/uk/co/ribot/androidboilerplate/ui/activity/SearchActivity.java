package uk.co.ribot.androidboilerplate.ui.activity;

import android.support.v4.app.Fragment;

import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.ui.fragment.SearchFragment;

public class SearchActivity extends SingleFragmentActivity {


    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected Fragment createFragment() {
        SearchFragment searchFragment = new SearchFragment();
        return searchFragment;
    }
}
