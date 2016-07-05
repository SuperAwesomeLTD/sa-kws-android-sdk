package kws.superawesome.tv.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.JSONSerializable;

/**
 * Created by gabriel.coman on 23/05/16.
 */
public class KWSPermissions implements Parcelable, JSONSerializable {

    // push notificaiton permission
    public Object sendPushNotification = null;

    // normal
    public KWSPermissions(){
        // do nothing
    }

    // json
    public KWSPermissions(JSONObject json) throws JSONException {
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
    public void readFromJson(JSONObject json) {
        if (!json.isNull("sendPushNotification")) {
            sendPushNotification = json.optBoolean("sendPushNotification");
        }
    }

    @Override
    public JSONObject writeToJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("sendPushNotification", sendPushNotification);
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
    }
}
