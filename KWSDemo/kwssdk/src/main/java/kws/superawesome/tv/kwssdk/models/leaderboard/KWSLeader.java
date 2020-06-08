package kws.superawesome.tv.kwssdk.models.leaderboard;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.json.KWSBaseObject;
import kws.superawesome.tv.kwssdk.json.KWSJsonParser;

/**
 * Created by gabriel.coman on 28/07/16.
 */
public class KWSLeader extends KWSBaseObject implements Parcelable {

    public int rank;
    public int score;
    public String user;

    protected KWSLeader(Parcel in) {
        rank = in.readInt();
        score = in.readInt();
        user = in.readString();
    }

    public KWSLeader(JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    public static final Creator<KWSLeader> CREATOR = new Creator<KWSLeader>() {
        @Override
        public KWSLeader createFromParcel(Parcel in) {
            return new KWSLeader(in);
        }

        @Override
        public KWSLeader[] newArray(int size) {
            return new KWSLeader[size];
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
        dest.writeString(user);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        rank = KWSJsonParser.getInt(jsonObject, "rank");
        score = KWSJsonParser.getInt(jsonObject, "score");
        user = KWSJsonParser.getString(jsonObject, "user");
    }

    @Override
    public JSONObject writeToJson() {
        return KWSJsonParser.newObject(new Object[]{
                "rank", rank,
                "score", score,
                "user", user
        });
    }

    @Override
    public boolean isValid () {
        return true;
    }
}
