package kws.superawesome.tv.kwssdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 08/10/16.
 */
public class KWSUserCreateDetail extends SABaseObject implements Parcelable {

    public int status;
    public int userId;
    public String token;

    public KWSUserCreateDetail (JSONObject json) {
        readFromJson(json);
    }

    protected KWSUserCreateDetail(Parcel in) {
        status = in.readInt();
        userId = in.readInt();
        token = in.readString();
    }

    public static final Creator<KWSUserCreateDetail> CREATOR = new Creator<KWSUserCreateDetail>() {
        @Override
        public KWSUserCreateDetail createFromParcel(Parcel in) {
            return new KWSUserCreateDetail(in);
        }

        @Override
        public KWSUserCreateDetail[] newArray(int size) {
            return new KWSUserCreateDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeInt(userId);
        dest.writeString(token);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        status = SAJsonParser.getInt(jsonObject, "status");
        userId = SAJsonParser.getInt(jsonObject, "userId");
        token = SAJsonParser.getString(jsonObject, "token");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "status", status,
                "userId", userId,
                "token", token
        });
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
