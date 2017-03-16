package kws.superawesome.tv.kwssdk;

import android.content.Context;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import kws.superawesome.tv.kwssdk.kws.KWSParentEmail;
import kws.superawesome.tv.kwssdk.kws.KWSParentEmailInterface;
import kws.superawesome.tv.kwssdk.kws.KWSRandomNameInterface;
import kws.superawesome.tv.kwssdk.managers.CheckManager;
import kws.superawesome.tv.kwssdk.managers.CheckManagerInterface;
import kws.superawesome.tv.kwssdk.managers.KWSManager;
import kws.superawesome.tv.kwssdk.managers.KWSManagerInterface;
import kws.superawesome.tv.kwssdk.managers.KWSRandomNameManager;
import kws.superawesome.tv.kwssdk.managers.PushManager;
import kws.superawesome.tv.kwssdk.managers.PushManagerInterface;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SAAlertInterface;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWS implements KWSManagerInterface, PushManagerInterface, CheckManagerInterface, KWSParentEmailInterface {

    // singleton instance
    public static KWS sdk = new KWS();
    private KWS() {}

    // registerToken variables
    private String oauthToken;
    private String kwsApiUrl;
    private String clientId;
    private boolean stringPermissionPopup;
    private Context context;
    private KWSMetadata metadata;
    private KWSParentEmail parentEmail;
    private KWSRandomNameManager randomNameManager;

    // listeners
    private KWSRegisterInterface registerListener;
    private KWSUnregisterInterface unregisterListener;
    private KWSCheckInterface checkListener;

    public String getVersion () {
        return "android-1.2.3.2";
    }

    // <Setup> functions

    public void setup (String kwsApiUrl, String clientId) {
        this.kwsApiUrl = kwsApiUrl;
        this.clientId = clientId;
    }

    public void registerToken(Context context, String oauthToken, boolean stringPermissionPopup) {
        this.context = context;
        this.oauthToken = oauthToken;
        this.stringPermissionPopup = stringPermissionPopup;
        this.metadata = getMetadata(oauthToken);
        if (metadata != null) {
            JSONObject smetadata = metadata.writeToJson();
            Log.d("SuperAwesome", smetadata.toString());
        }

        KWSManager.sharedInstance.listener = this;
        PushManager.sharedInstance.listener = this;
        CheckManager.sharedInstance.listener = this;

        parentEmail = new KWSParentEmail();
        parentEmail.listener = this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Public exposed functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void getRandomName (Context context, KWSRandomNameInterface listener) {
        randomNameManager = new KWSRandomNameManager();
        randomNameManager.getRandomName(context, listener);
    }

    // Main public functions

    public void registerForRemoteNotifications (KWSRegisterInterface registerListener) {
        // get listener
        this.registerListener = registerListener;

        // add registerListener
        if (stringPermissionPopup) {

            SAAlert.getInstance().show(context, "Hey!", "Do you want to enable Remote Notifications?", "Yes", "No", false, 0, new SAAlertInterface() {
                @Override
                public void saDidClickOnAlertButton(int button, String s) {
                    if (button == SAAlert.OK_BUTTON) {
                        KWSManager.sharedInstance.checkIfNotficationsAreAllowed(context);
                    }
                }
            });
        } else {
            KWSManager.sharedInstance.checkIfNotficationsAreAllowed(context);
        }
    }

    public void unregisterForRemoteNotifications (KWSUnregisterInterface unregisterInterface) {
        this.unregisterListener = unregisterInterface;
        PushManager.sharedInstance.unregisterForRemoteNotifications(context);
    }

    public void userIsRegistered (KWSCheckInterface listener) {
        checkListener = listener;
        CheckManager.sharedInstance.areNotificationsEnabled(context);
    }

    // Aux main functions

    public void showParentEmailPopup () {
        SAAlert.getInstance().show(context, "Hey!", "To enable Remote Notifications in KWS you'll need to provide a parent email", "Submit", "Cancel", true, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, new SAAlertInterface() {
            @Override
            public void saDidClickOnAlertButton(int button, String s) {
                if (button == SAAlert.OK_BUTTON) {
                    submitParentEmail(s);
                }
            }
        });
    }

    public void submitParentEmail (String  email) {
        parentEmail.execute(context, email);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Implementation of all these listeners
    ////////////////////////////////////////////////////////////////////////////////////////////////

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
        PushManager.sharedInstance.registerForPushNotifications(context);
    }

    // <KWSParentEmailInterface>

    @Override
    public void emailSubmittedInKWS() {
        PushManager.sharedInstance.registerForPushNotifications(context);
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
        lisKWSSDKDidFailToUnregisterUserForRemoteNotifications();
    }

    // <CheckManagerInterface> implementation

    @Override
    public void pushAllowedOverall() {
        lisKWSSDKUserIsRegistered();
    }

    @Override
    public void pushDisabledOverall() {
        lisKWSSDKUserIsNotRegistered();
    }

    @Override
    public void networkErrorTryingToCheckUserStatus() {
        lisKWSSDKDidFailToCheckIfUserIsRegistered();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getOauthToken () {
        return oauthToken;
    }

    public String getKwsApiUrl () {
        return kwsApiUrl;
    }

    public String getClientId () {
        return clientId;
    }

    public KWSMetadata getMetadata () {
        return metadata;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Private metadata function
    ////////////////////////////////////////////////////////////////////////////////////////////////

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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Listener functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    // <Listener 1> functions

    void lisKWSSDKDidRegisterUserForRemoteNotifications () {
        if (registerListener != null) {
            registerListener.kwsSDKDidRegisterUserForRemoteNotifications();
        }
    }


    void lisKWSSDKDidFailToRegisterUserForRemoteNotificationsWithError (KWSErrorType type) {
        if (registerListener != null) {
            registerListener.kwsSDKDidFailToRegisterUserForRemoteNotificationsWithError(type);
        }
    }

    // <Listener 2> functions

    void lisKWSSDKDidUnregisterUserForRemoteNotifications () {
        if (unregisterListener != null) {
            unregisterListener.kwsSDKDidUnregisterUserForRemoteNotifications();
        }
    }

    void lisKWSSDKDidFailToUnregisterUserForRemoteNotifications () {
        if (unregisterListener != null) {
            unregisterListener.kwsSDKDidFailToUnregisterUserForRemoteNotifications();
        }
    }

    // <Listener 3> functions

    void lisKWSSDKUserIsRegistered () {
        if (checkListener != null) {
            checkListener.kwsSDKUserIsRegistered();
        }
    }

    void lisKWSSDKUserIsNotRegistered () {
        if (checkListener != null) {
            checkListener.kwsSDKUserIsNotRegistered();
        }
    }

    void lisKWSSDKDidFailToCheckIfUserIsRegistered () {
        if (checkListener != null) {
            checkListener.kwsSDKDidFailToCheckIfUserIsRegistered();
        }
    }
}
