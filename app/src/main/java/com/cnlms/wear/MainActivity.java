package com.cnlms.wear;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_create_notif).setOnClickListener(this);
        findViewById(R.id.btn_create_notif_with_action).setOnClickListener(this);
        findViewById(R.id.btn_create_notif_with_multi_actions).setOnClickListener(this);
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
                NotificationHelper.raiseNotification(this, NotificationHelper.browseAction(this), NotificationHelper.mapAction(this));
                break;

            default:
                break;
        }

    }
}
