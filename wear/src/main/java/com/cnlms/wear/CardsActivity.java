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
import android.os.Bundle;
import android.view.View;

import com.cnlms.wear.confirmations.DelayedConfirmationActivity;

/**
 * Created by can on 05/05/15.
 */
public class CardsActivity extends Activity implements View.OnClickListener {

    public static Intent newIntent(final Context context) {
        return new Intent(context, CardsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // shape aware layout i.e. BoxInLayout
        setContentView(R.layout.activity_cards);

        findViewById(R.id.btn_card_fragment).setOnClickListener(this);
        findViewById(R.id.btn_card_scrollview).setOnClickListener(this);
        findViewById(R.id.btn_card_listview).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_card_fragment:
                startActivity(CardFragmentActivity.newIntent(this));
                break;

            case R.id.btn_card_scrollview:
                startActivity(CardScrollViewActivity.newnIntent(this));
                break;

            case R.id.btn_card_listview:
//                startActivity(ListActivity.newIntent(this));
                startActivity(DelayedConfirmationActivity.newIntent(this));
                break;

            default:
                break;
        }

    }
}
