package kws.superawesome.tv.kwssdk.kws;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public interface KWSParentEmailInterface {

    /**
     This function is called when the parent email has been successfully submitted
     to the KWS backend
     */
    void emailSubmittedInKWS ();

    /**
     This function is called when there's a network or email error of any kind
     */
    void emailError ();

    /**
     * Called when the email is invalid
     */
    void invalidEmail ();

}
