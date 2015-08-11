package uk.co.ribot.androidboilerplate.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import butterknife.Bind;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.ui.fragment.DetailFragment;

public class DetailActivity extends SingleFragmentActivity {

    public static String TAG = "DETAIL ACTIVITY";

    @Bind(R.id.tool_bar)
    Toolbar toolbar;

    Fragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected Fragment createFragment(Bundle savedInstanceState, Intent intent) {
        if (this.detailFragment == null) {
            this.detailFragment = new DetailFragment();
        }
        return detailFragment;
    }
}