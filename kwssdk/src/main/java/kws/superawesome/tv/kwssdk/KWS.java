package kws.superawesome.tv.kwssdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import kws.superawesome.tv.kwssdk.models.KWSMetadata;
import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.models.user.KWSUser;
import kws.superawesome.tv.kwssdk.process.KWSAuthUserProcess;
import kws.superawesome.tv.kwssdk.process.KWSAuthUserProcessInterface;
import kws.superawesome.tv.kwssdk.process.KWSCreateUserProcess;
import kws.superawesome.tv.kwssdk.process.KWSCreateUserProcessInterface;
import kws.superawesome.tv.kwssdk.process.KWSIsRegisteredInterface;
import kws.superawesome.tv.kwssdk.process.KWSNotificationProcess;
import kws.superawesome.tv.kwssdk.process.KWSNotificationStatus;
import kws.superawesome.tv.kwssdk.process.KWSRegisterInterface;
import kws.superawesome.tv.kwssdk.process.KWSUnregisterInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSCreateUser;
import kws.superawesome.tv.kwssdk.services.kws.KWSCreateUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetAppData;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetAppDataInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetLeaderboard;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetLeaderboardInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetScore;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetScoreInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetUser;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSHasTriggeredEvent;
import kws.superawesome.tv.kwssdk.services.kws.KWSHasTriggeredEventInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSInviteUser;
import kws.superawesome.tv.kwssdk.services.kws.KWSInviteUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSParentEmail;
import kws.superawesome.tv.kwssdk.services.kws.KWSParentEmailInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSPermissionType;
import kws.superawesome.tv.kwssdk.services.kws.KWSRequestPermission;
import kws.superawesome.tv.kwssdk.services.kws.KWSRequestPermissionInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSSetAppData;
import kws.superawesome.tv.kwssdk.services.kws.KWSSetAppDataInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSTriggerEvent;
import kws.superawesome.tv.kwssdk.services.kws.KWSTriggerEventInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSUpdateUser;
import kws.superawesome.tv.kwssdk.services.kws.KWSUpdateUserInterface;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SAAlertInterface;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWS {

    // singleton instance
    public static KWS sdk = new KWS();

    // startSession variables
    private String kwsApiUrl;
    private String clientId;
    private String clientSecret;
    private KWSLoggedUser loggedUser;

    // internal services & processes
    private KWSNotificationProcess notificationProcess;
    private KWSCreateUserProcess createUserProcess;
    private KWSAuthUserProcess authUserProcess;
    private KWSParentEmail parentEmail;
    private KWSGetUser getUser;
    private KWSGetLeaderboard getLeaderboard;
    private KWSRequestPermission requestPermission;
    private KWSTriggerEvent triggerEvent;
    private KWSGetScore getScore;
    private KWSInviteUser inviteUser;
    private KWSHasTriggeredEvent hasTriggeredEvent;
    private KWSGetAppData getAppData;
    private KWSSetAppData setAppData;
    private KWSUpdateUser updateUser;

    // prefferences
    private static final String LOGGED_USER_KEY = "KWS_SA_LOGGED_USER";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    // private constructor

    private KWS() {
        notificationProcess = new KWSNotificationProcess();
        createUserProcess = new KWSCreateUserProcess();
        authUserProcess = new KWSAuthUserProcess();
        parentEmail = new KWSParentEmail();
        getUser = new KWSGetUser();
        getLeaderboard = new KWSGetLeaderboard();
        requestPermission = new KWSRequestPermission();
        triggerEvent = new KWSTriggerEvent();
        getScore = new KWSGetScore();
        inviteUser = new KWSInviteUser();
        hasTriggeredEvent = new KWSHasTriggeredEvent();
        getAppData = new KWSGetAppData();
        setAppData = new KWSSetAppData();
        updateUser = new KWSUpdateUser();
    }

    // <Setup> and <Desetup> functions

    public void startSession (Context context, String clientId, String clientSecret, String apiUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.kwsApiUrl = apiUrl;

        // get preferences
        preferences = context.getSharedPreferences(LOGGED_USER_KEY, 0);
        editor = preferences.edit();

        if (preferences.contains(LOGGED_USER_KEY)) {
            String loggedUserString = preferences.getString(LOGGED_USER_KEY, "{}");
            JSONObject loggedUserJson = SAJsonParser.newObject(loggedUserString);
            KWSLoggedUser tmpUser = new KWSLoggedUser(loggedUserJson);

            if (tmpUser.isValid()) {
                loggedUser = tmpUser;
                Log.d("KWS", "KWS started with logged usser " + loggedUser.metadata.userId);
            } else {
                Log.d("KWS", "KWS started with a logged user that had an expired OAuth token. Clearning cache!");
                editor.remove(LOGGED_USER_KEY);
                editor.apply();
            }
        } else {
            Log.d("KWS", "KWS started without a logged user since none was found");
        }
    }

    public void stopSession () {
        this.kwsApiUrl = null;
        this.loggedUser = null;
        this.clientSecret = null;
        this.clientId = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Public exposed functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    // user creation, auth & logout
    public void createUser (Context context, String username, String password, String dateOfBirth, String country, String parentEmail, KWSCreateUserProcessInterface listener) {
        createUserProcess.create(context, username, password, dateOfBirth, country, parentEmail, listener);
    }

    public void loginUser(Context context, String username, String password, KWSAuthUserProcessInterface listener) {
        authUserProcess.auth(context, username, password, listener);
    }

    public void logoutUser (Context context) {
        // get preferences
        preferences = context.getSharedPreferences(LOGGED_USER_KEY, 0);
        editor = preferences.edit();
        this.loggedUser = null;
        if (preferences.contains(LOGGED_USER_KEY)) {
            editor.remove(LOGGED_USER_KEY);
            editor.apply();
        }
    }

    // user details
    public void getUser (Context context, KWSGetUserInterface listener) {
        getUser.execute(context, listener);
    }

    public void updateUser(Context context, KWSUser updatedUser, KWSUpdateUserInterface listener) {
        updateUser.execute(context, updatedUser, listener);
    }

    // permissions
    public void submitParentEmail (Context context, String  email, KWSParentEmailInterface listener) {
        parentEmail.execute(context, email, listener);
    }

    public void requestPermission (Context context, KWSPermissionType[] requestedPermissions, KWSRequestPermissionInterface listener) {
        requestPermission.execute(context, requestedPermissions, listener);
    }

    // invite another user
    public void inviteUser (Context context, String emailAddress, KWSInviteUserInterface listener) {
        inviteUser.execute(context, emailAddress, listener);
    }

    // events, points, leader boards

    public void triggerEvent(Context context, String token, int points, KWSTriggerEventInterface listener) {
        triggerEvent.execute(context, token, points, listener);
    }

    public void hasTriggeredEvent (Context context, int eventId, KWSHasTriggeredEventInterface listener) {
        hasTriggeredEvent.execute(context, eventId, listener);
    }

    public void getScore(Context context, KWSGetScoreInterface listener) {
        getScore.execute(context, listener);
    }

    public void getLeaderBoard (Context context, KWSGetLeaderboardInterface listener) {
        getLeaderboard.execute(context, listener);
    }

    // app data

    public void getAppData (Context context, KWSGetAppDataInterface listener) {
        getAppData.execute(context, listener);
    }

    public void setAppData(Context context, String name, int value, KWSSetAppDataInterface listener) {
        setAppData.execute(context, name, value, listener);
    }

    // handle remote notifications

    public void register (Context context, final KWSRegisterInterface listener) {
        notificationProcess.register(context, new KWSRegisterInterface() {
            @Override
            public void register(KWSNotificationStatus status) {
                if (status == KWSNotificationStatus.Success && loggedUser != null) {
                    loggedUser.setIsRegisteredForNotifications(true);
                }
                if (listener != null) {
                    listener.register(status);
                }
            }
        });
    }

    public void unregister (Context context, final KWSUnregisterInterface listener) {
        notificationProcess.unregister(context, new KWSUnregisterInterface() {
            @Override
            public void unregister(boolean unregistered) {
                if (unregistered && loggedUser != null && loggedUser.isRegisteredForNotifications()) {
                    loggedUser.setIsRegisteredForNotifications(false);
                }
                if (listener != null) {
                    listener.unregister(unregistered);
                }
            }
        });
    }

    public void isRegistered (Context context, final KWSIsRegisteredInterface listener) {
        notificationProcess.isRegistered(context, new KWSIsRegisteredInterface() {
            @Override
            public void isRegistered(boolean registered) {
                if (loggedUser != null) {
                    loggedUser.setIsRegisteredForNotifications(registered);
                }
                if (listener != null) {
                    listener.isRegistered(registered);
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Aux helper functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void registerWithPopup (final Context context, final KWSRegisterInterface listener) {
        SAAlert.getInstance().show(context, "Hey!", "Do you want to enable Remote Notifications?", "Yes", "No", false, 0, new SAAlertInterface() {
            @Override
            public void pressed(int button, String s) {
                if (button == SAAlert.OK_BUTTON) {
                    register(context, listener);
                }
            }
        });
    }

    public void submitParentEmailWithPopup (final Context context, final KWSParentEmailInterface listener) {
        SAAlert.getInstance().show(context, "Hey!", "To enable Remote Notifications in KWS you'll need to provide a parent email", "Submit", "Cancel", true, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, new SAAlertInterface() {
            @Override
            public void pressed(int button, String email) {
                if (button == SAAlert.OK_BUTTON) {
                    submitParentEmail(context, email, listener);
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters & Setters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getVersion () {
        return "android-2.1.3";
    }

    public String getKwsApiUrl () {
        return kwsApiUrl;
    }

    public String getClientId () {
        return clientId;
    }

    public String getClientSecret () {
        return clientSecret;
    }

    public KWSLoggedUser getLoggedUser () {
        return loggedUser;
    }

    public void setLoggedUser (KWSLoggedUser loggedUser) {
        // assign the logged user
        this.loggedUser = loggedUser;

        // save in preferences
        if (editor != null) {
            editor.putString(LOGGED_USER_KEY, loggedUser.writeToJson().toString());
            editor.apply();
        }
    }
}
