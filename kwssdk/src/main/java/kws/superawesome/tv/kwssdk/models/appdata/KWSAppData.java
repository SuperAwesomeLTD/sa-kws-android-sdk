package kws.superawesome.tv.kwssdk.models.appdata;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 25/08/16.
 */
public class KWSAppData extends SABaseObject implements Parcelable {
    public String name;
    public int value;

    protected KWSAppData(Parcel in) {
        name = in.readString();
        value = in.readInt();
    }

    public KWSAppData(JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    public static final Creator<KWSAppData> CREATOR = new Creator<KWSAppData>() {
        @Override
        public KWSAppData createFromParcel(Parcel in) {
            return new KWSAppData(in);
        }

        @Override
        public KWSAppData[] newArray(int size) {
            return new KWSAppData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(value);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        name = SAJsonParser.getString(jsonObject, "name");
        value = SAJsonParser.getInt(jsonObject, "value");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "name", name,
                "value", value
        });
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
