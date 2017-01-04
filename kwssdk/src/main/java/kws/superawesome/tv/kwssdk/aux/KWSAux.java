package kws.superawesome.tv.kwssdk.aux;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import kws.superawesome.tv.kwssdk.models.oauth.KWSMetadata;

/**
 * Created by gabriel.coman on 11/10/16.
 */
public class KWSAux {

    public static KWSMetadata processMetadata(String  oauthToken) {

        // get token
        String[] components = oauthToken.split("\\.");
        String tokenO = null;
        if (components.length >= 2) tokenO = components[1];
        if (tokenO == null) return null;

        // get JSON from base64 data
        byte[] data;
        try {
            data = Base64.decode(tokenO, Base64.DEFAULT);
        } catch (IllegalArgumentException e1) {
            try {
                tokenO += "=";
                data = Base64.decode(tokenO, Base64.DEFAULT);
            } catch (IllegalArgumentException e2) {
                try {
                    tokenO += "=";
                    data = Base64.decode(tokenO, Base64.DEFAULT);
                } catch (IllegalArgumentException e3){
                    return null;
                }
            }
        }

        try {
            String jsonData = new String(data, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonData);
            return new KWSMetadata(jsonObject);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean checkRegex (String regex, String target) {
        return Pattern.matches(regex, target);
    }

}
