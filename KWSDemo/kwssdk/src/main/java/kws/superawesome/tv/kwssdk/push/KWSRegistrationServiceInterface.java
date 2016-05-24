package kws.superawesome.tv.kwssdk.push;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public interface KWSRegistrationServiceInterface {

    void didGetToken(String token);
    void didNotGetToken();

}
