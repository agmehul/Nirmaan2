package com.nirmaan_bits.nirmaan.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nirmaan_bits.nirmaan.R;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class MyFirebaseSrevice extends FirebaseMessagingService  {
    public static int userProp;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().isEmpty()) {

            showNotification((Objects.requireNonNull(remoteMessage.getNotification())).getTitle(), remoteMessage.getNotification().getBody());
        }
        else
            showNotification(remoteMessage.getData());

            }

    private void showNotification(Map<String, String> data) {
String title = data.get("title");
String body = data.get("body");

        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CAHNNEL_ID ="com.nirmaan_bits.nirmaan.test";

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel= new NotificationChannel(NOTIFICATION_CAHNNEL_ID, "Notification_tabs",NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Nirmaan");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder notificatonBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CAHNNEL_ID);

        notificatonBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_star_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");

        notificationManager.notify(new Random().nextInt(),notificatonBuilder.build());

    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CAHNNEL_ID ="com.nirmaan_bits.nirmaan.test";

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel= new NotificationChannel(NOTIFICATION_CAHNNEL_ID, "Notification_tabs",NotificationManager.IMPORTANCE_DEFAULT);

        notificationChannel.setDescription("Nirmaan");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
        notificationChannel.enableLights(true);
        notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder notificatonBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CAHNNEL_ID);

        notificatonBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_star_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");

        notificationManager.notify(new Random().nextInt(),notificatonBuilder.build());

    }



    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Log.d("TOKENFIREBASE",s);

    }
}
