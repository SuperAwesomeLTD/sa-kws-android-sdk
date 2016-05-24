package kws.superawesome.tv.kwssdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwslib.JSONSerializable;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSUser implements Parcelable, JSONSerializable {

    // unique username
    public String username;

    // user first name
    public String firstName;

    // user last name
    public String lastName;

    // user email
    public String email;

    // user permission object
    public KWSPermissions applicationPermissions;

    // normal
    public KWSUser() {
        // do nothing
    }

    // json
    public KWSUser(JSONObject json) throws JSONException {
        readFromJson(json);
    }

    // parcel
    protected KWSUser(Parcel in) {
        username = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        applicationPermissions = in.readParcelable(KWSPermissions.class.getClassLoader());
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
    public void readFromJson(JSONObject json) {
        if (!json.isNull("username")) {
            username = json.optString("username");
        }
        if (!json.isNull("firstName")) {
            firstName = json.optString("firstName");
        }
        if (!json.isNull("lastName")) {
            lastName = json.optString("lastName");
        }
        if (!json.isNull("email")) {
            email = json.optString("email");
        }
        if (!json.isNull("applicationPermissions")) {
            JSONObject obj = json.optJSONObject("applicationPermissions");
            try {
                applicationPermissions = new KWSPermissions(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JSONObject writeToJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("firstName", firstName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("lastName", lastName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("applicationPermissions", applicationPermissions.writeToJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeParcelable(applicationPermissions, flags);
    }
}
