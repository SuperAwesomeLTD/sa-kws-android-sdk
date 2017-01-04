package kws.superawesome.tv.kwsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import kws.superawesome.tv.kwssdk.models.appdata.KWSAppData;
import kws.superawesome.tv.kwssdk.models.leaderboard.KWSLeader;
import kws.superawesome.tv.kwssdk.models.user.KWSScore;
import kws.superawesome.tv.kwssdk.models.user.KWSUser;
import kws.superawesome.tv.kwssdk.process.KWSAuthUserProcessInterface;
import kws.superawesome.tv.kwssdk.process.KWSAuthUserStatus;
import kws.superawesome.tv.kwssdk.process.KWSCreateUserProcessInterface;
import kws.superawesome.tv.kwssdk.process.KWSCreateUserStatus;
import kws.superawesome.tv.kwssdk.process.KWSIsRegisteredInterface;
import kws.superawesome.tv.kwssdk.process.KWSRegisterInterface;
import kws.superawesome.tv.kwssdk.process.KWSUnregisterInterface;
import kws.superawesome.tv.kwssdk.services.kws.appdata.KWSGetAppDataInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSGetLeaderboardInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSGetScoreInterface;
import kws.superawesome.tv.kwssdk.services.kws.user.KWSGetUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSHasTriggeredEventInterface;
import kws.superawesome.tv.kwssdk.services.kws.user.KWSInviteUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.parentemail.KWSParentEmailInterface;
import kws.superawesome.tv.kwssdk.services.kws.parentemail.KWSParentEmailStatus;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSPermissionStatus;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSPermissionType;
import kws.superawesome.tv.kwssdk.services.kws.randomname.KWSRandomNameInterface;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSRequestPermissionInterface;
import kws.superawesome.tv.kwssdk.services.kws.appdata.KWSSetAppDataInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSTriggerEventInterface;
import tv.superawesome.lib.sautils.SAUtils;
import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.process.KWSNotificationStatus;

public class MainActivity extends AppCompatActivity {

    // view
    private TextView logView = null;
    private Button createUser = null;

    // log text
    private String log = "";

    private static final String CLIENT_ID = "sa-mobile-app-sdk-client-0";
    private static final int APP_ID = 313;
    private static final String SECRET = "_apikey_5cofe4ppp9xav2t9";
    private static final String API = "https://kwsapi.demo.superawesome.tv/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // create this
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get text view
        logView = (TextView) findViewById(R.id.TextLogs);
        logView.setMovementMethod(new ScrollingMovementMethod());
        createUser = (Button) findViewById(R.id.CreateUser);

        KWS.sdk.startSession(this, CLIENT_ID, APP_ID, SECRET, API);

    }

    // MARK: Actions

    public void createNewUser (View v) {
        final String username = "testuser" + SAUtils.randomNumberBetween(100, 10000);

        KWS.sdk.createUser(this, username, "testtest", "2011-03-02", "US", "dev.gabriel.coman@gmail.com", new KWSCreateUserProcessInterface() {
            @Override
            public void userCreated(KWSCreateUserStatus status) {
                switch (status) {
                    case Success:
                        log += "User " + username + " created OK\n";
                        break;
                    case InvalidUsername:
                        log += "Invalid username\n";
                        break;
                    case InvalidPassword:
                        log += "Invalid password\n";
                        break;
                    case InvalidDateOfBirth:
                        log += "Invalid date of birth\n";
                        break;
                    case InvalidCountry:
                        log += "Invalid country\n";
                        break;
                    case InvalidParentEmail:
                        log += "Invalid parent email\n";
                        break;
                    case DuplicateUsername:
                        log += "Duplicate username\n";
                        break;
                    case NetworkError:
                        log += "Network error\n";
                        break;
                    case InvalidOperation:
                        log += "Invalid operation\n";
                        break;
                }

                logView.setText(log);
            }
        });
    }

    public void authUser (View v) {

        KWS.sdk.loginUser(this, "tutu2", "12345678", new KWSAuthUserProcessInterface() {
            @Override
            public void userAuthenticated(KWSAuthUserStatus status) {
                switch (status) {
                    case Success:
                        log += "Auth as tutu2 w/ 12345678\n";
                        Log.d("SuperAwesome", KWS.sdk.getLoggedUser().writeToJson() + "");
                        break;
                    case NetworkError:
                        log += "Network error authing\n";
                        break;
                    case InvalidCredentials:
                        log += "Invalid credentials\n";
                        break;
                }

                logView.setText(log);
            }
        });

    }

    public void logoutUser (View v) {
        KWS.sdk.logoutUser(this);
    }

    public void generateRandomName (View v) {
        KWS.sdk.generateRandomName(this, new KWSRandomNameInterface() {
            @Override
            public void gotRandomName(String name) {
                log += "Random name " + name + "\n";
                logView.setText(log);
            }
        });
    }

    public void registerAction(View v) {
        log += "Trying to register user\n";
        logView.setText(log);


        KWS.sdk.registerWithPopup(this, new KWSRegisterInterface() {
            @Override
            public void register(KWSNotificationStatus status) {
                switch (status) {
                    case ParentDisabledNotifications: {
                        log += "User has  no permissions (KWS)\n";
                        break;
                    }
                    case UserDisabledNotifications: {
                        log += "User has  no permissions (System)\n";
                        break;
                    }
                    case NoParentEmail: {
                        log += "User has  no parent email (KWS)\n";

                        final KWSRegisterInterface local = this;

                        KWS.sdk.submitParentEmailWithPopup(MainActivity.this, new KWSParentEmailInterface() {
                            @Override
                            public void submitted(KWSParentEmailStatus status) {

                                switch (status) {
                                    case Success: {
                                        KWS.sdk.register(MainActivity.this, local);
                                        break;
                                    }
                                    case Invalid: {
                                        log += "Parent email invalid\n";
                                        logView.setText(log);
                                        break;
                                    }
                                    case NetworkError: {
                                        log += "Network error\n";
                                        logView.setText(log);
                                        break;
                                    }
                                }
                            }
                        });
                        break;
                    }
                    case FirebaseNotSetup: {
                        log += "No Google Play Services found\n";
                        break;
                    }
                    case FirebaseCouldNotGetToken: {
                        log += "Firebase could not get token\n";
                        break;
                    }
                    case NetworkError: {
                        log += "Network error checking for KWS notification permission\n";
                        break;
                    }
                    case Success:{
                        log += "Registered for Notifications\n";
                        break;
                    }
                }

                if (KWS.sdk.getLoggedUser() != null) {
                    log += "Local user is " + KWS.sdk.getLoggedUser().isRegisteredForNotifications() + "\n";
                }
                logView.setText(log);

            }
        });
    }

    public void unregisterAction(View v) {
        log += "Trying to unregister user\n";
        logView.setText(log);
        KWS.sdk.unregister(this, new KWSUnregisterInterface() {
            @Override
            public void unregister(boolean unregistered) {
                log += unregistered ? "User is un-registered\n" : "Network error unsubscribing Firebase token to KWS\n";
                if (KWS.sdk.getLoggedUser() != null) {
                    log += "Local user is " + KWS.sdk.getLoggedUser().isRegisteredForNotifications() + "\n";
                }
                logView.setText(log);
            }
        });
    }

    public void checkRegisteredAction(View v) {

        log += "Checking if user is registered or not\n";
        logView.setText(log);
        KWS.sdk.isRegistered(this, new KWSIsRegisteredInterface() {
            @Override
            public void isRegistered(boolean registered) {
                log += registered ? "User is already registered\n" : "User is not registered\n";
                if (KWS.sdk.getLoggedUser() != null) {
                    log += "Local user is " + KWS.sdk.getLoggedUser().isRegisteredForNotifications() + "\n";
                }
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
            public void requested(KWSPermissionStatus status) {

                switch (status) {
                    case Success: {
                        log += "Requested permission OK\n";
                        break;
                    }
                    case NoParentEmail: {
                        log += "No parent email found\n";
                        break;
                    }
                    case NeworkError: {
                        log += "Network error\n";
                        break;
                    }
                }
                logView.setText(log);
            }
        });


    }

    public void triggerEvent (View v) {
        KWS.sdk.triggerEvent(this, "a7tzV7QLhlR0rS8KK98QcZgrQk3ur260", 20, new KWSTriggerEventInterface() {
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
                log += score != null ? "Rank: " + score.rank + " Score: " + score.score + "\n" : "Could not get score\n";
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
