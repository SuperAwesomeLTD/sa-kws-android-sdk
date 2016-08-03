package kws.superawesome.tv.kwssdk.kws;

import org.json.JSONObject;

/**
 * Created by gabriel.coman on 29/07/16.
 */
public interface KWSServiceInterface {
    String getEndpoint ();
    KWSHTTPMethod getMethod ();
    JSONObject getQuery ();
    JSONObject getHeader ();
    JSONObject getBody ();
    void success(int status, String payload);
    void failure();
}
