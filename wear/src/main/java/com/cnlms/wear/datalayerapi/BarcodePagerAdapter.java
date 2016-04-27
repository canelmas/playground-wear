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

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.wearable.view.FragmentGridPagerAdapter;

import com.cnlms.wear.R;

/**
 * Created by can on 10/05/15.
 */
public class BarcodePagerAdapter extends FragmentGridPagerAdapter {

    private static final int ROW_COUNT = 1;
    private static final int COLUMN_COUNT = 2;

    private final Context context;

    static final int[] IMAGES = new int[]{
            R.drawable.debug_background_1,
            R.drawable.debug_background_2
    };

    public BarcodePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getFragment(int row, int col) {

        if (col == 0) {

            //  flight info
            return FlightInfoFragment.newInstance();

        }

        // boarding pass
        return BoardingPassFragment.newInstance();

    }

    @Override
    public int getRowCount() {
        return ROW_COUNT;
    }

    @Override
    public int getColumnCount(int rowNum) {
        return COLUMN_COUNT;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        return context.getResources().getDrawable(IMAGES[row % IMAGES.length], null);
    }
}
