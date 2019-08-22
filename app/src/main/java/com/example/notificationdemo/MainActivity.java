package com.example.notificationdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static com.example.notificationdemo.App.CHANNEL_ID;

public class MainActivity extends AppCompatActivity {

    Button btnNotification;
    NotificationManagerCompat manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null) {

            Intent newIntent = new Intent(this, SecondActivity.class);

            startActivity(newIntent);

        }

        manager = NotificationManagerCompat.from(this);

        btnNotification = findViewById(R.id.btnNotf);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG122", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Log.d("TAG122", token);
                    }
                });


        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //action to do when user clicked on the notification (action to second activity)
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                //pending intent specify an action to take in the future
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //build and customize notification here
                NotificationCompat.Builder notification =
                        new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Notification Title")
                                .setContentText("Notification Content")
                                .setDefaults(Notification.DEFAULT_ALL)
                                .setPriority(NotificationCompat.PRIORITY_MAX)
                                //action to be triggered after clicked on the notification
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);

                //pass in id of notification
                manager.notify(123, notification.build());
            }
        });

    }
}
