package com.cnlms.wear.gridviewpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;

import com.cnlms.wear.R;

/**
 * Created by can on 06/05/15.
 */
public class PickerActivity extends Activity {

    public static Intent newIntent(final Context context) {
        return new Intent(context, PickerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_picker);

        GridViewPager pager = (GridViewPager) findViewById(R.id.pager);

        pager.setAdapter(new GridPagerAdapter(getFragmentManager()));
    }
}
