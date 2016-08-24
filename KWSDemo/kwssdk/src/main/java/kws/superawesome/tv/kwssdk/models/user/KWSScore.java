package kws.superawesome.tv.kwssdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.JSONSerializable;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 24/08/16.
 */
public class KWSScore implements Parcelable, JSONSerializable {
    public int rank;
    public int score;

    protected KWSScore(Parcel in) {
        rank = in.readInt();
        score = in.readInt();
    }

    public KWSScore(JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    public static final Creator<KWSScore> CREATOR = new Creator<KWSScore>() {
        @Override
        public KWSScore createFromParcel(Parcel in) {
            return new KWSScore(in);
        }

        @Override
        public KWSScore[] newArray(int size) {
            return new KWSScore[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rank);
        dest.writeInt(score);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        rank = SAJsonParser.getInt(jsonObject, "rank");
        score = SAJsonParser.getInt(jsonObject, "score");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[] {
                "rank", rank,
                "score", score
        });
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
