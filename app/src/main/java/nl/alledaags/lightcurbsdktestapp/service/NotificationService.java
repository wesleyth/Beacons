package nl.alledaags.lightcurbsdktestapp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.lightcurb.sdk.Constants;
import com.lightcurb.sdk.model.Promotion;

import nl.alledaags.lightcurbsdktestapp.R;
import nl.alledaags.lightcurbsdktestapp.activity.MainActivity;

/**
 * Created by basholtrop on 12/01/15.
 */
public class NotificationService extends Service
{
    private static final String TAG = NotificationService.class.getSimpleName();

    private final IBinder binder = new NotificationServiceBinder();
    private boolean isBound;
    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Bundle extras = intent.getExtras();
            Promotion promotion = extras.getParcelable("promotion");

            if (promotion.showNotification)
            {
                pushNotification(promotion);
            }
        }
    };


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.PROMOTION_RECEIVED);

        registerReceiver(receiver, intentFilter);

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }

    private void pushNotification(Promotion promotion)
    {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(promotion.title);
        builder.setContentText(promotion.message);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);

        int defaults = 0;

        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;

//		if (user.hasNotificationSound())
//		{
        defaults = defaults | Notification.DEFAULT_SOUND;
//		}

        builder.setDefaults(defaults);

        builder.setAutoCancel(true);
        Notification n = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);
    }

    @Override
    public IBinder onBind(Intent intent) { return binder; }

    @Override
    public boolean onUnbind(Intent intent) { return true; }

    public class NotificationServiceBinder extends Binder
    {
        public NotificationService getService()
        {
            return NotificationService.this;
        }
    }
}