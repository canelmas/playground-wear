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
public class GridViewPagerActivity extends Activity {

    public static Intent newIntent(final Context context) {
        return new Intent(context, GridViewPagerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_picker);

        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);

        pager.setAdapter(new GridPagerAdapter(this, getFragmentManager()));
    }
}
