package kws.superawesome.tv.kwsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.KWSErrorType;
import kws.superawesome.tv.kwssdk.KWSRegisterInterface;
import kws.superawesome.tv.kwssdk.KWSUnregisterInterface;
import kws.superawesome.tv.kwssdk.kws.KWSRandomNameInterface;
import kws.superawesome.tv.kwssdk.network.request.SANetwork;
import kws.superawesome.tv.kwssdk.network.request.SANetworkInterface;
import kws.superawesome.tv.kwssdk.utils.SAUtils;

//import kws.superawesome.tv.kwssdk.KWSCheckInterface;
//import kws.superawesome.tv.kwssdk.KWSRegisterInterface;
//import kws.superawesome.tv.kwssdk.KWSUnregisterInterface;
//import kws.superawesome.tv.kwssdk.kws.KWSRandomNameInterface;
//import tv.superawesome.lib.sautils.SAUtils;
//import tv.superawesome.lib.sanetwork.request.*;
//import kws.superawesome.tv.kwssdk.KWS;
//import kws.superawesome.tv.kwssdk.KWSErrorType;

public class MainActivity extends AppCompatActivity {

    // view
    private TextView logView = null;
    private Button registerUser = null;

    // log text
    private String log = "";

    private String TOKEN = null;
    private static final String API = "https://kwsapi.demo.superawesome.tv/";
    private static final String CLIENTID = "sa-mobile-app-sdk-client-0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // create this
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get text view
        logView = (TextView) findViewById(R.id.TextLogs);
        registerUser = (Button) findViewById(R.id.RegisterUser);

//        // registerToken KWS SDK
        KWS.sdk.setup(API, CLIENTID);
    }

    // MARK: Actions

    public void registerNewUser (View v) {
        final String username = "testuser" + SAUtils.randomNumberBetween(100, 10000);
        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
            body.put("password", "testtest");
            body.put("dateOfBirth", "2011-03-02");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject header = new JSONObject();
        try {
            header.put("Content-Type", "application/json");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SANetwork network = new SANetwork();
        network.sendPOST("https://kwsdemobackend.herokuapp.com/create", new JSONObject(), header, body, new SANetworkInterface() {
            @Override
            public void saDidGetResponse(int status, String payload, boolean success) {

                if (!success) {
                    log += "Failed to register " + username + "\n";
                    logView.setText(log);
                    return;
                }

                Log.d("SuperAwesome", status + "\n" + payload);

                try {
                    JSONObject json = new JSONObject(payload);
                    KWSModel model = new KWSModel(json);
                    model.username = username;

                    if (model.status == 1) {
                        registerUser.setText("I am " + username);
                        TOKEN = model.token;
                        KWS.sdk.registerToken(MainActivity.this, TOKEN, true);
                    } else {
                        log += "Failed to register " + username + "\n";
                        logView.setText(log);
                    }
                } catch (JSONException e) {
                    log += "Failed to register " + username + "\n";
                    logView.setText(log);
                }
            }
        });
    }

    public void registerAction(View v) {
        if (TOKEN != null) {
            log += "Trying to register user\n";
            logView.setText(log);
            KWS.sdk.registerForRemoteNotifications(new KWSRegisterInterface() {
                @Override
                public void kwsSDKDidRegisterUserForRemoteNotifications() {
                    log += "User is registered\n";
                    logView.setText(log);
                }

                @Override
                public void kwsSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType type) {
                    switch (type) {
                        case ParentHasDisabledRemoteNotifications: {
                            log += "User has  no permissions (KWS)\n";
                            logView.setText(log);
                            break;
                        }
                        case UserHasDisabledRemoteNotifications: {
                            log += "User has  no permissions (System)\n";
                            logView.setText(log);
                            break;
                        }
                        case UserHasNoParentEmail: {
                            log += "User has  no parent email (KWS)\n";
                            logView.setText(log);
                            KWS.sdk.showParentEmailPopup();
                            break;
                        }
                        case ParentEmailInvalid: {
                            log += "Parent email is invalid\n";
                            logView.setText(log);
                            break;
                        }
                        case FirebaseNotSetup: {
                            log += "No Google Play Services found\n";
                            logView.setText(log);
                            break;
                        }
                        case FirebaseCouldNotGetToken: {
                            log += "Firebase could not get token\n";
                            logView.setText(log);
                            break;
                        }
                        case FailedToCheckIfUserHasNotificationsEnabledInKWS: {
                            log += "Network error checking for KWS notification permission\n";
                            logView.setText(log);
                            break;
                        }
                        case FailedToRequestNotificationsPermissionInKWS: {
                            log += "Network error requesting notification permission in KWS\n";
                            logView.setText(log);
                            break;
                        }
                        case FailedToSubmitParentEmail: {
                            log += "Network error submitting parent email to KWS\n";
                            logView.setText(log);
                            break;
                        }
                        case FailedToSubscribeTokenToKWS: {
                            log += "Network error subscribing Firebase token to KWS\n";
                            logView.setText(log);
                            break;
                        }
                    }
                }
            });
        } else {
            log += "No user registered\n";
            logView.setText(log);
        }
    }

    public void unregisterAction(View v) {
        log = "";
        log += "Trying to unregister user\n";
        logView.setText(log);
        KWS.sdk.unregisterForRemoteNotifications(new KWSUnregisterInterface() {
            @Override
            public void kwsSDKDidUnregisterUserForRemoteNotifications() {
                log += "User is un-registered\n";
                logView.setText(log);
            }

            @Override
            public void kwsSDKDidFailToUnregisterUserForRemoteNotifications() {
                log += "Network error ubsubscribing Firebase token to KWS\n";
                logView.setText(log);
            }
        });
    }

    public void checkRegisteredAction(View v) {

        KWS.sdk.getRandomName(this, new KWSRandomNameInterface() {
            @Override
            public void didGetRandomName(String name) {
                log += "Random name " + name + "\n";
                logView.setText(log);
            }
        });

//        log += "Checking if user is registered or not\n";
//        logView.setText(log);
//        KWS.sdk.userIsRegistered(new KWSCheckInterface() {
//            @Override
//            public void kwsSDKUserIsRegistered() {
//                log += "User is already registered for Remote Notifications in KWS\n";
//                logView.setText(log);
//            }
//
//            @Override
//            public void kwsSDKUserIsNotRegistered() {
//                log += "User is not registered yet for Remote Notifications in KWS\n";
//                logView.setText(log);
//            }
//
//            @Override
//            public void kwsSDKDidFailToCheckIfUserIsRegistered() {
//                log += "Failed to check if user is registered for Remote Notifications in KWS\n";
//                logView.setText(log);
//            }
//        });
    }
}
