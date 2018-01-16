package kws.superawesome.tv.kwssdk.providers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.providers.UserProvider;
import kws.superawesome.tv.kwssdk.base.responses.Score;
import kws.superawesome.tv.kwssdk.base.responses.UserDetails;

/**
 * Created by guilherme.mota on 16/01/2018.
 */

public class TestUserProvider extends TestBaseProvider {

    // class to test
    private UserProvider provider;

    //given
    private int goodAppId = 2;
    private int badAppId = 0;

    private int goodUserId = 25;
    private int badUserId = 0;

    private String goodMockedToken = "good_token";
    private String goodEmail = "good_email";

    private String badEmail = "bad_email";

    private String goodPermissionString = "good_permission";
    private String badPermissionString = "bad_permission";


    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();


        //then
        // init class to test
        provider = new UserProvider(environment, task);


    }

    //for User Details

    @Test
    public void testUserProviderGetUserDetailsOK() {

        provider.getUserDetails(goodUserId, goodMockedToken, new Function2<UserDetails, Throwable, Unit>() {
            @Override
            public Unit invoke(UserDetails userDetails, Throwable throwable) {

                Assert.assertNotNull(userDetails);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserProviderGetUserDetailsBadUserId() {

        provider.getUserDetails(badUserId, goodMockedToken, new Function2<UserDetails, Throwable, Unit>() {
            @Override
            public Unit invoke(UserDetails userDetails, Throwable throwable) {

                Assert.assertNull(userDetails);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


    //for Invite User
    @Test
    public void testUserProviderInviteUserOK() {

        provider.inviteUser(goodEmail, goodUserId, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertTrue(aSuccess);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserProviderInviteUserBadEmptyEmail() {
        provider.inviteUser("", goodUserId, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserProviderInviteUserBadNullEmail() {
        provider.inviteUser(null, goodUserId, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }

    @Test
    public void testUserProviderInviteUserBadEmail() {

        provider.inviteUser(badEmail, goodUserId, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserProviderInviteUserBadUserId() {

        provider.inviteUser(goodEmail, badUserId, goodMockedToken, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


    //for Get Score
    @Test
    public void testUserProviderGetScoreOK() {

        provider.getScore(goodAppId, goodMockedToken, new Function2<Score, Throwable, Unit>() {
            @Override
            public Unit invoke(Score score, Throwable throwable) {

                Assert.assertNotNull(score);
                Assert.assertNull(throwable);

                return null;
            }
        });


    }

    @Test
    public void testUserProviderGetScoreBadUserId() {

        provider.getScore(badAppId, goodMockedToken, new Function2<Score, Throwable, Unit>() {
            @Override
            public Unit invoke(Score score, Throwable throwable) {

                Assert.assertNull(score);
                Assert.assertNotNull(throwable);

                return null;
            }
        });


    }


    //for Request Permissions

    @Test
    public void testUserProviderRequestPermissionOK() {
        List<String> listOfPermissions = new ArrayList<>();
        listOfPermissions.add(goodPermissionString);

        provider.requestPermissions(goodUserId, goodMockedToken, listOfPermissions, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertTrue(aSuccess);
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserProviderRequestPermissionBadPermission() {
        List<String> listOfPermissions = new ArrayList<>();
        listOfPermissions.add(badPermissionString);

        provider.requestPermissions(goodUserId, goodMockedToken, listOfPermissions, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testUserProviderRequestPermissionBadUserId() {
        List<String> listOfPermissions = new ArrayList<>();

        provider.requestPermissions(badUserId, goodMockedToken, listOfPermissions, new Function2<Boolean, Throwable, Unit>() {
            @Override
            public Unit invoke(Boolean aSuccess, Throwable throwable) {

                Assert.assertFalse(aSuccess);
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }
}
