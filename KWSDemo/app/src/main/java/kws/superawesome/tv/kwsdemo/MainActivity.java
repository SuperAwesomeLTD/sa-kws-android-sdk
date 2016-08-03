package kws.superawesome.tv.kwsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import kws.superawesome.tv.kwssdk.models.leaderboard.KWSLeader;
import kws.superawesome.tv.kwssdk.models.user.KWSUser;
import kws.superawesome.tv.kwssdk.process.IsRegisteredInterface;
import kws.superawesome.tv.kwssdk.process.RegisterInterface;
import kws.superawesome.tv.kwssdk.process.UnregisterInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetLeaderboardInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSParentEmailInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSPermissionType;
import kws.superawesome.tv.kwssdk.services.kws.KWSRequestPermissionInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.sanetwork.request.*;
import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.process.KWSErrorType;

public class MainActivity extends AppCompatActivity {

    // view
    private TextView logView = null;
    private Button createUser = null;

    // log text
    private String log = "";

    private String TOKEN = null;
    private static final String API = "https://kwsapi.demo.superawesome.tv/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // create this
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup KWS SDK
        KWS.sdk.setApplicationContext(getApplicationContext());

        // get text view
        logView = (TextView) findViewById(R.id.TextLogs);
        logView.setMovementMethod(new ScrollingMovementMethod());
        createUser = (Button) findViewById(R.id.CreateUser);
    }

    // MARK: Actions

    public void createNewUser (View v) {
        final String username = "testuser" + SAUtils.randomNumberBetween(100, 10000);

        JSONObject body = SAJsonParser.newObject(new Object[]{
                "username", username,
                "password", "testtest",
                "dateOfBirth", "2011-03-02"
        });

        JSONObject header = SAJsonParser.newObject(new Object[] {
                "Content-Type", "application/json"
        });

        SANetwork network = new SANetwork();
        network.sendPOST(this, "https://kwsdemobackend.herokuapp.com/create", new JSONObject(), header, body, new SANetworkInterface() {
            @Override
            public void response(int status, String payload, boolean success) {

                // handle failure
                if (!success) {
                    log += "Failed to register " + username + "\n";
                    logView.setText(log);
                    return;
                }

                Log.d("SuperAwesome", status + "\n" + payload);

                // get json
                JSONObject json = SAJsonParser.newObject(payload);
                KWSModel model = new KWSModel(json);

                if (model.status == 1) {
                    createUser.setText("I am " + username);
                    TOKEN = model.token;
                    KWS.sdk.setup(MainActivity.this, TOKEN, API);
                } else {
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

            KWS.sdk.registerWithPopup(new RegisterInterface() {
                @Override
                public void register(boolean registered, KWSErrorType type) {
                    if (!registered) {
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
                                final RegisterInterface local = this;
                                KWS.sdk.submitParentEmailWithPopup(new KWSParentEmailInterface() {
                                    @Override
                                    public void submitted(boolean success) {
                                        if (success) {
                                            KWS.sdk.register(local);
                                        } else {
                                            log += "Parent email invalid\n";
                                            logView.setText(log);
                                        }
                                    }
                                });
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
                    } else {
                        log += "User is registered\n";
                        logView.setText(log);
                    }
                }
            });
        } else {
            log += "No user registered\n";
            logView.setText(log);
        }
    }

    public void unregisterAction(View v) {
        log += "Trying to unregister user\n";
        logView.setText(log);
        KWS.sdk.unregister(new UnregisterInterface() {
            @Override
            public void unregister(boolean unregistered) {
                log += unregistered ? "User is un-registered\n" : "Network error ubsubscribing Firebase token to KWS\n";
                logView.setText(log);
            }
        });
    }

    public void checkRegisteredAction(View v) {
        log += "Checking if user is registered or not\n";
        logView.setText(log);
        KWS.sdk.isRegistered(new IsRegisteredInterface() {
            @Override
            public void isRegistered(boolean registered) {
                log += registered ? "User is already registered\n" : "User is not registered\n";
                logView.setText(log);
            }
        });
    }

    public void getUserDetails (View v) {
        log += "Getting user details\n";
        logView.setText(log);
        KWS.sdk.getUser(new KWSGetUserInterface() {
            @Override
            public void gotUser(KWSUser user) {
                if (user != null) {
                    Log.d("SuperAwesome", user.writeToJson().toString() + "");
                    log += "Got user details for " + user.username + "\n";
                    logView.setText(log);
                } else {
                    log += "Failed to get user details\n";
                    logView.setText(log);
                }
            }
        });
    }

    public void  getLeaderboard (View v) {
        KWS.sdk.getLeaderBoard(new KWSGetLeaderboardInterface() {
            @Override
            public void gotLeaderboard(List<KWSLeader> leaderboard) {
                log += "Got " + leaderboard.size() + " leaders!\n";
                logView.setText(log);
            }
        });
    }

    public void requestPermissions (View v) {
        KWS.sdk.requestPermission(new KWSPermissionType[]{
                KWSPermissionType.accessAddress,
                KWSPermissionType.accessFirstName
        }, new KWSRequestPermissionInterface() {
            @Override
            public void requested(boolean success, boolean requested) {
                Log.d("SuperAwesome", "Success: " + success + " | " + requested);
            }
        });
    }
}
