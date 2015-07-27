package uk.co.ribot.androidboilerplate.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Thing;
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

    @ViewId(R.id.text_role)
    TextView mRole;

    ImageLoader imageLoader;

    public ThingItemViewHolder(View view) {
        super(view);
        imageLoader = ImageLoader.getInstance(); // Get singleton instance
    }

    @Override
    public void onSetValues(Thing thing, PositionInfo positionInfo) {
        imageLoader.displayImage(thing.getImages()[0], imageView);
        mName.setText(thing.getTitle());
    }

}