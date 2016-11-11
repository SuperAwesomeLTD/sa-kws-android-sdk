package kws.superawesome.tv.kwssdk.models.oauth;

import android.os.Parcel;
import android.os.Parcelable;
import android.test.ServiceTestCase;

import org.json.JSONObject;

import java.io.Serializable;

import kws.superawesome.tv.kwssdk.aux.KWSAux;
import kws.superawesome.tv.kwssdk.models.KWSMetadata;
import tv.superawesome.lib.sajsonparser.JSONSerializable;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 11/10/16.
 */
public class KWSLoggedUser implements Parcelable, JSONSerializable {

    public int id;
    public String username;
    public String dateOfBirth;
    public String country;
    public String parentEmail;
    public String accessToken;
    public String token;
    public int expiresIn;
    public long loginDate;
    public KWSMetadata metadata;
    private boolean registeredForRM = false;

    public KWSLoggedUser () {
        // do nothing
    }

    public KWSLoggedUser (JSONObject json) {
        readFromJson(json);
    }

    protected KWSLoggedUser(Parcel in) {
        id = in.readInt();
        username = in.readString();
        dateOfBirth = in.readString();
        country = in.readString();
        parentEmail = in.readString();
        accessToken = in.readString();
        token = in.readString();
        registeredForRM = in.readByte() != 0;
        expiresIn = in.readInt();
        loginDate = in.readLong();
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
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(dateOfBirth);
        dest.writeString(country);
        dest.writeString(parentEmail);
        dest.writeString(accessToken);
        dest.writeString(token);
        dest.writeByte((byte) (registeredForRM ? 1 : 0));
        dest.writeInt(expiresIn);
        dest.writeLong(loginDate);
        dest.writeParcelable(metadata, flags);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        id = SAJsonParser.getInt(jsonObject, "id");
        username = SAJsonParser.getString(jsonObject, "username");
        parentEmail = SAJsonParser.getString(jsonObject, "parentEmail");
        country = SAJsonParser.getString(jsonObject, "country");
        accessToken = SAJsonParser.getString(jsonObject, "access_token");
        token = SAJsonParser.getString(jsonObject, "token");
        registeredForRM = SAJsonParser.getBoolean(jsonObject, "registeredForRM");
        dateOfBirth = SAJsonParser.getString(jsonObject, "dateOfBirth");
        expiresIn = SAJsonParser.getInt(jsonObject, "expires_in");
        loginDate = SAJsonParser.getLong(jsonObject, "loginDate");
        if (accessToken != null) {
            metadata = KWSAux.processMetadata(accessToken);
        }
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "id", id,
                "username", username,
                "parentEmail", parentEmail,
                "country", country,
                "access_token", accessToken,
                "token", token,
                "registeredForRM", registeredForRM,
                "dateOfBirth", dateOfBirth,
                "expires_in", expiresIn,
                "loginDate", loginDate,
                "metadata", metadata.writeToJson()
        });
    }

    @Override
    public boolean isValid() {
        long now = System.currentTimeMillis() / 1000L;
        long nowMinusExp = now - expiresIn;
        return nowMinusExp <= loginDate;
    }

    // some more direct setters & getters

    public boolean isRegisteredForNotifications () {
        return registeredForRM;
    }

    public void setIsRegisteredForNotifications (boolean value) {
        registeredForRM = value;
    }
}
