package kws.superawesome.tv.kwssdk;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public interface KWSManagerInterface {

    void pushDisabledInKWS ();
    void parentEmailIsMissingInKWS ();
    void networkError ();
    void isAllowedToRegister ();
    void isAlreadyRegistered ();

}
