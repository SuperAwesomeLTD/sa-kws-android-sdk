package kws.superawesome.tv.kwssdk.services;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.services.KWSHTTPMethod;

/**
 * Created by gabriel.coman on 29/07/16.
 */
public interface KWSServiceInterface {
    String getEndpoint ();
    KWSHTTPMethod getMethod ();
    JSONObject getQuery ();
    JSONObject getHeader ();
    JSONObject getBody ();
    void success(int status, String payload, boolean success);
}
