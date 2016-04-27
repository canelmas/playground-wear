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

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;

import com.cnlms.wear.R;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by can on 09/05/15.
 */
public class DataLayerListenerService extends WearableListenerService {

    static final String LOG_TAG = DataLayerListenerService.class.getSimpleName();

    static final String KEY_PAYLOAD = "com.cnlms.wear.datalayerapi.PAYLOAD";
    static final String KEY_COUNT = "com.cnlms.wear.datalayerapi.COUNT";


    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {

        log("onDataChanged");

        for (DataEvent event : dataEvents) {

            final DataItem item = event.getDataItem();

            if (item.getUri().getPath().compareTo("/add-airlines/flight") == 0) {

                DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();

                startBoardingPassActivity(dataMap);

            }
        }
    }

    private void startBoardingPassActivity(final DataMap dataMap) {

        log("starting boarding pass");

        final Intent boardingPassIntent = new Intent(this, BoardingPassActivity.class);
        boardingPassIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        boardingPassIntent.putExtras(dataMap.toBundle());

        startActivity(boardingPassIntent);

    }

    private void raiseNotification(final DataMap dataMap) {

        final String from           = dataMap.getString("from");
        final String to             = dataMap.getString("to");
        final String gate           = dataMap.getString("gate");
        final String time           = dataMap.getString("time");
        final String number         = dataMap.getString("number");
        final String boardingpass   = dataMap.getString("boardingpass");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Your flight from " + from + " to " + to + "is close!")
                .setContentText("Please proceed to gate " + gate)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLocalOnly(true);


        ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).notify(1, builder.build());
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        log("onMessageReceived");
    }

    @Override
    public void onPeerConnected(Node peer) {
        log("onPeerConnected");

        notify("Peer Connected",peer.toString() + " is connected");
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        log("onPeerDisconnected");

        notify("Peer Disconnected", peer.toString() + " is disconnected!");

    }

    private void notify(final String title, final String content) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(content)
                .setVibrate(new long[]{0, 200})
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLocalOnly(true);

        ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).notify(1, builder.build());

    }

    private void notifyWithVoiceAction(final String remoteText) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Handheld Said")
                .setContentText(remoteText)
                .setVibrate(new long[]{0, 200})
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLocalOnly(true);

        ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).notify(1, builder.build());

        //  remote input
        final RemoteInput remoteInput = new RemoteInput.Builder("extra_voice_reply")
                .setLabel("Reply to Handheld")
                .build();

        builder.extend(
                new NotificationCompat.WearableExtender()
                        .addAction(
                                new NotificationCompat.Action.Builder(
                                        android.support.wearable.R.drawable.ic_full_sad,
                                        "Reply",
                                        PendingIntent.getActivity(this, 0, new Intent(this, ReplyActivity.class), PendingIntent.FLAG_UPDATE_CURRENT)
                                ).addRemoteInput(remoteInput).build()
                        ));

        NotificationManagerCompat.from(this).notify(1, builder.build());
    }

    private void log(String text) {
        Log.d(LOG_TAG, text);
    }

}
