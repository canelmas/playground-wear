package com.cnlms.wear.list;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnlms.wear.R;

import java.util.List;

/**
 * Created by can on 05/05/15.
 */
public class ListAdapter extends WearableListView.Adapter {

    private final Context context;
    private final List<ListItem> data;

    public ListAdapter(final Context context, final List<ListItem> data) {
        this.context    = context;
        this.data       = data;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WearableListView.ViewHolder(new ListItemView(context));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {

        ListItemView view = (ListItemView) holder.itemView;

        ListItem item  = data.get(position);

        final TextView title = (TextView) view.findViewById(R.id.text);
        title.setText(item.title);

        final ImageView icon = (ImageView) view.findViewById(R.id.image);
        icon.setImageResource(item.iconResId);
    }

    @Override
    public int getItemCount() {
        return null != data ? data.size() : 0;
    }
}
