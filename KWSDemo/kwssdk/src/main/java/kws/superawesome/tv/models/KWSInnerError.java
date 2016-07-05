package kws.superawesome.tv.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.JSONSerializable;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSInnerError implements Parcelable, JSONSerializable {

    // internal error code
    public int code = 0;

    // code meaning
    public String codeMeaning;

    // code error
    public String errorMessage;

    // normal
    public KWSInnerError(){
        // do nothing
    }

    // json
    public KWSInnerError(JSONObject json) throws JSONException {
        readFromJson(json);
    }

    // parcel
    protected KWSInnerError(Parcel in) {
        code = in.readInt();
        codeMeaning = in.readString();
        errorMessage = in.readString();
    }

    public static final Creator<KWSInnerError> CREATOR = new Creator<KWSInnerError>() {
        @Override
        public KWSInnerError createFromParcel(Parcel in) {
            return new KWSInnerError(in);
        }

        @Override
        public KWSInnerError[] newArray(int size) {
            return new KWSInnerError[size];
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
    }
}
