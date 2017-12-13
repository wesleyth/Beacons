package nl.alledaags.lightcurbsdktestapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lightcurb.sdk.Constants;
import com.lightcurb.sdk.LightcurbSDK;
import com.lightcurb.sdk.model.Beacon;
import com.lightcurb.sdk.model.Promotion;

import java.util.ArrayList;

import nl.alledaags.lightcurbsdktestapp.R;


public class MainActivity extends Activity implements View.OnClickListener
{

    private LightcurbSDK lightcurbSDK;

    private TextView uuid;
    private TextView distance;
    private TextView major;
    private TextView minor;
    private TextView title;
    private TextView message;
    private TextView type;
    private TextView notification;

    private String method;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        uuid = (TextView) findViewById(R.id.beacon_uuid_textview);
        distance = (TextView) findViewById(R.id.beacon_distance_textview);
        major = (TextView) findViewById(R.id.beacon_major_textview);
        minor = (TextView) findViewById(R.id.beacon_minor_textview);
        title = (TextView) findViewById(R.id.beacon_promotion_title);
        message = (TextView) findViewById(R.id.beacon_promotion_message);
        type = (TextView) findViewById(R.id.beacon_promotion_type);
        notification = (TextView) findViewById(R.id.beacon_promotion_show_notification);

        method = "";

        Button login = (Button) findViewById(R.id.button_login);
        Button logout = (Button) findViewById(R.id.button_logout);
        Button beaconsearch = (Button) findViewById(R.id.button_beaconsearch);
        Button getowners = (Button) findViewById(R.id.button_getowners);
        Button getowner = (Button) findViewById(R.id.button_getowner);
        Button ownerNotifyEnable = (Button) findViewById(R.id.button_owner_notify_enable);
        Button ownerNotifyDisable = (Button) findViewById(R.id.button_owner_notify_disable);
        Button ownerBlockEnable = (Button) findViewById(R.id.button_owner_block_enable);
        Button ownerBlockDisable = (Button) findViewById(R.id.button_owner_block_disable);
        Button promotion = (Button) findViewById(R.id.button_promotion);
        Button createUser = (Button) findViewById(R.id.button_createuser);
        Button resetPassword = (Button) findViewById(R.id.button_resetpassword);
        Button updatePassword = (Button) findViewById(R.id.button_updatepassword);
        Button inbox = (Button) findViewById(R.id.button_inbox);
        Button inboxItem = (Button) findViewById(R.id.button_inboxitem);
        Button inboxItemDelete = (Button) findViewById(R.id.button_inboxitem_delete);
        Button inboxItemRead = (Button) findViewById(R.id.button_inboxitem_read);
        Button inboxItemUnread = (Button) findViewById(R.id.button_inboxitem_unread);
        Button profile = (Button) findViewById(R.id.button_profile);
        Button profileUpdate = (Button) findViewById(R.id.button_updateprofile);

        login.setOnClickListener(this);
        logout.setOnClickListener(this);
        beaconsearch.setOnClickListener(this);
        getowners.setOnClickListener(this);
        getowner.setOnClickListener(this);
        ownerNotifyEnable.setOnClickListener(this);
        ownerNotifyDisable.setOnClickListener(this);
        ownerBlockEnable.setOnClickListener(this);
        ownerBlockDisable.setOnClickListener(this);
        promotion.setOnClickListener(this);
        createUser.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
        updatePassword.setOnClickListener(this);
        inbox.setOnClickListener(this);
        inboxItem.setOnClickListener(this);
        inboxItemDelete.setOnClickListener(this);
        inboxItemRead.setOnClickListener(this);
        inboxItemUnread.setOnClickListener(this);
        profile.setOnClickListener(this);
        profileUpdate.setOnClickListener(this);

        IntentFilter apiManagerFilter = new IntentFilter();
        apiManagerFilter.addAction(Constants.PROMOTION_RECEIVED);
        registerReceiver(lightcurbServiceReceiver, apiManagerFilter);

        checkPermissionLocation();

        startBeaconMonitoring();
    }

    private void checkPermissionLocation() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {


            int hasFineLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

        startBeaconMonitoring();


    }

    private void startBeaconMonitoring() {
        lightcurbSDK = new LightcurbSDK(this.getApplicationContext());

        lightcurbSDK.setOnBeaconsChangedListener(new LightcurbSDK.OnBeaconsChangedListener()
        {
            @Override
            public void onBeaconsChanged(ArrayList<Beacon> beacons)
            {
                setBeacon(beacons.get(0));
            }
        });


        lightcurbSDK.startBeaconMonitoring();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    startBeaconMonitoring();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "FINE Location Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        lightcurbSDK.onResume();

    }

    @Override
    public void onPause()
    {
        super.onPause();

    }


    private BroadcastReceiver lightcurbServiceReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Bundle extras = intent.getExtras();
            Promotion promotion = extras.getParcelable("promotion");

            setPromotion(promotion);

        }
    };

    private void setBeacon(Beacon beacon) {
        uuid.setText(beacon.getUuid());
        distance.setText(beacon.getDistanceQualification());
        major.setText("major: " + beacon.getMajor());
        minor.setText("minor: " + beacon.getMinor());
    }

    private void setPromotion(Promotion promotion) {
        title.setText(promotion.title);
        message.setText(promotion.message);
        type.setText("Type: " + promotion.type);
        notification.setText("Show notification: " + String.valueOf(promotion.showNotification));
    }

    public void beaconStart(View v) {
        lightcurbSDK.startBeaconMonitoring();
    }

    public void beaconStop(View v) {
        lightcurbSDK.stopBeaconMonitoring();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_login:
                method = "Login";
                break;
            case R.id.button_logout:
                method = "Logout";
                break;
            case R.id.button_beaconsearch:
                method = "Beacon search";
                break;
            case R.id.button_getowners:
                method = "Owners";
                break;
            case R.id.button_getowner:
                method = "Owner by id";
                break;
            case R.id.button_owner_notify_enable:
                method = "Owner notification enable";
                break;
            case R.id.button_owner_notify_disable:
                method = "Owner notification disable";
                break;
            case R.id.button_owner_block_enable:
                method = "Owner block enable";
                break;
            case R.id.button_owner_block_disable:
                method = "Owner block disable";
                break;
            case R.id.button_promotion:
                method = "Promotion by id";
                break;
            case R.id.button_createuser:
                method = "User create";
                break;
            case R.id.button_resetpassword:
                method = "User reset password";
                break;
            case R.id.button_updatepassword:
                method = "User change password";
                break;
            case R.id.button_inbox:
                method = "Inbox";
                break;
            case R.id.button_inboxitem:
                method = "Inbox item by id";
                break;
            case R.id.button_inboxitem_delete:
                method = "Inbox delete by id";
                break;
            case R.id.button_inboxitem_read:
                method = "Inbox item is read";
                break;
            case R.id.button_inboxitem_unread:
                method = "Inbox item is unread";
                break;
            case R.id.button_profile:
                method = "Profile";
                break;
            case R.id.button_updateprofile:
                method = "Profile update";
                break;

        }

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("method",method);
        startActivity(intent);

    }

}