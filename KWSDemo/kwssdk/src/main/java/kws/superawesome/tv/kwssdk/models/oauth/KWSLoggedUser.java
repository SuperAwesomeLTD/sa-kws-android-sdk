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
    public String password;
    public String dateOfBirth;
    public String country;
    public String parentEmail;
    public String accessToken;
    public String token;
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
        password = in.readString();
        dateOfBirth = in.readString();
        country = in.readString();
        parentEmail = in.readString();
        accessToken = in.readString();
        token = in.readString();
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
        dest.writeString(password);
        dest.writeString(dateOfBirth);
        dest.writeString(country);
        dest.writeString(parentEmail);
        dest.writeString(accessToken);
        dest.writeString(token);
        dest.writeParcelable(metadata, flags);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        id = SAJsonParser.getInt(jsonObject, "id");
        username = SAJsonParser.getString(jsonObject, "username");
        password = SAJsonParser.getString(jsonObject, "password");
        parentEmail = SAJsonParser.getString(jsonObject, "parentEmail");
        country = SAJsonParser.getString(jsonObject, "country");
        accessToken = SAJsonParser.getString(jsonObject, "access_token");
        token = SAJsonParser.getString(jsonObject, "token");
        dateOfBirth = SAJsonParser.getString(jsonObject, "dateOfBirth");
        if (accessToken != null) {
            metadata = KWSAux.processMetadata(accessToken);
        }
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "id", id,
                "username", username,
                "password", password,
                "parentEmail", parentEmail,
                "country", country,
                "access_token", accessToken,
                "token", token,
                "dateOfBirth", dateOfBirth,
                "metadata", metadata.writeToJson()
        });
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
