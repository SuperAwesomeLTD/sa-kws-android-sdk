package kws.superawesome.tv.kwssdk;

import android.content.Context;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import kws.superawesome.tv.kwssdk.models.KWSMetadata;
import kws.superawesome.tv.kwssdk.models.user.KWSUser;
import kws.superawesome.tv.kwssdk.process.IsRegisteredInterface;
import kws.superawesome.tv.kwssdk.process.NotificationProcess;
import kws.superawesome.tv.kwssdk.process.RegisterInterface;
import kws.superawesome.tv.kwssdk.process.UnregisterInterface;
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
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SAAlertInterface;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWS {

    // singleton instance
    public static KWS sdk = new KWS();

    // startSession variables
    private String oauthToken;
    private String kwsApiUrl;
    private KWSMetadata metadata;

    // internal services & processes
    private NotificationProcess notificationProcess;
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
    private KWSCreateUser createUser;

    // private constructor

    private KWS() {
        notificationProcess = new NotificationProcess();
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
        createUser = new KWSCreateUser();
    }

    // <Setup> and <Desetup> functions

    public void startSession (String oauthToken, String kwsApiUrl) {
        this.oauthToken = oauthToken;
        this.kwsApiUrl = kwsApiUrl;
        if (oauthToken != null) {
            this.metadata = processMetadata(oauthToken);
        }
        if (metadata != null) {
            Log.d("SuperAwesome", metadata.writeToJson().toString());
        } else {
            Log.d("SuperAwesome", "Warning, could not startSession KWS. Probably invalid OAuth Token.");
        }
    }

    public void stopSession () {
        this.oauthToken = null;
        this.kwsApiUrl = null;
        this.metadata = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Public exposed functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void register (Context context, final RegisterInterface listener) {
        notificationProcess.register(context, listener);
    }

    public void unregister (Context context, UnregisterInterface listener) {
        notificationProcess.unregister(context, listener);
    }

    public void isRegistered (Context context, IsRegisteredInterface listener) {
        notificationProcess.isRegistered(context, listener);
    }

    public void createUser (Context context, String username, String password, String dateOfBirth, String country, KWSCreateUserInterface listener) {
        createUser.execute(context, username, password, dateOfBirth, country, listener);
    }

    public void submitParentEmail (Context context, String  email, KWSParentEmailInterface listener) {
        parentEmail.execute(context, email, listener);
    }

    public void getUser (Context context, KWSGetUserInterface listener) {
        getUser.execute(context, listener);
    }

    public void getLeaderBoard (Context context, KWSGetLeaderboardInterface listener) {
        getLeaderboard.execute(context, listener);
    }

    public void requestPermission (Context context, KWSPermissionType[] requestedPermissions, KWSRequestPermissionInterface listener) {
        requestPermission.execute(context, requestedPermissions, listener);
    }

    public void triggerEvent(Context context, String token, int points, String description, KWSTriggerEventInterface listener) {
        triggerEvent.execute(context, token, points, description, listener);
    }

    public void getScore(Context context, KWSGetScoreInterface listener) {
        getScore.execute(context, listener);
    }

    public void inviteUser (Context context, String emailAddress, KWSInviteUserInterface listener) {
        inviteUser.execute(context, emailAddress, listener);
    }

    public void hasTriggeredEvent (Context context, int eventId, KWSHasTriggeredEventInterface listener) {
        hasTriggeredEvent.execute(context, eventId, listener);
    }

    public void getAppData (Context context, KWSGetAppDataInterface listener) {
        getAppData.execute(context, listener);
    }

    public void setAppData(Context context, String name, int value, KWSSetAppDataInterface listener) {
        setAppData.execute(context, name, value, listener);
    }

    public void updateUser(Context context, KWSUser updatedUser, KWSUpdateUserInterface listener) {
        updateUser.execute(context, updatedUser, listener);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Aux helper functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void registerWithPopup (final Context context, final RegisterInterface listener) {
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
        return "android-2.0.6";
    }

    public String getOauthToken () {
        return oauthToken;
    }

    public String getKwsApiUrl () {
        return kwsApiUrl;
    }

    public KWSMetadata getMetadata () {
        return metadata;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Private metadata function
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private KWSMetadata processMetadata(String  oauthToken) {

        // get token
        String[] components = oauthToken.split("\\.");
        String tokenO = null;
        if (components.length >= 2) tokenO = components[1];
        if (tokenO == null) return null;

        // get JSON from base64 data
        byte[] data;
        try {
            data = Base64.decode(tokenO, Base64.DEFAULT);
        } catch (IllegalArgumentException e1) {
            try {
                tokenO += "=";
                data = Base64.decode(tokenO, Base64.DEFAULT);
            } catch (IllegalArgumentException e2) {
                try {
                    tokenO += "=";
                    data = Base64.decode(tokenO, Base64.DEFAULT);
                } catch (IllegalArgumentException e3){
                    return null;
                }
            }
        }

        try {
            String jsonData = new String(data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonData);
            return new KWSMetadata(jsonObject);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
