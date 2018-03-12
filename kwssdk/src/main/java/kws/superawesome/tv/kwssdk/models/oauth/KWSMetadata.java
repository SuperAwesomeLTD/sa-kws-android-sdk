package kws.superawesome.tv.kwssdk.models.oauth;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSMetadata extends SABaseObject implements Parcelable {

    private static final int DEFAULT_VAL = -1;

    public int userId = DEFAULT_VAL;
    public int appId = DEFAULT_VAL;
    public  String clientId;
    public String scope;
    public int iat = DEFAULT_VAL;
    public int exp = DEFAULT_VAL;
    public  String iss;

    // normal
    public KWSMetadata(){
        // do nothing
    }

    // json
    public KWSMetadata(JSONObject json){
        readFromJson(json);
    }

    // parcel
    protected KWSMetadata(Parcel in) {
        userId = in.readInt();
        appId = in.readInt();
        clientId = in.readString();
        scope = in.readString();
        iat = in.readInt();
        exp = in.readInt();
        iss = in.readString();
    }

    public static final Creator<KWSMetadata> CREATOR = new Creator<KWSMetadata>() {
        @Override
        public KWSMetadata createFromParcel(Parcel in) {
            return new KWSMetadata(in);
        }

        @Override
        public KWSMetadata[] newArray(int size) {
            return new KWSMetadata[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeInt(appId);
        dest.writeString(clientId);
        dest.writeString(scope);
        dest.writeInt(iat);
        dest.writeInt(exp);
        dest.writeString(iss);
    }

    @Override
    public void readFromJson(JSONObject json) {
        userId = SAJsonParser.getInt(json, "id");
        appId = SAJsonParser.getInt(json, "appId");
        clientId = SAJsonParser.getString(json, "clientId");
        scope = SAJsonParser.getString(json, "scope");
        iat = SAJsonParser.getInt(json, "iat");
        exp = SAJsonParser.getInt(json, "exp");
        iss = SAJsonParser.getString(json, "iss");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[]{
                "id", userId,
                "appId", appId,
                "clientId", clientId,
                "scope", scope,
                "iat", iat,
                "exp", exp,
                "iss", iss
        });
    }

    @Override
    public boolean isValid () {
        if (appId == DEFAULT_VAL || userId == DEFAULT_VAL || iat == DEFAULT_VAL || exp == DEFAULT_VAL) {
            return false;
        } else {
            long now = System.currentTimeMillis() / 1000L;
            long time = now - exp;
            return time < 0;
        }
    }

    public static KWSMetadata processMetadata(String  oauthToken) {

        // get token
        if (oauthToken == null) return null;
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
}
