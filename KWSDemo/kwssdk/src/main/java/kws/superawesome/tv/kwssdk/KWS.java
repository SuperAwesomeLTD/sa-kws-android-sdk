package kws.superawesome.tv.kwssdk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import kws.superawesome.tv.kwssdk.process.IsRegisteredInterface;
import kws.superawesome.tv.kwssdk.process.NotificationProcess;
import kws.superawesome.tv.kwssdk.process.RegisterInterface;
import kws.superawesome.tv.kwssdk.process.UnregisterInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetLeaderboard;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetLeaderboardInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetUser;
import kws.superawesome.tv.kwssdk.services.kws.KWSGetUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.KWSPermissionType;
import kws.superawesome.tv.kwssdk.services.kws.KWSRequestPermission;
import kws.superawesome.tv.kwssdk.services.kws.KWSRequestPermissionInterface;
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SAAlertInterface;
import tv.superawesome.lib.sautils.SAApplication;
import kws.superawesome.tv.kwssdk.services.kws.KWSParentEmail;
import kws.superawesome.tv.kwssdk.services.kws.KWSParentEmailInterface;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWS {

    // singleton instance
    public static KWS sdk = new KWS();

    // private constructor
    private KWS() {
        notificationProcess = new NotificationProcess();
        parentEmail = new KWSParentEmail();
        getUser = new KWSGetUser();
        getLeaderboard = new KWSGetLeaderboard();
        requestPermission = new KWSRequestPermission();
    }

    // setup variables
    private String oauthToken;
    private String kwsApiUrl;
    private Context context;
    private KWSMetadata metadata;

    // internal services & processes
    private NotificationProcess notificationProcess;
    private KWSParentEmail parentEmail;
    private KWSGetUser getUser;
    private KWSGetLeaderboard getLeaderboard;
    private KWSRequestPermission requestPermission;

    public String getVersion () {
        return "android-1.2.2";
    }

    // <Setup> functions

    public void setup(Context context, String oauthToken, String kwsApiUrl) {
        this.context = context;
        this.oauthToken = oauthToken;
        this.kwsApiUrl = kwsApiUrl;
        this.metadata = processMetadata(oauthToken);
        if (metadata != null) {
            JSONObject smetadata = metadata.writeToJson();
            Log.d("SuperAwesome", smetadata.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Public exposed functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void register (@NonNull final RegisterInterface listener) {
        notificationProcess.register(listener);
    }

    public void unregister (@NonNull UnregisterInterface listener) {
        notificationProcess.unregister(listener);
    }

    public void isRegistered (@NonNull IsRegisteredInterface listener) {
        notificationProcess.isRegistered(listener);
    }

    public void submitParentEmail (@NonNull String  email, @NonNull KWSParentEmailInterface listener) {
        parentEmail.execute(email, listener);
    }

    public void getUser (@NonNull KWSGetUserInterface listener) {
        getUser.execute(listener);
    }

    public void getLeaderBoard (@NonNull KWSGetLeaderboardInterface listener) {
        getLeaderboard.execute(listener);
    }

    public void requestPermission (KWSPermissionType[] requestedPermissions, @NonNull KWSRequestPermissionInterface listener) {
        requestPermission.execute(requestedPermissions, listener);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Aux helper functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void registerWithPopup (@NonNull final RegisterInterface listener) {
        SAAlert.getInstance().show(context, "Hey!", "Do you want to enable Remote Notifications?", "Yes", "No", false, 0, new SAAlertInterface() {
            @Override
            public void pressed(int button, String s) {
                if (button == SAAlert.OK_BUTTON) {
                    register(listener);
                }
            }
        });
    }

    public void submitParentEmailWithPopup (@NonNull final KWSParentEmailInterface listener) {
        SAAlert.getInstance().show(context, "Hey!", "To enable Remote Notifications in KWS you'll need to provide a parent email", "Submit", "Cancel", true, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, new SAAlertInterface() {
            @Override
            public void pressed(int button, String email) {
                if (button == SAAlert.OK_BUTTON) {
                    submitParentEmail(email, listener);
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters & Setters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getOauthToken () {
        return oauthToken;
    }

    public String getKwsApiUrl () {
        return kwsApiUrl;
    }

    public KWSMetadata getMetadata () {
        return metadata;
    }

    public void setApplicationContext(Context _appContext) {
        SAApplication.setSAApplicationContext(_appContext);
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
