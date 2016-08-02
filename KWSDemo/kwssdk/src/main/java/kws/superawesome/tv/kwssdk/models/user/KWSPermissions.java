package kws.superawesome.tv.kwssdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.kws.KWSSubscribeToken;
import tv.superawesome.lib.sajsonparser.JSONSerializable;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sanetwork.request.SANetworkResponse;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSPermissions implements Parcelable, JSONSerializable {

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
        accessAddress = SAJsonParser.get(json, "accessAddress");
        accessPhoneNumber = SAJsonParser.get(json, "accessPhoneNumber");
        accessFirstName = SAJsonParser.get(json, "accessFirstName");
        accessLastName = SAJsonParser.get(json, "accessLastName");
        accessEmail = SAJsonParser.get(json, "accessEmail");
        accessStreetAddress = SAJsonParser.get(json, "accessStreetAddress");
        accessCity = SAJsonParser.get(json, "accessCity");
        accessPostalCode = SAJsonParser.get(json, "accessPostalCode");
        accessCountry = SAJsonParser.get(json, "accessCountry");
        sendPushNotification = SAJsonParser.get(json, "sendPushNotification");
        sendNewsletter = SAJsonParser.get(json, "sendNewsletter");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[]{
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
