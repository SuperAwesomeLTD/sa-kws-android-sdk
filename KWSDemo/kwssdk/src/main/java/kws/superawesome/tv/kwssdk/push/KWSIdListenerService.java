package kws.superawesome.tv.kwssdk.push;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public class KWSIdListenerService extends InstanceIDListenerService {

    private static final String TAG = "KWSIdListenerService";

    @Override
    public void onTokenRefresh() {

        KWSRegistrationService registration = new KWSRegistrationService();
        registration.listener = new KWSRegistrationServiceInterface() {
            @Override
            public void didGetToken(String token) {
                PushRegisterPermission registerPermission = new PushRegisterPermission();
                registerPermission.register(token);
            }

            @Override
            public void didNotGetToken() {
                // do nothing
            }
        };
        registration.register(this);
    }
}
