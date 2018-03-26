package kws.superawesome.tv.kwssdk.services.session;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import kws.superawesome.tv.kwssdk.base.KWSSDK;
import kws.superawesome.tv.kwssdk.services.TestBaseService;
import tv.superawesome.protobufs.features.session.ISessionService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by guilherme.mota on 19/03/2018.
 */

public class TestSessionService_IsUserLoggedIn extends TestBaseService {

    // class or data to test
    private ISessionService service;

    @Override
    @Before
    public void setup () throws Throwable {
        super.setup();
        KWSSDK factory = new KWSSDK(environment, task);
        service = factory.get(ISessionService.class);
    }

    @Test
    public void testSessionService_ToNotBeNull () {
        Assert.assertNotNull(service);
    }

    @Test
    public void testSessionService_IsUserLoggedIn_WithInvalidSession () {
        // given
        Context context = mock(Context.class);
        SharedPreferences prefs = mock(SharedPreferences.class);
        String token = "eyJhbGciOiJIUzzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4iOnRydWV9TJVA95OrM7E2cBabEfxjoYZgeFONFh7HgQ";

        // when
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.getString("KWS_USER_TOKEN", null)).thenReturn(token);

        // then
        boolean isLogged = service.isUserLoggedIn(context);
        Assert.assertFalse(isLogged);
    }

    @Test
    public void testSessionService_IsUserLoggedIn_WithoutPreviousSession () {
        // given
        Context context = mock(Context.class);
        SharedPreferences prefs = mock(SharedPreferences.class);

        // when
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.getString("KWS_USER_TOKEN", null)).thenReturn(null);

        // then
        boolean isLogged = service.isUserLoggedIn(context);
        Assert.assertFalse(isLogged);
    }

    @Test
    public void testSessionService_IsUserLoggedIn_WithPreviousSession () {
        // given
        Context context = mock(Context.class);
        SharedPreferences prefs = mock(SharedPreferences.class);
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjM0NDMsImFwcElkIjozNTgsImNsaWVudElkIjoia3dzLXNkay10ZXN0aW5nIiwic2NvcGUiOiJ1c2VyIiwiaWF0IjoxNTIxNDczMTM3LCJleHAiOjE1MjE1NTk1MzcsImlzcyI6InN1cGVyYXdlc29tZSJ9.nsZ05--MGbLcc3gvRfp0w3XJhcZeszTP5iwQg6trp7Y";

        // when
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.getAll()).thenReturn(mock(HashMap.class));
        when(prefs.getAll().get("KWS_USER_TOKEN")).thenReturn(token);

        // then
        boolean isLogged = service.isUserLoggedIn(context);
        Assert.assertTrue(isLogged);

    }
}
