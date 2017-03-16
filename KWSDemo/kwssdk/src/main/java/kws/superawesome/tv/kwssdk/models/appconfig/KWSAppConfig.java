package kws.superawesome.tv.kwssdk.models.appconfig;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonSerializable;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 04/01/2017.
 */

public class KWSAppConfig extends SABaseObject implements Parcelable {

    public int id;

    public KWSAppConfig (String json) {
        JSONObject jsonObject = SAJsonParser.newObject(json);
        readFromJson(jsonObject);
    }

    public KWSAppConfig (JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    protected KWSAppConfig(Parcel in) {
        id = in.readInt();
    }

    public static final Creator<KWSAppConfig> CREATOR = new Creator<KWSAppConfig>() {
        @Override
        public KWSAppConfig createFromParcel(Parcel in) {
            return new KWSAppConfig(in);
        }

        @Override
        public KWSAppConfig[] newArray(int size) {
            return new KWSAppConfig[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        JSONObject app = SAJsonParser.getJsonObject(jsonObject, "app");
        id = SAJsonParser.getInt(app, "id", 0);
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "app", SAJsonParser.newObject(new Object[] {
                    "id", id
                })
        });
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
