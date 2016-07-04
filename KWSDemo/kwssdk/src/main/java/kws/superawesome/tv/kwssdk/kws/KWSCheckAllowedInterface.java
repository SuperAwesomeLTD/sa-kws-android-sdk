package kws.superawesome.tv.kwssdk.kws;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public interface KWSCheckAllowedInterface {

    /**
     This is called when SDK has found push notification permissions are
     enabled in KWS
     */
    void pushEnabledInKWS ();

    /**
     This is called when SDK has found push notification permissions are
     disabled in KWS
     */
    void pushDisabledInKWS ();

    /**
     This is called in case of any type of network error
     */
    void checkError ();
}
