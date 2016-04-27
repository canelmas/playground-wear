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

package com.cnlms.wear.confirmations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DelayedConfirmationView;
import android.view.View;
import android.widget.Toast;

import com.cnlms.wear.R;

/**
 * Created by can on 07/05/15.
 */
public class DelayedConfirmationActivity extends Activity implements DelayedConfirmationView.DelayedConfirmationListener {

    private DelayedConfirmationView delayedView;

    public static Intent newIntent(final Context context) {
        return new Intent(context, DelayedConfirmationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_delayed_confirmation);

        delayedView = (DelayedConfirmationView) findViewById(R.id.delayed_confirm);

        delayedView.setListener(this);

        findViewById(R.id.btn_do_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delayedView.setTotalTimeMs(5000);
                delayedView.start();
            }
        });

        findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent = new Intent(DelayedConfirmationActivity.this, ConfirmationActivity.class);
                intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION);
                intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Done!");

                startActivity(intent);
            }
        });
    }

    @Override
    public void onTimerFinished(View view) {
        Toast.makeText(this, "execute action!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimerSelected(View view) {
        Toast.makeText(this, "aborted!", Toast.LENGTH_SHORT).show();
    }
}
