package kws.superawesome.tv.kwssdk;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.UtilsHelper;
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.models.internal.LoggedUser;
import kws.superawesome.tv.kwssdk.base.models.internal.TokenData;
import kws.superawesome.tv.kwssdk.models.appdata.KWSAppData;
import kws.superawesome.tv.kwssdk.models.leaderboard.KWSLeader;
import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.models.user.KWSAddress;
import kws.superawesome.tv.kwssdk.models.user.KWSApplicationProfile;
import kws.superawesome.tv.kwssdk.models.user.KWSPermissions;
import kws.superawesome.tv.kwssdk.models.user.KWSPoints;
import kws.superawesome.tv.kwssdk.models.user.KWSScore;
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
import kws.superawesome.tv.kwssdk.services.kws.parentemail.KWSChildrenUpdateParentEmailStatus;
import kws.superawesome.tv.kwssdk.services.kws.parentemail.KWSParentEmail;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenPermissionType;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenRequestPermissionInterface;
import kws.superawesome.tv.kwssdk.services.kws.permissions.KWSChildrenRequestPermissionStatus;
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
import tv.superawesome.protobufs.features.auth.IAuthService;
import tv.superawesome.protobufs.features.auth.ISingleSignOnService;
import tv.superawesome.protobufs.features.auth.IUsernameService;
import tv.superawesome.protobufs.features.scoring.IScoringService;
import tv.superawesome.protobufs.features.session.ISessionService;
import tv.superawesome.protobufs.features.user.IUserActionsService;
import tv.superawesome.protobufs.features.user.IUserService;
import tv.superawesome.protobufs.models.address.IAddressModel;
import tv.superawesome.protobufs.models.appdata.IAppDataModel;
import tv.superawesome.protobufs.models.appdata.IAppDataWrapperModel;
import tv.superawesome.protobufs.models.auth.ILoggedUserModel;
import tv.superawesome.protobufs.models.permission.IPermissionModel;
import tv.superawesome.protobufs.models.score.IHasTriggeredEventModel;
import tv.superawesome.protobufs.models.score.ILeaderModel;
import tv.superawesome.protobufs.models.score.ILeaderWrapperModel;
import tv.superawesome.protobufs.models.score.IPointsModel;
import tv.superawesome.protobufs.models.score.IScoreModel;
import tv.superawesome.protobufs.models.user.IApplicationProfileModel;
import tv.superawesome.protobufs.models.user.IUserDetailsModel;
import tv.superawesome.protobufs.models.usernames.IRandomUsernameModel;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSChildren {

    // singleton instance
    public static KWSChildren sdk = new KWSChildren();
    public static String TAG = "SA";

    public String kNoLoggedUserMsg = "No valid user logged in/cached.";

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
    public void createUser(final Context context, String username, String password, String dateOfBirth, String country, String parentEmail, final KWSChildrenCreateUserInterface listener) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IAuthService authService = factory.get(IAuthService.class);

        if (authService != null) {
            authService.createUser(username, password, null, dateOfBirth, country, parentEmail,
                    new Function2<ILoggedUserModel, Throwable, Unit>() {
                        @Override
                        public Unit invoke(ILoggedUserModel createdUser, Throwable throwable) {

                            if (createdUser != null) {
                                String token = createdUser.getToken();
                                TokenData tokenData = getMetadataFromToken(token);
                                if (tokenData != null) {
                                    LoggedUser loggedUser = new LoggedUser(token, tokenData, tokenData.getUserId());
                                    setLoggedUser(loggedUser, context);
                                    listener.didCreateUser(KWSChildrenCreateUserStatus.Success);
                                } else {
                                    listener.didCreateUser(KWSChildrenCreateUserStatus.InvalidOperation);
                                }
                            } else {
                                listener.didCreateUser(KWSChildrenCreateUserStatus.InvalidOperation);
                                if (throwable != null) {
                                    Log.d(TAG, throwable.toString());
                                }
                            }
                            return null;
                        }
                    });
        }

    }

    public void loginUser(final Context context, String username, String password, final KWSChildrenLoginUserInterface listener) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IAuthService authService = factory.get(IAuthService.class);

        if (authService != null) {
            authService.loginUser(username, password, new Function2<ILoggedUserModel, Throwable, Unit>() {
                @Override
                public Unit invoke(ILoggedUserModel loginAuthResponse, Throwable throwable) {

                    if (loginAuthResponse != null) {
                        String token = loginAuthResponse.getToken();
                        TokenData tokenData = getMetadataFromToken(token);
                        if (tokenData != null) {
                            LoggedUser loggedUser = new LoggedUser(token, tokenData, tokenData.getUserId());
                            setLoggedUser(loggedUser, context);
                            listener.didLoginUser(KWSChildrenLoginUserStatus.Success);
                        } else {
                            listener.didLoginUser(KWSChildrenLoginUserStatus.InvalidCredentials);
                        }
                    } else {
                        listener.didLoginUser(KWSChildrenLoginUserStatus.InvalidCredentials);
                        if (throwable != null) {
                            Log.d(TAG, throwable.toString());
                        }
                    }

                    return null;
                }
            });
        }
    }

    public void authWithSingleSignOnUrl(final Context context, String singleSignOnUrl, Activity parent, final KWSChildrenLoginUserInterface listener) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        ISingleSignOnService singleSignOnService = factory.get(ISingleSignOnService.class);

        if (singleSignOnService != null) {
            singleSignOnService.signOn(singleSignOnUrl, parent, new Function2<ILoggedUserModel, Throwable, Unit>() {
                @Override
                public Unit invoke(ILoggedUserModel loggedUser, Throwable throwable) {

                    if (loggedUser != null && throwable == null) {
                        setLoggedUser(loggedUser, context);
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

        /**New way*/
        LoggedUser emptyUser = new LoggedUser("", new TokenData(), 0);
        setLoggedUser(emptyUser, context);

        /**Here for legacy**/
        // get preferences
        preferences = context.getSharedPreferences(LOGGED_USER_KEY, 0);
        this.loggedUser = null;
        if (preferences.contains(LOGGED_USER_KEY)) {
            preferences.edit().remove(LOGGED_USER_KEY).apply();
        }
    }

    // user random name
    public void getRandomUsername(Context context, final KWSChildrenGetRandomUsernameInterface listener) {


        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IUsernameService usernameService = factory.get(IUsernameService.class);

        if (usernameService != null) {

            usernameService.getRandomUsername(new Function2<IRandomUsernameModel, Throwable, Unit>() {
                @Override
                public Unit invoke(IRandomUsernameModel randomUsername, Throwable throwable) {

                    if (randomUsername != null) {
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

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IUserService userService = factory.get(IUserService.class);

        if (userService != null) {

            final LoggedUser loggedUser = getLoggedUser(context);

            if (loggedUser == null) {
                listener.didGetUser(null);
                Log.d(TAG, kNoLoggedUserMsg);
                return;
            }

            userService.getUser(loggedUser.getTokenData().getUserId(), loggedUser.getToken(), new Function2<IUserDetailsModel, Throwable, Unit>() {
                @Override
                public Unit invoke(IUserDetailsModel userDetailsResponse, Throwable throwable) {
                    if (userDetailsResponse != null) {
                        KWSUser kwsUser = buildKWSUser(userDetailsResponse);
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

                private KWSUser buildKWSUser(IUserDetailsModel userDetailsResponse) {
                    KWSUser kwsUser = new KWSUser();

                    if (userDetailsResponse.getId() != null) {
                        kwsUser.id = (Integer) userDetailsResponse.getId();
                    } else {
                        return null;
                    }

                    kwsUser.username = userDetailsResponse.getName();
                    kwsUser.firstName = userDetailsResponse.getFirstName();
                    kwsUser.lastName = userDetailsResponse.getLastName();
                    kwsUser.dateOfBirth = userDetailsResponse.getDateOfBirth();
                    kwsUser.gender = userDetailsResponse.getGender();
                    kwsUser.language = userDetailsResponse.getLanguage();
                    kwsUser.email = userDetailsResponse.getEmail();
                    kwsUser.address = buildAddress(userDetailsResponse.getAddress());
                    kwsUser.points = buildPoints(userDetailsResponse.getPoints());
                    kwsUser.applicationPermissions = buildPermissions(userDetailsResponse.getApplicationPermissions());
                    kwsUser.applicationProfile = buildProfile(userDetailsResponse.getApplicationProfile());

                    return kwsUser;
                }

                private KWSApplicationProfile buildProfile(IApplicationProfileModel applicationProfileResponse) {


                    KWSApplicationProfile kwsApplicationProfile = new KWSApplicationProfile();

                    kwsApplicationProfile.username = applicationProfileResponse.getName();

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

                private KWSPermissions buildPermissions(IPermissionModel permissions) {

                    KWSPermissions kwsPermissions = new KWSPermissions();

                    kwsPermissions.accessAddress = permissions.getAddress();
                    kwsPermissions.accessFirstName = permissions.getFirstName();
                    kwsPermissions.accessLastName = permissions.getLastName();
                    kwsPermissions.accessEmail = permissions.getEmail();
                    kwsPermissions.accessStreetAddress = permissions.getStreetAddress();
                    kwsPermissions.accessCity = permissions.getCity();
                    kwsPermissions.accessPostalCode = permissions.getPostalCode();
                    kwsPermissions.accessCountry = permissions.getCountry();
                    kwsPermissions.sendPushNotification = permissions.getNotifications();
                    kwsPermissions.sendNewsletter = permissions.getNewsletter();

                    return kwsPermissions;

                }

                private KWSPoints buildPoints(IPointsModel points) {
                    KWSPoints kwsPoints = new KWSPoints();
                    if (points.getReceived() != null) {
                        kwsPoints.totalReceived = points.getReceived();
                    }

                    if (points.getTotal() != null) {
                        kwsPoints.total = points.getTotal();
                    }

                    if (points.getInApp() != null) {
                        kwsPoints.totalPointsReceivedInCurrentApp = points.getInApp();
                    }

                    if (points.getBalance() != null) {
                        kwsPoints.availableBalance = points.getBalance();
                    }

                    if (points.getPending() != null) {
                        kwsPoints.pending = points.getPending();
                    }

                    return kwsPoints;
                }

                private KWSAddress buildAddress(IAddressModel addressResponse) {
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

    public void updateUser(Context context, Map<String, Object> details, final KWSChildrenUpdateUserInterface listener) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IUserService userService = factory.get(IUserService.class);

        if (userService != null) {

            final LoggedUser loggedUser = getLoggedUser(context);

            if (loggedUser == null) {
                listener.didUpdateUser(false);
                Log.d(TAG, kNoLoggedUserMsg);
                return;
            }

            userService.updateUser(details, loggedUser.getId(), loggedUser.getToken(), new Function1<Throwable, Unit>() {
                @Override
                public Unit invoke(Throwable throwable) {

                    if (throwable == null) {
                        listener.didUpdateUser(true);
                    } else {
                        listener.didUpdateUser(false);
                    }

                    return null;
                }
            });
        }
    }

    // applicationPermissions
    public void updateParentEmail(Context context, String email, final KWSChildrenUpdateParentEmailInterface listener) {

        HashMap<String, Object> details = new HashMap<>();
        details.put("parentEmail", email);

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IUserService userService = factory.get(IUserService.class);

        if (userService != null) {

            final LoggedUser loggedUser = getLoggedUser(context);

            if (loggedUser == null) {
                listener.didUpdateParentEmail(KWSChildrenUpdateParentEmailStatus.NetworkError);
                Log.d(TAG, kNoLoggedUserMsg);
                return;
            }

            userService.updateUser(details, loggedUser.getId(), loggedUser.getToken(), new Function1<Throwable, Unit>() {
                @Override
                public Unit invoke(Throwable throwable) {

                    if (throwable == null) {
                        listener.didUpdateParentEmail(KWSChildrenUpdateParentEmailStatus.Success);
                    } else {
                        listener.didUpdateParentEmail(KWSChildrenUpdateParentEmailStatus.NetworkError);
                    }

                    return null;
                }
            });
        }
    }

    public void requestPermission(Context context, KWSChildrenPermissionType[] requestedPermissions, final KWSChildrenRequestPermissionInterface listener) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IUserActionsService userActionsService = factory.get(IUserActionsService.class);

        if (userActionsService != null) {

            final LoggedUser loggedUser = getLoggedUser(context);

            if (loggedUser == null) {

                listener.didRequestPermission(KWSChildrenRequestPermissionStatus.NetworkError);
                Log.d(TAG, kNoLoggedUserMsg);
                return;
            }

            List<String> listOfPermissions = getListOfPermissions(requestedPermissions);

            userActionsService.requestPermissions(listOfPermissions, loggedUser.getTokenData().getUserId(), loggedUser.getToken(), new Function1<Throwable, Unit>() {
                @Override
                public Unit invoke(Throwable throwable) {

                    if (throwable == null) {
                        listener.didRequestPermission(KWSChildrenRequestPermissionStatus.Success);
                    } else {
                        listener.didRequestPermission(KWSChildrenRequestPermissionStatus.NetworkError);

                    }

                    return null;
                }
            });

        }

    }

    private List<String> getListOfPermissions(KWSChildrenPermissionType[] requestedPermissions) {

        List<String> permissionsArray = new ArrayList<>();

        for (KWSChildrenPermissionType typeOfPermission : requestedPermissions) {
            permissionsArray.add(typeOfPermission.toString());
        }

        return permissionsArray;
    }

    // invite another user
    public void inviteUser(Context context, String emailAddress, final KWSChildrenInviteUserInterface listener) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IUserActionsService userActionsService = factory.get(IUserActionsService.class);

        if (userActionsService != null) {

            final LoggedUser loggedUser = getLoggedUser(context);

            if (loggedUser == null) {

                listener.didInviteUser(false);
                Log.d(TAG, kNoLoggedUserMsg);
                return;
            }

            userActionsService.inviteUser(emailAddress, loggedUser.getTokenData().getUserId(), loggedUser.getToken(), new Function1<Throwable, Unit>() {
                @Override
                public Unit invoke(Throwable throwable) {
                    if (throwable == null) {
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

    public void triggerEvent(Context context, String token, int points, final KWSChildrenTriggerEventInterface listener) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IUserActionsService userActionsService = factory.get(IUserActionsService.class);

        if (userActionsService != null) {

            final LoggedUser loggedUser = getLoggedUser(context);

            if (loggedUser == null) {

                listener.didTriggerEvent(false);
                Log.d(TAG, kNoLoggedUserMsg);
                return;
            }

            userActionsService.triggerEvent(token, points, loggedUser.getTokenData().getUserId(), loggedUser.getToken(), new Function1<Throwable, Unit>() {
                @Override
                public Unit invoke(Throwable throwable) {
                    if (throwable == null) {
                        listener.didTriggerEvent(true);
                    } else {
                        listener.didTriggerEvent(false);
                    }
                    return null;
                }
            });
        }


    }

    public void hasTriggeredEvent(Context context, int eventId, final KWSChildrenHasTriggeredEventInterface listener) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IUserActionsService userActionsService = factory.get(IUserActionsService.class);

        if (userActionsService != null) {

            final LoggedUser loggedUser = getLoggedUser(context);

            if (loggedUser == null) {

                listener.didTriggerEvent(false);
                Log.d(TAG, kNoLoggedUserMsg);
                return;
            }

            userActionsService.hasTriggeredEvent(eventId, loggedUser.getTokenData().getUserId(), loggedUser.getToken(), new Function2<IHasTriggeredEventModel, Throwable, Unit>() {
                @Override
                public Unit invoke(IHasTriggeredEventModel hasTriggeredEvent, Throwable throwable) {

                    if (hasTriggeredEvent != null && hasTriggeredEvent.getHasTriggeredModel()) {
                        listener.didTriggerEvent(true);
                    } else {
                        listener.didTriggerEvent(false);
                    }

                    return null;
                }
            });


        }

    }

    public void getScore(Context context, final KWSChildrenGetScoreInterface listener) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IScoringService scoringService = factory.get(IScoringService.class);

        if (scoringService != null) {

            final LoggedUser loggedUser = getLoggedUser(context);

            if (loggedUser == null) {

                listener.didGetScore(null);
                Log.d(TAG, kNoLoggedUserMsg);
                return;
            }

            scoringService.getScore(loggedUser.getTokenData().getAppId(), loggedUser.getToken(), new Function2<IScoreModel, Throwable, Unit>() {
                @Override
                public Unit invoke(IScoreModel score, Throwable throwable) {

                    if (score != null) {
                        KWSScore kwsScore = buildScore(score);
                        listener.didGetScore(kwsScore);
                    } else {
                        listener.didGetScore(null);
                    }

                    return null;
                }

                private KWSScore buildScore(IScoreModel score) {
                    KWSScore kwsScore = new KWSScore();

                    kwsScore.score = score.getScore();
                    kwsScore.rank = score.getRank();

                    return kwsScore;
                }
            });

        }


    }

    public void getLeaderboard(Context context, final KWSChildrenGetLeaderboardInterface listener) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IScoringService scoringService = factory.get(IScoringService.class);

        if (scoringService != null) {

            final LoggedUser loggedUser = getLoggedUser(context);

            if (loggedUser == null) {

                listener.didGetLeaderboard(new ArrayList<KWSLeader>());
                Log.d(TAG, kNoLoggedUserMsg);
                return;
            }


            scoringService.getLeaderboard(loggedUser.getTokenData().getAppId(), loggedUser.getToken(), new Function2<ILeaderWrapperModel, Throwable, Unit>() {
                @Override
                public Unit invoke(ILeaderWrapperModel leadersWrapper, Throwable throwable) {

                    if (leadersWrapper != null) {
                        ArrayList<KWSLeader> listOfKWSLeader = getListOfKWSLeaders(leadersWrapper.getResults());
                        listener.didGetLeaderboard(listOfKWSLeader);

                    } else {
                        listener.didGetLeaderboard(new ArrayList<KWSLeader>());
                    }

                    return null;
                }

                private ArrayList<KWSLeader> getListOfKWSLeaders(List<ILeaderModel> results) {

                    ArrayList<KWSLeader> listOfKWSLeader = new ArrayList<>();

                    for (ILeaderModel leaders : results) {
                        KWSLeader builtKWSLeaderObject = buildKWSLeaderObject(leaders);
                        listOfKWSLeader.add(builtKWSLeaderObject);
                    }
                    return listOfKWSLeader;
                }

                private KWSLeader buildKWSLeaderObject(ILeaderModel leaders) {
                    KWSLeader kwsLeader = new KWSLeader();
                    kwsLeader.rank = leaders.getRank();
                    kwsLeader.score = leaders.getScore();
                    kwsLeader.user = leaders.getName();
                    return kwsLeader;
                }
            });

        }


    }

    // app data

    public void getAppData(Context context, final KWSChildrenGetAppDataInterface listener) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IUserActionsService userActionsService = factory.get(IUserActionsService.class);

        if (userActionsService != null) {

            final LoggedUser loggedUser = getLoggedUser(context);

            if (loggedUser == null) {

                listener.didGetAppData(new ArrayList<KWSAppData>());
                Log.d(TAG, kNoLoggedUserMsg);
                return;
            }

            userActionsService.getAppData(
                    loggedUser.getTokenData().getUserId(),
                    loggedUser.getTokenData().getAppId(),
                    loggedUser.getToken(), new Function2<IAppDataWrapperModel, Throwable, Unit>() {
                        @Override
                        public Unit invoke(IAppDataWrapperModel appDataWrapper, Throwable throwable) {

                            if (appDataWrapper != null) {
                                ArrayList<KWSAppData> listOfKWSAppData = getListOfKWSAppData(appDataWrapper.getResults());
                                listener.didGetAppData(listOfKWSAppData);
                            } else {
                                listener.didGetAppData(new ArrayList<KWSAppData>());
                            }

                            return null;
                        }

                        private ArrayList<KWSAppData> getListOfKWSAppData(List<IAppDataModel> results) {
                            ArrayList<KWSAppData> listOfKWSAppData = new ArrayList<>();

                            for (IAppDataModel appData : results) {
                                KWSAppData builtKWSAppDataObject = buildKWSAppDataObject(appData);
                                listOfKWSAppData.add(builtKWSAppDataObject);
                            }
                            return listOfKWSAppData;
                        }

                        private KWSAppData buildKWSAppDataObject(IAppDataModel appData) {

                            KWSAppData kwsAppData = new KWSAppData();
                            kwsAppData.name = appData.getName();
                            kwsAppData.value = appData.getValue();

                            return kwsAppData;

                        }
                    });
        }


    }

    public void setAppData(Context context, int value, String name, final KWSChildrenSetAppDataInterface listener) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        IUserActionsService userActionsService = factory.get(IUserActionsService.class);

        if (userActionsService != null) {

            final LoggedUser loggedUser = getLoggedUser(context);

            if (loggedUser == null) {
                listener.didSetAppData(false);
                Log.d(TAG, kNoLoggedUserMsg);
                return;
            }

            userActionsService.setAppData(
                    value,
                    name,
                    loggedUser.getTokenData().getUserId(),
                    loggedUser.getTokenData().getAppId(),
                    loggedUser.getToken(), new Function1<Throwable, Unit>() {
                        @Override
                        public Unit invoke(Throwable throwable) {

                            if (throwable == null) {
                                listener.didSetAppData(true);
                            } else {
                                listener.didSetAppData(false);
                            }

                            return null;
                        }
                    });
        }

    }

    // handle remote notifications

    public void registerForRemoteNotifications(final Context context, final KWSChildrenRegisterForRemoteNotificationsInterface listener) {

        final LoggedUser loggedUser = getLoggedUser(context);

        if (loggedUser == null) {

            Log.d(TAG, kNoLoggedUserMsg);
            listener.didRegisterForRemoteNotifications(KWSChildrenRegisterForRemoteNotificationsStatus.NoValidUser);
            return;
        }

        notificationProcess.register(context, new KWSChildrenRegisterForRemoteNotificationsInterface() {
            @Override
            public void didRegisterForRemoteNotifications(KWSChildrenRegisterForRemoteNotificationsStatus status) {

                LoggedUser loggedUser = getLoggedUser(context);

                if (status == KWSChildrenRegisterForRemoteNotificationsStatus.Success && loggedUser != null) {
                    loggedUser.setRegisteredForRM(true);
                    Log.d(TAG, "User registered for RM.");
                } else {
                    Log.d(TAG, "Something went wrong...user not registered for RM.");
                }

                if (listener != null) {
                    listener.didRegisterForRemoteNotifications(status);
                }
            }
        });
    }

    public void unregisterForRemoteNotifications(final Context context, final KWSChildrenUnregisterForRemoteNotificationsInterface listener) {

        final LoggedUser loggedUser = getLoggedUser(context);

        if (loggedUser == null) {

            Log.d(TAG, kNoLoggedUserMsg);
            listener.didUnregisterForRemoteNotifications(false);
            return;
        }

        notificationProcess.unregister(context, new KWSChildrenUnregisterForRemoteNotificationsInterface() {
            @Override
            public void didUnregisterForRemoteNotifications(boolean unregistered) {

                LoggedUser loggedUser = getLoggedUser(context);

                if (unregistered && loggedUser != null && loggedUser.getRegisteredForRM()) {

                    loggedUser.setRegisteredForRM(false);
                    setLoggedUser(loggedUser, context);

                    Log.d(TAG, "User found and UNREGISTERED for RM!");

                } else {

                    Log.d(TAG, "Something went wrong...");
                }

                if (listener != null) {
                    listener.didUnregisterForRemoteNotifications(unregistered);
                }
            }
        });
    }

    public void isRegisteredForRemoteNotifications(final Context context, final KWSChildrenIsRegisteredForRemoteNotificationsInterface listener) {

        LoggedUser loggedUser = getLoggedUser(context);

        if (loggedUser == null) {

            Log.d(TAG, kNoLoggedUserMsg);
            listener.isRegisteredForRemoteNotifications(false);
            return;
        }

        notificationProcess.isRegistered(context, new KWSChildrenIsRegisteredForRemoteNotificationsInterface() {
            @Override
            public void isRegisteredForRemoteNotifications(boolean registered) {

                LoggedUser loggedUser = getLoggedUser(context);

                if (loggedUser != null) {

                    loggedUser.setRegisteredForRM(registered);
                    setLoggedUser(loggedUser, context);

                    Log.d(TAG, "User found and registered for RM!");
                } else {
                    Log.d(TAG, kNoLoggedUserMsg);
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

    public LoggedUser getLoggedUser(final Context context) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        ISessionService sessionService = factory.get(ISessionService.class);

        if (sessionService != null) {
            return (LoggedUser) sessionService.getCurrentUser(context);
        } else {
            return null;
        }

    }

    public void setLoggedUser(ILoggedUserModel loggedUser, Context context) {

        KWSSDK factory = new KWSSDK(kwsEnvironment);
        ISessionService sessionService = factory.get(ISessionService.class);

        if (sessionService != null) {
            sessionService.saveLoggedUser(context, loggedUser);
        }
    }

    private TokenData getMetadataFromToken(String token) {

        return UtilsHelper.getMetadataFromToken(token);
    }

}
