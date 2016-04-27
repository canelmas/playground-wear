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

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.support.v4.app.RemoteInput;
import android.view.Gravity;
import android.widget.Toast;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;


/**
 * Created by can on 04/04/15.
 */
public final class NotificationHelper {

    private static final String EXTRA_EVENT_ID = "extra-event-id";

    private static final double LATITUDE = 44.433106;
    private static final double LONGITUDE = 26.103687;

    //  Default Values
    private static final String NOTIF_CONTENT_TITLE = "Hello Wearable!";
    private static final String NOTIF_CONTENT_TEXT = "Sample text";
    private static final int NOTIF_SMALL_ICON = R.drawable.ic_notif;

    private static final String CONTENT_BIG_TEXT = "Lorem ipsum dolor sit amet, consectetur " +
            "adipiscing elit. Nulla blandit tristique augue,";

    private static final String RECIPE_STEP_1 = "Lorem ipsum dolor sit amet, " +
            "consectetur adipiscing elit.";

    private static final String RECIPE_STEP_2 = "Lorem ipsum dolor sit amet, " +
            "consectetur adipiscing elit. Nulla blandit tristique augue, eget tempor urna " +
            "dignissim suscipit. Nunc pulvinar mattis diam. Nunc at consectetur ligula. Ut orci " +
            "leo, mollis eu lobortis sit amet, luctus eget nisi. Nulla bibendum ex eu arcu " +
            "convallis, ut pellentesque urna commodo.";

    //  Voice Input
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    //  Key to used for grouping notifications
    private static final String GROUP_KEY = "group_key_messages";

    private NotificationHelper() {}

    /**
     * Returns map action.
     *
     * @param context
     * @return          map action
     */
    public static NotificationCompat.Action mapAction(final Context context) {
        return new NotificationCompat.Action(
                android.R.drawable.ic_dialog_map,
                "Show on Map",
                mapPendingIntent(context)
        );
    }

    public static NotificationCompat.Action browseAction(final Context context) {
        return new NotificationCompat.Action(
                android.R.drawable.ic_search_category_default,
                "Show on Browser",
                browserPendingIntent(context)
        );
    }

    public static NotificationCompat.Action replyAction(final Context context) {
        return new NotificationCompat.Action(
                R.drawable.abc_ic_voice_search_api_mtrl_alpha,
                "Reply",
                PendingIntent.getActivity(context, 0, new Intent(context, ReplyActivity.class), PendingIntent.FLAG_UPDATE_CURRENT)
        );

    }

    public static NotificationCompat.Action viewAction(final Context context) {
        return new NotificationCompat.Action(
                android.R.drawable.ic_menu_slideshow,
                "Show",
                PendingIntent.getActivity(context, 0, new Intent(context, ViewDetailsActivity.class), 0)
        );
    }

    private static PendingIntent mapPendingIntent(final Context context) {

        final String urlAddress = "http://maps.google.com/maps?q="+ LATITUDE  +"," +
                LONGITUDE+"(Jorgesys @ Bucharest)&iwloc=A&hl=es";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));

        return PendingIntent.getActivity(context, 0, intent, 0);

    }

    private static PendingIntent browserPendingIntent(final Context context) {

        final Intent intent  = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.google.com/wear"));

        return PendingIntent.getActivity(context, 0, intent, 0);

    }

    public static PendingIntent viewPendingIntent(final Context context) {

        final Intent intent = new Intent(context, ViewDetailsActivity.class);

        return PendingIntent.getActivity(context,0,intent,0);

    }

    public static void raiseNotification(final Context context) {
        raiseNotification(context, defaultBuilder(context));

    }

    public static void raiseNotification(final Context context, final NotificationCompat.Action... actions) {

        final NotificationCompat.Builder builder = defaultBuilder(context);

        for (NotificationCompat.Action action : actions) {
            builder.addAction(action);
        }

        raiseNotification(
                context,
                builder
        );

    }

    public static void raiseNotificationWithWearableAction(final Context context,
                                                           final NotificationCompat.Action action1,
                                                           final NotificationCompat.Action action2,
                                                           final NotificationCompat.Action wearableAction) {

        final NotificationCompat.Builder builder = defaultBuilder(context);

        builder.addAction(action1);
        builder.addAction(action2);

        // won't be displayed on phone; only on wearable
        builder.extend(new WearableExtender().addAction(wearableAction));

        raiseNotification(
                context,
                builder
        );

    }

    public static void raiseNotificationWithBigStyle(final Context context) {

        final NotificationCompat.Builder builder = defaultBuilder(context);

        builder.setLargeIcon(
                BitmapFactory.decodeResource(
                        context.getResources(),
                        android.R.drawable.sym_action_chat
                ))
                .setContentIntent(viewPendingIntent(context))
                .addAction(replyAction(context));

        //  Big Style
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText(CONTENT_BIG_TEXT);

        builder.setStyle(style);

        raiseNotification(context, builder);
    }

    public static void raiseNotificationWithWearableExtensions(final Context context) {

        //  Wearable Extensions
        final NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender();

        wearableExtender.addAction(mapAction(context));
        wearableExtender.setHintHideIcon(true);
        wearableExtender.setBackground(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.wear)
        );

        wearableExtender.setContentIcon(R.drawable.gplus);
        wearableExtender.setContentIconGravity(Gravity.END);

        final NotificationCompat.Builder builder = defaultBuilder(context);

        builder.setContentIntent(viewPendingIntent(context))
                .addAction(replyAction(context))
                .extend(wearableExtender);

        //  Big Style
        /*NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText(CONTENT_BIG_TEXT);

        builder.setStyle(style);*/

        raiseNotification(context, builder);

    }

    public static void raiseNotificationWithVoiceInput(final Context context) {

        //  remote input
        final RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(context.getString(R.string.reply_label))
                .setChoices(context.getResources().getStringArray(R.array.reply_choices))
                .build();

        final NotificationCompat.Builder builder = defaultBuilder(context);

        builder.extend(
                new NotificationCompat.WearableExtender()
                        .addAction(
                                new NotificationCompat.Action.Builder(
                                        R.drawable.abc_ic_voice_search_api_mtrl_alpha,
                                        "Reply",
                                        PendingIntent.getActivity(context, 0, new Intent(context,
                                                ReplyActivity.class), PendingIntent.FLAG_UPDATE_CURRENT)
                                ).addRemoteInput(remoteInput).build()
                        ));

        raiseNotification(context, builder);

    }

    public static void raiseNotificationWithMultiPages(final Context context) {

        final NotificationCompat.Builder builder = defaultBuilder(context);

        builder.setContentIntent(viewPendingIntent(context))
                .addAction(mapAction(context))
                .addAction(browseAction(context));

        //  Second and Third Page Notification Style
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.setSummaryText("Summary Text");
        style.bigText(CONTENT_BIG_TEXT);
        style.setBigContentTitle("Biiig Content Title");

        //  second and third pages
        final Notification secondPage   = new NotificationCompat.Builder(context).setStyle(style).build();
        final Notification thirdPage    = new NotificationCompat.Builder(context).setStyle(style).build();

        builder.extend(
                new NotificationCompat.WearableExtender()
                        .setContentIcon(R.drawable.gplus)
                        .addPage(secondPage)
                        .addPage(thirdPage)
        ).build();

        raiseNotification(context, builder);

        // todo : food recipe pages on wearable, different screen layouts on phone

    }

    public static void raiseNotificationStack(final Context context) {

        raiseNotification(
                context,
                defaultBuilder(context)
                        .setContentTitle("New Message from Can Elmas")
                        .setContentText("Did you listen the Songbook album by Chris Cornell?")
                        .setGroup(GROUP_KEY),
                1
        );

        raiseNotification(
                context,
                defaultBuilder(context)
                        .setContentTitle("New Message from Chris Cornell")
                        .setContentText("Did you listen my album?")
                        .setGroup(GROUP_KEY),
                2
        );

    }

    public static void raiseNotificationStackWithSummary(final Context context) {

        raiseNotification(
                context,
                defaultBuilder(context)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                                android.R.drawable.ic_notification_clear_all))
                        .setStyle(
                                new NotificationCompat.InboxStyle()
                                        .addLine("Can Elmas     Did you...")
                                        .addLine("Chris Cornell     Did you...")
                                        .setBigContentTitle("2 New Messages")
                                        .setSummaryText("Summary")
                        )
                        .setGroup(GROUP_KEY)
                        .setGroupSummary(true)
        );
    }


    public static void raiseNotificationAndDetectDismiss(final Context context) {

        raiseNotification(
                context,
                defaultBuilder(context).setDeleteIntent(
                        PendingIntent.getBroadcast(context, 0, new Intent(context, NotificationDismissedReceiver.class), 0)
                )
        );

    }

    private static void raiseNotification(final Context context, final NotificationCompat.Builder builder) {
        raiseNotification(context, builder, 1);
    }

    private static void raiseNotification(final Context context,
                                          final NotificationCompat.Builder builder,
                                          final int notificationId) {

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =  NotificationManagerCompat.from(context);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, builder.build());

    }

    public static void raiseSampleNotification(final Context context) {

        final int notificationId = 1;

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle("Hello Wearable!")
                .setContentText("Sample text");

        NotificationManagerCompat.from(context).notify(notificationId, builder.build());

    }

    public static void raiseAddToCartNotification(final Context context) {

        final int notificationId = 1;

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle("Hello Wearable!")
                .setContentText("Sample text")
                .setContentIntent(browserPendingIntent(context))
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.drawable.notif_bg2
                ))
                .addAction(R.drawable.ic_add_to_cart, "Add to Cart", addToCartIntent(context));

        NotificationManagerCompat.from(context).notify(notificationId, builder.build());

    }

    public static void raiseAddToCartNotificationWithNearestShopWearableAction(final Context context) {

        final int notificationId = 1;

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle("HoverBoard is on sale!")
                .setContentText("Check it out!")
                .setContentIntent(itemDetailsIntent(context))
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.drawable.bg_hoverboard2
                ));

        // Handheld only actions
        builder.addAction(R.drawable.ic_add_to_cart, "Add to Cart", addToCartIntent(context));

        //  Wearable-only actions
        final NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender();

        wearableExtender.addAction(
                new NotificationCompat.Action(
                        R.drawable.ic_navigation,
                        "Nearest Shop",
                        navigationIntent(context))
        );

        builder.extend(wearableExtender);

        NotificationManagerCompat.from(context).notify(notificationId, builder.build());

    }

    public static void raiseNotificaiontAndGetReplyWithVoiceInput(final Context context) {

        final RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel("Rate the session")
                .setChoices(context.getResources().getStringArray(R.array.reply_choices))
                .build();

        final int notificationId = 1;

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle("Was the session helpful?")
                .setContentIntent(openSessionDetailsIntent(context))
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.drawable.bg_gdg
                ));

        builder.extend(
                new NotificationCompat.WearableExtender()
                        .addAction(
                                new NotificationCompat.Action.Builder(
                                        R.drawable.abc_ic_voice_search_api_mtrl_alpha,
                                        "Reply",
                                        openSessionDetailsIntent(context)
                                ).addRemoteInput(remoteInput).build()
                        )
        );

        NotificationManagerCompat.from(context).notify(notificationId, builder.build());

    }

    public static void raiseNotificationWithMultiRecipes(final Context context) {

        final int notificationId = 1;

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle("New Pancake Recipe!")
                .setContentText("Start making now!")
                .setContentIntent(openRecipeIntent(context))
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.drawable.bg_pancakes1
                ));


        final Notification secondPage = new NotificationCompat.Builder(context)
                .setContentTitle("Step 1")
                .setContentText(RECIPE_STEP_1)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.drawable.bg_pancakes2
                )).build();

        final Notification thirdPage = new NotificationCompat.Builder(context)
                .setContentTitle("Step 2")
                .setContentText(RECIPE_STEP_2)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        R.drawable.bg_pancakes3
                )).build();

        builder.extend(
                new NotificationCompat.WearableExtender()
                        .addPage(secondPage)
                        .addPage(thirdPage)
        ).build();

        NotificationManagerCompat.from(context).notify(notificationId, builder.build());

    }

    private static PendingIntent openRecipeIntent(final Context context) {
        return mapPendingIntent(context);
    }

    private static PendingIntent addToCartIntent(final Context context) {
        return mapPendingIntent(context);
    }

    private static PendingIntent itemDetailsIntent(final Context context) {
        return mapPendingIntent(context);
    }

    private static PendingIntent openSessionDetailsIntent(final Context context) {
        return mapPendingIntent(context);
    }

    private static PendingIntent navigationIntent(final Context context) {

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:///?q=48.649469,-2.02579&mode=w"));

        return PendingIntent.getActivity(context, 0, intent, 0);

    }

    private static NotificationCompat.Builder defaultBuilder(final Context context) {

        return new NotificationCompat.Builder(context)
                .setSmallIcon(NOTIF_SMALL_ICON)
                .setContentTitle(NOTIF_CONTENT_TITLE)
                .setContentText(NOTIF_CONTENT_TEXT);

    }

    public static class NotificationDismissedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"Notification Dismissed", Toast.LENGTH_SHORT).show();
        }
    }


}
