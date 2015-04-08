package com.cnlms.wear;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.view.Gravity;

/**
 * Created by can on 04/04/15.
 */
public final class NotificationHelper {

    private static final String EXTRA_EVENT_ID = "extra-event-id";

    private static final double LATITUDE = 44.433106;
    private static final double LONGITUDE = 26.103687;

    //  Default Values
    private static final String NOTIF_CONTENT_TITLE = "Wear-Showcase";
    private static final String NOTIF_CONTENT_TEXT = "Content Text";
    private static final int NOTIF_SMALL_ICON = R.drawable.abc_ic_voice_search_api_mtrl_alpha;

    private static final String CONTENT_BIG_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla blandit tristique augue, eget tempor urna dignissim suscipit. Nunc pulvinar mattis diam. Nunc at consectetur ligula. Ut orci leo, mollis eu lobortis sit amet, luctus eget nisi. Nulla bibendum ex eu arcu convallis, ut pellentesque urna commodo. ";

    //  Voice Input
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

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

    /**
     * Returns browse action
     *
     * @param context
     * @return          browse action
     */
    public static NotificationCompat.Action browseAction(final Context context) {
        return new NotificationCompat.Action(
                android.R.drawable.ic_search_category_default,
                "Show on Browser",
                browserPendingIntent(context)
        );
    }

    /**
     * Returns reply action
     *
     * @param context
     * @return          reply action
     */
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

        final String urlAddress = "http://maps.google.com/maps?q="+ LATITUDE  +"," + LONGITUDE+"(Jorgesys @ Bucharest)&iwloc=A&hl=es";
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
        builder.extend(new NotificationCompat.WearableExtender().addAction(wearableAction));

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
                )
        ).setContentIntent(viewPendingIntent(context)).
                addAction(replyAction(context));

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
                                        PendingIntent.getActivity(context, 0, new Intent(context, ReplyActivity.class), PendingIntent.FLAG_UPDATE_CURRENT)
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

        final Notification secondPage = new NotificationCompat.Builder(context).setStyle(style).build();

        final Notification thirdPage = new NotificationCompat.Builder(context).setStyle(style).build();

        builder.extend(
                new NotificationCompat.WearableExtender()
                        .setContentIcon(R.drawable.gplus)
                        .addPage(secondPage)
                        .addPage(thirdPage)
        ).build();

        raiseNotification(context, builder);

        // todo : food recipe pages on wearable, different screen layouts on phone

    }


    private static void raiseNotification(final Context context, final NotificationCompat.Builder builder) {

        final int notificationId = 001;

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =  NotificationManagerCompat.from(context);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, builder.build());

    }

    private static NotificationCompat.Builder defaultBuilder(final Context context) {

        return new NotificationCompat.Builder(context)
                        .setSmallIcon(NOTIF_SMALL_ICON)
                        .setContentTitle(NOTIF_CONTENT_TITLE)
                        .setContentText(NOTIF_CONTENT_TEXT);

    }

}
