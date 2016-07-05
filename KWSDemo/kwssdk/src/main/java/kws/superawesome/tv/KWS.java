package kws.superawesome.tv;

import android.content.Context;
import android.media.RemoteController;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SAAlertInterface;
import tv.superawesome.lib.sautils.SAApplication;
import kws.superawesome.tv.kws.KWSParentEmail;
import kws.superawesome.tv.kws.KWSParentEmailInterface;
import kws.superawesome.tv.models.KWSMetadata;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWS implements KWSManagerInterface, PushManagerInterface, KWSParentEmailInterface {

    // singleton instance
    public static KWS sdk = new KWS();
    private KWS() {}

    // setup variables
    private String oauthToken;
    private String kwsApiUrl;
    private boolean stringPermissionPopup;
    private KWSInterface listener;
    private Context context;
    private KWSMetadata metadata;

    // <Setup> functions

    public void setup(Context context, String oauthToken, String kwsApiUrl, boolean stringPermissionPopup, KWSInterface listener) {
        this.context = context;
        this.oauthToken = oauthToken;
        this.kwsApiUrl = kwsApiUrl;
        this.listener = listener;
        this.stringPermissionPopup = stringPermissionPopup;
        this.metadata = getMetadata(oauthToken);
        if (metadata != null) {
            JSONObject smetadata = metadata.writeToJson();
            Log.d("SuperAwesome", smetadata.toString());
        }
    }

    // <Public> functions

    public void checkIfNotificationsAreAllowed () {
        // add listener
        KWSManager.sharedInstance.listener = this;

        if (stringPermissionPopup) {
            SAAlert permissionAlert = new SAAlert();
            permissionAlert.show(context, "Hey!", "Do you want to enable Remote Notifications?", "Yes", "No", false, 0, new SAAlertInterface() {
                @Override
                public void didClickOnOK(String s) {
                    KWSManager.sharedInstance.checkIfNotficationsAreAllowed();
                }

                @Override
                public void didClickOnNOK() {
                    // do nothing
                }
            });
        } else {
            KWSManager.sharedInstance.checkIfNotficationsAreAllowed();
        }
    }

    public void showParentEmailPopup () {
        SAAlert emailAlert = new SAAlert();
        emailAlert.show(context, "Hey!", "To enable Remote Notifications in KWS you'll need to provide a parent email", "Submit", "Cancel", true, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, new SAAlertInterface() {
            @Override
            public void didClickOnOK(String s) {
                submitParentEmail(s);
            }

            @Override
            public void didClickOnNOK() {
                // do nothing
            }
        });
    }

    public void submitParentEmail (String  email) {
        KWSParentEmail parentEmail = new KWSParentEmail();
        parentEmail.listener = this;
        parentEmail.submitEmail(email);
    }

    public void registerForRemoteNotifications () {
        PushManager.sharedInstance.listener = this;
        PushManager.sharedInstance.registerForPushNotifications();
    }

    public void unregisterForRemoteNotifications () {
        PushManager.sharedInstance.unregisterForRemoteNotifications();
    }

    // <KWSManagerInterface>

    @Override
    public void pushDisabledInKWS() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.NoKWSPermission);
    }

    @Override
    public void parentEmailIsMissingInKWS() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.ParentEmailNotFound);
    }

    @Override
    public void networkError() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.NetworkError);
    }

    @Override
    public void isAllowedToRegister() {
        lisKWSSDKDoesAllowUserToRegisterForRemoteNotifications();
    }

    @Override
    public void isAlreadyRegistered() {
        lisKWSSDKDidRegisterUserForRemoteNotifications();
    }

    // <KWSParentEmailInterface>

    @Override
    public void emailSubmittedInKWS() {
        lisKWSSDKDoesAllowUserToRegisterForRemoteNotifications();
    }

    @Override
    public void emailError() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.ParentEmailInvalid);
    }

    // <PushManagerInterface>

    @Override
    public void didRegister(String token) {
        lisKWSSDKDidRegisterUserForRemoteNotifications();
    }

    @Override
    public void didNotRegister() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.FirebaseCouldNotGetToken);
    }

    @Override
    public void didUnregister() {
        lisKWSSDKDidUnregisterUserForRemoteNotifications();
    }

    @Override
    public void didNotUnregister() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.CouldNotUnsubscribeInKWS);
    }

    // getters

    public String getOauthToken () {
        return oauthToken;
    }

    public String getKwsApiUrl () {
        return kwsApiUrl;
    }

    public KWSMetadata getMetadata () {
        return metadata;
    }

    // <Private> functions

    private KWSMetadata getMetadata(String  oauthToken) {

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

    // <Aux> functions

    public void setApplicationContext(Context _appContext) {
        SAApplication.setSAApplicationContext(_appContext);
    }

    public Context getApplicationContext() {
        return SAApplication.getSAApplicationContext();
    }

    // <Listener> functions

    void lisKWSSDKDoesAllowUserToRegisterForRemoteNotifications () {
        if (listener != null) {
            listener.kwsSDKDoesAllowUserToRegisterForRemoteNotifications();
        }
    }

    void lisKWSSDKDidRegisterUserForRemoteNotifications () {
        if (listener != null) {
            listener.kwsSDKDidRegisterUserForRemoteNotifications();
        }
    }

    void lisKWSSDKDidUnregisterUserForRemoteNotifications () {
        if (listener != null) {
            listener.kwsSDKDidUnregisterUserForRemoteNotifications();
        }
    }

    void lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError (KWSErrorType type) {
        if (listener != null) {
            listener.kwsSDKDidFailToRegisterUserForRemoteNotificationsWithError(type);
        }
    }
}
