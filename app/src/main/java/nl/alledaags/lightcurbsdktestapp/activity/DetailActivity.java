package nl.alledaags.lightcurbsdktestapp.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import com.lightcurb.sdk.LightcurbSDK;

import java.util.Map;

import nl.alledaags.lightcurbsdktestapp.R;


public class DetailActivity extends Activity {

    private String email;
    private String password;
    private int ownerId;
    private int promotionId;
    private int inboxItemId;
    private String name;
    private LightcurbSDK.Gender gender;
    private String date;
    private String locale;

    private String method;

    private TextView function;
    private TextView response;

    private LightcurbSDK lightcurbSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        //**********************************************
        //Set these variables:
        email = "lightcurbtest@alledaags.nl";  //Change to your own email address
        password = "test1234";
        ownerId = 368;
        promotionId = 3792;
        inboxItemId = 36689;

        name = "username";
        gender = LightcurbSDK.Gender.FEMALE;
        date = "1900-01-01";
        locale = "nl-nl";

        //**********************************************


        Intent intent = getIntent();
        method = intent.getStringExtra("method");

        function = (TextView) findViewById(R.id.function_name);
        response = (TextView) findViewById(R.id.detail_json_response);

        lightcurbSDK = new LightcurbSDK(this.getApplicationContext());

        function.setText(method);

        startMethod(method);


    }

    private void startMethod(String method) {

        switch (method) {
            case "Login":
                login();
                break;
            case "Logout":
                logout();
                break;
            case "Beacon search":
                beaconSearch();
                break;
            case "Owners":
                getOwners();
                break;
            case "Owner by id":
                getOwner();
                break;
            case "Owner notification enable":
                setNotification(true);
                break;
            case "Owner notification disable":
                setNotification(false);
                break;
            case "Owner block enable":
                setBlocked(true);
                break;
            case "Owner block disable":
                setBlocked(false);
                break;
            case "Promotion by id":
                getPromotion(promotionId);
                break;
            case "User create":
                createUser();
                break;
            case "User reset password":
                userResetPassword();
                break;
            case "User change password":
                updatePassword();
                break;
            case "Inbox":
                getInbox();
                break;
            case "Inbox item by id":
                getInboxItem();
                break;
            case "Inbox delete by id":
                inboxItemDelete();
                break;
            case "Inbox item is read":
                inboxItemRead(true);
                break;
            case "Inbox item is unread":
                inboxItemRead(false);
                break;
            case "Profile":
                getProfile();
                break;
            case "Profile update":
                updateProfile();
                break;




        }

    }

    public void tryMethodAgain(View view) {
        startMethod(method);
    }

    public void login()
    {

        lightcurbSDK.setOnLoginListener(new LightcurbSDK.OnLoginListener() {

            @Override
            public void onLoginFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }

            @Override
            public void onLoginCompleted(String data) {
                response.setText(data.toString());
            }
        });

        lightcurbSDK.login(email, password);
    }

    public void logout()
    {
        lightcurbSDK.setOnLogoutListener(new LightcurbSDK.OnLogoutListener() {

            @Override
            public void onLogoutFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }

            @Override
            public void onLogoutCompleted(String data) {
                response.setText(data.toString());
            }
        });
        lightcurbSDK.logout();

    }

    public void beaconSearch() {
        lightcurbSDK.setOnBeaconSearchListener(new LightcurbSDK.OnBeaconSearchListener() {

            @Override
            public void onBeaconSearchFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }

            @Override
            public void onBeaconSearchCompleted(String data) {
                response.setText(data.toString());
            }
        });

        lightcurbSDK.beaconSearch(53.444484, 7.108286, 51.362327, 3.379636, 0, 10);
    }



    public void getOwners() {
        lightcurbSDK.setOnGetOwnersListener(new LightcurbSDK.OnGetOwnersListener() {


            @Override
            public void onGetOwnersFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }


            @Override
            public void onGetOwnersCompleted(String data) {
                response.setText(data.toString());
            }
        });
        lightcurbSDK.getOwners();
    }

    public void getOwner() {

        lightcurbSDK.setOnGetOwnerListener(new LightcurbSDK.OnGetOwnerListener() {

            @Override
            public void onGetOwnerFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }

            @Override
            public void onGetOwnerCompleted(String data) {
                response.setText(data.toString());
            }

        });

        lightcurbSDK.getOwner(ownerId);

    }

    public void setNotification(Boolean enable) {

        lightcurbSDK.setOnSetNotificationListener(new LightcurbSDK.OnSetNotificationListener() {
            @Override
            public void onSetNotificationFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }

            @Override
            public void onSetNotificationCompleted(String data) {
                response.setText(data.toString());
            }
        });

        lightcurbSDK.setNotification(ownerId, enable);

    }

    public void setBlocked(Boolean enable) {

        lightcurbSDK.setOnSetBlockedListener(new LightcurbSDK.OnSetBlockedListener() {
            @Override
            public void onSetBlockedFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }

            @Override
            public void onSetBlockedCompleted(String data) {
                response.setText(data.toString());
            }
        });

        lightcurbSDK.setBlocked(ownerId, enable);
    }

    public void getPromotion(int id) {

        lightcurbSDK.setOnGetPromotionListener(new LightcurbSDK.OnGetPromotionListener() {
            @Override
            public void onGetPromotionFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }

            @Override
            public void onGetPromotionCompleted(String data) {
                response.setText(data.toString());
            }
        });

        lightcurbSDK.getPromotion(id);

    }

    public void createUser() {
        lightcurbSDK.setOnUserCreateListener(new LightcurbSDK.OnUserCreateListener() {


            @Override
            public void onUserCreateFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }


            @Override
            public void onUserCreateCompleted(String data) {
                response.setText(data.toString());
            }
        });

        lightcurbSDK.userCreate(email, password, name, gender, date, locale);

    }

    public void userResetPassword() {

        lightcurbSDK.setOnUserResetPasswordListener(new LightcurbSDK.OnUserResetPasswordListener() {
            @Override
            public void onUserResetPasswordFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }

            @Override
            public void onUserResetPasswordCompleted(String data) {
                response.setText(data.toString());
            }
        });

        lightcurbSDK.userResetPassword(email);

    }

    public void updatePassword() {
        lightcurbSDK.setOnUserChangePasswordListener(new LightcurbSDK.OnUserChangePasswordListener() {

            @Override
            public void onUserChangePasswordFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }


            @Override
            public void onUserChangePasswordCompleted(String data) {
                response.setText(data.toString());
            }
        });

        lightcurbSDK.userChangePassword(password, "test4567");

    }

    public void getInbox() {
        lightcurbSDK.setOnGetInboxListener(new LightcurbSDK.OnGetInboxListener() {


            @Override
            public void onGetInboxFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }


            @Override
            public void onGetInboxCompleted(String data) {
                response.setText(data.toString());
            }
        });
        lightcurbSDK.getInbox();

    }

    public void getInboxItem() {

        lightcurbSDK.setOnGetInboxItemListener(new LightcurbSDK.OnGetInboxItemListener() {
            @Override
            public void onGetInboxItemFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }

            @Override
            public void onGetInboxItemCompleted(String data) {
                response.setText(data.toString());
            }
        });

        lightcurbSDK.getInboxItem(inboxItemId);

    }

    public void inboxItemDelete() {

        lightcurbSDK.setOnInboxItemDeleteListener(new LightcurbSDK.OnInboxItemDeleteListener() {
            @Override
            public void onInboxItemDeleteFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }

            @Override
            public void onInboxItemDeleteCompleted(String data) {
                response.setText(data.toString());
            }
        });

        lightcurbSDK.inboxItemDelete(inboxItemId);

    }

    public void inboxItemRead(Boolean read) {

        lightcurbSDK.setOnInboxItemReadListener(new LightcurbSDK.OnInboxItemReadListener() {
            @Override
            public void onInboxItemReadFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }

            @Override
            public void onInboxItemReadCompleted(String data) {
                response.setText(data.toString());
            }
        });

        lightcurbSDK.inboxItemRead(inboxItemId, read);

    }

    public void getProfile() {
        lightcurbSDK.setOnGetProfileListener(new LightcurbSDK.OnGetProfileListener() {


            @Override
            public void onGetProfileFailed(Map<String, Object> data) {
                response.setText(data.toString());
            }


            @Override
            public void onGetProfileCompleted(String data) {
                response.setText(data.toString());
            }
        });
        lightcurbSDK.getProfile();
    }

    public void updateProfile() {
        lightcurbSDK.setOnProfileUpdateListener(new LightcurbSDK.OnProfileUpdateListener() {


            @Override
            public void onProfileUpdateFailed(Map<String, Object> data) {
                response.setText(data.toString());

            }

            @Override
            public void onProfileUpdateCompleted(String data) {
                response.setText(data.toString());
            }
        });

        lightcurbSDK.profileUpdate(email, name, LightcurbSDK.Gender.MALE, "2000-01-20", locale);

    }



}

