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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.widget.Toast;

import com.cnlms.wear.R;
import com.cnlms.wear.gridviewpager.GridViewPagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 05/05/15.
 */
public class ListActivity extends Activity {

    public static Intent newIntent(final Context context) {
        return new Intent(context, ListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        WearableListView listView = (WearableListView) findViewById(R.id.list);

        final List<ListItem> data = new ArrayList<>();
        data.add(new ListItem(R.drawable.ic_full_sad, "Motorola 360"));
        data.add(new ListItem(R.drawable.ic_full_cancel, "LG G Watch"));
        data.add(new ListItem(R.drawable.ic_full_sad, "Asus ZenWatch"));
        data.add(new ListItem(R.drawable.ic_full_cancel, "Samsung Gear Live"));
        data.add(new ListItem(R.drawable.ic_full_sad, "Sony Smartwatch 3"));
        data.add(new ListItem(R.drawable.ic_full_cancel, "LG G Watch R"));
        data.add(new ListItem(R.drawable.ic_full_sad, "LG Watch Urbane"));
        data.add(new ListItem(R.drawable.ic_plusone_tall_off_client, "2D Picker"));

        ListAdapter adapter = new ListAdapter(this, data);

        listView.setAdapter(adapter);
        listView.setClickListener(new WearableListView.ClickListener() {

            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {

                if (viewHolder.getPosition() != data.size() -1) {
                    Toast.makeText(ListActivity.this, ((ListItemView) viewHolder.itemView).txt.getText(), Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(GridViewPagerActivity.newIntent(ListActivity.this));
                }

            }

            @Override
            public void onTopEmptyRegionClick() {
                Toast.makeText(ListActivity.this, "onTopEmptyRegionClicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
