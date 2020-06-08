package kws.superawesome.tv.kwssdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.json.KWSBaseObject;
import kws.superawesome.tv.kwssdk.json.KWSJsonParser;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSPermissions extends KWSBaseObject implements Parcelable {

    // push notificaiton permission
    public Object accessAddress = null;
    public Object accessPhoneNumber = null;
    public Object accessFirstName = null;
    public Object accessLastName = null;
    public Object accessEmail = null;
    public Object accessStreetAddress = null;
    public Object accessCity = null;
    public Object accessPostalCode = null;
    public Object accessCountry = null;
    public Object sendPushNotification = null;
    public Object sendNewsletter = null;

    // normal
    public KWSPermissions(){
        // do nothing
    }

    // json
    public KWSPermissions(JSONObject json){
        readFromJson(json);
    }

    protected KWSPermissions(Parcel in) {
    }

    public static final Creator<KWSPermissions> CREATOR = new Creator<KWSPermissions>() {
        @Override
        public KWSPermissions createFromParcel(Parcel in) {
            return new KWSPermissions(in);
        }

        @Override
        public KWSPermissions[] newArray(int size) {
            return new KWSPermissions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public void readFromJson(JSONObject json) {
        accessAddress = KWSJsonParser.get(json, "accessAddress");
        accessPhoneNumber = KWSJsonParser.get(json, "accessPhoneNumber");
        accessFirstName = KWSJsonParser.get(json, "accessFirstName");
        accessLastName = KWSJsonParser.get(json, "accessLastName");
        accessEmail = KWSJsonParser.get(json, "accessEmail");
        accessStreetAddress = KWSJsonParser.get(json, "accessStreetAddress");
        accessCity = KWSJsonParser.get(json, "accessCity");
        accessPostalCode = KWSJsonParser.get(json, "accessPostalCode");
        accessCountry = KWSJsonParser.get(json, "accessCountry");
        sendPushNotification = KWSJsonParser.get(json, "sendPushNotification");
        sendNewsletter = KWSJsonParser.get(json, "sendNewsletter");
    }

    @Override
    public JSONObject writeToJson() {
        return KWSJsonParser.newObject(new Object[]{
                "accessAddress", accessAddress,
                "accessPhoneNumber", accessPhoneNumber,
                "accessFirstName", accessFirstName,
                "accessLastName", accessLastName,
                "accessEmail", accessEmail,
                "accessStreetAddress", accessStreetAddress,
                "accessCity", accessCity,
                "accessPostalCode", accessPostalCode,
                "accessCountry", accessCountry,
                "sendPushNotification", sendPushNotification,
                "sendNewsletter", sendNewsletter
        });
    }

    @Override
    public boolean isValid () {
        return true;
    }
}
