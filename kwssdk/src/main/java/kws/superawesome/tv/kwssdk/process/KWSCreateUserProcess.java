package kws.superawesome.tv.kwssdk.process;

import android.content.Context;
import android.util.Patterns;

import java.util.regex.Pattern;

import kws.superawesome.tv.kwssdk.KWSChildren;
import kws.superawesome.tv.kwssdk.models.oauth.KWSAccessToken;
import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata;
import kws.superawesome.tv.kwssdk.services.kws.create.KWSCreateUser;
import kws.superawesome.tv.kwssdk.services.kws.create.KWSCreateUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.create.KWSGetAccessTokenCreate;
import kws.superawesome.tv.kwssdk.services.kws.create.KWSGetAccessTokenCreateInterface;

/**
 * Created by gabriel.coman on 12/10/16.
 */
public class KWSCreateUserProcess {

    private KWSChildrenCreateUserInterface listener;
    private KWSCreateUser createUser;
    private KWSGetAccessTokenCreate getAccessTokenCreate;

    public KWSCreateUserProcess () {
        listener = new KWSChildrenCreateUserInterface() { @Override public void didCreateUser(KWSChildrenCreateUserStatus status) {} };
        createUser = new KWSCreateUser();
        getAccessTokenCreate = new KWSGetAccessTokenCreate();
    }

    public void create (final Context context,
                        final String username,
                        final String password,
                        final String dateOfBirth,
                        final String country,
                        final String parentEmail,
                        final KWSChildrenCreateUserInterface listener)
    {

        // getService a proper callback
        this.listener = listener != null ? listener : this.listener;

        // validate stuff
        boolean validUsername = validateUsername(username);
        boolean validPassword = validatePassword(password);
        boolean validParentEmail = validateEmail(parentEmail);
        boolean validBirthDate = validateDate(dateOfBirth);
        boolean validCountry = validateCountry(country);

        if (!validUsername) {
            this.listener.didCreateUser(KWSChildrenCreateUserStatus.InvalidUsername);
            return;
        }

        if (!validPassword) {
            this.listener.didCreateUser(KWSChildrenCreateUserStatus.InvalidPassword);
            return;
        }

        if (!validParentEmail) {
            this.listener.didCreateUser(KWSChildrenCreateUserStatus.InvalidParentEmail);
            return;
        }

        if (!validBirthDate) {
            this.listener.didCreateUser(KWSChildrenCreateUserStatus.InvalidDateOfBirth);
            return;
        }

        if (!validCountry) {
            this.listener.didCreateUser(KWSChildrenCreateUserStatus.InvalidCountry);
            return;
        }

        // getService access token
        getAccessTokenCreate.execute(context, new KWSGetAccessTokenCreateInterface() {
            @Override
            public void gotToken(final KWSAccessToken accessToken) {

                if (accessToken != null) {

                    // getService app id info mainly from the previous temporary access token
                    KWSMetadata metadata = KWSMetadata.processMetadata(accessToken.access_token);

                    // handle error
                    if (metadata == null) {
                        KWSCreateUserProcess.this.listener.didCreateUser(KWSChildrenCreateUserStatus.NetworkError);
                        return;
                    }

                    // continue w/ success
                    createUser.execute(
                            context,
                            accessToken.access_token,
                            metadata.appId,
                            username,
                            password,
                            dateOfBirth,
                            country,
                            parentEmail,
                            new KWSCreateUserInterface() {
                                @Override
                                public void created(int status, KWSLoggedUser loggedUser) {

                                    if (loggedUser != null && loggedUser.isValid()) {

                                        // set a proper logged user
                                        KWSChildren.sdk.setLoggedUser(loggedUser);

                                        // send callback
                                        KWSCreateUserProcess.this.listener.didCreateUser(KWSChildrenCreateUserStatus.Success);

                                    }
                                    else {
                                        if (status == 409) {
                                            KWSCreateUserProcess.this.listener.didCreateUser(KWSChildrenCreateUserStatus.DuplicateUsername);
                                        } else {
                                            KWSCreateUserProcess.this.listener.didCreateUser(KWSChildrenCreateUserStatus.InvalidOperation);
                                        }
                                    }

                                }
                            });

                } else {
                    KWSCreateUserProcess.this.listener.didCreateUser(KWSChildrenCreateUserStatus.NetworkError);
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

    private boolean validateEmail (String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateDate (String date) {
        return Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}", date);
    }

    private boolean validateCountry (String country) {
        return Pattern.matches("[A-Z]{2}", country);
    }
}
