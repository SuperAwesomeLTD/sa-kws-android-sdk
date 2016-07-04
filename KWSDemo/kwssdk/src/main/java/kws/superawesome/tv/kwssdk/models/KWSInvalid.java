package kws.superawesome.tv.kwssdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.JSONSerializable;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSInvalid implements Parcelable, JSONSerializable {

    // the inner error details
    public KWSInnerError parentEmail;

    // normal
    public KWSInvalid(){
        // do nothing
    }

    // json
    public KWSInvalid(JSONObject json) throws JSONException {
        readFromJson(json);
    }

    // parcel
    protected KWSInvalid(Parcel in) {
        parentEmail = in.readParcelable(KWSInnerError.class.getClassLoader());
    }

    public static final Creator<KWSInvalid> CREATOR = new Creator<KWSInvalid>() {
        @Override
        public KWSInvalid createFromParcel(Parcel in) {
            return new KWSInvalid(in);
        }

        @Override
        public KWSInvalid[] newArray(int size) {
            return new KWSInvalid[size];
        }
    };

    @Override
    public void readFromJson(JSONObject json) {
        if (!json.isNull("parentEmail")) {
            JSONObject obj = json.optJSONObject("parentEmail");
            try {
                parentEmail = new KWSInnerError(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JSONObject writeToJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("parentEmail", parentEmail.writeToJson());
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
        dest.writeParcelable(parentEmail, flags);
    }
}
