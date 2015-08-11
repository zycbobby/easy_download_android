package uk.co.ribot.androidboilerplate.ui.adapter;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.item_tag)
public class TagItemViewHolder extends ItemViewHolder<String> {

    @ViewId(R.id.tag_name)
    TextView mName;

    @ViewId(R.id.deleteBtn)
    ImageButton deleteBtn;

    SwipeLayout swipeLayout;

    public TagItemViewHolder(View view) {
        super(view);
        this.swipeLayout = (SwipeLayout) view;
        this.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TagItemViewHolder", "deleted " + mName.getText());
            }
        });
    }

    @Override
    public void onSetValues(String tag, PositionInfo positionInfo) {
        mName.setText(tag);
    }
}