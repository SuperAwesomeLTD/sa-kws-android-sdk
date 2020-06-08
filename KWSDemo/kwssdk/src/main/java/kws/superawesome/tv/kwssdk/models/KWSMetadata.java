package kws.superawesome.tv.kwssdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.json.KWSBaseObject;
import kws.superawesome.tv.kwssdk.json.KWSJsonParser;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSMetadata extends KWSBaseObject implements Parcelable {

    public int userId = 0;
    public int appId = 0;
    public  String clientId;
    public String scope;
    public int iat = 0;
    public int exp = 0;
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
        userId = KWSJsonParser.getInt(json, "userId");
        appId = KWSJsonParser.getInt(json, "appId");
        clientId = KWSJsonParser.getString(json, "clientId");
        scope = KWSJsonParser.getString(json, "scope");
        iat = KWSJsonParser.getInt(json, "iat");
        exp = KWSJsonParser.getInt(json, "exp");
        iss = KWSJsonParser.getString(json, "iss");
    }

    @Override
    public JSONObject writeToJson() {
        return KWSJsonParser.newObject(new Object[]{
                "userId", userId,
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
        return true;
    }
}