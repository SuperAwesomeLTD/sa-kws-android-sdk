package kws.superawesome.tv.kwsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;

import java.util.List;

import kws.superawesome.tv.kwssdk.models.appdata.KWSAppData;
import kws.superawesome.tv.kwssdk.models.leaderboard.KWSLeader;
import kws.superawesome.tv.kwssdk.models.user.KWSScore;
import kws.superawesome.tv.kwssdk.models.user.KWSUser;
import kws.superawesome.tv.kwssdk.process.IsRegisteredInterface;
import kws.superawesome.tv.kwssdk.process.RegisterInterface;
import kws.superawesome.tv.kwssdk.process.UnregisterInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSCreateUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetAppDataInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetLeaderboardInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetScoreInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSHasTriggeredEventInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSInviteUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSParentEmailInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSPermissionType;
import kws.superawesome.tv.kwssdk.services.kws.KWSRequestPermissionInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSSetAppDataInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSTriggerEventInterface;
import tv.superawesome.lib.sautils.SAUtils;
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

        // get text view
        logView = (TextView) findViewById(R.id.TextLogs);
        logView.setMovementMethod(new ScrollingMovementMethod());
        createUser = (Button) findViewById(R.id.CreateUser);
    }

    // MARK: Actions

    public void createNewUser (View v) {
        final String username = "testuser" + SAUtils.randomNumberBetween(100, 10000);

        KWS.sdk.createUser(this, username, "testtest", "2011-03-02", "US", new KWSCreateUserInterface() {
            @Override
            public void created(boolean success, String token) {
                if (success) {
                    log += "Created user " + username + "\n";
                    logView.setText(log);
                    KWS.sdk.startSession(token, API);
                    TOKEN = token;
                } else {
                    log += "Failed to create user " + username + "\n";
                    logView.setText(log);
                }
            }
        });
    }

    public void registerAction(View v) {
        if (TOKEN != null) {
            log += "Trying to register user\n";
            logView.setText(log);


            KWS.sdk.registerWithPopup(this, new RegisterInterface() {
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
                                KWS.sdk.submitParentEmailWithPopup(MainActivity.this, new KWSParentEmailInterface() {
                                    @Override
                                    public void submitted(boolean success) {
                                        if (success) {
                                            KWS.sdk.register(MainActivity.this, local);
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
        KWS.sdk.unregister(this, new UnregisterInterface() {
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
        KWS.sdk.isRegistered(this, new IsRegisteredInterface() {
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
        KWS.sdk.getUser(this, new KWSGetUserInterface() {
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
        KWS.sdk.getLeaderBoard(this, new KWSGetLeaderboardInterface() {
            @Override
            public void gotLeaderboard(List<KWSLeader> leaderboard) {
                log += "Got " + leaderboard.size() + " leaders!\n";
                logView.setText(log);
            }
        });
    }

    public void requestPermissions (View v) {
        KWS.sdk.requestPermission(this, new KWSPermissionType[]{
                KWSPermissionType.accessAddress,
                KWSPermissionType.accessFirstName
        }, new KWSRequestPermissionInterface() {
            @Override
            public void requested(boolean success, boolean requested) {
                log += "Requested perm: " + success + " | " + requested + "\n";
                logView.setText(log);
            }
        });


    }

    public void triggerEvent (View v) {
        KWS.sdk.triggerEvent(this, "a7tzV7QLhlR0rS8KK98QcZgrQk3ur260", 20, "Sent points!", new KWSTriggerEventInterface() {
            @Override
            public void triggered(boolean success) {
                log += "Triggered evt: " + success + "\n";
                logView.setText(log);
            }
        });
    }

    public void getScore (View v) {
        KWS.sdk.getScore(this, new KWSGetScoreInterface() {
            @Override
            public void gotScore(KWSScore score) {
                log += "Rank: " + score.rank + " Score: " + score.score + "\n";
                logView.setText(log);
            }
        });
    }

    public void inviteUser (View v) {
        KWS.sdk.inviteUser(this, "gabriel.coman@superawesome.tv", new KWSInviteUserInterface() {
            @Override
            public void invited(boolean success) {
                log += "Invited gabriel.coman@superawesome.tv " + success + "\n";
                logView.setText(log);
            }
        });

    }

    public void checkEvent (View v) {
        KWS.sdk.hasTriggeredEvent(this, 762, new KWSHasTriggeredEventInterface() {
            @Override
            public void hasTriggered(Boolean triggered) {
                log += "Event 762 is : " + triggered + "\n";
                logView.setText(log);
            }
        });

    }

    public void setAppData (View v) {
        KWS.sdk.setAppData(this, "new_val", 33, new KWSSetAppDataInterface() {
            @Override
            public void setAppData(boolean success) {
                log += "Set new_val=33 with " + success + "\n";
                logView.setText(log);
            }
        });
    }

    public void getAppData (View v) {
        KWS.sdk.getAppData(this, new KWSGetAppDataInterface() {
            @Override
            public void gotAppData(List<KWSAppData> appData) {
                for (KWSAppData data : appData) {
                    log += "[" + data.name + "]=" + data.value + "\n";
                }
                logView.setText(log);
            }
        });
    }

}
