package kws.superawesome.tv.kwsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.KWSInterface;
import kws.superawesome.tv.kwssdk.KWSManager;
import kws.superawesome.tv.kwssdk.KWSManagerInterface;
import kws.superawesome.tv.kwssdk.PushManager;
import kws.superawesome.tv.kwssdk.PushManagerInterface;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;

public class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjc0OSwiYXBwSWQiOjMxMywiY2xpZW50SWQiOiJzYS1tb2JpbGUtYXBwLXNkay1jbGllbnQtMCIsInNjb3BlIjoidXNlciIsImlhdCI6MTQ2NTgwNjg0NSwiZXhwIjoxNDY1ODkzMjQ1LCJpc3MiOiJzdXBlcmF3ZXNvbWUifQ.IiPf7JuntZKUChKl1yy0FWLnNE4I7zChcZtL0dCZnKk";
        KWS.sdk.setApplicationContext(getApplicationContext());
        KWS.sdk.setup(token, "https://kwsapi.demo.superawesome.tv/v1/", "32", new KWSInterface() {
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
