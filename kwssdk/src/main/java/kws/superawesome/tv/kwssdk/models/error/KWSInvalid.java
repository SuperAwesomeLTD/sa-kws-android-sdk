package kws.superawesome.tv.kwssdk.models.error;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSInvalid extends SABaseObject implements Parcelable {

    public KWSInnerError parentEmail;

    // normal
    public KWSInvalid(){
        // do nothing
    }

    // json
    public KWSInvalid(JSONObject json){
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(parentEmail, flags);
    }

    @Override
    public void readFromJson(JSONObject json) {
        parentEmail = new KWSInnerError(SAJsonParser.getJsonObject(json, "parentEmail"));
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "parentEmail", parentEmail != null ? parentEmail.writeToJson() : null
        });
    }

    @Override
    public boolean isValid () {
        return true;
    }
}
