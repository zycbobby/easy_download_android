package uk.co.ribot.androidboilerplate.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Thing;
import uk.co.ribot.androidboilerplate.ui.activity.DetailActivity;
import uk.co.ribot.androidboilerplate.ui.fragment.DetailFragment;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.item_thing)
public class ThingItemViewHolder extends ItemViewHolder<Thing> {

    @ViewId(R.id.view_hex_color)
    ImageView imageView;

    @ViewId(R.id.text_name)
    TextView mName;

    ImageLoader imageLoader;

    Thing thing;

    public ThingItemViewHolder(View view) {
        super(view);
        imageLoader = ImageLoader.getInstance(); // Get singleton instance
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ThingItemViewHolder", thing + " was clicked");
                Intent i = new Intent(getContext(), DetailActivity.class);
                i.putExtra(DetailFragment.EXTRA_URL, thing.getUrl());
                getContext().startActivity(i);
            }
        });
    }

    @Override
    public void onSetValues(final Thing thing, PositionInfo positionInfo) {
        imageLoader.displayImage(thing.getImages()[0], imageView);
        mName.setText(thing.getTitle());
        this.thing = thing;
    }
}