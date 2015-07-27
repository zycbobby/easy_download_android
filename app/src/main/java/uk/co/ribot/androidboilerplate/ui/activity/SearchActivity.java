package uk.co.ribot.androidboilerplate.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.ui.fragment.SearchFragment;

public class SearchActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
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
}
