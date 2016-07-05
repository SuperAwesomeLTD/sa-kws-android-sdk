package kws.superawesome.tv.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by gabriel.coman on 05/07/16.
 */
public class FirebaseStubMessagingService extends FirebaseMessagingService {
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
