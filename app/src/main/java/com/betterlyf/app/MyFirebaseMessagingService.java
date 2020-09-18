package com.betterlyf.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;


import com.betterlyf.app.ui.activity.SplashActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "RECEIVED_NOTI";
    private int notifId;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        Map<String, String> data = remoteMessage.getData();
        Log.e("notiData", remoteMessage.getData().toString());
        updatedNotification(remoteMessage.getData().get("message"));

    }

    private void showNotification(String message) {

        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Default stuff; making and showing notification
        final Context context = getApplicationContext();
        final NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        final Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher) // Needed for the notification to work/show!!
                .setContentTitle("Mfound")
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setStyle(new NotificationCompat.BigPictureStyle())
                .build();

        notificationManager.notify(notifId, notification);
        if (Build.VERSION.SDK_INT >= 16) {
            // Get RemoteView and id's needed
            final RemoteViews contentView = notification.contentView;
            final int iconId = android.R.id.icon;

            // Uncomment for BigPictureStyle, Requires API 16!
            final RemoteViews bigContentView = notification.bigContentView;
            final int bigIconId = context.getResources().getIdentifier("android:id/big_picture", null, null);

            // Use Picasso with RemoteViews to load image into a notification
            Handler uiHandler = new Handler(Looper.getMainLooper());
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                   /* Picasso.with(context)
                            .load(WebServices.BaseImage + image)
                            .resize(600, 400)
                            .into(bigContentView, bigIconId, notifId++, notification);*/
                }
            });
        }
    }

    private void updatedNotification(String aMessage) {
        notifId = generateRandom();
        String name = "my_channel";
        String id = "betterlyf";
        String description = "betterlyf";

        Intent intent;
        PendingIntent pendingIntent;

        intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        TaskStackBuilder stackBuilder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addNextIntent(intent);
        }


        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        final Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(getApplicationContext().getResources().getColor(R.color.colorPrimary))
                .setContentTitle("Hark - Listen Us")
                .setContentText(aMessage)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        /*int smallIconId = getApplicationContext().getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());
        if (smallIconId != 0) {
            notification.contentView.setViewVisibility(smallIconId, View.INVISIBLE);
            //notification.bigContentView.setViewVisibility(smallIconId, View.INVISIBLE);
        }*/
        manager.notify(notifId, notification);
    }

    public int generateRandom() {
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }
}
