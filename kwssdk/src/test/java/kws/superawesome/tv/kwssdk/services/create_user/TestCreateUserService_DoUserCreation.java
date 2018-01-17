package kws.superawesome.tv.kwssdk.services.create_user;

import org.junit.Assert;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.responses.CreateUser;

/**
 * Created by guilherme.mota on 17/01/2018.
 */

public class TestCreateUserService_DoUserCreation extends TestCreateUserService {

    private String goodUsername = "testuser123";
    private String badUsername = "bad_username";

    private String goodPassword = "testtest";
    private String badPassword = "bad_password";

    private String goodDOB = "2012-03-03";
    private String badDOB = "bad_dob";

    private String goodCountry = "US";
    private String badCountry = "bad_country";

    private String goodParentEmail = "good_parent@email.com";
    private String badParentEmail = "bad_parent";


    @Test
    public void testCreateUserServiceDoUserCreationOK() {
        //then
        service.doUserCreation(environment, goodUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNotNull(createUser);
                        Assert.assertNull(throwable);

                        return null;
                    }
                });


    }

    @Test
    public void testCreateUserServiceDoUserCreationBadUsername() {
        //then
        service.doUserCreation(environment, badUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });


    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserServiceDoUserCreationBadNullUsername() {
        //then
        service.doUserCreation(environment, null, goodPassword, goodDOB, goodCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });


    }

    @Test
    public void testCreateUserServiceDoUserCreationBadPassword() {
        //then
        service.doUserCreation(environment, goodUsername, badPassword, goodDOB, goodCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });


    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserServiceDoUserCreationBadNullPassword() {
        //then
        service.doUserCreation(environment, goodUsername, null, goodDOB, goodCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });


    }

    @Test
    public void testCreateUserServiceDoUserCreationBadDOB() {
        //then
        service.doUserCreation(environment, goodUsername, goodPassword, badDOB, goodCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });
    }

    @Test
    public void testCreateUserServiceDoUserCreationBadCountry() {
        //then
        service.doUserCreation(environment, goodUsername, goodPassword, goodDOB, badCountry,
                goodParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });

    }


    @Test
    public void testCreateUserServiceDoUserCreationBadParentEmail() {
        //then
        service.doUserCreation(environment, goodUsername, goodPassword, goodDOB, goodCountry,
                badParentEmail, goodAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });
    }

    @Test
    public void testCreateUserServiceDoUserCreationBadAppId() {
        //then
        service.doUserCreation(environment, goodUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, badAppId, goodMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });
    }

    @Test
    public void testCreateUserServiceDoUserCreationBadToken() {
        //then
        service.doUserCreation(environment, goodUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, goodAppId, badMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });
    }

    @Test
    public void testCreateUserServiceDoUserCreationBadAppIdAndToken() {
        //then
        service.doUserCreation(environment, goodUsername, goodPassword, goodDOB, goodCountry,
                goodParentEmail, badAppId, badMockedToken, new Function2<CreateUser, Throwable, Unit>() {
                    @Override
                    public Unit invoke(CreateUser createUser, Throwable throwable) {

                        Assert.assertNull(createUser);
                        Assert.assertNotNull(throwable);

                        return null;
                    }
                });


    }


}
