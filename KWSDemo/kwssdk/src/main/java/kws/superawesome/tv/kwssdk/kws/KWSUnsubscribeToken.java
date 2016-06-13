package kws.superawesome.tv.kwssdk.kws;

/**
 * Created by gabriel.coman on 13/06/16.
 */
public class KWSUnsubscribeToken {

    // listener
    public KWSUnsubscribeTokenInterface listener = null;

    public void request () {

    }

    // <Private> functions

    private void lisTokenWasUnsubscribed () {
        if (listener != null) {
            listener.tokenWasUnsubscribed();
        }
    }

    private void lisTokenError () {
        if (listener != null) {
            listener.tokenError();
        }
    }
}
