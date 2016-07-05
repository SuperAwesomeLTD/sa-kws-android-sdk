package kws.superawesome.tv.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.JSONSerializable;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSMetadata implements Parcelable, JSONSerializable {

    // user id
    public int userId = 0;

    // app id
    public int appId = 0;

    // client id
    public  String clientId;

    // scope of user
    public String scope;

    // start date
    public int iat = 0;

    // expiry date
    public int exp = 0;

    // some kind of domain
    public  String iss;

    // normal
    public KWSMetadata(){
        // do nothing
    }

    // json
    public KWSMetadata(JSONObject json) throws JSONException {
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
    public void readFromJson(JSONObject json) {
        if (!json.isNull("userId")) {
            userId = json.optInt("userId");
        }
        if (!json.isNull("appId")) {
            appId = json.optInt("appId");
        }
        if (!json.isNull("clientId")) {
            clientId = json.optString("clientId");
        }
        if (!json.isNull("scope")) {
            scope = json.optString("scope");
        }
        if (!json.isNull("iat")) {
            iat = json.optInt("iat");
        }
        if (!json.isNull("exp")) {
            exp = json.optInt("exp");
        }
        if (!json.isNull("iss")) {
            iss = json.optString("iss");
        }
    }

    @Override
    public JSONObject writeToJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("appId", appId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("clientId", clientId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("scope", scope);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("iat", iat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("exp", exp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("iss", iss);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

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
}
