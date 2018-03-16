package kws.superawesome.tv.kwsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import kws.superawesome.tv.kwssdk.KWSChildren;
import kws.superawesome.tv.kwssdk.models.appdata.KWSAppData;
import kws.superawesome.tv.kwssdk.models.leaderboard.KWSLeader;
import kws.superawesome.tv.kwssdk.models.user.KWSScore;
import kws.superawesome.tv.kwssdk.models.user.KWSUser;
import kws.superawesome.tv.kwssdk.process.KWSChildrenCreateUserInterface;
import kws.superawesome.tv.kwssdk.process.KWSChildrenCreateUserStatus;
import kws.superawesome.tv.kwssdk.process.KWSChildrenIsRegisteredForRemoteNotificationsInterface;
import kws.superawesome.tv.kwssdk.process.KWSChildrenLoginUserInterface;
import kws.superawesome.tv.kwssdk.process.KWSChildrenLoginUserStatus;
import kws.superawesome.tv.kwssdk.process.KWSChildrenRegisterForRemoteNotificationsInterface;
import kws.superawesome.tv.kwssdk.process.KWSChildrenRegisterForRemoteNotificationsStatus;
import kws.superawesome.tv.kwssdk.process.KWSChildrenUnregisterForRemoteNotificationsInterface;
import kws.superawesome.tv.kwssdk.services.kws.appdata.KWSChildrenGetAppDataInterface;
import kws.superawesome.tv.kwssdk.services.kws.appdata.KWSChildrenSetAppDataInterface;
import kws.superawesome.tv.kwssdk.services.kws.parentemail.KWSChildrenUpdateParentEmailInterface;
import kws.superawesome.tv.kwssdk.services.kws.parentemail.KWSChildrenUpdateParentEmailStatus;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenPermissionType;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenRequestPermissionInterface;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenRequestPermissionStatus;
import kws.superawesome.tv.kwssdk.services.kws.randomname.KWSChildrenGetRandomUsernameInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSChildrenGetLeaderboardInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSChildrenGetScoreInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSChildrenHasTriggeredEventInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSChildrenTriggerEventInterface;
import kws.superawesome.tv.kwssdk.services.kws.user.KWSChildrenGetUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.user.KWSChildrenInviteUserInterface;
import tv.superawesome.lib.sautils.SAUtils;

public class MainActivity extends AppCompatActivity {

    // view
    private TextView logView = null;
    private Button createUser = null;

    // log text
    private String log = "";

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

    public void createNewUser(View v) {
        final String username = "testuser" + SAUtils.randomNumberBetween(100, 10000);
        final String pwd = "testtest";

        final String parentEmail = "guilherme.mota@superawesome.tv";

        KWSChildren.sdk.createUser(this, username, pwd,
                "2011-03-02",
                "US",
                parentEmail,
                new KWSChildrenCreateUserInterface() {
                    @Override
                    public void didCreateUser(KWSChildrenCreateUserStatus status) {
                        switch (status) {
                            case Success:
                                log += "User " + username + " created OK\n";
                                break;
                            case InvalidUsername:
                                log += "InvalidEmail name\n";
                                break;
                            case InvalidPassword:
                                log += "InvalidEmail password\n";
                                break;
                            case InvalidDateOfBirth:
                                log += "InvalidEmail date of birth\n";
                                break;
                            case InvalidCountry:
                                log += "InvalidEmail country\n";
                                break;
                            case InvalidParentEmail:
                                log += "InvalidEmail parent email\n";
                                break;
                            case DuplicateUsername:
                                log += "Duplicate name\n";
                                break;
                            case NetworkError:
                                log += "Network error\n";
                                break;
                            case InvalidOperation:
                                log += "InvalidEmail operation\n";
                                break;
                        }

                        logView.setText(log);

                        Log.d("SuperAwesome", status.toString());
                    }
                });
    }

    public void loginUser(View v) {

        final String username = "guitestnumber4";
//        final String username = "testguitest1";
        final String pwd = "testtest";

        KWSChildren.sdk.loginUser(this, username, pwd, new KWSChildrenLoginUserInterface() {
            @Override
            public void didLoginUser(KWSChildrenLoginUserStatus status) {
                switch (status) {
                    case Success:
                        log += "Auth as " + username + "w/ pwd as: " + pwd + "\n";
                        Log.d("SuperAwesome", KWSChildren.sdk.getLoggedUser(MainActivity.this).getTokenData().toString());
                        break;
                    case NetworkError:
                        log += "Network error authing\n";
                        break;
                    case InvalidCredentials:
                        log += "Invalid Email credentials\n";
                        break;
                }
                Log.d("SuperAwesome", status.toString());
                logView.setText(log);
            }
        });
    }

    public void authUser(View v) {

        //stan test ---> https://stan-test-cluster.accounts.kws.superawesome.tv/

        String singleSignOnUrl = "https://club.demo.superawesome.tv/";

        KWSChildren.sdk.authWithSingleSignOnUrl(this, singleSignOnUrl, this, new KWSChildrenLoginUserInterface() {
            @Override
            public void didLoginUser(KWSChildrenLoginUserStatus status) {
                log += "Web View Authentication status is " + status + "\n";
                logView.setText(log);
                Log.d("SuperAwesome", status.toString());
            }
        });
    }

    public void logoutUser(View v) {
        KWSChildren.sdk.logoutUser(this);
    }

    public void generateRandomName(View v) {

        KWSChildren.sdk.getRandomUsername(this, new KWSChildrenGetRandomUsernameInterface() {
            @Override
            public void didGetRandomUsername(String name) {

                if (name != null) {
                    log += "Random name " + name + "\n";
                    logView.setText(log);
                    Log.d("SuperAwesome", name);
                } else {
                    log += "Invalid Random name. (null)  \n";
                    logView.setText(log);
                    Log.d("SuperAwesome", "Random name is (null)");
                }
            }
        });
    }

    public void registerAction(View v) {
        log += "Trying to registerForRemoteNotifications user\n";
        logView.setText(log);

        //TODO update to new GET LOGGED USER

        KWSChildren.sdk.registerForRemoteNotificationsWithPopup(this, new KWSChildrenRegisterForRemoteNotificationsInterface() {
            @Override
            public void didRegisterForRemoteNotifications(KWSChildrenRegisterForRemoteNotificationsStatus status) {
                switch (status) {
                    case ParentDisabledNotifications: {
                        log += "User has  no permissions (KWSChildren)\n";
                        break;
                    }
                    case UserDisabledNotifications: {
                        log += "User has  no permissions (System)\n";
                        break;
                    }
                    case NoParentEmail: {
                        log += "User has  no parent email (KWSChildren)\n";

                        final KWSChildrenRegisterForRemoteNotificationsInterface local = this;

                        KWSChildren.sdk.updateParentEmailWithPopup(MainActivity.this, new KWSChildrenUpdateParentEmailInterface() {
                            @Override
                            public void didUpdateParentEmail(KWSChildrenUpdateParentEmailStatus status) {

                                switch (status) {
                                    case Success: {
                                        KWSChildren.sdk.registerForRemoteNotifications(MainActivity.this, local);
                                        break;
                                    }
                                    case InvalidEmail: {
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
                        log += "Network error checking for KWSChildren notification permission\n";
                        break;
                    }
                    case Success: {
                        log += "Registered for Notifications\n";
                        break;
                    }
                }

                if (KWSChildren.sdk.getLoggedUser() != null) {
                    log += "SuperAwesome Local user is " + KWSChildren.sdk.getLoggedUser().isRegisteredForNotifications() + "\n";
                }
                logView.setText(log);
                Log.d("SuperAwesome", status.toString());
            }
        });
    }

    public void unregisterAction(View v) {

        //TODO update to new GET LOGGED USER

        log += "Trying to unregisterForRemoteNotifications user\n";
        logView.setText(log);
        KWSChildren.sdk.unregisterForRemoteNotifications(this, new KWSChildrenUnregisterForRemoteNotificationsInterface() {
            @Override
            public void didUnregisterForRemoteNotifications(boolean unregistered) {
                log += unregistered ? "User is un-registered\n" : "Network error unsubscribing Firebase token to KWSChildren\n";
                if (KWSChildren.sdk.getLoggedUser() != null) {
                    Log.d("SuperAwesome", KWSChildren.sdk.getLoggedUser().writeToJson().toString());
                    log += "SuperAwesome Local user is " + KWSChildren.sdk.getLoggedUser().isRegisteredForNotifications() + "\n";
                }
                logView.setText(log);
            }
        });
    }

    public void checkRegisteredAction(View v) {

        //TODO needs to update to use the new GET LOGGED USER

        log += "Checking if user is registered or not\n";
        logView.setText(log);
        KWSChildren.sdk.isRegisteredForRemoteNotifications(this, new KWSChildrenIsRegisteredForRemoteNotificationsInterface() {
            @Override
            public void isRegisteredForRemoteNotifications(boolean registered) {
                log += registered ? "User is already registered\n" : "User is not registered\n";
                if (KWSChildren.sdk.getLoggedUser() != null) {
                    Log.d("SuperAwesome", KWSChildren.sdk.getLoggedUser().writeToJson().toString());
                    log += "SuperAwesome Local user is " + KWSChildren.sdk.getLoggedUser().isRegisteredForNotifications() + "\n";
                }
                logView.setText(log);

            }
        });
    }

    public void getUserDetails(View v) {
        log += "Getting user details\n";
        logView.setText(log);
        KWSChildren.sdk.getUser(this, new KWSChildrenGetUserInterface() {
            @Override
            public void didGetUser(KWSUser user) {
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

    public void getLeaderboard(View v) {
        KWSChildren.sdk.getLeaderboard(this, new KWSChildrenGetLeaderboardInterface() {
            @Override
            public void didGetLeaderboard(List<KWSLeader> leaderboard) {
                log += "Got " + leaderboard.size() + " leaders!\n";

                StringBuilder logString = new StringBuilder();
                for (KWSLeader item : leaderboard) {
                    logString.append(item.writeToJson().toString()).append(", ");
                }
                if (logString.length() != 0) {
                    Log.d("SuperAwesome", logString + "");
                }
                logView.setText(log);
            }
        });
    }

    public void requestPermissions(View v) {
        KWSChildren.sdk.requestPermission(this, new KWSChildrenPermissionType[]{
                KWSChildrenPermissionType.AccessAddress,
                KWSChildrenPermissionType.AccessFirstName
        }, new KWSChildrenRequestPermissionInterface() {
            @Override
            public void didRequestPermission(KWSChildrenRequestPermissionStatus status) {

                switch (status) {
                    case Success: {
                        log += "Requested permission OK\n";
                        break;
                    }
                    case NoParentEmail: {
                        log += "No parent email found\n";
                        break;
                    }
                    case NetworkError: {
                        log += "Network error\n";
                        break;
                    }
                }
                logView.setText(log);
                Log.d("SuperAwesome", status.toString());
            }
        });


    }

    public void triggerEvent(View v) {

        String event = "8X9QneMSaxU2VzCBJI5YdxRGG7l3GOUw";

        KWSChildren.sdk.triggerEvent(this, event, 20, new KWSChildrenTriggerEventInterface() {
            @Override
            public void didTriggerEvent(boolean success) {
                log += "Triggered evt: " + success + "\n";
                logView.setText(log);
                Log.d("SuperAwesome ", String.valueOf(success));
            }
        });
    }

    public void getScore(View v) {
        KWSChildren.sdk.getScore(this, new KWSChildrenGetScoreInterface() {
            @Override
            public void didGetScore(KWSScore score) {
                log += score != null ? "Rank: " + score.rank + " Score: " + score.score + "\n" : "Could not get score\n";
                logView.setText(log);
                if (score != null) {
                    Log.d("SuperAwesome ", score.writeToJson().toString());
                }
            }
        });
    }

    public void inviteUser(View v) {
        final String email = "guilherme.mota+9@superawesome.tv";
        KWSChildren.sdk.inviteUser(this, email, new KWSChildrenInviteUserInterface() {
            @Override
            public void didInviteUser(boolean success) {
                log += "Invited " + email + " " + success + "\n";
                logView.setText(log);
                Log.d("SuperAwesome ", String.valueOf(success));
            }
        });

    }

    public void checkEvent(View v) {
        final int eventId = 10;
        KWSChildren.sdk.hasTriggeredEvent(this, eventId, new KWSChildrenHasTriggeredEventInterface() {
            @Override
            public void didTriggerEvent(Boolean triggered) {
                log += "Event " + String.valueOf(eventId) + " is : " + triggered + "\n";
                logView.setText(log);
                Log.d("SuperAwesome ", String.valueOf(triggered));
            }
        });

    }

    public void setAppData(View v) {

        final int value = 33;
        final String name = "new_val";

        KWSChildren.sdk.setAppData(this, value, "new_val", new KWSChildrenSetAppDataInterface() {
            @Override
            public void didSetAppData(boolean success) {
                log += "Set " + name + " = " + value + " with " + success + "\n";
                logView.setText(log);
                Log.d("SuperAwesome ", String.valueOf(success));
            }
        });
    }

    public void getAppData(View v) {
        KWSChildren.sdk.getAppData(this, new KWSChildrenGetAppDataInterface() {
            @Override
            public void didGetAppData(List<KWSAppData> appData) {
                String logHelp = "";
                if (appData.isEmpty()) {
                    log += "No data to show\n";
                } else {
                    log += "Get App Data response as:\n";
                    for (KWSAppData data : appData) {
                        log += "[" + data.name + "]=" + data.value + "\n";
                        logHelp += "[" + data.name + "]=" + data.value + "\n";
                    }
                }
                logView.setText(log);
                Log.d("SuperAwesome ", logHelp);
            }
        });
    }

}
