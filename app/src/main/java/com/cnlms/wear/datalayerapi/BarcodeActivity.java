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
import android.widget.Button;
import android.widget.Toast;

import com.cnlms.wear.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by can on 10/05/15.
 */
public class BarcodeActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        DataApi.DataListener,
        NodeApi.NodeListener {

    static final String LOG_TAG = BarcodeActivity.class.getSimpleName();

    static final String STATE_RESOLVING_ERROR = "resolving_error";

    static final int REQUEST_RESOLVE_ERROR = 1001;

    private GoogleApiClient googleApiClient;

    private boolean resolvingError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_barcode);

        Button button = (Button) findViewById(R.id.btn_barcode);
        button.setEnabled(false);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFlightInfo();
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

    private void sendFlightInfo() {

        final PutDataMapRequest request = PutDataMapRequest.create("/add-airlines/flight");

        request.getDataMap().putString("from",      "SAW");
        request.getDataMap().putString("to",        "ESB");
        request.getDataMap().putString("gate",      "G22");
        request.getDataMap().putString("barcode", "test");


        Wearable.DataApi.putDataItem(googleApiClient, request.asPutDataRequest())
                .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(DataApi.DataItemResult dataItemResult) {

                        if (!dataItemResult.getStatus().isSuccess()) {
                            // handle error
                        }
                    }
                });

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
        Wearable.NodeApi.removeListener(googleApiClient, this);
    }


    @Override
    public void onConnected(Bundle bundle) {
        log("onConnected : " + bundle);

        // listen for data changes
        Wearable.DataApi.addListener(googleApiClient, this);
        Wearable.NodeApi.addListener(googleApiClient, this);

        findViewById(R.id.btn_barcode).setEnabled(true);

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

    }

    @Override
    public void onPeerConnected(Node node) {
        log("onPeerConnected");
    }

    @Override
    public void onPeerDisconnected(Node node) {
        log("onPeerDisconnected");
    }

}
