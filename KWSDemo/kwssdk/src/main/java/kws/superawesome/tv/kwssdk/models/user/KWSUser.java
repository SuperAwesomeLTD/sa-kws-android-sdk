package kws.superawesome.tv.kwssdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.json.KWSBaseObject;
import kws.superawesome.tv.kwssdk.json.KWSJsonParser;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSUser extends KWSBaseObject implements Parcelable {

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
        id = KWSJsonParser.getInt(json, "id");
        username = KWSJsonParser.getString(json, "username");
        firstName = KWSJsonParser.getString(json, "firstName");
        lastName = KWSJsonParser.getString(json, "lastName");
        dateOfBirth = KWSJsonParser.getString(json, "dateOfBirth");
        gender = KWSJsonParser.getString(json, "gender");
        phoneNumber = KWSJsonParser.getString(json, "phoneNumber");
        language = KWSJsonParser.getString(json, "language");
        email = KWSJsonParser.getString(json, "email");
        address = new KWSAddress(KWSJsonParser.getJsonObject(json, "address"));
        points = new KWSPoints(KWSJsonParser.getJsonObject(json, "points"));
        applicationPermissions = new KWSPermissions(KWSJsonParser.getJsonObject(json, "applicationPermissions"));
        applicationProfile = new KWSApplicationProfile(KWSJsonParser.getJsonObject(json, "applicationProfile"));
    }

    @Override
    public JSONObject writeToJson() {
        return KWSJsonParser.newObject(new Object[]{
                "id", id,
                "username", username,
                "firstName", firstName,
                "lastName", lastName,
                "dateOfBirth", dateOfBirth,
                "gender", gender,
                "phoneNumber", phoneNumber,
                "language", language,
                "email", email,
                "address", address != null ? address.writeToJson() : null,
                "points", points != null ? points.writeToJson() : null,
                "applicationPermissions", applicationPermissions != null ? applicationPermissions.writeToJson() : null,
                "applicationProfile", applicationProfile != null ? applicationProfile.writeToJson() : null
        });
    }

    @Override
    public boolean isValid () {
        return true;
    }
}
