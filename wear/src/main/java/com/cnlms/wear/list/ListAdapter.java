/*
 * Copyright (C) 2015 Can Elmas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
