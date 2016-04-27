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

package com.cnlms.wear.gridviewpager;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.view.Gravity;

import com.cnlms.wear.R;

/**
 * Created by can on 06/05/15.
 */
public class GridPagerAdapter extends FragmentGridPagerAdapter {

    static final int[] IMAGES = new int[]{
            R.drawable.debug_background_1,
            R.drawable.debug_background_2,
            R.drawable.debug_background_3,
            R.drawable.debug_background_4,
    };

    static class Page {
        String title;
        String text;
        int iconResId;

        Page(String title, String text, int iconResId) {
            this.title = title;
            this.text = text;
            this.iconResId = iconResId;
        }
    }

    static final Page[][] PAGES = new Page[][]{
            new Page[]{
                    new Page("1",
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur elementum mi orci.",
                            R.drawable.ic_full_cancel),
                    new Page("1.1",
                            "1.1 Details",
                            R.drawable.ic_full_cancel)
            },
            new Page[]{
                    new Page("2",
                            "2 Details",
                            R.drawable.ic_full_cancel),
                    new Page("2.1",
                            "2.1 Details",
                            R.drawable.ic_full_cancel)
            },
            new Page[]{
                    new Page("3",
                            "3 Details",
                            R.drawable.ic_full_cancel),
                    new Page("3.1",
                            "3.1 Details",
                            R.drawable.ic_full_cancel)
            },
            new Page[]{
                    new Page("4",
                            "4 Details",
                            R.drawable.ic_full_cancel),
                    new Page("4.1",
                            "4.1 Details",
                            R.drawable.ic_full_cancel)
            }
    };

    private final Context context;

    public GridPagerAdapter(final Context context, final FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getFragment(int row, int col) {

        Page page = PAGES[row][col];

        CardFragment cardFragment = CardFragment.create(
                page.title,
                page.text,
                page.iconResId
        );

        cardFragment.setCardGravity(Gravity.BOTTOM);
        cardFragment.setExpansionEnabled(true);
        cardFragment.setExpansionDirection(CardFragment.EXPAND_DOWN);
        cardFragment.setExpansionFactor(10);

        return cardFragment;
    }

    @Override
    public int getRowCount() {
        return PAGES.length;
    }

    @Override
    public int getColumnCount(int rowNum) {
        return PAGES[rowNum].length;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Drawable getBackgroundForRow(int row) {
        return context.getResources().getDrawable(IMAGES[row % IMAGES.length], null);
    }

}
