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

@LayoutId(R.layout.item_tag)
public class TagItemViewHolder extends ItemViewHolder<String> {


    @ViewId(R.id.tag_name)
    TextView mName;

    public TagItemViewHolder(View view) {
        super(view);
    }

    @Override
    public void onSetValues(String tag, PositionInfo positionInfo) {
        mName.setText(tag);
    }
}