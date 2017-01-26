package kws.superawesome.tv.kwssdk.models.error;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSError extends SABaseObject implements Parcelable {

    public int code;
    public String codeMeaning;
    public String errorMessage;
    public KWSInvalid invalid;

    // normal
    public KWSError(){
        // do nothing
    }

    // json
    public KWSError(JSONObject json) {
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

    @Override
    public void readFromJson(JSONObject json) {
        code = SAJsonParser.getInt(json, "code");
        codeMeaning = SAJsonParser.getString(json, "codeMeaning");
        errorMessage = SAJsonParser.getString(json, "errorMessage");
        invalid = new KWSInvalid(SAJsonParser.getJsonObject(json, "invalid"));
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[]{
                "code", code,
                "codeMeaning", codeMeaning,
                "errorMessage", errorMessage,
                "invalid", invalid != null ? invalid.writeToJson() : null
        });
    }

    @Override
    public boolean isValid () {
        return true;
    }
}
