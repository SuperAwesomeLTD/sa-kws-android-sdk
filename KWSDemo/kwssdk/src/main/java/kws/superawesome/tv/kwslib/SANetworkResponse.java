package kws.superawesome.tv.kwslib;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class SANetworkResponse {

    // status code
    public int statusCode = 0;

    // payload
    public String payload;

    // normal
    public SANetworkResponse () {
        // do nothing
    }

    // custom
    public SANetworkResponse(int statusCode, String payload){
        this.statusCode = statusCode;
        this.payload = payload;
    }
}
