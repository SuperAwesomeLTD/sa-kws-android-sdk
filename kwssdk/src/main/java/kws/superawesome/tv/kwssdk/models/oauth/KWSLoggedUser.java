package kws.superawesome.tv.kwssdk.models.oauth;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.JSONSerializable;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 11/10/16.
 */
public class KWSLoggedUser implements Parcelable, JSONSerializable {

    public String token;
    public KWSMetadata metadata;
    private boolean registeredForRM = false;

    public KWSLoggedUser () {
        // do nothing
    }

    public KWSLoggedUser (JSONObject json) {
        readFromJson(json);
    }

    protected KWSLoggedUser(Parcel in) {
        token = in.readString();
        registeredForRM = in.readByte() != 0;
        metadata = in.readParcelable(KWSMetadata.class.getClassLoader());
    }

    public static final Creator<KWSLoggedUser> CREATOR = new Creator<KWSLoggedUser>() {
        @Override
        public KWSLoggedUser createFromParcel(Parcel in) {
            return new KWSLoggedUser(in);
        }

        @Override
        public KWSLoggedUser[] newArray(int size) {
            return new KWSLoggedUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeByte((byte) (registeredForRM ? 1 : 0));
        dest.writeParcelable(metadata, flags);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        token = SAJsonParser.getString(jsonObject, "token");
        registeredForRM = SAJsonParser.getBoolean(jsonObject, "registeredForRM");
        metadata = KWSMetadata.processMetadata(token);
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "token", token,
                "registeredForRM", registeredForRM,
                "metadata", metadata.writeToJson()
        });
    }

    @Override
    public boolean isValid() {
        return token != null && metadata != null && metadata.isValid();
    }

    // some more direct setters & getters

    public boolean isRegisteredForNotifications () {
        return registeredForRM;
    }

    public void setIsRegisteredForNotifications (boolean value) {
        registeredForRM = value;
    }
}
