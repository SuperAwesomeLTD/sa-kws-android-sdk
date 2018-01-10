package kws.superawesome.tv.kwssdk.TestProviders;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

/**
 * Created by guilherme.mota on 10/01/2018.
 */

public class MockKWSServer {

    private MockWebServer server;

    MockKWSServer() {

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
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                switch (request.getPath()) {
                    //
                    // for login
                    case "/oauth/token?": {
                        String body = request.getBody().readUtf8();
                        try {
                            JSONObject bodyJson = new JSONObject(body);
                            String username = bodyJson.getString("username");
                            String password = bodyJson.getString("password");

                            if (username == null || password == null)
                                return responseFromResource("mock_empty_response.json");
                            else if (username.isEmpty() || password.isEmpty())
                                return responseFromResource("mock_login_missing_credentials_response.json");
                            else if (username.equals("bad_username"))
                                return responseFromResource("mock_login_bad_credentials_response.json");
                            else if (password.equals("bad_password"))
                                return responseFromResource("mock_login_bad_credentials_response.json");
                            else return responseFromResource("mock_login_success_response.json");


                        } catch (JSONException e) {
                            return new MockResponse().setResponseCode(404);
                        }
                    } //
                    // any other case
                    default:
                        return new MockResponse().setResponseCode(404).setBody("No response");
                }

            }
        };

        // set dispatcher
        server.setDispatcher(dispatcher);
    }


    private MockResponse responseFromResource(String file) {
        String responseBody = ResourceReader.readResource(file);
        return new MockResponse().setBody(responseBody);
    }

    void shutdown() throws Throwable {
        // Shut down the server. Instances cannot be reused.
        server.shutdown();
    }

}
