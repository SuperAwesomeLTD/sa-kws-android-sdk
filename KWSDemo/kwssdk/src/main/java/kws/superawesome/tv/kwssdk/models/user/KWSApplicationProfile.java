package kws.superawesome.tv.kwssdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.JSONSerializable;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 28/07/16.
 */
public class KWSApplicationProfile implements Parcelable, JSONSerializable {

    public String username;
    public int avatarId;
    public int customField1;
    public int customField2;
    public int customField3;
    public int customField4;
    public int customField5;

    protected KWSApplicationProfile(Parcel in) {
        username = in.readString();
        avatarId = in.readInt();
        customField1 = in.readInt();
        customField2 = in.readInt();
        customField3 = in.readInt();
        customField4 = in.readInt();
        customField5 = in.readInt();
    }

    public KWSApplicationProfile(JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    public static final Creator<KWSApplicationProfile> CREATOR = new Creator<KWSApplicationProfile>() {
        @Override
        public KWSApplicationProfile createFromParcel(Parcel in) {
            return new KWSApplicationProfile(in);
        }

        @Override
        public KWSApplicationProfile[] newArray(int size) {
            return new KWSApplicationProfile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeInt(avatarId);
        dest.writeInt(customField1);
        dest.writeInt(customField2);
        dest.writeInt(customField3);
        dest.writeInt(customField4);
        dest.writeInt(customField5);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        username = SAJsonParser.getString(jsonObject, "username");
        avatarId = SAJsonParser.getInt(jsonObject, "avatarId");
        customField1 = SAJsonParser.getInt(jsonObject, "customField1");
        customField2 = SAJsonParser.getInt(jsonObject, "customField2");
        customField3 = SAJsonParser.getInt(jsonObject, "customField3");
        customField4 = SAJsonParser.getInt(jsonObject, "customField4");
        customField5 = SAJsonParser.getInt(jsonObject, "customField5");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.create(new Object[]{
                "username", username,
                "avatarId", avatarId,
                "customField1", customField1,
                "customField2", customField2,
                "customField3", customField3,
                "customField4", customField4,
                "customField5", customField5
        });
    }
}
