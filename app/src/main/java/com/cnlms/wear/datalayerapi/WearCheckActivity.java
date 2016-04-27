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

package com.cnlms.wear.datalayerapi;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cnlms.wear.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by can on 09/05/15.
 */
public class WearCheckActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        DataApi.DataListener {

    static final String LOG_TAG = WearCheckActivity.class.getSimpleName();

    static final String STATE_RESOLVING_ERROR = "resolving_error";

    static final int REQUEST_RESOLVE_ERROR = 1001;

    private GoogleApiClient googleApiClient;

    private boolean resolvingError;

    //  Log Section
    private TextView txtViewLog;
    private ScrollView scrollViewLog;

    //  Input & Response
    private TextView txtViewResponse;
    private EditText editTextInput;

    // Path
    static final String PATH_SAY = "/say";

    // Keys
    static final String KEY_PAYLOAD = "com.cnlms.wear.datalayerapi.PAYLOAD";
    static final String KEY_COUNT = "com.cnlms.wear.datalayerapi.COUNT";

    private int msgCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wear_check);

        txtViewLog      = (TextView) findViewById(R.id.log);
        scrollViewLog   = (ScrollView) findViewById(R.id.log_scrollview);

        txtViewResponse = (TextView) findViewById(R.id.response);
        editTextInput   = (EditText) findViewById(R.id.input);
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayToWear(editTextInput.getText().toString());
            }
        });

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
        log("onStart");
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
        log("onStop");
        Wearable.DataApi.removeListener(googleApiClient, this);
    }


    @Override
    public void onConnected(Bundle bundle) {
        log("onConnected : " + bundle);

        // listen for data changes
        Wearable.DataApi.addListener(googleApiClient, this);

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        log("onDataChanged");

        for (DataEvent event : dataEventBuffer) {

            if (event.getType() == DataEvent.TYPE_CHANGED) {

                log("Data Item changed " + DataMapItem.fromDataItem(event.getDataItem()).getDataMap().toString());



            } else if (event.getType() == DataEvent.TYPE_DELETED) {

                log("Data Item deleted");
            }

        }
    }

    private void sayToWear(final String text) {

        log("saying [" + text + "] to wear");

        final PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(PATH_SAY);
        putDataMapRequest.getDataMap().putString(KEY_PAYLOAD, text);
        putDataMapRequest.getDataMap().putInt(KEY_COUNT, ++msgCount);

        final PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(googleApiClient, putDataMapRequest.asPutDataRequest());

        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(DataApi.DataItemResult dataItemResult) {

                if (dataItemResult.getStatus().isSuccess()) {
                    log("[sayToWear] Data Item synced: " + dataItemResult.getDataItem().getUri());
                } else {
                    log("ERROR : failed to putDataItem, status code :" + dataItemResult.getStatus().getStatusCode());
                }

            }
        });

    }

    /*private void syncWithDataItem() {

        final PutDataRequest putDataRequest = PutDataRequest.create("/hello");
        putDataRequest.setData("Warm hello from handheld".getBytes());

        final PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(googleApiClient, putDataRequest);

        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(DataApi.DataItemResult dataItemResult) {

                if (dataItemResult.getStatus().isSuccess()) {
                    log("[syncWithDataItem] Data Item synced: " + dataItemResult.getDataItem().getUri());
                }
            }
        });

    }*/

    /*private void synchWithDataMap() {

        final PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/count");
        putDataMapRequest.getDataMap().putInt("COUNT_KEY", 1);

        final PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(googleApiClient, putDataMapRequest.asPutDataRequest());

        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(DataApi.DataItemResult dataItemResult) {

                if (dataItemResult.getStatus().isSuccess()) {
                    log("[synchWithDataMap] Data Item synced: " + dataItemResult.getDataItem().getUri());
                }

            }
        });
    }*/

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
