package kws.superawesome.tv.kwssdk;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.base.environments.KWSNetworkEnvironment;
import kws.superawesome.tv.kwssdk.base.models.internal.LoggedUser;
import kws.superawesome.tv.kwssdk.base.models.AppDataWrapper;
import kws.superawesome.tv.kwssdk.base.models.AppData;
import kws.superawesome.tv.kwssdk.base.models.Permissions;
import kws.superawesome.tv.kwssdk.base.models.ApplicationProfile;
import kws.superawesome.tv.kwssdk.base.models.CreateUser;
import kws.superawesome.tv.kwssdk.base.models.HasTriggeredEvent;
import kws.superawesome.tv.kwssdk.base.models.Leaders;
import kws.superawesome.tv.kwssdk.base.models.LeadersDetail;
import kws.superawesome.tv.kwssdk.base.models.Login;
import kws.superawesome.tv.kwssdk.base.models.Points;
import kws.superawesome.tv.kwssdk.base.models.RandomUsername;
import kws.superawesome.tv.kwssdk.base.models.Score;
import kws.superawesome.tv.kwssdk.base.models.Address;
import kws.superawesome.tv.kwssdk.base.models.UserDetails;
import kws.superawesome.tv.kwssdk.base.services.AppService;
import kws.superawesome.tv.kwssdk.base.services.AuthService;
import kws.superawesome.tv.kwssdk.base.services.CreateUserService;
import kws.superawesome.tv.kwssdk.base.services.EventsService;
import kws.superawesome.tv.kwssdk.base.services.LoginService;
import kws.superawesome.tv.kwssdk.base.services.RandomUsernameService;
import kws.superawesome.tv.kwssdk.base.services.UserService;
import kws.superawesome.tv.kwssdk.models.appdata.KWSAppData;
import kws.superawesome.tv.kwssdk.models.leaderboard.KWSLeader;
import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata;
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
import tv.superawesome.samobilebase.parsebase64.ParseBase64Request;
import tv.superawesome.samobilebase.parsebase64.ParseBase64Task;
import tv.superawesome.samobilebase.parsejson.ParseJsonRequest;
import tv.superawesome.samobilebase.parsejson.ParseJsonTask;

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
            createUserService.createUser(username, password, dateOfBirth, country, parentEmail,
                    new Function2<CreateUser, Throwable, Unit>() {
                        @Override
                        public Unit invoke(CreateUser createdUser, Throwable throwable) {

                            if (createdUser != null) {
                                String token = createdUser.getToken();
                                KWSMetadata kwsMetadata = getMetadataFromToken(token);
                                if (kwsMetadata != null && kwsMetadata.isValid()) {
                                    LoggedUser loggedUser = new LoggedUser(token, kwsMetadata);
                                    setLoggedUser(loggedUser);
                                    listener.didCreateUser(KWSChildrenCreateUserStatus.Success);
                                } else {
                                    listener.didCreateUser(KWSChildrenCreateUserStatus.InvalidOperation);
                                }
                            } else {
                                listener.didCreateUser(KWSChildrenCreateUserStatus.InvalidOperation);
                                if(throwable != null){
                                    Log.d("SA", throwable.toString());
                                }
                            }
                            return null;
                        }
                    });
        }

    }

    public void loginUser(Context context, String username, String password, final KWSChildrenLoginUserInterface listener) {

        LoginService loginService = KWSSDK.get(kwsEnvironment, LoginService.class);

        if (loginService != null) {
            loginService.loginUser(username, password, new Function2<Login, Throwable, Unit>() {
                @Override
                public Unit invoke(Login login, Throwable throwable) {

                    if (login != null) {
                        String token = login.getToken();
                        KWSMetadata kwsMetadata = getMetadataFromToken(token);
                        if (kwsMetadata != null && kwsMetadata.isValid()) {
                            LoggedUser loggedUser = new LoggedUser(token, kwsMetadata);
                            setLoggedUser(loggedUser);
                            listener.didLoginUser(KWSChildrenLoginUserStatus.Success);
                        } else {
                            listener.didLoginUser(KWSChildrenLoginUserStatus.InvalidCredentials);
                        }
                    } else {
                        listener.didLoginUser(KWSChildrenLoginUserStatus.InvalidCredentials);
                        if (throwable != null) {
                            Log.d("SA", throwable.toString());
                        }
                    }

                    return null;
                }
            });
        }
    }

    public void authWithSingleSignOnUrl(String singleSignOnUrl, Activity parent, final KWSChildrenLoginUserInterface listener) {

        AuthService authService = KWSSDK.get(kwsEnvironment, AuthService.class);

        if (authService != null) {
            authService.authUser(singleSignOnUrl, parent, new Function2<LoggedUser, Throwable, Unit>() {
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

        RandomUsernameService randomUsernameService = KWSSDK.get(kwsEnvironment, RandomUsernameService.class);

        if (randomUsernameService != null) {
            randomUsernameService.getRandomUsername(new Function2<RandomUsername, Throwable, Unit>() {
                @Override
                public Unit invoke(RandomUsername randomUsername, Throwable throwable) {
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

        UserService userService = KWSSDK.get(kwsEnvironment, UserService.class);

        if (userService != null) {

            if (loggedUser == null || loggedUser.metadata == null) {
                listener.didGetUser(null);
                return;
            }

            userService.getUserDetails(loggedUser.metadata.userId, loggedUser.token, new Function2<UserDetails, Throwable, Unit>() {
                @Override
                public Unit invoke(UserDetails userDetailsResponse, Throwable throwable) {
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

                private KWSUser buildKWSUser(UserDetails userDetailsResponse) {
                    KWSUser kwsUser = new KWSUser();

                    if (userDetailsResponse.getId() != null) {
                        kwsUser.id = userDetailsResponse.getId();
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

                private KWSApplicationProfile buildProfile(ApplicationProfile applicationProfileResponse) {


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

                private KWSPermissions buildPermissions(Permissions permissions) {

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

                private KWSPoints buildPoints(Points points) {
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

                private KWSAddress buildAddress(Address addressResponse) {
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

    // applicationPermissions
    public void updateParentEmail(Context context, String email, KWSChildrenUpdateParentEmailInterface listener) {
        parentEmail.execute(context, email, listener);
    }

    public void requestPermission(Context context, KWSChildrenPermissionType[] requestedPermissions, final KWSChildrenRequestPermissionInterface listener) {
        UserService userService = KWSSDK.get(kwsEnvironment, UserService.class);

        if (userService != null) {

            if (loggedUser == null || loggedUser.metadata == null) {
                listener.didRequestPermission(KWSChildrenRequestPermissionStatus.NetworkError);
                return;
            }

            List<String> listOfPermissions = getListOfPermissions(requestedPermissions);

            userService.requestPermissions(loggedUser.metadata.userId, loggedUser.token,
                    listOfPermissions, new Function2<Boolean, Throwable, Unit>() {
                        @Override
                        public Unit invoke(Boolean success, Throwable throwable) {

                            if (success) {
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

        UserService userService = KWSSDK.get(kwsEnvironment, UserService.class);

        if (userService != null) {
            if (loggedUser == null || loggedUser.metadata == null) {
                listener.didInviteUser(false);
                return;
            }

            userService.inviteUser(emailAddress, loggedUser.metadata.userId, loggedUser.token, new Function2<Boolean, Throwable, Unit>() {
                @Override
                public Unit invoke(Boolean isInviteUser, Throwable throwable) {

                    listener.didInviteUser(isInviteUser);

                    return null;
                }
            });
        }

    }

    // events, points, leader boards

    public void triggerEvent(Context context, String token, int points, final KWSChildrenTriggerEventInterface listener) {

        EventsService eventsService = KWSSDK.get(kwsEnvironment, EventsService.class);

        if (eventsService != null) {
            if (loggedUser == null || loggedUser.metadata == null) {
                listener.didTriggerEvent(false);
                return;
            }

            eventsService.triggerEvent(points, loggedUser.metadata.userId, loggedUser.token, token, new Function2<Boolean, Throwable, Unit>() {
                @Override
                public Unit invoke(Boolean isTriggerEvent, Throwable throwable) {

                    listener.didTriggerEvent(isTriggerEvent);

                    return null;
                }
            });
        }


    }

    public void hasTriggeredEvent(Context context, int eventId, final KWSChildrenHasTriggeredEventInterface listener) {

        EventsService eventsService = KWSSDK.get(kwsEnvironment, EventsService.class);

        if (eventsService != null) {

            if (loggedUser == null || loggedUser.metadata == null) {
                listener.didTriggerEvent(false);
                return;
            }

            eventsService.hasTriggeredEvent(loggedUser.metadata.userId, eventId, loggedUser.token, new Function2<HasTriggeredEvent, Throwable, Unit>() {
                @Override
                public Unit invoke(HasTriggeredEvent hasTriggeredEvent, Throwable throwable) {

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

        UserService userService = KWSSDK.get(kwsEnvironment, UserService.class);

        if (userService != null) {

            if (loggedUser == null || loggedUser.metadata == null) {
                listener.didGetScore(null);
                return;
            }


            userService.getScore(loggedUser.metadata.appId, loggedUser.token, new Function2<Score, Throwable, Unit>() {
                @Override
                public Unit invoke(Score score, Throwable throwable) {

                    if (score != null) {
                        KWSScore kwsScore = buildScore(score);
                        listener.didGetScore(kwsScore);
                    } else {
                        listener.didGetScore(null);
                    }

                    return null;
                }

                private KWSScore buildScore(Score score) {
                    KWSScore kwsScore = new KWSScore();

                    kwsScore.score = score.getScore();
                    kwsScore.rank = score.getRank();

                    return kwsScore;
                }
            });

        }


    }

    public void getLeaderboard(Context context, final KWSChildrenGetLeaderboardInterface listener) {

        AppService appService = KWSSDK.get(kwsEnvironment, AppService.class);

        if (appService != null) {

            if (loggedUser == null || loggedUser.metadata == null) {
                listener.didGetLeaderboard(new ArrayList<KWSLeader>());
                return;
            }

            appService.getLeaders(loggedUser.metadata.appId, loggedUser.token, new Function2<Leaders, Throwable, Unit>() {
                @Override
                public Unit invoke(Leaders leaders, Throwable throwable) {

                    if (leaders != null) {
                        ArrayList<KWSLeader> listOfKWSLeader = getListOfKWSLeaders(leaders.getResults());
                        listener.didGetLeaderboard(listOfKWSLeader);

                    } else {
                        listener.didGetLeaderboard(new ArrayList<KWSLeader>());
                    }

                    return null;
                }

                private ArrayList<KWSLeader> getListOfKWSLeaders(ArrayList<LeadersDetail> results) {

                    ArrayList<KWSLeader> listOfKWSLeader = new ArrayList<>();

                    for (LeadersDetail leadersDetail : results) {
                        KWSLeader builtKWSLeaderObject = buildKWSLeaderObject(leadersDetail);
                        listOfKWSLeader.add(builtKWSLeaderObject);
                    }
                    return listOfKWSLeader;
                }

                private KWSLeader buildKWSLeaderObject(LeadersDetail leadersDetail) {
                    KWSLeader kwsLeader = new KWSLeader();
                    kwsLeader.rank = leadersDetail.getRank();
                    kwsLeader.score = leadersDetail.getScore();
                    kwsLeader.user = leadersDetail.getUser();
                    return kwsLeader;
                }
            });

        }


    }

    // app data

    public void getAppData(Context context, final KWSChildrenGetAppDataInterface listener) {
//        getAppData.execute(context, listener);

        AppService appService = KWSSDK.get(kwsEnvironment, AppService.class);

        if (appService != null) {

            if (loggedUser == null || loggedUser.metadata == null) {
                listener.didGetAppData(new ArrayList<KWSAppData>());
                return;
            }

            appService.getAppData(loggedUser.metadata.appId,
                    loggedUser.metadata.userId,
                    loggedUser.token, new Function2<AppDataWrapper, Throwable, Unit>() {
                        @Override
                        public Unit invoke(AppDataWrapper appDataWrapper, Throwable throwable) {

                            if (appDataWrapper != null) {
                                ArrayList<KWSAppData> listOfKWSAppData = getListOfKWSAppData(appDataWrapper.getResults());
                                listener.didGetAppData(listOfKWSAppData);
                            } else {
                                listener.didGetAppData(new ArrayList<KWSAppData>());
                            }

                            return null;
                        }

                        private ArrayList<KWSAppData> getListOfKWSAppData(ArrayList<AppData> results) {
                            ArrayList<KWSAppData> listOfKWSAppData = new ArrayList<>();

                            for (AppData appData : results) {
                                KWSAppData builtKWSAppDataObject = buildKWSAppDataObject(appData);
                                listOfKWSAppData.add(builtKWSAppDataObject);
                            }
                            return listOfKWSAppData;
                        }

                        private KWSAppData buildKWSAppDataObject(AppData appData) {

                            KWSAppData kwsAppData = new KWSAppData();
                            kwsAppData.name = appData.getName();
                            kwsAppData.value = appData.getValue();

                            return kwsAppData;

                        }
                    });
        }


    }

    public void setAppData(Context context, int value, String name, final KWSChildrenSetAppDataInterface listener) {

        AppService appService = KWSSDK.get(kwsEnvironment, AppService.class);

        if (appService != null) {

            if (loggedUser == null || loggedUser.metadata == null) {
                listener.didSetAppData(false);
                return;
            }

            appService.setAppData(loggedUser.metadata.appId,
                    loggedUser.metadata.userId,
                    name,
                    value,
                    loggedUser.token, new Function2<Boolean, Throwable, Unit>() {
                        @Override
                        public Unit invoke(Boolean isSetAppData, Throwable throwable) {

                            listener.didSetAppData(isSetAppData);

                            return null;
                        }
                    });

        }

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

    private KWSMetadata getMetadataFromToken(String token) {
        ParseBase64Request base64req = new ParseBase64Request(token);
        ParseBase64Task base64Task = new ParseBase64Task();
        String metadataJson = base64Task.execute(base64req);

        ParseJsonRequest parseJsonReq = new ParseJsonRequest(metadataJson);
        ParseJsonTask parseJsonTask = new ParseJsonTask();
        return parseJsonTask.execute(parseJsonReq, KWSMetadata.class);
    }

}
