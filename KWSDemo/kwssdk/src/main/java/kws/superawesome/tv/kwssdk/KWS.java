package kws.superawesome.tv.kwssdk;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import kws.superawesome.tv.kwslib.SAApplication;
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
    private KWSInterface listener;
    private KWSMetadata metadata;

    // <Setup> functions

    public void setup(String oauthToken, String kwsApiUrl, KWSInterface listener) {
        this.oauthToken = oauthToken;
        this.kwsApiUrl = kwsApiUrl;
        this.listener = listener;
        this.metadata = getMetadata(oauthToken);
        Log.d("SuperAwesome", metadata.writeToJson().toString());
    }

    // <Public> functions

    public void checkIfNotificationsAreAllowed () {
        KWSManager.sharedInstance.listener = this;
        KWSManager.sharedInstance.checkIfNotficationsAreAllowed();
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
        lisDidFailBecauseKWSDoesNotAllowRemoteNotifications();
    }

    @Override
    public void parentEmailIsMissingInKWS() {
        lisDidFailBecauseKWSCouldNotFindParentEmail();
    }

    @Override
    public void networkError() {
        lisDidFailBecauseOfError();
    }

    @Override
    public void isAllowedToRegister() {
        lisIsAllowedToRegisterForRemoteNotifications();
    }

    @Override
    public void isAlreadyRegistered() {
        lisIsAlreadyRegisteredForRemoteNotifications();
    }

    // <KWSParentEmailInterface>

    @Override
    public void emailSubmittedInKWS() {
        lisIsAllowedToRegisterForRemoteNotifications();
    }

    @Override
    public void emailError() {
        lisDidFailBecauseOfError();
    }

    // <PushManagerInterface>

    @Override
    public void didRegister(String token) {
        lisDidRegisterForRemoteNotifications(token);
    }

    @Override
    public void didNotRegister() {
        lisDidFailBecauseOfError();
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

            // form metadata object
            JSONObject jsonObject = new JSONObject(jsonData);
            return new KWSMetadata(jsonObject);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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

    void lisIsAllowedToRegisterForRemoteNotifications () {
        if (listener != null) {
            listener.isAllowedToRegisterForRemoteNotifications();
        }
    }

    void lisIsAlreadyRegisteredForRemoteNotifications() {
        if (listener != null) {
            listener.isAlreadyRegisteredForRemoteNotifications();
        }
    }

    void lisDidRegisterForRemoteNotifications (String token) {
        if (listener != null) {
            listener.didRegisterForRemoteNotifications(token);
        }
    }

    void lisDidFailBecauseKWSDoesNotAllowRemoteNotifications() {
        if (listener != null) {
            listener.didFailBecauseKWSDoesNotAllowRemoteNotifications();;
        }
    }

    void lisDidFailBecauseKWSCouldNotFindParentEmail() {
        if (listener != null) {
            listener.didFailBecauseKWSCouldNotFindParentEmail();
        }
    }

    void lisDidFailBecauseRemoteNotificationsAreDisabled() {
        if (listener != null) {
            listener.didFailBecauseRemoteNotificationsAreDisabled();
        }
    }

    void lisDidFailBecauseOfError() {
        if (listener != null) {
            listener.didFailBecauseOfError();
        }
    }
}
