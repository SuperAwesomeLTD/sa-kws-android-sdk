package kws.superawesome.tv.kwssdk.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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
                switch (request.getRequestLine()) {
                    //
                    // for getting leaders
                    case "GET /v1/apps/2/leaders HTTP/1.1": {
                        return responseFromResource("mock_get_leaders_success_response.json");
                    }
                    case "GET /v1/apps/0/leaders HTTP/1.1": {
                        return responseFromResource("mock_generic_forbidden_response.json", 403);
                    }

                    //
                    //for setting app data
                    case "POST /v1/apps/2/users/25/app-data/set HTTP/1.1": {
                        body = request.getBody().readUtf8();
                        try {
                            String bodyForJSON = body;
                            JSONObject bodyJson = new JSONObject(bodyForJSON);
                            String name = bodyJson.getString("name");

                            if (name == null || name.isEmpty())
                                return responseFromResource("mock_set_app_data_empty_name_response.json", 400);
                            else
                                return responseFromResource("mock_generic_empty_response.json", 204);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    case "POST /v1/apps/0/users/25/app-data/set HTTP/1.1":
                    case "POST /v1/apps/0/users/0/app-data/set HTTP/1.1":
                    case "POST /v1/apps/2/users/0/app-data/set HTTP/1.1": {
                        return responseFromResource("mock_generic_forbidden_response.json", 403);
                    }
                    //
                    //for getting app data
                    case "GET /v1/apps/2/users/25/app-data HTTP/1.1": {
                        return responseFromResource("mock_get_app_data_success_response.json");
                    }
                    case "GET /v1/apps/0/users/25/app-data HTTP/1.1":
                    case "GET /v1/apps/0/users/0/app-data HTTP/1.1":
                    case "GET /v1/apps/2/users/0/app-data HTTP/1.1": {
                        return responseFromResource("mock_generic_forbidden_response.json", 403);
                    }
                    //
                    // for create user
                    case "POST /v1/apps/2/users?access_token=bad_token HTTP/1.1":
                    case "POST /v1/apps/0/users?access_token=bad_token HTTP/1.1": {
                        return responseFromResource("mock_create_user_bad_token_response.json", 401);
                    }
                    case "POST /v1/apps/0/users?access_token=good_token HTTP/1.1": {
                        return responseFromResource("mock_generic_forbidden_response.json", 403);
                    }
                    case "POST /v1/apps/2/users?access_token=good_token HTTP/1.1": {
                        body = request.getBody().readUtf8();
                        try {
                            String bodyForJSON = body;
                            JSONObject bodyJson = new JSONObject(bodyForJSON);
                            String username = bodyJson.getString("username");
                            String password = bodyJson.getString("password");
                            String dateOfBirth = bodyJson.getString("dateOfBirth");
                            String country = bodyJson.getString("country");
                            String parentEmail = bodyJson.getString("parentEmail");

                            if (username == null || username.isEmpty() || username.length() < 3 || username.equals("bad_username"))
                                return responseFromResource("mock_create_user_bad_username_response.json", 400);
                            if (password == null || password.isEmpty() || password.length() < 8 || password.equals("bad_password"))
                                return responseFromResource("mock_create_user_bad_password_response.json", 400);
                            if (dateOfBirth == null || dateOfBirth.isEmpty() || dateOfBirth.equals("bad_dob"))
                                return responseFromResource("mock_create_user_bad_dob_response.json", 400);
                            if (country == null || country.isEmpty() || country.equals("bad_country"))
                                return responseFromResource("mock_create_user_bad_country_response.json", 400);
                            if (parentEmail == null || parentEmail.isEmpty() || parentEmail.equals("bad_parent"))
                                return responseFromResource("mock_create_user_bad_email_response.json", 400);
                            else
                                return responseFromResource("mock_create_user_success_response.json");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    //
                    // for login && for get temp access token (same endpoint)
                    case "POST /oauth/token HTTP/1.1": {
                        body = request.getBody().readUtf8();
                        try {
                            String bodyForURLEncoded = body;
                            Map<String, List<String>> stringListMap = splitQuery(bodyForURLEncoded);

                            if (stringListMap.containsKey("username")
                                    && stringListMap.containsKey("password")) {
                                //username and password in the urlencoded string is for the login
                                return getLoginMockResponse(stringListMap);
                            } else {
                                //otherwise it's for the temp access token
                                return getTempAccessTokenMockResponse(stringListMap);

                            }

                        } catch (UnsupportedEncodingException ex) {
                            ex.printStackTrace();
                            return new MockResponse().setResponseCode(404);
                        }
                    }
                    //
                    // trigger events
                    case "POST /v1/users/0/trigger-event HTTP/1.1": {
                        return responseFromResource("mock_generic_forbidden_response.json", 403);
                    }
                    case "POST /v1/users/25/trigger-event HTTP/1.1": {
                        body = request.getBody().readUtf8();
                        try {
                            String bodyForJSON = body;
                            JSONObject bodyJson = new JSONObject(bodyForJSON);
                            String eventToken = bodyJson.getString("token");

                            if (eventToken == null || eventToken.isEmpty() || eventToken.equals("bad_event_token"))
                                return responseFromResource("mock_generic_event_not_found_response.json", 404);
                            else
                                return responseFromResource("mock_has_triggered_event_success_response.json");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    //
                    // has triggered events
                    case "POST /v1/users/0/has-triggered-event HTTP/1.1": {
                        return responseFromResource("mock_generic_forbidden_response.json", 403);
                    }
                    case "POST /v1/users/25/has-triggered-event HTTP/1.1": {
                        body = request.getBody().readUtf8();
                        try {
                            String bodyForJSON = body;
                            JSONObject bodyJson = new JSONObject(bodyForJSON);
                            int eventId = bodyJson.getInt("eventId");

                            if (eventId == 1)
                                return responseFromResource("mock_generic_event_not_found_response.json", 404);
                            else
                                return responseFromResource("mock_has_triggered_event_success_response.json");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //
                    // get app config
                    case "GET /v1/apps/config?oauthClientId=bad_client_id HTTP/1.1": {
                        return responseFromResource("mock_generic_event_not_found_response.json", 404);
                    }
                    case "GET /v1/apps/config?oauthClientId=good_client_id HTTP/1.1": {
                        return responseFromResource("mock_get_app_config_success_response.json");
                    }
                    //
                    // get random username
                    case "GET /v2/apps/0/random-display-name HTTP/1.1": {
                        return responseFromResource("mock_generic_simpler_not_found_response.json", 404);
                    }
                    case "GET /v2/apps/2/random-display-name HTTP/1.1": {
                        return responseFromResource("mock_get_random_username_success_response.json");

                    }
                    //
                    // get user details
                    case "GET /v1/users/0 HTTP/1.1": {
                        return responseFromResource("mock_generic_event_not_found_response.json", 404);
                    }

                    case "GET /v1/users/25 HTTP/1.1": {
                        return responseFromResource("mock_get_user_details_success_response.json");
                    }

                    //
                    // invite user
                    case "POST /v1/users/0/invite-user HTTP/1.1": {
                        return responseFromResource("mock_generic_forbidden_response.json", 403);
                    }
                    case "POST /v1/users/25/invite-user HTTP/1.1": {
                        body = request.getBody().readUtf8();
                        try {
                            String bodyForJSON = body;
                            JSONObject bodyJson = new JSONObject(bodyForJSON);
                            String email = bodyJson.getString("email");

                            if (email == null || email.isEmpty() || email.equals("bad_email"))
                                return responseFromResource("mock_invite_user_bad_email_response.json", 403);
                            else
                                return responseFromResource("mock_generic_empty_response.json", 204);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //
                    // get user score
                    case "GET /v1/apps/0/score HTTP/1.1": {
                        return responseFromResource("mock_generic_forbidden_response.json", 403);
                    }
                    case "GET /v1/apps/2/score HTTP/1.1": {
                        return responseFromResource("mock_get_user_score_success_response.json");
                    }
                    //
                    // request permissions
                    case "POST /v1/users/0/request-permissions HTTP/1.1": {
                        return responseFromResource("mock_generic_forbidden_response.json", 403);
                    }
                    case "POST /v1/users/25/request-permissions HTTP/1.1": {
                        body = request.getBody().readUtf8();
                        try {
                            String bodyForJSON = body;
                            JSONObject bodyJson = new JSONObject(bodyForJSON);
                            JSONArray permissionsArray = bodyJson.getJSONArray("permissions");

                            ArrayList<String> permissionsList = new ArrayList<>();
                            if (permissionsArray != null) {
                                for (int i = 0; i < permissionsArray.length(); i++) {
                                    permissionsList.add(permissionsArray.getString(i));
                                }
                            } else {
                                return new MockResponse().setResponseCode(400).setBody("Null array");
                            }

                            if (permissionsList.contains("bad_permission"))
                                return responseFromResource("mock_request_permissions_bad_permission_response.json", 400);
                            else
                                return responseFromResource("mock_generic_empty_response.json", 204);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //
                    // any other case
                    default:
                        return new MockResponse().setResponseCode(404).setBody("No response");
                }

            }


            private MockResponse getTempAccessTokenMockResponse(Map<String, List<String>> stringListMap) {
                String grantType = getStringValueFromMap(stringListMap, "grant_type");
                String clientId = getStringValueFromMap(stringListMap, "client_id");
                String clientSecret = getStringValueFromMap(stringListMap, "client_secret");

                if (grantType.isEmpty()
                        || clientId == null || clientId.isEmpty() || clientId.equals("bad_client_id")
                        || clientSecret == null || clientSecret.isEmpty() || clientSecret.equals("bad_client_secret"))
                    return responseFromResource("mock_temp_access_token_bad_cred_response.json", 400);
                else
                    return responseFromResource("mock_temp_access_token_success_response.json");

            }


            public MockResponse getLoginMockResponse(Map<String, List<String>> stringListMap) {
                String username = getStringValueFromMap(stringListMap, "username");
                String password = getStringValueFromMap(stringListMap, "password");

                if (username == null || password == null)
                    return responseFromResource("mock_generic_empty_response.json", 400);
                else if (username.isEmpty() || password.isEmpty())
                    return responseFromResource("mock_login_missing_credentials_response.json", 400);
                else if (username.equals("bad_username") || password.equals("bad_password"))
                    return responseFromResource("mock_login_bad_credentials_response.json", 400);
                else
                    return responseFromResource("mock_login_success_response.json");
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
        return responseFromResource(file, 200);
    }

    private MockResponse responseFromResource(String file, int httpCode) {
        String responseBody = ResourceReader.readResource(file);
        return new MockResponse().setResponseCode(httpCode).setBody(responseBody);
    }

    void shutdown() throws Throwable {
        // Shut down the server. Instances cannot be reused.
        server.shutdown();
    }

}
