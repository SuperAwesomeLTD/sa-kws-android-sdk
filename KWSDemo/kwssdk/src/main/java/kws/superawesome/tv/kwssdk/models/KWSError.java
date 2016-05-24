package kws.superawesome.tv.kwssdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwslib.JSONSerializable;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSError implements Parcelable, JSONSerializable {

    // error code
    public int code = 0;

    // error meaning
    public String codeMeaning;

    // error message
    public String errorMessage;

    // invalid
    public KWSInvalid invalid;

    // normal
    public KWSError(){
        // do nothing
    }

    // json
    public KWSError(JSONObject json) throws JSONException {
        readFromJson(json);
    }

    // parcel
    protected KWSError(Parcel in) {
        code = in.readInt();
        codeMeaning = in.readString();
        errorMessage = in.readString();
        invalid = in.readParcelable(KWSInvalid.class.getClassLoader());
    }

    public static final Creator<KWSError> CREATOR = new Creator<KWSError>() {
        @Override
        public KWSError createFromParcel(Parcel in) {
            return new KWSError(in);
        }

        @Override
        public KWSError[] newArray(int size) {
            return new KWSError[size];
        }
    };

    @Override
    public void readFromJson(JSONObject json) {
        if (!json.isNull("code")) {
            code = json.optInt("code");
        }
        if (!json.isNull("codeMeaning")) {
            codeMeaning = json.optString("codeMeaning");
        }
        if (!json.isNull("errorMessage")) {
            errorMessage = json.optString("errorMessage");
        }
        if (!json.isNull("invalid")) {
            JSONObject obj = json.optJSONObject("invalid");
            try {
                invalid = new KWSInvalid(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JSONObject writeToJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("codeMeaning", codeMeaning);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("errorMessage", errorMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("invalid", invalid.writeToJson());
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
        dest.writeInt(code);
        dest.writeString(codeMeaning);
        dest.writeString(errorMessage);
        dest.writeParcelable(invalid, flags);
    }
}
