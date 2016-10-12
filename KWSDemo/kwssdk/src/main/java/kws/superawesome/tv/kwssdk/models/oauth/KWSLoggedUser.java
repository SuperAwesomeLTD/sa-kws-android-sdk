package kws.superawesome.tv.kwssdk.models.oauth;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

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
}
