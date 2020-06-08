package kws.superawesome.tv.kwssdk.models.appconfig;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.json.KWSBaseObject;
import kws.superawesome.tv.kwssdk.json.KWSJsonParser;

/**
 * Created by gabriel.coman on 04/01/2017.
 */

public class KWSAppConfig extends KWSBaseObject implements Parcelable {

    public int id;

    public KWSAppConfig (String json) {
        JSONObject jsonObject = KWSJsonParser.newObject(json);
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
        JSONObject app = KWSJsonParser.getJsonObject(jsonObject, "app");
        id = KWSJsonParser.getInt(app, "id", 0);
    }

    @Override
    public JSONObject writeToJson() {
        return KWSJsonParser.newObject(new Object[] {
                "app", KWSJsonParser.newObject(new Object[] {
                    "id", id
                })
        });
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
