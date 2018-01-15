package kws.superawesome.tv.kwssdk.providers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kws.superawesome.tv.kwssdk.base.providers.AppProvider;
import kws.superawesome.tv.kwssdk.base.responses.Leaders;

/**
 * Created by guilherme.mota on 15/01/2018.
 */

public class TestAppProvider extends BaseProvider {

    //class to test
    private AppProvider provider;

    private int goodAppId = 2;
    private int badAppId = 0;

    private String mockedToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjI1LCJhcHBJZCI6MiwiY2xpZW50SWQiOiJzdGFuLXRlc3QiLCJzY29wZSI6InVzZXIiLCJpYXQiOjE1MTYwMTI3NDksImV4cCI6MTUxNjA5OTE0OSwiaXNzIjoic3VwZXJhd2Vzb21lIn0." +
            "R4AGaBDhEdecmhaISrgmhHNRvGvcAvrC5vFG4UPm9-o";


    @Before
    public void setup() throws Throwable {

        //extended method from Base
        prepareMockedClient();


        // init class to test
        provider = new AppProvider(environment, task);

    }

    @Test
    public void testAppProviderGetLeadersOK() {

        provider.getLeaders(goodAppId, mockedToken, new Function2<Leaders, Throwable, Unit>() {
            @Override
            public Unit invoke(Leaders leaders, Throwable throwable) {


                Assert.assertNotNull(leaders.getResults());
                Assert.assertNull(throwable);

                return null;
            }
        });

    }

    @Test
    public void testAppProviderGetLeadersForbiddenResponse() {
        provider.getLeaders(badAppId, mockedToken, new Function2<Leaders, Throwable, Unit>() {
            @Override
            public Unit invoke(Leaders leaders, Throwable throwable) {

                Assert.assertNull(leaders.getResults());
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }

    @Test (expected = NumberFormatException.class)
    public void testAppProviderGetLeadersNotFound() throws Throwable {
        provider.getLeaders(Integer.valueOf("badAppId"), mockedToken, new Function2<Leaders, Throwable, Unit>() {
            @Override
            public Unit invoke(Leaders leaders, Throwable throwable) {

                Assert.assertNull(leaders.getResults());
                Assert.assertNotNull(throwable);

                return null;
            }
        });

    }


    @Test(expected = IllegalArgumentException.class)
    public void testAppProviderFailNullToken() throws Throwable {
        provider.getLeaders(goodAppId, null, new Function2<Leaders, Throwable, Unit>() {
            @Override
            public Unit invoke(Leaders leaders, Throwable throwable) {

                Assert.assertNull(leaders.getResults());
                Assert.assertNotNull(throwable);

                return null;
            }
        });
    }


}
