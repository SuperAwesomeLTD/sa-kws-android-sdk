package kws.superawesome.tv.kwssdk;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public interface KWSInterface {

    /**
     This function is called by the KWS singleton when the user is permitted
     to register for push notifications.
     In this callback the call for finishing the registration should be done
     */
    void isAllowedToRegisterForRemoteNotifications ();

    /**
     This function is called by the KWS singleton when the user is already
     registered for push notifications, and no other action should be taken
     */
    void isAlreadyRegisteredForRemoteNotifications();

    /**
     This function is called by the KWS singleton when the user has been
     successfully registered
     */
    void didRegisterForRemoteNotifications(String token);

    /**
     This function is called by the KWS singleton when the user is not allowed
     push notifications by KWS
     */
    void didFailBecauseKWSDoesNotAllowRemoteNotifications();

    /**
     This function is called by the KWS singleton when the user has no
     associated Parent Email in the KWS backend, and thus the email must be
     first submitted, and then the SDK implementer must ask for
     permissions again
     */
    void didFailBecauseKWSCouldNotFindParentEmail();

    /**
     This function is called by the KWS singleton when push notifications are
     totally disabled by the user
     */
    void didFailBecauseRemoteNotificationsAreDisabled();

    /**
     * Parent email is invalid
     */
    void didFailBecauseParentEmailIsInvalid();

    /**
     This function is called by the KWS singleton because of some network or
     some other kidn of error
     */
    void didFailBecauseOfError();
}
