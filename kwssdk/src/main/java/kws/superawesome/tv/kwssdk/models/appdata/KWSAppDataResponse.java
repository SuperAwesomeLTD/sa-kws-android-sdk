package kws.superawesome.tv.kwssdk.models.appdata;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.List;

import tv.superawesome.lib.sajsonparser.JSONSerializable;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sajsonparser.SAJsonToList;
import tv.superawesome.lib.sajsonparser.SAListToJson;

/**
 * Created by gabriel.coman on 25/08/16.
 */
public class KWSAppDataResponse implements Parcelable, JSONSerializable {
    public int count;
    public int limit;
    public int offset;
    public List<KWSAppData> results;

    protected KWSAppDataResponse(Parcel in) {
        count = in.readInt();
        limit = in.readInt();
        offset = in.readInt();
        results = in.createTypedArrayList(KWSAppData.CREATOR);
    }

    public KWSAppDataResponse (JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    public static final Creator<KWSAppDataResponse> CREATOR = new Creator<KWSAppDataResponse>() {
        @Override
        public KWSAppDataResponse createFromParcel(Parcel in) {
            return new KWSAppDataResponse(in);
        }

        @Override
        public KWSAppDataResponse[] newArray(int size) {
            return new KWSAppDataResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeInt(limit);
        dest.writeInt(offset);
        dest.writeTypedList(results);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        count = SAJsonParser.getInt(jsonObject, "count");
        limit = SAJsonParser.getInt(jsonObject, "limit");
        offset = SAJsonParser.getInt(jsonObject, "offset");
        results = SAJsonParser.getListFromJsonArray(jsonObject, "results", new SAJsonToList<KWSAppData, JSONObject>() {
            @Override
            public KWSAppData traverseItem(JSONObject jsonObject) {
                return new KWSAppData(jsonObject);
            }
        });
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "count", count,
                "limit", limit,
                "offset", offset,
                "results", SAJsonParser.getJsonArrayFromList(results, new SAListToJson<JSONObject, KWSAppData>() {
            @Override
            public JSONObject traverseItem(KWSAppData kwsAppData) {
                return kwsAppData.writeToJson();
            }
        })
        });
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
