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

package com.cnlms.wear.datalayerapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;

import com.cnlms.wear.R;
import com.google.android.gms.wearable.DataMap;

/**
 * Created by can on 10/05/15.
 */
public class BoardingPassActivity extends Activity {

    public static Intent newIntent(final Context context, DataMap dataMap) {

        Intent intent = new Intent(context, BoardingPassActivity.class);

        intent.putExtras(dataMap.toBundle());

        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_boarding_pass);

        //  Grid View Pager
        final GridViewPager pager = (GridViewPager) findViewById(R.id.grid);

        pager.setAdapter(new BarcodePagerAdapter(this, getFragmentManager()));

        //  Dots Indicator
        DotsPageIndicator dots = (DotsPageIndicator) findViewById(R.id.dots);
        dots.setPager(pager);


    }
}
