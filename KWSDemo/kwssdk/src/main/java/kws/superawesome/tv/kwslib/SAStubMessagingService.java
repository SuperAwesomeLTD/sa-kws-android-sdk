package kws.superawesome.tv.kwslib;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by gabriel.coman on 13/06/16.
 */
public class SAStubMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("SuperAwesome", "From: " + remoteMessage.getFrom());
        Log.d("SuperAwesome", "Body" + remoteMessage.getNotification());
        if (remoteMessage.getNotification() != null) {
            Log.d("SuperAwesome", "Notification Message Body: " + remoteMessage.getNotification().getBody());
        }
    }
}
