package uk.co.ribot.androidboilerplate.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.data.model.Thing;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.item_thing)
public class ThingItemViewHolder extends ItemViewHolder<Thing> {

    @ViewId(R.id.view_hex_color)
    View mHexColorBox;

    @ViewId(R.id.text_name)
    TextView mName;

    @ViewId(R.id.text_role)
    TextView mRole;

    public ThingItemViewHolder(View view) {
        super(view);
    }

    @Override
    public void onSetValues(Thing thing, PositionInfo positionInfo) {
        mHexColorBox.setBackgroundColor(Color.BLUE);
        mName.setText(thing.getTitle());
    }

}