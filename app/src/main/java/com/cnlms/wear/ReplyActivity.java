package com.cnlms.wear;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by can on 07/04/15.
 */
public final class ReplyActivity extends AppCompatActivity {

    private static final String LOG_TAG = ReplyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reply);

        if (null != getIntent()) {
            Bundle remoteInput = RemoteInput.getResultsFromIntent(getIntent());

            if (null != remoteInput) {
                Toast.makeText(this, remoteInput.getCharSequence(NotificationHelper.EXTRA_VOICE_REPLY), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(LOG_TAG, "onNewIntent");
    }
}
