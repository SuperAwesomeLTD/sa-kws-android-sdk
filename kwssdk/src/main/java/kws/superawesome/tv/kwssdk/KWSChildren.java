package kws.superawesome.tv.kwssdk;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.models.LoggedUser;
import kws.superawesome.tv.kwssdk.base.responses.ApplicationPermissionsResponse;
import kws.superawesome.tv.kwssdk.base.responses.GetRandomUsernameResponse;
import kws.superawesome.tv.kwssdk.base.responses.GetUserDetailsResponse;
import kws.superawesome.tv.kwssdk.base.responses.PointsResponse;
import kws.superawesome.tv.kwssdk.base.responses.UserAddressResponse;
import kws.superawesome.tv.kwssdk.base.responses.UserApplicationProfileResponse;
import kws.superawesome.tv.kwssdk.base.services.CreateUserService;
import kws.superawesome.tv.kwssdk.base.services.GetRandomUsernameService;
import kws.superawesome.tv.kwssdk.base.services.GetUserDetailsService;
import kws.superawesome.tv.kwssdk.base.services.InviteUserService;
import kws.superawesome.tv.kwssdk.base.services.LoginService;
import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.models.user.KWSAddress;
import kws.superawesome.tv.kwssdk.models.user.KWSApplicationProfile;
import kws.superawesome.tv.kwssdk.models.user.KWSPermissions;
import kws.superawesome.tv.kwssdk.models.user.KWSPoints;
import kws.superawesome.tv.kwssdk.models.user.KWSUser;
import kws.superawesome.tv.kwssdk.process.KWSChildrenCreateUserInterface;
import kws.superawesome.tv.kwssdk.process.KWSChildrenCreateUserStatus;
import kws.superawesome.tv.kwssdk.process.KWSChildrenIsRegisteredForRemoteNotificationsInterface;
import kws.superawesome.tv.kwssdk.process.KWSChildrenLoginUserInterface;
import kws.superawesome.tv.kwssdk.process.KWSChildrenLoginUserStatus;
import kws.superawesome.tv.kwssdk.process.KWSChildrenRegisterForRemoteNotificationsInterface;
import kws.superawesome.tv.kwssdk.process.KWSChildrenRegisterForRemoteNotificationsStatus;
import kws.superawesome.tv.kwssdk.process.KWSChildrenUnregisterForRemoteNotificationsInterface;
import kws.superawesome.tv.kwssdk.process.KWSNotificationProcess;
import kws.superawesome.tv.kwssdk.process.KWSRandomNameProcess;
import kws.superawesome.tv.kwssdk.services.kws.appdata.KWSChildrenGetAppDataInterface;
import kws.superawesome.tv.kwssdk.services.kws.appdata.KWSChildrenSetAppDataInterface;
import kws.superawesome.tv.kwssdk.services.kws.appdata.KWSGetAppData;
import kws.superawesome.tv.kwssdk.services.kws.appdata.KWSSetAppData;
import kws.superawesome.tv.kwssdk.services.kws.parentemail.KWSChildrenUpdateParentEmailInterface;
import kws.superawesome.tv.kwssdk.services.kws.parentemail.KWSParentEmail;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenPermissionType;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenRequestPermissionInterface;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSRequestPermission;
import kws.superawesome.tv.kwssdk.services.kws.randomname.KWSChildrenGetRandomUsernameInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSChildrenGetLeaderboardInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSChildrenGetScoreInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSChildrenHasTriggeredEventInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSChildrenTriggerEventInterface;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSGetLeaderboard;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSGetScore;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSHasTriggeredEvent;
import kws.superawesome.tv.kwssdk.services.kws.score.KWSTriggerEvent;
import kws.superawesome.tv.kwssdk.services.kws.user.KWSChildrenGetUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.user.KWSChildrenInviteUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.user.KWSChildrenUpdateUserInterface;
import kws.superawesome.tv.kwssdk.services.kws.user.KWSGetUser;
import kws.superawesome.tv.kwssdk.services.kws.user.KWSInviteUser;
import kws.superawesome.tv.kwssdk.services.kws.user.KWSUpdateUser;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sautils.SAAlert;
import tv.superawesome.lib.sautils.SAAlertInterface;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSChildren {

    // singleton instance
    public static KWSChildren sdk = new KWSChildren();

    // setup variables
    private String kwsApiUrl;
    private String clientId;
    private String clientSecret;
    private KWSLoggedUser loggedUser;
    private KWSNetworkEnvironment kwsEnvironment;

    // internal services & processes
    private KWSNotificationProcess notificationProcess;
    private KWSParentEmail parentEmail;
    private KWSGetUser getUser;
    private KWSGetLeaderboard getLeaderboard;
    private KWSRequestPermission requestPermission;
    private KWSTriggerEvent triggerEvent;
    private KWSGetScore getScore;
    private KWSInviteUser inviteUser;
    private KWSHasTriggeredEvent hasTriggeredEvent;
    private KWSGetAppData getAppData;
    private KWSSetAppData setAppData;
    private KWSUpdateUser updateUser;
    private KWSRandomNameProcess randomNameProcess;

    // preferences
    private static final String LOGGED_USER_KEY = "KWS_SA_LOGGED_USER";
    private SharedPreferences preferences;

    // private constructor

    private KWSChildren() {
        notificationProcess = new KWSNotificationProcess();
        parentEmail = new KWSParentEmail();
        getUser = new KWSGetUser();
        getLeaderboard = new KWSGetLeaderboard();
        requestPermission = new KWSRequestPermission();
        triggerEvent = new KWSTriggerEvent();
        getScore = new KWSGetScore();
        inviteUser = new KWSInviteUser();
        hasTriggeredEvent = new KWSHasTriggeredEvent();
        getAppData = new KWSGetAppData();
        setAppData = new KWSSetAppData();
        updateUser = new KWSUpdateUser();
        randomNameProcess = new KWSRandomNameProcess();
    }

    // <Setup> and <Desetup> functions

    public void setup(Context context, final String clientId, final String clientSecret, final String apiUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.kwsApiUrl = apiUrl;
        this.kwsEnvironment = new KWSNetworkEnvironment() {
            @NotNull
            @Override
            public String getAppID() {
                return clientId;
            }

            @NotNull
            @Override
            public String getMobileKey() {
                return clientSecret;
            }

            @NotNull
            @Override
            public String getDomain() {
                return apiUrl;
            }
        };

        // get preferences
        preferences = context.getSharedPreferences(LOGGED_USER_KEY, 0);

        if (preferences.contains(LOGGED_USER_KEY)) {
            String loggedUserString = preferences.getString(LOGGED_USER_KEY, "{}");
            JSONObject loggedUserJson = SAJsonParser.newObject(loggedUserString);
            KWSLoggedUser tmpUser = new KWSLoggedUser(loggedUserJson);

            if (tmpUser.isValid()) {
                loggedUser = tmpUser;
                Log.d("KWSChildren-USER", "KWSChildren started with logged user " + loggedUser.metadata.userId);
            } else {
                Log.d("KWSChildren-USER", "KWSChildren started with a logged user that had an expired OAuth token. Clearing cache!");
                preferences.edit().remove(LOGGED_USER_KEY).apply();
            }
        } else {
            Log.d("KWSChildren-USER", "KWSChildren started without a logged user since none was found");
        }
    }

    public void reset() {
        this.kwsApiUrl = null;
        this.loggedUser = null;
        this.clientSecret = null;
        this.clientId = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Public exposed functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    // user creation, auth & logout
    public void createUser(Context context, String username, String password, String dateOfBirth, String country, String parentEmail, final KWSChildrenCreateUserInterface listener) {

        CreateUserService createUserService = KWSSDK.get(kwsEnvironment, CreateUserService.class);

        if (createUserService != null) {
            createUserService.createUser(username, password, dateOfBirth, country, parentEmail, new Function2<LoggedUser, Throwable, Unit>() {
                @Override
                public Unit invoke(LoggedUser loggedUser, Throwable throwable) {

                    if (loggedUser != null && throwable == null) {
                        setLoggedUser(loggedUser);
                        listener.didCreateUser(KWSChildrenCreateUserStatus.Success);
                    } else {
                        listener.didCreateUser(KWSChildrenCreateUserStatus.InvalidOperation);
                    }

                    return null;
                }
            });
        }

    }

    public void loginUser(Context context, String username, String password, final KWSChildrenLoginUserInterface listener) {

        LoginService loginService = KWSSDK.get(kwsEnvironment, LoginService.class);

        if (loginService != null) {
            loginService.loginUser(username, password, new Function2<LoggedUser, Throwable, Unit>() {
                @Override
                public Unit invoke(LoggedUser loggedUser, Throwable throwable) {

                    if (loggedUser != null && throwable == null) {
                        setLoggedUser(loggedUser);
                        listener.didLoginUser(KWSChildrenLoginUserStatus.Success);
                    } else {
                        listener.didLoginUser(KWSChildrenLoginUserStatus.InvalidCredentials);
                    }

                    return null;
                }
            });
        }
    }

    public void authWithSingleSignOnUrl(String singleSignOnUrl, Activity parent, final KWSChildrenLoginUserInterface listener) {

        LoginService loginService = KWSSDK.get(kwsEnvironment, LoginService.class);

        if (loginService != null) {
            loginService.authUser(singleSignOnUrl, parent, new Function2<LoggedUser, Throwable, Unit>() {
                @Override
                public Unit invoke(LoggedUser loggedUser, Throwable throwable) {

                    if (loggedUser != null && throwable == null) {
                        setLoggedUser(loggedUser);
                        listener.didLoginUser(KWSChildrenLoginUserStatus.Success);
                    } else {
                        listener.didLoginUser(KWSChildrenLoginUserStatus.InvalidCredentials);
                    }

                    return null;
                }
            });
        }

    }

    public void logoutUser(Context context) {
        // get preferences
        preferences = context.getSharedPreferences(LOGGED_USER_KEY, 0);
        this.loggedUser = null;
        if (preferences.contains(LOGGED_USER_KEY)) {
            preferences.edit().remove(LOGGED_USER_KEY).apply();
        }
    }

    // user random name
    public void getRandomUsername(Context context, final KWSChildrenGetRandomUsernameInterface listener) {

        GetRandomUsernameService getRandomUsernameService = KWSSDK.get(kwsEnvironment, GetRandomUsernameService.class);

        if (getRandomUsernameService != null) {
            getRandomUsernameService.getRandomUsername(new Function2<GetRandomUsernameResponse, Throwable, Unit>() {
                @Override
                public Unit invoke(GetRandomUsernameResponse randomUsername, Throwable throwable) {
                    if (randomUsername != null && throwable == null) {
                        listener.didGetRandomUsername(randomUsername.getRandomUsername());
                    } else {
                        listener.didGetRandomUsername(null);
                    }
                    return null;
                }
            });
        }
    }

    // user details
    public void getUser(Context context, final KWSChildrenGetUserInterface listener) {

        GetUserDetailsService getUserDetailsService = KWSSDK.get(kwsEnvironment, GetUserDetailsService.class);

        if (getUserDetailsService != null) {

            if (loggedUser == null || loggedUser.metadata == null) {
                listener.didGetUser(null);
                return;
            }

            getUserDetailsService.getUserDetails(loggedUser.metadata.userId, loggedUser.token, new Function2<GetUserDetailsResponse, Throwable, Unit>() {
                @Override
                public Unit invoke(GetUserDetailsResponse getUserDetailsResponse, Throwable throwable) {
                    if (getUserDetailsResponse != null) {
                        KWSUser kwsUser = buildKWSUser(getUserDetailsResponse);
                        if (kwsUser != null) {
                            listener.didGetUser(kwsUser);
                        } else {
                            listener.didGetUser(null);
                        }
                    } else {
                        listener.didGetUser(null);
                    }

                    return null;
                }

                private KWSUser buildKWSUser(GetUserDetailsResponse getUserDetailsResponse) {
                    KWSUser kwsUser = new KWSUser();

                    if (getUserDetailsResponse.getId() != null) {
                        kwsUser.id = getUserDetailsResponse.getId();
                    } else {
                        return null;
                    }

                    kwsUser.username = getUserDetailsResponse.getUsername();
                    kwsUser.firstName = getUserDetailsResponse.getFirstName();
                    kwsUser.lastName = getUserDetailsResponse.getLastName();
                    kwsUser.dateOfBirth = getUserDetailsResponse.getDateOfBirth();
                    kwsUser.gender = getUserDetailsResponse.getGender();
                    kwsUser.language = getUserDetailsResponse.getLanguage();
                    kwsUser.email = getUserDetailsResponse.getEmail();
                    kwsUser.address = buildAddress(getUserDetailsResponse.getAddressResponse());
                    kwsUser.points = buildPoints(getUserDetailsResponse.getPointsResponse());
                    kwsUser.applicationPermissions = buildPermissions(getUserDetailsResponse.getApplicationPermissionsResponse());
                    kwsUser.applicationProfile = buildProfile(getUserDetailsResponse.getApplicationProfileResponse());

                    return kwsUser;
                }

                private KWSApplicationProfile buildProfile(UserApplicationProfileResponse applicationProfileResponse) {


                    KWSApplicationProfile kwsApplicationProfile = new KWSApplicationProfile();

                    kwsApplicationProfile.username = applicationProfileResponse.getUsername();

                    if (applicationProfileResponse.getAvatarId() != null) {
                        kwsApplicationProfile.avatarId = applicationProfileResponse.getAvatarId();
                    }

                    if (applicationProfileResponse.getCustomField1() != null) {
                        kwsApplicationProfile.customField1 = applicationProfileResponse.getCustomField1();
                    }

                    if (applicationProfileResponse.getCustomField2() != null) {
                        kwsApplicationProfile.customField2 = applicationProfileResponse.getCustomField2();
                    }


                    return kwsApplicationProfile;


                }

                private KWSPermissions buildPermissions(ApplicationPermissionsResponse applicationPermissionsResponse) {

                    KWSPermissions kwsPermissions = new KWSPermissions();

                    kwsPermissions.accessAddress = applicationPermissionsResponse.getAccessAddress();
                    kwsPermissions.accessFirstName = applicationPermissionsResponse.getAccessFirstName();
                    kwsPermissions.accessLastName = applicationPermissionsResponse.getAccessLastName();
                    kwsPermissions.accessEmail = applicationPermissionsResponse.getAccessEmail();
                    kwsPermissions.accessStreetAddress = applicationPermissionsResponse.getAccessStreetAddress();
                    kwsPermissions.accessCity = applicationPermissionsResponse.getAccessCity();
                    kwsPermissions.accessPostalCode = applicationPermissionsResponse.getAccessPostalCode();
                    kwsPermissions.accessCountry = applicationPermissionsResponse.getAccessCountry();
                    kwsPermissions.sendPushNotification = applicationPermissionsResponse.getSendPushNotification();
                    kwsPermissions.sendNewsletter = applicationPermissionsResponse.getSendNewsletter();

                    return kwsPermissions;

                }

                private KWSPoints buildPoints(PointsResponse pointsResponse) {
                    KWSPoints kwsPoints = new KWSPoints();
                    if (pointsResponse.getTotalReceived() != null) {
                        kwsPoints.totalReceived = pointsResponse.getTotalReceived();
                    }

                    if (pointsResponse.getTotal() != null) {
                        kwsPoints.total = pointsResponse.getTotal();
                    }

                    if (pointsResponse.getTotalPointsReceivedInCurrentApp() != null) {
                        kwsPoints.totalPointsReceivedInCurrentApp = pointsResponse.getTotalPointsReceivedInCurrentApp();
                    }

                    if (pointsResponse.getAvailableBalance() != null) {
                        kwsPoints.availableBalance = pointsResponse.getAvailableBalance();
                    }

                    if (pointsResponse.getPending() != null) {
                        kwsPoints.pending = pointsResponse.getPending();
                    }

                    return kwsPoints;
                }

                private KWSAddress buildAddress(UserAddressResponse addressResponse) {
                    KWSAddress kwsAddress = new KWSAddress();
                    kwsAddress.street = addressResponse.getStreet();
                    kwsAddress.city = addressResponse.getCity();
                    kwsAddress.postCode = addressResponse.getPostCode();
                    kwsAddress.country = addressResponse.getCountry();
                    return kwsAddress;
                }
            });

        }


    }

    public void updateUser(Context context, KWSUser updatedUser, KWSChildrenUpdateUserInterface listener) {
        updateUser.execute(context, updatedUser, listener);
    }

    // permissions
    public void updateParentEmail(Context context, String email, KWSChildrenUpdateParentEmailInterface listener) {
        parentEmail.execute(context, email, listener);
    }

    public void requestPermission(Context context, KWSChildrenPermissionType[] requestedPermissions, KWSChildrenRequestPermissionInterface listener) {
        requestPermission.execute(context, requestedPermissions, listener);
    }

    // invite another user
    public void inviteUser(Context context, String emailAddress, final KWSChildrenInviteUserInterface listener) {

        InviteUserService inviteUserService = KWSSDK.get(kwsEnvironment, InviteUserService.class);

        if (inviteUserService != null) {
            if (loggedUser == null || loggedUser.metadata == null) {
                listener.didInviteUser(false);
                return;
            }

            inviteUserService.inviteUser(emailAddress, loggedUser.metadata.userId, loggedUser.token, new Function2<Boolean, Throwable, Unit>() {
                @Override
                public Unit invoke(Boolean isUserInvited, Throwable throwable) {

                    if (isUserInvited) {
                        listener.didInviteUser(true);
                    } else {
                        listener.didInviteUser(false);
                    }


                    return null;
                }
            });
        }

    }

    // events, points, leader boards

    public void triggerEvent(Context context, String token, int points, KWSChildrenTriggerEventInterface listener) {
        triggerEvent.execute(context, token, points, listener);
    }

    public void hasTriggeredEvent(Context context, int eventId, KWSChildrenHasTriggeredEventInterface listener) {
        hasTriggeredEvent.execute(context, eventId, listener);
    }

    public void getScore(Context context, KWSChildrenGetScoreInterface listener) {
        getScore.execute(context, listener);
    }

    public void getLeaderboard(Context context, KWSChildrenGetLeaderboardInterface listener) {
        getLeaderboard.execute(context, listener);
    }

    // app data

    public void getAppData(Context context, KWSChildrenGetAppDataInterface listener) {
        getAppData.execute(context, listener);
    }

    public void setAppData(Context context, int value, String name, KWSChildrenSetAppDataInterface listener) {
        setAppData.execute(context, name, value, listener);
    }

    // handle remote notifications

    public void registerForRemoteNotifications(Context context, final KWSChildrenRegisterForRemoteNotificationsInterface listener) {
        notificationProcess.register(context, new KWSChildrenRegisterForRemoteNotificationsInterface() {
            @Override
            public void didRegisterForRemoteNotifications(KWSChildrenRegisterForRemoteNotificationsStatus status) {
                if (status == KWSChildrenRegisterForRemoteNotificationsStatus.Success && loggedUser != null) {
                    loggedUser.setIsRegisteredForNotifications(true);
                }
                if (listener != null) {
                    listener.didRegisterForRemoteNotifications(status);
                }
            }
        });
    }

    public void unregisterForRemoteNotifications(Context context, final KWSChildrenUnregisterForRemoteNotificationsInterface listener) {
        notificationProcess.unregister(context, new KWSChildrenUnregisterForRemoteNotificationsInterface() {
            @Override
            public void didUnregisterForRemoteNotifications(boolean unregistered) {
                if (unregistered && loggedUser != null && loggedUser.isRegisteredForNotifications()) {
                    loggedUser.setIsRegisteredForNotifications(false);
                }
                if (listener != null) {
                    listener.didUnregisterForRemoteNotifications(unregistered);
                }
            }
        });
    }

    public void isRegisteredForRemoteNotifications(Context context, final KWSChildrenIsRegisteredForRemoteNotificationsInterface listener) {
        notificationProcess.isRegistered(context, new KWSChildrenIsRegisteredForRemoteNotificationsInterface() {
            @Override
            public void isRegisteredForRemoteNotifications(boolean registered) {
                if (loggedUser != null) {
                    loggedUser.setIsRegisteredForNotifications(registered);
                }
                if (listener != null) {
                    listener.isRegisteredForRemoteNotifications(registered);
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Aux helper functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void registerForRemoteNotificationsWithPopup(final Context context, final KWSChildrenRegisterForRemoteNotificationsInterface listener) {
        SAAlert.getInstance().show(context, "Hey!", "Do you want to enable Remote Notifications?", "Yes", "No", false, 0, new SAAlertInterface() {
            @Override
            public void saDidClickOnAlertButton(int button, String s) {
                if (button == SAAlert.OK_BUTTON) {
                    registerForRemoteNotifications(context, listener);
                }
            }
        });
    }

    public void updateParentEmailWithPopup(final Context context, final KWSChildrenUpdateParentEmailInterface listener) {
        SAAlert.getInstance().show(context, "Hey!", "To enable Remote Notifications in KWSChildren you'll need to provide a parent email", "Submit", "Cancel", true, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, new SAAlertInterface() {
            @Override
            public void saDidClickOnAlertButton(int button, String email) {
                if (button == SAAlert.OK_BUTTON) {
                    updateParentEmail(context, email, listener);
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters & Setters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getVersion() {
        return "android-2.3.2";
    }

    public String getKwsApiUrl() {
        return kwsApiUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public KWSLoggedUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(KWSLoggedUser loggedUser) {
        // assign the logged user
        this.loggedUser = loggedUser;

        // save in preferences
        if (preferences != null) {
            preferences.edit().putString(LOGGED_USER_KEY, loggedUser.writeToJson().toString()).apply();
        }
    }

    public void setLoggedUser(LoggedUser loggedUser) {
        // assign the logged user
        this.loggedUser = new KWSLoggedUser();
        this.loggedUser.token = loggedUser.getToken();
        this.loggedUser.metadata = loggedUser.getKwsMetaData();

        // save in preferences
        if (preferences != null) {

            preferences.edit().putString(LOGGED_USER_KEY, this.loggedUser.writeToJson().toString()).apply();
        }
    }
}
