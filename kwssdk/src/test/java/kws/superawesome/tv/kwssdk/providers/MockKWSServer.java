package kws.superawesome.tv.kwssdk.providers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

/**
 * Created by guilherme.mota on 10/01/2018.
 */

class MockKWSServer {

    private MockWebServer server;
    private String body;

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
                        body = request.getBody().readUtf8();
                        try {
                            String bodyForJSON = body;
                            JSONObject bodyJson = new JSONObject(bodyForJSON);
                            String username = bodyJson.getString("username");
                            String password = bodyJson.getString("password");

                            return getLoginMockResponse(username, password);

                        } catch (JSONException e) {

                            //might of not been of type JSON, decode string
                            String bodyForURLEncoded = body;
                            try {
                                Map<String, List<String>> stringListMap = splitQuery(bodyForURLEncoded);
                                String username = getStringValueFromMap(stringListMap, "username");
                                String password = getStringValueFromMap(stringListMap, "password");

                                return getLoginMockResponse(username, password);

                            } catch (UnsupportedEncodingException ex) {
                                ex.printStackTrace();
                                return new MockResponse().setResponseCode(404);
                            }
                        }
                    } //
                    // any other case
                    default:
                        return new MockResponse().setResponseCode(404).setBody("No response");
                }

            }

            public MockResponse getLoginMockResponse(String username, String password) {
                if (username == null || password == null)
                    return responseFromResource("mock_empty_response.json");
                else if (username.isEmpty() || password.isEmpty())
                    return responseFromResource("mock_login_missing_credentials_response.json");
                else if (username.equals("bad_username"))
                    return responseFromResource("mock_login_bad_credentials_response.json");
                else if (password.equals("bad_password"))
                    return responseFromResource("mock_login_bad_credentials_response.json");
                else return responseFromResource("mock_login_success_response.json");
            }
        };

        // set dispatcher
        server.setDispatcher(dispatcher);
    }

    private String getStringValueFromMap(Map<String, List<String>> stringListMap, String value) {

        String valueToReturn = null;

        //get list for given key
        List<String> listOfItemsForKey = stringListMap.get(value);

        //if the list has elems and not empty
        if (listOfItemsForKey != null
                && !listOfItemsForKey.isEmpty()) {
            //get the first elem to get our value to return
            valueToReturn = listOfItemsForKey.get(0);
        }

        return valueToReturn;
    }


    public static Map<String, List<String>> splitQuery(String encodedString) throws UnsupportedEncodingException {

        final Map<String, List<String>> queryPairs = new LinkedHashMap<>();

        //split by char &
        final String[] pairs = encodedString.split("&");

        //iterate new list
        for (String pair : pairs) {
            //position of char =
            final int index = pair.indexOf("=");

            //if we have a valid index (> 0), substring it and decode it into a new key, else, use the default iterating string (pair)
            final String key = index > 0 ? URLDecoder.decode(pair.substring(0, index), "UTF-8") : pair;
            if (!queryPairs.containsKey(key)) {
                //add list to key
                queryPairs.put(key, new LinkedList<String>());
            }

            //if we have a valid index (> 0) and the default iterating string (pair) has a valid size, decode it and had to key's list, else, add 'null' to the key's list
            final String value = index > 0 && pair.length() > index + 1 ? URLDecoder.decode(pair.substring(index + 1), "UTF-8") : null;
            queryPairs.get(key).add(value);
        }

        return queryPairs;
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
