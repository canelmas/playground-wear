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
