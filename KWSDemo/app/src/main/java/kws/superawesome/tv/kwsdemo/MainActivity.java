package kws.superawesome.tv.kwsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import kws.superawesome.tv.kwslib.SAUtils;
import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.KWSErrorType;
import kws.superawesome.tv.kwssdk.KWSInterface;

public class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMTAsImFwcElkIjozMTMsImNsaWVudElkIjoic2EtbW9iaWxlLWFwcC1zZGstY2xpZW50LTAiLCJzY29wZSI6InVzZXIiLCJpYXQiOjE0Njc2MzE4ODQsImV4cCI6MTQ2NzcxODI4NCwiaXNzIjoic3VwZXJhd2Vzb21lIn0.z4BHNBnAj5FPLPhzsYIQxdZ9tSbftIZM50nUFmyR1iU";
    private static final String API = "https://kwsapi.demo.superawesome.tv/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KWS.sdk.setApplicationContext(getApplicationContext());
        KWS.sdk.setup(TOKEN, API, new KWSInterface() {
            @Override
            public void kwsSDKDoesAllowUserToRegisterForRemoteNotifications() {
                Log.d("SuperAwesome", "User is allowed to register for remote notifications");
                KWS.sdk.registerForRemoteNotifications();
            }

            @Override
            public void kwsSDKDidRegisterUserForRemoteNotifications() {
                Log.d("SuperAwesome", "User successfully registered for remote notifications");
            }

            @Override
            public void kwsSDKDidUnregisterUserForRemoteNotifications() {
                Log.d("SuperAwesome", "User successfully un-registered for remote notifications");
            }

            @Override
            public void kwsSDKDidFailToRegisterUserForRemoteNotificationsWithError(KWSErrorType type) {
                switch (type){
                    case NoKWSPermission:
                        break;
                    case NoSystemPermission:
                        break;
                    case ParentEmailNotFound: {
                        Log.d("SuperAwesome", "User could not register becasue KWS could not find parent email");
                        KWS.sdk.submitParentEmail("dev.gabriel.coman@gmail.com");
                        break;
                    }
                    case ParentEmailInvalid:
                        Log.d("SuperAwesome", "Parent email invalid");
                        break;
                    case FirebaseNotSetup:
                        break;
                    case FirebaseCouldNotGetToken:
                        break;
                    case NetworkError:
                        break;
                    case CouldNotUnsubscribeInKWS:
                        break;
                }
            }
        });
        KWS.sdk.checkIfNotificationsAreAllowed();
    }
}
