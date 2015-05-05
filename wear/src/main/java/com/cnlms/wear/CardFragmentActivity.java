package com.cnlms.wear;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;

/**
 * Created by can on 05/05/15.
 */
public class CardFragmentActivity extends Activity {

    public static Intent newIntent(final Context context) {
        return new Intent(context, CardFragmentActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_fragment);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        CardFragment cardFragment = CardFragment.create(
                "Card Title",
                "Card Content",
                R.drawable.common_ic_googleplayservices
        );

        ft.add(R.id.frame_layout, cardFragment);
        ft.commit();
    }
}
