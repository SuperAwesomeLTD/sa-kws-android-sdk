package kws.superawesome.tv.kwssdk.process;

import android.content.Context;
import android.util.Log;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.aux.KWSAux;
import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.services.kws.*;

/**
 * Created by gabriel.coman on 12/10/16.
 */
public class KWSAuthUserProcess {

    private KWSAuthUserProcessInterface listener;
    private KWSAuthUser authUser;
    private KWSGetAccessTokenAuth getAccessTokenAuth;

    public KWSAuthUserProcess () {
        listener = new KWSAuthUserProcessInterface() { @Override public void userAuthenticated(KWSAuthUserStatus status) {} };
        authUser = new KWSAuthUser();
        getAccessTokenAuth = new KWSGetAccessTokenAuth();
    }

    public void auth(final Context context,
                     final String username,
                     final String password,
                     KWSAuthUserProcessInterface listener)
    {
        // get vars
        this.listener = listener != null ? listener : this.listener;
        boolean usernameValid = validateUsername(username);
        boolean passwordValid = validatePassword(password);

        if (!usernameValid) {
            this.listener.userAuthenticated(KWSAuthUserStatus.InvalidCredentials);
            return;
        }

        if (!passwordValid) {
            this.listener.userAuthenticated(KWSAuthUserStatus.InvalidCredentials);
            return;
        }

        // get access token
        getAccessTokenAuth.execute(context, username, password, new KWSGetAccessTokenAuthInterface() {
            @Override
            public void gotToken(final String accessToken) {

                if (accessToken != null) {

                    // create a user
                    KWSLoggedUser loggedUser = new KWSLoggedUser();
                    loggedUser.username = username;
                    loggedUser.password = password;
                    loggedUser.accessToken = accessToken;
                    loggedUser.metadata = KWSAux.processMetadata(accessToken);

                    authUser.execute(context, loggedUser, new KWSAuthUserInterface() {
                        @Override
                        public void authUser(int status, KWSLoggedUser tmpUser) {

                            if (tmpUser != null) {

                                // process final user
                                KWSLoggedUser finalUser = new KWSLoggedUser();
                                finalUser.id = tmpUser.id;
                                finalUser.token = tmpUser.token;
                                finalUser.username = username;
                                finalUser.password = password;
                                finalUser.accessToken = accessToken;
                                finalUser.metadata = KWSAux.processMetadata(tmpUser.token);

                                // set final user
                                KWS.sdk.setLoggedUser(finalUser);

                                // send response
                                KWSAuthUserProcess.this.listener.userAuthenticated(KWSAuthUserStatus.Success);

                            } else {
                                KWSAuthUserProcess.this.listener.userAuthenticated(KWSAuthUserStatus.InvalidCredentials);
                            }
                        }
                    });

                } else {
                    KWSAuthUserProcess.this.listener.userAuthenticated(KWSAuthUserStatus.NetworkError);
                }

            }
        });
    }

    private boolean validateUsername (String username) {
        return KWSAux.checkRegex("^[a-zA-Z0-9]*$", username) && username.length() >= 3;
    }

    private boolean validatePassword (String password) {
        return password.length() >= 8;
    }
}
