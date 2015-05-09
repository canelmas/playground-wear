package com.cnlms.wear;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import org.w3c.dom.Text;

/**
 * Created by can on 09/05/15.
 */
public class WearCheckActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    static final String LOG_TAG = WearCheckActivity.class.getSimpleName();

    static final String STATE_RESOLVING_ERROR = "resolving_error";

    static final int REQUEST_RESOLVE_ERROR = 1001;

    private GoogleApiClient googleApiClient;

    private boolean resolvingError;

    private TextView txtViewLog;
    private ScrollView scrollViewLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wear_check);

        txtViewLog      = (TextView) findViewById(R.id.log);
        scrollViewLog   = (ScrollView) findViewById(R.id.log_scrollview);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        resolvingError = savedInstanceState != null &&
                savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_RESOLVING_ERROR, resolvingError);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        log("onConnected : " + bundle);
    }

    @Override
    public void onConnectionSuspended(int cause) {
        log("onConnectionSuspended: " + cause);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        log("onConnectionFailed : " + connectionResult);

        if (!resolvingError) {

            if (connectionResult.hasResolution()) {

                try {

                    resolvingError = true;

                    connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);

                } catch (IntentSender.SendIntentException e) {
                    googleApiClient.connect();
                }

            } else {

                Toast.makeText(this,
                        GooglePlayServicesUtil.getErrorString(connectionResult.getErrorCode()),
                        Toast.LENGTH_SHORT
                ).show();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_RESOLVE_ERROR) {
            resolvingError = false;

            if (resultCode == RESULT_OK) {

                if (googleApiClient.isConnecting() && !googleApiClient.isConnected()) {
                    googleApiClient.connect();
                }
            }
        }

    }

    private void log(final String text) {

        Log.d(LOG_TAG, text);

        txtViewLog.post(new Runnable() {
            @Override
            public void run() {
                txtViewLog.append(text);
                txtViewLog.append("\n");
                scrollViewLog.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}
