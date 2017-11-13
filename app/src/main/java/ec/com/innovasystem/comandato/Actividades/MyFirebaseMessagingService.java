package ec.com.innovasystem.comandato.Actividades;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

import ec.com.innovasystem.comandato.R;
import ec.com.innovasystem.comandato.Utils.ValidatorUtil;


/**
 * Created by InnovaCaicedo on 20/6/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
       public void onMessageReceived(RemoteMessage remoteMessage) {
        String mensajeCorto="", titulo="", mensajeLargo="";
        if(remoteMessage.getNotification()!=null)
        {
            //texto=remoteMessage.getNotification().getBody().toString();
            Log.i("datos","datos "+  remoteMessage.getNotification().getBody());
        }
        else if(remoteMessage.getData()!=null)
        {
            Log.i("datos","datos "+remoteMessage);
            if(!ValidatorUtil.Texto(remoteMessage.getData().get("mensajeCorto").toString()))
                mensajeCorto="";
            else
            mensajeCorto=remoteMessage.getData().get("mensajeCorto").toString();

            if(!ValidatorUtil.Texto(remoteMessage.getData().get("mensajeLargo").toString()))
                mensajeLargo="";
            else
            mensajeLargo=remoteMessage.getData().get("mensajeLargo").toString();

            if(!ValidatorUtil.Texto(remoteMessage.getData().get("titulo").toString()))
                titulo="";
            else
            titulo=remoteMessage.getData().get("titulo").toString();

            Log.e("firebase","firebase "+remoteMessage.getData().get("message"));
        }

        showNotification(mensajeCorto,titulo, mensajeLargo);
    }

    private void showNotification(String message, String titulo, String largeMessage)
    {
        Intent i= new Intent(this, MesajesPush.class);
        Log.i("mensaje","mensaje "+message);
        Date now = new Date();
        long uniqueId = now.getTime();
        i.putExtra("titulo",titulo);
        i.putExtra("textoLargo",largeMessage);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.setAction("ec.com.innovacaicedo.opusdei"+uniqueId);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentText(message)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentTitle(titulo)
                .setSound(Uri.parse("android.resource://"
                        + getApplicationContext().getPackageName() + "/" + R.raw.notificacion))
                .setSmallIcon(R.drawable.icono_puntocanje)
                .setContentIntent(pendingIntent);

        NotificationManager managern= (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        managern.notify((int)uniqueId, builder.build());
    }
}
