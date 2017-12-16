package kws.superawesome.tv.kwssdk.services.kws.auth;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import kws.superawesome.tv.kwssdk.KWSChildren;
import kws.superawesome.tv.kwssdk.models.oauth.KWSAccessToken;
import kws.superawesome.tv.kwssdk.models.oauth.KWSLoggedUser;
import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.asynctask.SAAsyncTask;
import tv.superawesome.lib.sanetwork.asynctask.SAAsyncTaskInterface;

public class KWSAuthUser extends KWSService {

    private String username;
    private String password;
    private KWSAuthUserInterface listener;

    public KWSAuthUser() {
        listener = new KWSAuthUserInterface() { @Override public void authUser(int status, KWSLoggedUser loggedUser) {} };
    }

    @Override
    public String getEndpoint() {
        return "oauth/token";
    }

    @Override
    public KWSHTTPMethod getMethod() {
        return KWSHTTPMethod.POST;
    }

    @Override
    public boolean needsLoggedUser() {
        return false;
    }

    @Override
    public JSONObject getBody() {
        return SAJsonParser.newObject(new Object[] {
                "grant_type", "password",
                "username", username,
                "password", password,
                "client_id", KWSChildren.sdk.getClientId(),
                "client_secret", KWSChildren.sdk.getClientSecret()
        });
    }

    @Override
    public JSONObject getHeader() {
        return SAJsonParser.newObject(new Object[] {
                "Content-Type", "application/x-www-form-urlencoded"
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.authUser(status, null);
        } else {
            if (status == 200 && payload != null) {
                JSONObject jsonObject = SAJsonParser.newObject(payload);
                KWSAccessToken accessToken = new KWSAccessToken(jsonObject);
                KWSLoggedUser loggedUser = new KWSLoggedUser();
                loggedUser.token = accessToken.access_token;
                loggedUser.metadata = KWSMetadata.processMetadata(loggedUser.token);
                listener.authUser(status, loggedUser.token != null ? loggedUser : null);
            } else {
                listener.authUser(status, null);
            }
        }
    }

    public void execute (Context context, String username, String password, KWSAuthUserInterface listener) {
        this.username = username;
        this.password = password;
        this.listener = listener != null ? listener : this.listener;

        // create endpoint
        final String endpoint = KWSChildren.sdk.getKwsApiUrl() + getEndpoint();

        // create a new async task
        new SAAsyncTask<>(context, new SAAsyncTaskInterface<SANetworkResult>() {
            @Override
            public SANetworkResult taskToExecute() throws Exception {
                int statusCode;
                String response;
                InputStreamReader in;
                OutputStream os;

                // create a connection
                URL url = new URL(endpoint);
                HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");

                // set headers
                Iterator<String> keys = getHeader().keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = getHeader().optString(key);
                    conn.setRequestProperty(key, value);
                }

                // connect
                conn.connect();

                // write body
                List<String> strings = new ArrayList<>();
                Iterator<String> bodykeys = getBody().keys();
                while (bodykeys.hasNext()) {
                    String key = bodykeys.next();
                    String value = getBody().optString(key);
                    strings.add(key +"=" + value);
                }
                String joinedBody = TextUtils.join("&", strings.toArray());
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(joinedBody.getBytes());
                os.flush();

                // read the result
                statusCode = conn.getResponseCode();
                if (statusCode >= HttpsURLConnection.HTTP_BAD_REQUEST) {
                    in = new InputStreamReader(conn.getErrorStream());
                } else {
                    in = new InputStreamReader(conn.getInputStream());
                }

                // get response
                String line;
                response = "";
                BufferedReader reader = new BufferedReader(in);
                while ((line = reader.readLine()) != null) {
                    response+=line;
                }

                // close the body writer
                if (os != null) {
                    os.close();
                }
                // close the reader
                in.close();

                // disconnect
                conn.disconnect();

                // The final saDidGetResponse of the SAAsyncTask will be an object of type
                // SANetworkResult that will contain the status code and the string saDidGetResponse
                return new SANetworkResult(statusCode, response);
            }

            @Override
            public void onFinish(SANetworkResult result) {

                if (result.isValid()) {
                    success(result.getStatus(), result.getPayload(), true);
                } else {
                    success(0, null, false);
                }
            }

            @Override
            public void onError() {
                success(0, null, false);
            }
        });
    }
}

/**
 * This private class hold the important details needed when receiving a network saDidGetResponse from
 * a remote server: the HTTP request status (200, 201, 400, 404, etc) and a string saDidGetResponse
 * that will get parsed subsequently.
 */
class SANetworkResult {

    // constants
    private static final int DEFAULT_STATUS = -1;

    // private variables - status & payload
    private int status = DEFAULT_STATUS;
    private String payload;

    /**
     * Custom constructor taking into account all the class members variables
     *
     * @param status    the current request status (200, 201, 400, 404, etc)
     * @param payload   the current request payload. Can be null
     */
    SANetworkResult(int status, String payload) {
        this.status = status;
        this.payload = payload;
    }

    /**
     * Public getter for the "status" member variable
     *
     * @return  the value of the "status" member variable
     */
    int getStatus() {
        return status;
    }

    /**
     * Public getter for the "payload" member variable
     *
     * @return  the value of the "payload" member variable
     */
    String getPayload() {
        return payload;
    }

    /**
     * Method that internally determines if the returned network result has validity
     *
     * @return  true or false depending on the condition
     */
    boolean isValid () {
        return status > DEFAULT_STATUS && payload != null;
    }
}
