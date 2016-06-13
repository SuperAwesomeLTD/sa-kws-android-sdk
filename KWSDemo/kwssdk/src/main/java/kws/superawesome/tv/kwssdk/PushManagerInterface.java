package kws.superawesome.tv.kwssdk;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public interface PushManagerInterface {
    void didRegister (String token);
    void didNotRegister ();
}
