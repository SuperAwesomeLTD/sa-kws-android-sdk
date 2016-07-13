package kws.superawesome.tv.kwssdk;

import android.content.Context;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SAAlertInterface;
import tv.superawesome.lib.sautils.SAApplication;
import kws.superawesome.tv.kwssdk.kws.KWSParentEmail;
import kws.superawesome.tv.kwssdk.kws.KWSParentEmailInterface;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;

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
    private KWSParentEmail parentEmail;

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

        KWSManager.sharedInstance.listener = this;
        PushManager.sharedInstance.listener = this;
        parentEmail = new KWSParentEmail();
        parentEmail.listener = this;
    }

    // <Public> functions

    public void registerForRemoteNotifications () {

        // add listener
        if (stringPermissionPopup) {
            SAAlert.getInstance().show(context, "Hey!", "Do you want to enable Remote Notifications?", "Yes", "No", false, 0, new SAAlertInterface() {
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
        SAAlert.getInstance().show(context, "Hey!", "To enable Remote Notifications in KWS you'll need to provide a parent email", "Submit", "Cancel", true, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, new SAAlertInterface() {
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
        parentEmail.submitEmail(email);
    }

    public void unregisterForRemoteNotifications () {
        PushManager.sharedInstance.unregisterForRemoteNotifications();
    }

    // <KWSManagerInterface>

    @Override
    public void pushDisabledInKWS() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.ParentHasDisabledRemoteNotifications);
    }

    @Override
    public void parentEmailIsMissingInKWS() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.UserHasNoParentEmail);
    }

    @Override
    public void networkErrorCheckingForKWS() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.FailedToCheckIfUserHasNotificationsEnabledInKWS);
    }

    @Override
    public void networkErrorRequestingPermissionFromKWS() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.FailedToRequestNotificationsPermissionInKWS);
    }

    @Override
    public void isAllowedToRegister() {
        PushManager.sharedInstance.registerForPushNotifications();
    }

    // <KWSParentEmailInterface>

    @Override
    public void emailSubmittedInKWS() {
        PushManager.sharedInstance.registerForPushNotifications();
    }

    @Override
    public void emailError() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.FailedToSubmitParentEmail);
    }

    @Override
    public void invalidEmail() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.ParentEmailInvalid);
    }

    // <PushManagerInterface>

    @Override
    public void didRegister(String token) {
        lisKWSSDKDidRegisterUserForRemoteNotifications();
    }

    @Override
    public void didUnregister() {
        lisKWSSDKDidUnregisterUserForRemoteNotifications();
    }

    @Override
    public void didFailBecauseNoGoogleServicesFound() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.FirebaseNotSetup);
    }

    @Override
    public void didFailToGetFirebaseToken() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.FirebaseCouldNotGetToken);
    }

    @Override
    public void networkErrorTryingToSubscribeToken() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.FailedToSubscribeTokenToKWS);
    }

    @Override
    public void networkErrorTryingToUnsubscribeToken() {
        lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType.FailedToUbsubscribeTokenToKWS);
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

    // <Listener> functions

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
