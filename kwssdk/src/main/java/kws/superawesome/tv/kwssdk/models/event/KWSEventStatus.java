package kws.superawesome.tv.kwssdk.models.event;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 24/08/16.
 */
public class KWSEventStatus extends SABaseObject implements Parcelable {

    public boolean hasTriggeredEvent;

    protected KWSEventStatus(Parcel in) {
        hasTriggeredEvent = in.readByte() != 0;
    }

    public KWSEventStatus (JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    public static final Creator<KWSEventStatus> CREATOR = new Creator<KWSEventStatus>() {
        @Override
        public KWSEventStatus createFromParcel(Parcel in) {
            return new KWSEventStatus(in);
        }

        @Override
        public KWSEventStatus[] newArray(int size) {
            return new KWSEventStatus[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (hasTriggeredEvent ? 1 : 0));
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        hasTriggeredEvent = SAJsonParser.getBoolean(jsonObject, "hasTriggeredEvent");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "hasTriggeredEvent", hasTriggeredEvent
        });
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
