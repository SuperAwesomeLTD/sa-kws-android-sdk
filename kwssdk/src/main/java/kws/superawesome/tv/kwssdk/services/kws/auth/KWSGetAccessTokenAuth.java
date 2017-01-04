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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import kws.superawesome.tv.kwssdk.KWS;
import kws.superawesome.tv.kwssdk.models.oauth.KWSAccessToken;
import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;
import kws.superawesome.tv.kwssdk.services.KWSService;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.asynctask.SAAsyncTask;
import tv.superawesome.lib.sanetwork.asynctask.SAAsyncTaskInterface;

/**
 * Created by gabriel.coman on 12/10/16.
 */
public class KWSGetAccessTokenAuth extends KWSService {

    private String username;
    private String password;
    private KWSGetAccessTokenAuthInterface listener;

    public KWSGetAccessTokenAuth () {
        listener = new KWSGetAccessTokenAuthInterface() { @Override public void gotToken(KWSAccessToken accessToken) {} };
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
                "password", password
        });
    }

    @Override
    public JSONObject getHeader() {
        return SAJsonParser.newObject(new Object[] {
                "Content-Type", "application/x-www-form-urlencoded",
                "Authorization", "Basic c3VwZXJhd2Vzb21lY2x1YjpzdXBlcmF3ZXNvbWVjbHVi"
        });
    }

    @Override
    public void success(int status, String payload, boolean success) {
        if (!success) {
            listener.gotToken(null);
        } else {
            if (status == 200 && payload != null) {
                JSONObject jsonObject = SAJsonParser.newObject(payload);
                KWSAccessToken accessToken = new KWSAccessToken(jsonObject);

                if (accessToken.access_token != null) {
                    listener.gotToken(accessToken);
                } else {
                    listener.gotToken(null);
                }

            } else {
                listener.gotToken(null);
            }
        }
    }

    public void execute (Context context, String username, String password, KWSGetAccessTokenAuthInterface listener) {
        this.username = username;
        this.password = password;
        this.listener = listener != null ? listener : this.listener;

        // create endpoint
        final String endpoint = KWS.sdk.getKwsApiUrl() + getEndpoint();

        // create a new async task
        SAAsyncTask task = new SAAsyncTask(context, new SAAsyncTaskInterface() {
            @Override
            public Object taskToExecute() throws Exception {
                int statusCode;
                String response;
                InputStreamReader in;
                OutputStream os = null;

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

                // create the final response
                HashMap<String, Object> networkResponse = new HashMap<>();
                networkResponse.put("statusCode", statusCode);
                networkResponse.put("payload", response);

                // return
                return networkResponse;
            }

            @Override
            public void onFinish(Object result) {
                if (result != null && result instanceof HashMap) {

                    // get the hash map
                    HashMap<String, Object> response = (HashMap<String, Object>)result;
                    int status = -1;
                    String payload = null;
                    if (response.containsKey("statusCode")) {
                        status = (int) response.get("statusCode");
                    }
                    if (response.containsKey("payload")) {
                        payload = (String) response.get("payload");
                    }

                    // call the result
                    if (status > -1 && payload != null) {
                        success(status, payload, true);

                    } else {
                        success(status, null, false);
                    }
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
