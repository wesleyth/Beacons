package nl.alledaags.lightcurbsdktestapp;


import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;

import com.lightcurb.sdk.LightcurbSDK;
import java.util.Map;

import nl.alledaags.device.Bluetooth;
import nl.alledaags.lightcurbsdktestapp.service.NotificationService;

public class StartApplication extends Application
{
    public void onCreate()
    {
        super.onCreate();

        if (!Bluetooth.hasFeature(getApplicationContext()))
        {
            // Device has no bluetooth
        }
        else if (!Bluetooth.isEnabled(getApplicationContext()))
        {
            // Bluetooth is disabled

            // Switch to settings to enable
            final Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.bluetooth.BluetoothSettings");
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity( intent);
        }

        final LightcurbSDK sdk = new LightcurbSDK(getApplicationContext());

        sdk.configure("d29a1514-f06f-411a-a3e8-6de87543b5fc", new LightcurbSDK.OnConfigureListener()
        {
            @Override
            public void onConfigureFailed(Map<String, Object> data)
            {

            }

            @Override
            public void onConfigureCompleted(String data)
            {
                sdk.startBeaconMonitoring();
            }
        });


        // Start the service that receives promotions and broadcasts notifications
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
    }
}
