package com.cnlms.wear;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
