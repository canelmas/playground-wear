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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnlms.wear.R;

/**
 * Created by can on 05/05/15.
 */
public class ListItemView extends FrameLayout implements WearableListView.OnCenterProximityListener{

    public final ImageView imgView;
    public final TextView txt;

    public ListItemView(final Context context) {
        super(context);
        View.inflate(context, R.layout.list_item, this);

        imgView = (ImageView) findViewById(R.id.image);
        txt     = (TextView) findViewById(R.id.text);
    }

    @Override
    public void onCenterPosition(boolean b) {

        imgView.animate().scaleX(1f).scaleY(1f).alpha(1);
        txt.animate().scaleX(1f).scaleY(1f).alpha(1);
    }

    @Override
    public void onNonCenterPosition(boolean b) {
        imgView.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
        txt.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
    }
}
