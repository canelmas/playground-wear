package com.cnlms.wear;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by can on 04/04/15.
 */
public final class NotificationHelper {

    private static final String EXTRA_EVENT_ID = "extra-event-id";

    private static final double LATITUDE = 44.433106;
    private static final double LONGITUDE = 26.103687;

    //  Default Values
    private static final String NOTIF_CONTENT_TITLE = "Wear-Showcase";
    private static final String NOTIF_CONTENT_TEXT = "Lorem Ipsum Lorem Ipsum Lorem Ipsum";
    private static final int NOTIF_SMALL_ICON = R.drawable.abc_ic_voice_search_api_mtrl_alpha;

    private NotificationHelper() {}

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
