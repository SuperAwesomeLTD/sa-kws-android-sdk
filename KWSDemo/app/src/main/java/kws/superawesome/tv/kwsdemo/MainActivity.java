package kws.superawesome.tv.kwsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.sanetwork.request.*;
import kws.superawesome.tv.KWS;
import kws.superawesome.tv.KWSErrorType;
import kws.superawesome.tv.KWSInterface;

public class MainActivity extends AppCompatActivity implements KWSInterface {

    // view
    private TextView logView = null;
    private Button registerUser = null;

    // log text
    private String log = "";

    private String TOKEN = null;
    private static final String API = "https://kwsapi.demo.superawesome.tv/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // create this
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get text view
        logView = (TextView) findViewById(R.id.TextLogs);
        registerUser = (Button) findViewById(R.id.RegisterUser);

        // setup KWS SDK
        KWS.sdk.setApplicationContext(getApplicationContext());
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
        network.sendPOST(this, "https://kwsdemobackend.herokuapp.com/create", new JSONObject(), header, body, new SANetworkInterface() {
            @Override
            public void success(int status, String payload) {

                Log.d("SuperAwesome", status + "\n" + payload);

                try {
                    JSONObject json = new JSONObject(payload);
                    KWSModel model = new KWSModel(json);
                    model.username = username;

                    if (model.status == 1) {
                        registerUser.setText("I am " + username);
                        TOKEN = model.token;
                    } else {
                        log += "Failed to register " + username + "\n";
                        logView.setText(log);
                    }
                } catch (JSONException e) {
                    log += "Failed to register " + username + "\n";
                    logView.setText(log);
                }
            }

            @Override
            public void failure() {
                log += "Failed to register " + username + "\n";
                logView.setText(log);
            }
        });
    }

    public void registerAction(View v) {
        if (TOKEN != null) {
            log += "Trying to register user\n";
            logView.setText(log);
            KWS.sdk.setup(this, TOKEN, API, true, this);
            KWS.sdk.checkIfNotificationsAreAllowed();
        } else {
            log += "No user registered\n";
            logView.setText(log);
        }
    }

    public void unregisterAction(View v) {
        log = "";
        log += "Trying to unregister user\n";
        logView.setText(log);
        KWS.sdk.unregisterForRemoteNotifications();
    }

    // MARL: KWSInterface

    @Override
    public void kwsSDKDoesAllowUserToRegisterForRemoteNotifications() {
        log += "KWS allows user to regiser\n";
        logView.setText(log);
        KWS.sdk.registerForRemoteNotifications();
    }

    @Override
    public void kwsSDKDidRegisterUserForRemoteNotifications() {
        log += "User is registered\n";
        logView.setText(log);
    }

    @Override
    public void kwsSDKDidUnregisterUserForRemoteNotifications() {
        log += "User is un-registered\n";
        logView.setText(log);
    }

    @Override
    public void kwsSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType type) {
        switch (type){
            case NoKWSPermission: {
                log += "User has  no permissions (KWS)\n";
                logView.setText(log);
                break;
            }
            case NoSystemPermission: {
                log += "User has no pemrissions (System)\n";
                logView.setText(log);
                break;
            }
            case ParentEmailNotFound: {
                log += "User has no parent email in KWS\n";
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
                log += "Firebase is not setup\n";
                logView.setText(log);
                break;
            }
            case FirebaseCouldNotGetToken: {
                log += "Could not register - Firebase could not get token\n";
                logView.setText(log);
                break;
            }
            case NetworkError: {
                log += "Could not register - Network error\n";
                break;
            }
            case CouldNotUnsubscribeInKWS: {
                log += "Could not un-register - Network error\n";
                break;
            }
        }
    }
}
