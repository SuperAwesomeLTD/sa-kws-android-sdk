package kws.superawesome.tv.kwssdk.session.services;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kws.superawesome.tv.kwssdk.ComplianceSDK;
import kws.superawesome.tv.kwssdk.TestBaseService;
import kws.superawesome.tv.kwssdk.internal.LoggedUserModel;
import kws.superawesome.tv.kwssdk.internal.TokenData;
import tv.superawesome.protobufs.session.services.ISessionService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by guilherme.mota on 19/03/2018.
 */

public class TestSessionService_SaveLoggedUser extends TestBaseService {

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

    @Test(expected = IllegalArgumentException.class)
    public void testSessionService_SaveLoggedUser_NullToken() throws Exception {
        // given
        Context context = mock(Context.class);
        SharedPreferences prefs = mock(SharedPreferences.class);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        String token = null;

        // when
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.edit()).thenReturn(editor);
        when(editor.commit()).thenReturn(true);

        LoggedUserModel user = new LoggedUserModel(token, new TokenData(), 0);

        // then
        boolean success = service.saveLoggedUser(context, user);
        Assert.assertFalse(success);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_SessionService_SaveLoggedUser_NullUser() throws Exception {
        // given
        Context context = mock(Context.class);
        SharedPreferences prefs = mock(SharedPreferences.class);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);

        // when
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.edit()).thenReturn(editor);
        when(editor.commit()).thenReturn(true);

        // then
        boolean success = service.saveLoggedUser(context, null);
        Assert.assertTrue(success);
    }

    @Test
    public void test_SessionService_SaveLoggedUser_OK() {
        // given
        Context context = mock(Context.class);
        SharedPreferences prefs = mock(SharedPreferences.class);
        SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjM0NDMsImFwcElkIjozNTgsImNsaWVudElkIjoia3dzLXNkay10ZXN0aW5nIiwic2NvcGUiOiJ1c2VyIiwiaWF0IjoxNTIxNDczMTM3LCJleHAiOjE1MjE1NTk1MzcsImlzcyI6InN1cGVyYXdlc29tZSJ9.nsZ05--MGbLcc3gvRfp0w3XJhcZeszTP5iwQg6trp7Y";

        int userId = 3443;
        int appId = 348;
        String clientId = "__client_id__";
        String scope = "__scope__";
        String iss = "__iss__";
        Long iat = 1000L;
        Long exp = 2000L;
        TokenData tokenData = new TokenData(userId, appId, clientId, scope, iss, iat, exp);

        // when
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(prefs);
        when(prefs.edit()).thenReturn(editor);
        when(editor.commit()).thenReturn(true);

        LoggedUserModel user = new LoggedUserModel(token, tokenData, userId);

        // then
        boolean success = service.saveLoggedUser(context, user);
        Assert.assertTrue(success);
    }
}