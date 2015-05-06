package com.cnlms.wear.gridviewpager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.view.Gravity;

import com.cnlms.wear.R;

/**
 * Created by can on 06/05/15.
 */
public class GridPagerAdapter extends FragmentGridPagerAdapter {

    static final int[] IMAGES = new int[]{
            R.drawable.go_to_phone_00198,
            R.drawable.card_background,
            R.drawable.close_button
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
                            "1 Details",
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
            }
    };

    public GridPagerAdapter(FragmentManager fm) {
        super(fm);
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
}
