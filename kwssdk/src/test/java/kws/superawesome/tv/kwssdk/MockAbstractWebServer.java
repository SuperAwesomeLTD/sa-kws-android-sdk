package kws.superawesome.tv.kwssdk;

import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

/**
 * Created by guilherme.mota on 19/03/2018.
 */

abstract public class MockAbstractWebServer {

    private MockWebServer server;

    MockAbstractWebServer () {
        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.
        server = new MockWebServer();
    }

    String url() {
        return server.url("").toString();
    }

    void start() throws Throwable {
        // start the server instance
        server.start();

        // create a dispatcher
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch (RecordedRequest request) throws InterruptedException {
                return handleRequest(request);
            }
        };

        // set dispatcher
        server.setDispatcher(dispatcher);
    }

    // to be implemented
    public abstract MockResponse handleRequest(RecordedRequest request);

    void shutdown() throws Throwable {
        // Shut down the server. Instances cannot be reused.
        server.shutdown();
    }

    MockResponse responseFromResource(String file) {
        String responseBody = ResourceReader.readResource(file);
        return new MockResponse().setBody(responseBody).setResponseCode(200);
    }

    MockResponse responseFromResource(String file, int httpCode) {
        String responseBody = ResourceReader.readResource(file);
        return new MockResponse().setResponseCode(httpCode).setBody(responseBody);
    }

    MockResponse timeoutResponse () {
        return new MockResponse().setBody("")
                .setResponseCode(400)
                .setBodyDelay(16, TimeUnit.SECONDS);
    }
}
