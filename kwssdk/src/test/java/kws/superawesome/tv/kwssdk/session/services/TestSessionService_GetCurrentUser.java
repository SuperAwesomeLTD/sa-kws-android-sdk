package kws.superawesome.tv.kwssdk.session.services;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import kws.superawesome.tv.kwssdk.ComplianceSDK;
import kws.superawesome.tv.kwssdk.TestBaseService;
import tv.superawesome.protobufs.authentication.models.ILoggedUserModel;
import tv.superawesome.protobufs.session.services.ISessionService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by guilherme.mota on 19/03/2018.
 */

public class TestSessionService_GetCurrentUser extends TestBaseService {

    // class or data to test
    private ISessionService service;

    @Override
    @Before
    public void setup() throws Throwable {
        super.setup();
        ComplianceSDK sdk = new ComplianceSDK(environment, task);
        service = sdk.getService(ISessionService.class);
    }

    @Test
    public void test_SessionService_ToNotBeNull() {
        Assert.assertNotNull(service);
    }

    @Test
    public void test_SessionService_GetCurrentUser_WithInvalidSession() {
        // given
        String token = "eyJhbGciOiJIUzzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4iOnRydWV9TJVA95OrM7E2cBabEfxjoYZgeFONFh7HgQ";

        // when
        Context context = mock(Context.class);
        SharedPreferences prefs = mock(SharedPreferences.class);
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.getString("KWS_USER_TOKEN", null)).thenReturn(token);

        // then
        ILoggedUserModel user = service.getCurrentUser(context);
        Assert.assertNull(user);
    }

    @Test
    public void test_SessionService_GetCurrentUser_WithoutPreviousSession() {
        // when
        Context context = mock(Context.class);
        SharedPreferences prefs = mock(SharedPreferences.class);
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.getString("KWS_USER_TOKEN", null)).thenReturn(null);

        // then
        ILoggedUserModel user = service.getCurrentUser(context);
        Assert.assertNull(user);
    }

    @Test
    public void test_SessionService_GetCurrentUser_WithPreviousSession() {
        // given
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjM0NDMsImFwcElkIjozNTgsImNsaWVudElkIjoia3dzLXNkay10ZXN0aW5nIiwic2NvcGUiOiJ1c2VyIiwiaWF0IjoxNTIxNDczMTM3LCJleHAiOjE1MjE1NTk1MzcsImlzcyI6InN1cGVyYXdlc29tZSJ9.nsZ05--MGbLcc3gvRfp0w3XJhcZeszTP5iwQg6trp7Y";

        // when
        Context context = mock(Context.class);
        SharedPreferences prefs = mock(SharedPreferences.class);
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.getAll()).thenReturn(mock(HashMap.class));
        when(prefs.getAll().get("KWS_USER_TOKEN")).thenReturn(token);

        // then
        ILoggedUserModel user = service.getCurrentUser(context);
        Assert.assertNotNull(user);
        Assert.assertEquals(3443, user.getId());
        Assert.assertEquals(token, user.getToken());
    }

}
