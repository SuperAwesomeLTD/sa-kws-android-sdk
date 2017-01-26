package kws.superawesome.tv.kwssdk.models.leaderboard;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.List;

import tv.superawesome.lib.sajsonparser.SABaseObject;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.sajsonparser.SAJsonToList;
import tv.superawesome.lib.sajsonparser.SAListToJson;

/**
 * Created by gabriel.coman on 03/08/16.
 */
public class KWSLeaderboard extends SABaseObject implements Parcelable {
    public int count;
    public int offset;
    public int limit;
    public List<KWSLeader> results;

    protected KWSLeaderboard(Parcel in) {
        count = in.readInt();
        offset = in.readInt();
        limit = in.readInt();
        results = in.createTypedArrayList(KWSLeader.CREATOR);
    }

    public KWSLeaderboard(JSONObject json) {
        readFromJson(json);
    }

    public static final Creator<KWSLeaderboard> CREATOR = new Creator<KWSLeaderboard>() {
        @Override
        public KWSLeaderboard createFromParcel(Parcel in) {
            return new KWSLeaderboard(in);
        }

        @Override
        public KWSLeaderboard[] newArray(int size) {
            return new KWSLeaderboard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeInt(offset);
        dest.writeInt(limit);
        dest.writeTypedList(results);
    }

    @Override
    public void readFromJson(JSONObject json) {
        count = SAJsonParser.getInt(json, "count");
        offset = SAJsonParser.getInt(json, "offset");
        limit = SAJsonParser.getInt(json, "limit");
        results = SAJsonParser.getListFromJsonArray(json, "results", new SAJsonToList<KWSLeader, JSONObject>() {
            @Override
            public KWSLeader traverseItem(JSONObject jsonObject) {
                return new KWSLeader(jsonObject);
            }
        });
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "count", count,
                "offset", offset,
                "limit", limit,
                "results", SAJsonParser.getJsonArrayFromList(results, new SAListToJson<JSONObject, KWSLeader>() {
                    @Override
                    public JSONObject traverseItem(KWSLeader kwsLeader) {
                        return kwsLeader.writeToJson();
                    }
                })
        });
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
