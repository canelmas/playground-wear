package com.cnlms.wear;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public final class MainActivity extends ActionBarActivity implements View.OnClickListener {

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
                NotificationHelper.raiseNotification(this);
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
                NotificationHelper.raiseNotificationWithWearableExntensions(this);
                break;

            default:
                break;
        }

    }
}
