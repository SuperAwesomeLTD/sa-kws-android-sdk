package kws.superawesome.tv.kwssdk.kws;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public interface KWSRequestPermissionInterface {

    /**
     This function is called when the push notification permission has been
     successfully requested in the KWS backed
     */
    void pushPermissionRequestedInKWS ();

    /**
     This function is called when the push notification permissions could not
     be granted in the KWS backend because a parnet email is missing
     */
    void parentEmailIsMissingInKWS ();

    /**
     This funciton is called in case of any type of network error
     */
    void permissionError();
}
