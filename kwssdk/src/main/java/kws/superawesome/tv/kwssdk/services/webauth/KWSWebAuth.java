package kws.superawesome.tv.kwssdk.services.webauth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.KWSChildren;
import kws.superawesome.tv.kwssdk.base.webauth.KWSWebAuthResponse;
import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata;
import kws.superawesome.tv.kwssdk.services.KWSService;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sautils.SAUtils;

public class KWSWebAuth extends KWSService {

    private Interface listener;
    private String packageName;

    public KWSWebAuth() {
        this.listener = new Interface() { @Override public void didAuthUser(KWSLoggedUser user) {}};
    }

    @Override
    public String getEndpoint() {
        return "oauth";
    }

    @Override
    public JSONObject getQuery() {
        return SAJsonParser.newObject("clientId", KWSChildren.sdk.getClientId(),
                "redirectUri", packageName + "://");
    }

    public void execute (String singleSignOnUrl, Activity activity, final Interface listener) {

        //
        // get listener
        this.listener = listener != null ? listener : this.listener;

        //
        // get package name
        packageName = activity.getPackageName();

        //
        // form finalUrl & URI
        String finalUrl = singleSignOnUrl + getEndpoint() + "?" + SAUtils.formGetQueryFromDict(getQuery());
        Uri uri = Uri.parse(finalUrl);

        //
        // launch intent with browser
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);

        //
        // listen for resunts here
        KWSWebAuthResponse.listener = new KWSWebAuthResponse.Interface() {
            @Override
            public void didGetToken(String token) {

                if (token != null) {
                    KWSMetadata metadata = KWSMetadata.processMetadata(token);

                    if (metadata != null && metadata.isValid()) {
                        KWSLoggedUser loggedUser = new KWSLoggedUser();
                        loggedUser.token = token;
                        loggedUser.metadata = metadata;
                        if (listener != null) {
                            listener.didAuthUser(loggedUser);
                        }
                    }
                    else {
                        if (listener != null) {
                            listener.didAuthUser(null);
                        }
                    }
                }
                else {
                    if (listener != null) {
                        listener.didAuthUser(null);
                    }
                }
            }
        };
    }

    public interface Interface {
        void didAuthUser (KWSLoggedUser user);
    }
}
