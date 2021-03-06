package com.tumpi.tepesatu;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;

import com.tumpi.tepesatu.background.ImageDownload;
import com.tumpi.tepesatu.profile.ProfileActivity;
import com.tumpi.tepesatu.view.RingProgressBar;

public class MainActivity extends AppCompatActivity {

    private NotificationCompat.Builder mBuilder;
    private NotificationManager notificationManager;
    private int notifID;
    private RingProgressBar ringProgressBar;
    private ImageDownload imageDownload;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBuilder = new NotificationCompat.Builder(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        ringProgressBar = (RingProgressBar)findViewById(R.id.progressBar);
        imageView= (ImageView)findViewById(R.id.imageView);

        imageDownload = new ImageDownload(ringProgressBar,imageView);
        imageDownload.execute("https://avatars2.githubusercontent.com/u/21212043");

        notifID = 0;
    }

    private void showNotification(NotificationCompat.Builder mBuilder){
        Intent result = new Intent(this,ProfileActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(result);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setNumber(++notifID);

        notificationManager.notify(notifID,mBuilder.build());

    }

    public void openProfile(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
    public void clearNotif(View view){
        notificationManager.cancelAll();
    }

    public void showNotif(View view){
        mBuilder.setContentTitle("Arizal"); // judul
        mBuilder.setContentText("Hi apa kabar ?"); // desc
        mBuilder.setTicker("pesan baru datang"); // ticker
        mBuilder.setSmallIcon(R.mipmap.ic_launcher); //nambahin icon
        mBuilder.setAutoCancel(true);

        showNotification(mBuilder);
    }

}
