package kws.superawesome.tv;

/**
 * Created by gabriel.coman on 24/05/16.
 */
public interface PushManagerInterface {
    void didRegister (String token);
    void didNotRegister ();
    void didUnregister ();
    void didNotUnregister ();
}
