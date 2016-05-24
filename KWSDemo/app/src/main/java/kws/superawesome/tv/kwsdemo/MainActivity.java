package kws.superawesome.tv.kwsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.KWSInterface;
import kws.superawesome.tv.kwssdk.KWSManager;
import kws.superawesome.tv.kwssdk.KWSManagerInterface;
import kws.superawesome.tv.kwssdk.PushManager;
import kws.superawesome.tv.kwssdk.PushManagerInterface;

public class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String token00 = "020-10-21021kjjksajklas";
        String token01 = "89022jksjlaljkiouwqjnsasaljoisaoiwqoioiqw.jsajkjssu.hsjkajksajlsajlksa";
        String token1 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjc0NCwiYXBwSWQiOjMxMywiY2xpZW50SWQiOiJzYS1tb2JpbGUtYXBwLXNkay1jbGllbnQtMCIsInNjb3BlIjoidXNlciIsImlhdCI6MTQ2NDA4NTUyMSwiZXhwIjoxNDY0MTcxOTIxLCJpc3MiOiJzdXBlcmF3ZXNvbWUifQ.dnzKjgkaUF3hjSlu22BtzBGBmuFchYVcXdicptUfSyI";
        String token3 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjc0NiwiYXBwSWQiOjMxMywiY2xpZW50SWQiOiJzYS1tb2JpbGUtYXBwLXNkay1jbGllbnQtMCIsInNjb3BlIjoidXNlciIsImlhdCI6MTQ2NDA4MjQwMywiZXhwIjoxNDY0MTY4ODAzLCJpc3MiOiJzdXBlcmF3ZXNvbWUifQ.PEcEcUwKzB7YEMWXLSP8VqCn8yH01i-ZJtgtefDh7Fg";
        String token4 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjc0NywiYXBwSWQiOjMxMywiY2xpZW50SWQiOiJzYS1tb2JpbGUtYXBwLXNkay1jbGllbnQtMCIsInNjb3BlIjoidXNlciIsImlhdCI6MTQ2NDA5MjYyMiwiZXhwIjoxNDY0MTc5MDIyLCJpc3MiOiJzdXBlcmF3ZXNvbWUifQ.tdgWVh_FSsF67RxXiGaYzQPpAwoXs7JnRS2FJkKfn1U";

        KWS.sdk.setApplicationContext(getApplicationContext());
        KWS.sdk.setup(token4, "https://kwsapi.demo.superawesome.tv/v1/", "32", new KWSInterface() {
            @Override
            public void isAllowedToRegisterForRemoteNotifications() {
                Log.d("SuperAwesome", "User is allowed to register for remote notifications");
                KWS.sdk.registerForRemoteNotifications();
            }

            @Override
            public void isAlreadyRegisteredForRemoteNotifications() {
                Log.d("SuperAwesome", "User is already registered for remote notifications");
            }

            @Override
            public void didRegisterForRemoteNotifications() {
                Log.d("SuperAwesome", "User successfully registered for remote notifications");
            }

            @Override
            public void didFailBecauseKWSDoesNotAllowRemoteNotifications() {
                Log.d("SuperAwesome", "User could not register because KWS does not allow it");
            }

            @Override
            public void didFailBecauseKWSCouldNotFindParentEmail() {
                Log.d("SuperAwesome", "User could not register becasue KWS could not find parent email");
            }

            @Override
            public void didFailBecauseRemoteNotificationsAreDisabled() {
                Log.d("SuperAwesome", "User could not register because remote notifications are disabled");
            }

            @Override
            public void didFailBecauseOfError() {
                Log.d("SuperAwesome", "User could not register because of misc error");
            }
        });
        KWS.sdk.checkIfNotificationsAreAllowed();
    }
}
