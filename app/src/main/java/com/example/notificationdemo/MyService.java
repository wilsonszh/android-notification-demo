package com.example.notificationdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.example.notificationdemo.App.CHANNEL_ID;

public class MyService extends FirebaseMessagingService {

    NotificationManagerCompat manager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        manager = NotificationManagerCompat.from(this);

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            intent.putExtra("intent", "GoToSecond");
            //pending intent specify an action to take in the future

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //build and customize notification here
            NotificationCompat.Builder notification =
                    new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(remoteMessage.getNotification().getBody())
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            //action to be triggered after clicked on the notification
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

            NotificationManagerCompat manager = NotificationManagerCompat.from(this);
            //pass in id of notification
            manager.notify(12, notification.build());

        }
    }

    @Override
    public void onNewToken(String s) {

        Log.d("TAG122", "token:" + s);

    }


}
