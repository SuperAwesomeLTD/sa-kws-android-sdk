package kws.superawesome.tv.kwslib;

import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class SANetwork {

    /**
     * Send GET to network
     * @param endpoint the final endpoint
     * @param token oauth token
     * @param listener listener interface
     */
    public void sendGET(final String endpoint, final String token, final SANetworkInterface listener) {

        /** create a new SAAsync Task */
        SAAsyncTask task = new SAAsyncTask(SAApplication.getSAApplicationContext(), new SAAsyncTask.SAAsyncTaskInterface() {
            @Override
            public Object taskToExecute() throws Exception {

                URL url;
                int statusCode = 0;
                String response = null;
                HttpsURLConnection conn = null;
                InputStreamReader in = null;
                try {
                    url = new URL(endpoint);
                    conn = (HttpsURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Authorization", "Bearer " + token);
                    conn.connect();

                    // get response
                    statusCode = conn.getResponseCode();
                    if(statusCode >= 400) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    in.close();
                    conn.disconnect();
                }

                return new SANetworkResponse(statusCode, response);
            }

            @Override
            public void onFinish(Object result) {
                if (result != null) {
                    Log.d("SuperAwesome", "[OK] " + endpoint);
                    listener.success(result);
                } else {
                    Log.d("SuperAwesome", "[NOK] " + endpoint);
                    listener.failure();
                }
            }

            @Override
            public void onError() {
                Log.d("SuperAwesome", "[NOK] " + endpoint);
                listener.failure();
            }
        });
    }

    /**
     * Send POST request to endpoint
     * @param endpoint endpoint
     * @param token token
     * @param body body as JSON
     * @param listener listener
     */
    public void sendPOST(final String endpoint, final String token, final JSONObject body, final SANetworkInterface listener) {

        /** create a new SAAsync Task */
        SAAsyncTask task = new SAAsyncTask(SAApplication.getSAApplicationContext(), new SAAsyncTask.SAAsyncTaskInterface() {
            @Override
            public Object taskToExecute() throws Exception {

                URL url;
                int statusCode = 0;
                String response = null;
                OutputStream os = null;
                InputStreamReader in = null;
                HttpsURLConnection conn = null;
                try {
                    url = new URL(endpoint);
                    String message = body.toString();

                    conn = (HttpsURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    conn.setRequestProperty("Authorization", "Bearer " + token);
                    conn.connect();

                    // send post
                    os = new BufferedOutputStream(conn.getOutputStream());
                    os.write(message.getBytes());
                    os.flush();

                    // get response
                    statusCode = conn.getResponseCode();
                    if(statusCode >= 400) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    os.close();
                    in.close();
                    conn.disconnect();
                }

                return new SANetworkResponse(statusCode, response);
            }

            @Override
            public void onFinish(Object result) {
                if (result != null) {
                    Log.d("SuperAwesome", "[OK] " + endpoint);
                    listener.success(result);
                } else {
                    Log.d("SuperAwesome", "[NOK] " + endpoint);
                    listener.failure();
                }
            }

            @Override
            public void onError() {
                Log.d("SuperAwesome", "[NOK] " + endpoint);
                listener.failure();
            }
        });
    }
}
