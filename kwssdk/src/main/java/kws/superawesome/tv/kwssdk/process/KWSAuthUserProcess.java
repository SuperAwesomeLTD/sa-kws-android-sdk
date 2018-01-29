package kws.superawesome.tv.kwssdk.process;

import android.content.Context;

import java.util.regex.Pattern;

import kws.superawesome.tv.kwssdk.KWSChildren;
import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.services.kws.auth.KWSAuthUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.auth.KWSAuthUser;

public class KWSAuthUserProcess {

    private KWSChildrenLoginUserInterface listener;
    private KWSAuthUser authUser;

    public KWSAuthUserProcess () {
        listener = new KWSChildrenLoginUserInterface() { @Override public void didLoginUser(KWSChildrenLoginUserStatus status) {} };
        authUser = new KWSAuthUser();
    }

    public void auth(final Context context,
                     final String username,
                     final String password,
                     KWSChildrenLoginUserInterface listener)
    {
        // get vars
        this.listener = listener != null ? listener : this.listener;
        final boolean usernameValid = validateUsername(username);
        boolean passwordValid = validatePassword(password);

        if (!usernameValid) {
            this.listener.didLoginUser(KWSChildrenLoginUserStatus.InvalidCredentials);
            return;
        }

        if (!passwordValid) {
            this.listener.didLoginUser(KWSChildrenLoginUserStatus.InvalidCredentials);
            return;
        }

        // get access token
        authUser.execute(context, username, password, new KWSAuthUserInterface() {
            @Override
            public void authUser(int status, KWSLoggedUser loggedUser) {

                if (loggedUser != null && loggedUser.isValid()) {

                    // set final user
                    KWSChildren.sdk.setLoggedUser(loggedUser);

                    // send response
                    KWSAuthUserProcess.this.listener.didLoginUser(KWSChildrenLoginUserStatus.Success);

                }
                else {
                    KWSAuthUserProcess.this.listener.didLoginUser(KWSChildrenLoginUserStatus.InvalidCredentials);
                }

            }
        });
    }

    private boolean validateUsername (String username) {
        return Pattern.matches("^[a-zA-Z0-9]*$", username) && username.length() >= 3;
    }

    private boolean validatePassword (String password) {
        return password.length() >= 8;
    }
}
