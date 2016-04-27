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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public final class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_create_notif).setOnClickListener(this);
        findViewById(R.id.btn_create_notif_with_action).setOnClickListener(this);
        findViewById(R.id.btn_create_notif_with_multi_actions).setOnClickListener(this);
        findViewById(R.id.btn_create_notif_big_style).setOnClickListener(this);
        findViewById(R.id.btn_create_notif_wear_action).setOnClickListener(this);
        findViewById(R.id.btn_create_notif_with_wear_extensions).setOnClickListener(this);
        findViewById(R.id.btn_create_notif_voice_input).setOnClickListener(this);
        findViewById(R.id.btn_create_notif_multi_pages).setOnClickListener(this);
        findViewById(R.id.btn_create_notif_group).setOnClickListener(this);
        findViewById(R.id.btn_create_notif_group_with_summary).setOnClickListener(this);
        findViewById(R.id.btn_create_notif_and_detect_dismiss).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_create_notif:
//                NotificationHelper.raiseNotification(this);
//                NotificationHelper.hede3(this);
                NotificationHelper.hedePages(this);
                break;

            case R.id.btn_create_notif_with_action:
                NotificationHelper.raiseNotification(this, NotificationHelper.mapAction(this));
                break;

            case R.id.btn_create_notif_with_multi_actions:
                NotificationHelper.raiseNotification(this,
                        NotificationHelper.browseAction(this),
                        NotificationHelper.mapAction(this)
                );
                break;

            case R.id.btn_create_notif_big_style:
                NotificationHelper.raiseNotificationWithBigStyle(this);
                break;

            case R.id.btn_create_notif_wear_action:
                NotificationHelper.raiseNotificationWithWearableAction(this,
                        NotificationHelper.browseAction(this),
                        NotificationHelper.mapAction(this),
                        NotificationHelper.replyAction(this)
                );
                break;

            case R.id.btn_create_notif_with_wear_extensions:
                NotificationHelper.raiseNotificationWithWearableExtensions(this);
                break;

            case R.id.btn_create_notif_voice_input:
                NotificationHelper.raiseNotificationWithVoiceInput(this);
                break;

            case R.id.btn_create_notif_multi_pages:
                NotificationHelper.raiseNotificationWithMultiPages(this);
                break;

            case R.id.btn_create_notif_group:
                NotificationHelper.raiseNotificationStack(this);
                break;

            case R.id.btn_create_notif_group_with_summary:
                NotificationHelper.raiseNotificationStackWithSummary(this);
                break;

            case R.id.btn_create_notif_and_detect_dismiss:
                NotificationHelper.raiseNotificationAndDetectDismiss(this);
                break;

            default:
                break;
        }

    }
}
