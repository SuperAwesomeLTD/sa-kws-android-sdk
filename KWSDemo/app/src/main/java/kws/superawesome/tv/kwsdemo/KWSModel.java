package kws.superawesome.tv.kwsdemo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.json.KWSBaseObject;
import kws.superawesome.tv.kwssdk.json.KWSJsonParser;

/**
 * Created by gabriel.coman on 04/07/16.
 */
public class KWSModel extends KWSBaseObject implements Parcelable {

    public int status = 0;
    public int userId = 0;
    public String username = null;
    public String token = null;
    public String error = null;

    public KWSModel(JSONObject json) throws JSONException {
        readFromJson(json);
    }

    protected KWSModel(Parcel in) {
        status = in.readInt();
        userId = in.readInt();
        token = in.readString();
        error = in.readString();
        username = in.readString();
    }

    public static final Creator<KWSModel> CREATOR = new Creator<KWSModel>() {
        @Override
        public KWSModel createFromParcel(Parcel in) {
            return new KWSModel(in);
        }

        @Override
        public KWSModel[] newArray(int size) {
            return new KWSModel[size];
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
        dest.writeString(error);
        dest.writeString(username);
    }

    @Override
    public void readFromJson(JSONObject json) {
        status = KWSJsonParser.getInt(json, "status");
        userId = KWSJsonParser.getInt(json, "userId");
        token = KWSJsonParser.getString(json, "token");
        error = KWSJsonParser.getString(json, "error");
        username = KWSJsonParser.getString(json, "username");
    }

    @Override
    public JSONObject writeToJson() {
        return KWSJsonParser.newObject(new Object[]{
                "status", status,
                "userId", userId,
                "token", token,
                "error", error,
                "username", username
        });
    }

    //    @Override
    public boolean isValid () {
        return true;
    }
}