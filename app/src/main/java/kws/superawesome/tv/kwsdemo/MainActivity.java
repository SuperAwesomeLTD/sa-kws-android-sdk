package kws.superawesome.tv.kwsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import kws.superawesome.tv.kwssdk.process.KWSChildrenLoginUserInterface;
import kws.superawesome.tv.kwssdk.process.KWSChildrenLoginUserStatus;
import kws.superawesome.tv.kwssdk.process.KWSChildrenCreateUserInterface;
import kws.superawesome.tv.kwssdk.process.KWSChildrenCreateUserStatus;
import kws.superawesome.tv.kwssdk.process.KWSChildrenIsRegisteredForRemoteNotificationsInterface;
import kws.superawesome.tv.kwssdk.process.KWSChildrenRegisterForRemoteNotificationsInterface;
import kws.superawesome.tv.kwssdk.process.KWSChildrenUnregisterForRemoteNotificationsInterface;
import kws.superawesome.tv.kwssdk.services.kws.appdata.KWSChildrenGetAppDataInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSChildrenGetLeaderboardInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSChildrenGetScoreInterface;
import kws.superawesome.tv.kwssdk.services.kws.user.KWSChildrenGetUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSChildrenHasTriggeredEventInterface;
import kws.superawesome.tv.kwssdk.services.kws.user.KWSChildrenInviteUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.parentemail.KWSChildrenUpdateParentEmailInterface;
import kws.superawesome.tv.kwssdk.services.kws.parentemail.KWSChildrenUpdateParentEmailStatus;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenRequestPermissionStatus;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenPermissionType;
import kws.superawesome.tv.kwssdk.services.kws.randomname.KWSChildrenGetRandomUsernameInterface;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenRequestPermissionInterface;
import kws.superawesome.tv.kwssdk.services.kws.appdata.KWSChildrenSetAppDataInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSChildrenTriggerEventInterface;
import tv.superawesome.lib.sautils.SAUtils;
import kws.superawesome.tv.kwssdk.process.KWSChildrenRegisterForRemoteNotificationsStatus;

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

        KWSChildren.sdk.createUser(this, username, "testtest", "2011-03-02", "US", "dev.gabriel.coman@gmail.com", new KWSChildrenCreateUserInterface() {
            @Override
            public void didCreateUser(KWSChildrenCreateUserStatus status) {
                switch (status) {
                    case Success:
                        log += "User " + username + " created OK\n";
                        break;
                    case InvalidUsername:
                        log += "InvalidEmail username\n";
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
                        log += "Duplicate username\n";
                        break;
                    case NetworkError:
                        log += "Network error\n";
                        break;
                    case InvalidOperation:
                        log += "InvalidEmail operation\n";
                        break;
                }

                logView.setText(log);
            }
        });
    }

    public void loginUser(View v) {

        KWSChildren.sdk.loginUser(this, "testuser9112", "testtest", new KWSChildrenLoginUserInterface() {
            @Override
            public void didLoginUser(KWSChildrenLoginUserStatus status) {
                switch (status) {
                    case Success:
                        log += "Auth as testuser9112 w/ testtest\n";
                        Log.d("SuperAwesome", KWSChildren.sdk.getLoggedUser().writeToJson() + "");
                        break;
                    case NetworkError:
                        log += "Network error authing\n";
                        break;
                    case InvalidCredentials:
                        log += "Invalid Email credentials\n";
                        break;
                }

                logView.setText(log);
            }
        });
    }

    public void authUser (View v) {

        KWSChildren.sdk.authUser("https://stan-test-cluster.accounts.kws.superawesome.tv/", this, new KWSChildrenLoginUserInterface() {
            @Override
            public void didLoginUser(KWSChildrenLoginUserStatus status) {
                switch (status) {
                    case Success:
                        log += "Auth user!\n";
                        Log.d("SuperAwesome", KWSChildren.sdk.getLoggedUser().writeToJson() + "");
                        break;
                    case NetworkError:
                        log += "Network error authing\n";
                        break;
                    case InvalidCredentials:
                        log += "Invalid Email credentials\n";
                        break;
                }

                logView.setText(log);
            }
        });

    }

//    public void newAuthUser(View v) {
//
//
//        KWSChildren.sdk.loginUser("testuser9112", "testtest", new K() {
//            @Override
//            public void didLoginUser( LoggedUser loggedUser, Throwable error) {
//                if(loggedUser != null){
//                    log += "Auth as stanajdkfa w/ testtest\n";
//                    Log.d("SuperAwesome", KWSChildren.sdk.getLoggedUser().writeToJson() + "");
//                }else if(error != null){
//                    log += "Network error authing\n";
//                }
//
//                logView.setText(log);
//            }
//        });
//    }

    public void logoutUser(View v) {
        KWSChildren.sdk.logoutUser(this);
    }

    public void generateRandomName(View v) {
        KWSChildren.sdk.getRandomUsername(this, new KWSChildrenGetRandomUsernameInterface() {
            @Override
            public void didGetRandomUsername(String name) {
                log += "Random name " + name + "\n";
                logView.setText(log);
            }
        });
    }

    public void registerAction(View v) {
        log += "Trying to registerForRemoteNotifications user\n";
        logView.setText(log);


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
                    log += "Local user is " + KWSChildren.sdk.getLoggedUser().isRegisteredForNotifications() + "\n";
                }
                logView.setText(log);

            }
        });
    }

    public void unregisterAction(View v) {
        log += "Trying to unregisterForRemoteNotifications user\n";
        logView.setText(log);
        KWSChildren.sdk.unregisterForRemoteNotifications(this, new KWSChildrenUnregisterForRemoteNotificationsInterface() {
            @Override
            public void didUnregisterForRemoteNotifications(boolean unregistered) {
                log += unregistered ? "User is un-registered\n" : "Network error unsubscribing Firebase token to KWSChildren\n";
                if (KWSChildren.sdk.getLoggedUser() != null) {
                    log += "Local user is " + KWSChildren.sdk.getLoggedUser().isRegisteredForNotifications() + "\n";
                }
                logView.setText(log);
            }
        });
    }

    public void checkRegisteredAction(View v) {

        log += "Checking if user is registered or not\n";
        logView.setText(log);
        KWSChildren.sdk.isRegisteredForRemoteNotifications(this, new KWSChildrenIsRegisteredForRemoteNotificationsInterface() {
            @Override
            public void isRegisteredForRemoteNotifications(boolean registered) {
                log += registered ? "User is already registered\n" : "User is not registered\n";
                if (KWSChildren.sdk.getLoggedUser() != null) {
                    log += "Local user is " + KWSChildren.sdk.getLoggedUser().isRegisteredForNotifications() + "\n";
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
            }
        });


    }

    public void triggerEvent(View v) {
        KWSChildren.sdk.triggerEvent(this, "a7tzV7QLhlR0rS8KK98QcZgrQk3ur260", 20, new KWSChildrenTriggerEventInterface() {
            @Override
            public void didTriggerEvent(boolean success) {
                log += "Triggered evt: " + success + "\n";
                logView.setText(log);
            }
        });
    }

    public void getScore(View v) {
        KWSChildren.sdk.getScore(this, new KWSChildrenGetScoreInterface() {
            @Override
            public void didGetScore(KWSScore score) {
                log += score != null ? "Rank: " + score.rank + " Score: " + score.score + "\n" : "Could not get score\n";
                logView.setText(log);
            }
        });
    }

    public void inviteUser(View v) {
        KWSChildren.sdk.inviteUser(this, "gabriel.coman@superawesome.tv", new KWSChildrenInviteUserInterface() {
            @Override
            public void didInviteUser(boolean success) {
                log += "Invited gabriel.coman@superawesome.tv " + success + "\n";
                logView.setText(log);
            }
        });

    }

    public void checkEvent(View v) {
        KWSChildren.sdk.hasTriggeredEvent(this, 762, new KWSChildrenHasTriggeredEventInterface() {
            @Override
            public void didTriggerEvent(Boolean triggered) {
                log += "Event 762 is : " + triggered + "\n";
                logView.setText(log);
            }
        });

    }

    public void setAppData(View v) {
        KWSChildren.sdk.setAppData(this, 33, "new_val", new KWSChildrenSetAppDataInterface() {
            @Override
            public void didSetAppData(boolean success) {
                log += "Set new_val=33 with " + success + "\n";
                logView.setText(log);
            }
        });
    }

    public void getAppData(View v) {
        KWSChildren.sdk.getAppData(this, new KWSChildrenGetAppDataInterface() {
            @Override
            public void didGetAppData(List<KWSAppData> appData) {
                for (KWSAppData data : appData) {
                    log += "[" + data.name + "]=" + data.value + "\n";
                }
                logView.setText(log);
            }
        });
    }

}
