package com.cnlms.wear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.wearable.view.CardFrame;
import android.support.wearable.view.CardScrollView;
import android.view.Gravity;

/**
 * Created by can on 05/05/15.
 */
public class CardScrollViewActivity extends Activity {

    public static Intent newnIntent(final Context context) {
        return new Intent(context, CardScrollViewActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_scroll_view);

        CardScrollView cardScrollView = (CardScrollView)findViewById(R.id.card_scroll_view);

        cardScrollView.setCardGravity(Gravity.BOTTOM);
        cardScrollView.setExpansionEnabled(true);
        cardScrollView.setExpansionDirection(CardFrame.EXPAND_UP);

    }
}
