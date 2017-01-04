package kws.superawesome.tv.kwssdk.process;

import android.content.Context;
import android.util.Patterns;

import java.util.regex.Pattern;

import kws.superawesome.tv.kwssdk.KWS;
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

    private KWSCreateUserProcessInterface listener;
    private KWSCreateUser createUser;
    private KWSGetAccessTokenCreate getAccessTokenCreate;

    public KWSCreateUserProcess () {
        listener = new KWSCreateUserProcessInterface() { @Override public void userCreated(KWSCreateUserStatus status) {} };
        createUser = new KWSCreateUser();
        getAccessTokenCreate = new KWSGetAccessTokenCreate();
    }

    public void create (final Context context,
                        final String username,
                        final String password,
                        final String dateOfBirth,
                        final String country,
                        final String parentEmail,
                        final KWSCreateUserProcessInterface listener)
    {

        // get a proper callback
        this.listener = listener != null ? listener : this.listener;

        // validate stuff
        boolean validUsername = validateUsername(username);
        boolean validPassword = validatePassword(password);
        boolean validParentEmail = validateEmail(parentEmail);
        boolean validBirthDate = validateDate(dateOfBirth);
        boolean validCountry = validateCountry(country);

        if (!validUsername) {
            this.listener.userCreated(KWSCreateUserStatus.InvalidUsername);
            return;
        }

        if (!validPassword) {
            this.listener.userCreated(KWSCreateUserStatus.InvalidPassword);
            return;
        }

        if (!validParentEmail) {
            this.listener.userCreated(KWSCreateUserStatus.InvalidParentEmail);
            return;
        }

        if (!validBirthDate) {
            this.listener.userCreated(KWSCreateUserStatus.InvalidDateOfBirth);
            return;
        }

        if (!validCountry) {
            this.listener.userCreated(KWSCreateUserStatus.InvalidCountry);
            return;
        }

        // get access token
        getAccessTokenCreate.execute(context, new KWSGetAccessTokenCreateInterface() {
            @Override
            public void gotToken(final KWSAccessToken accessToken) {

                if (accessToken != null) {

                    // get app id info mainly from the previous temporary access token
                    KWSMetadata metadata = KWSMetadata.processMetadata(accessToken.access_token);

                    // handle error
                    if (metadata == null) {
                        KWSCreateUserProcess.this.listener.userCreated(KWSCreateUserStatus.NetworkError);
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
                                        KWS.sdk.setLoggedUser(loggedUser);

                                        // send callback
                                        KWSCreateUserProcess.this.listener.userCreated(KWSCreateUserStatus.Success);

                                    }
                                    else {
                                        if (status == 409) {
                                            KWSCreateUserProcess.this.listener.userCreated(KWSCreateUserStatus.DuplicateUsername);
                                        } else {
                                            KWSCreateUserProcess.this.listener.userCreated(KWSCreateUserStatus.InvalidOperation);
                                        }
                                    }

                                }
                            });

                } else {
                    KWSCreateUserProcess.this.listener.userCreated(KWSCreateUserStatus.NetworkError);
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
