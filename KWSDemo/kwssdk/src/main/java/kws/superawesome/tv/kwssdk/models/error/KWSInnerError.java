package kws.superawesome.tv.kwssdk.models.error;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.JSONSerializable;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSInnerError implements Parcelable, JSONSerializable {

    public int code = 0;
    public String codeMeaning;
    public String errorMessage;

    // normal
    public KWSInnerError(){
        // do nothing
    }

    // json
    public KWSInnerError(JSONObject json){
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(codeMeaning);
        dest.writeString(errorMessage);
    }

    @Override
    public void readFromJson(JSONObject json) {
        code = SAJsonParser.getInt(json, "code");
        codeMeaning = SAJsonParser.getString(json, "codeMeaning");
        errorMessage = SAJsonParser.getString(json, "errorMessage");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[]{
                "code", code,
                "codeMeaning", codeMeaning,
                "errorMessage", errorMessage
        });
    }

    @Override
    public boolean isValid () {
        return true;
    }
}
