package cd.sklservices.com.Beststockage.Outils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import cd.sklservices.com.Beststockage.ActivityFolder.SecureView;
import cd.sklservices.com.Beststockage.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String CANAL="MyNotifCanal";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String myMessage=remoteMessage.getNotification().getBody();
        Log.d("FirebaseMessage","Vous venez de recevoir une notification : "+myMessage);

        //Rediriger vers une page web
        //Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://fatimefresh.com"));
        //PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,0);

        //Rediriger vers une activity
        Intent intent=new Intent(getApplicationContext(), SecureView.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,0);

        //Creer une notification
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(getApplicationContext(),CANAL);
        notificationBuilder.setContentTitle("Ma super notif");
        notificationBuilder.setContentText(myMessage);

        //Ajouter l'action
        notificationBuilder.setContentIntent(pendingIntent);

        //Creer la vibration
        long[] vibrationPattern={500,1000};
        notificationBuilder.setVibrate(vibrationPattern);

        //icône
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);

        //envoie la notification

        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            String channelId=getString(R.string.notification_channel_id);
            String channelTitle=getString(R.string.notification_channel_title);
            String channelDescription=getString(R.string.notification_channel_description);
            NotificationChannel channel=new NotificationChannel(channelId,channelTitle,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDescription);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
        }

        notificationManager.notify(1,notificationBuilder.build());

    }
}
