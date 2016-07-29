package kws.superawesome.tv.kwssdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.kws.KWSSubscribeToken;
import tv.superawesome.lib.sajsonparser.JSONSerializable;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSUser implements Parcelable, JSONSerializable {

    public int id;
    public String username;
    public String firstName;
    public String lastName;
    public String dateOfBirth;
    public String gender;
    public String phoneNumber;
    public String language;
    public String email;
    public KWSAddress address;
    public KWSPoints points;
    public KWSPermissions applicationPermissions;
    public KWSApplicationProfile applicationProfile;

    // normal
    public KWSUser() {
        // do nothing
    }

    // json
    public KWSUser(JSONObject json){
        readFromJson(json);
    }


    protected KWSUser(Parcel in) {
        id = in.readInt();
        username = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        dateOfBirth = in.readString();
        gender = in.readString();
        phoneNumber = in.readString();
        language = in.readString();
        email = in.readString();
        address = in.readParcelable(KWSAddress.class.getClassLoader());
        points = in.readParcelable(KWSPoints.class.getClassLoader());
        applicationPermissions = in.readParcelable(KWSPermissions.class.getClassLoader());
        applicationProfile = in.readParcelable(KWSApplicationProfile.class.getClassLoader());
    }

    public static final Creator<KWSUser> CREATOR = new Creator<KWSUser>() {
        @Override
        public KWSUser createFromParcel(Parcel in) {
            return new KWSUser(in);
        }

        @Override
        public KWSUser[] newArray(int size) {
            return new KWSUser[size];
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
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(dateOfBirth);
        dest.writeString(gender);
        dest.writeString(phoneNumber);
        dest.writeString(language);
        dest.writeString(email);
        dest.writeParcelable(address, flags);
        dest.writeParcelable(points, flags);
        dest.writeParcelable(applicationPermissions, flags);
        dest.writeParcelable(applicationProfile, flags);
    }

    @Override
    public void readFromJson(JSONObject json) {
        id = SAJsonParser.getInt(json, "id");
        username = SAJsonParser.getString(json, "username");
        firstName = SAJsonParser.getString(json, "firstName");
        lastName = SAJsonParser.getString(json, "lastName");
        dateOfBirth = SAJsonParser.getString(json, "dateOfBirth");
        gender = SAJsonParser.getString(json, "gender");
        phoneNumber = SAJsonParser.getString(json, "phoneNumber");
        language = SAJsonParser.getString(json, "language");
        email = SAJsonParser.getString(json, "email");
        address = new KWSAddress(SAJsonParser.getJsonObject(json, "address"));
        points = new KWSPoints(SAJsonParser.getJsonObject(json, "points"));
        applicationPermissions = new KWSPermissions(SAJsonParser.getJsonObject(json, "applicationPermissions"));
        applicationProfile = new KWSApplicationProfile(SAJsonParser.getJsonObject(json, "applicationProfile"));
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[]{
                "id", id,
                "username", username,
                "firstName", firstName,
                "lastName", lastName,
                "dateOfBirth", dateOfBirth,
                "gender", gender,
                "phoneNumber", phoneNumber,
                "language", language,
                "email", email,
                "address", address.writeToJson(),
                "points", points.writeToJson(),
                "applicationPermissions", applicationPermissions.writeToJson(),
                "applicationProfile", applicationProfile.writeToJson()
        });
    }
}
