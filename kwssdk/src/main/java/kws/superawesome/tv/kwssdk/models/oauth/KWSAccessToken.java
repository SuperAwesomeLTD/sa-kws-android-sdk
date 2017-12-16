package kws.superawesome.tv.kwssdk.models.oauth;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 11/10/16.
 */
public class KWSAccessToken extends SABaseObject implements Parcelable {

    public String token_type;
    public String access_token;
    public int expires_in;

    public KWSAccessToken (JSONObject json) {
        readFromJson(json);
    }

    protected KWSAccessToken(Parcel in) {
        token_type = in.readString();
        access_token = in.readString();
        expires_in = in.readInt();
    }

    public static final Creator<KWSAccessToken> CREATOR = new Creator<KWSAccessToken>() {
        @Override
        public KWSAccessToken createFromParcel(Parcel in) {
            return new KWSAccessToken(in);
        }

        @Override
        public KWSAccessToken[] newArray(int size) {
            return new KWSAccessToken[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token_type);
        dest.writeString(access_token);
        dest.writeInt(expires_in);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        token_type = SAJsonParser.getString(jsonObject, "token_type");
        access_token = SAJsonParser.getString(jsonObject, "access_token");
        expires_in = SAJsonParser.getInt(jsonObject, "expires_in");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "token_type", token_type,
                "access_token", access_token,
                "expires_in", expires_in
        });
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
