package kws.superawesome.tv.kwssdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.JSONSerializable;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 28/07/16.
 */
public class KWSPoints implements Parcelable, JSONSerializable {

    public int totalReceived;
    public int total;
    public int totalPointsReceivedInCurrentApp;
    public int availableBalance;
    public int pending;

    protected KWSPoints(Parcel in) {
        totalReceived = in.readInt();
        total = in.readInt();
        totalPointsReceivedInCurrentApp = in.readInt();
        availableBalance = in.readInt();
        pending = in.readInt();
    }

    public KWSPoints(JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    public static final Creator<KWSPoints> CREATOR = new Creator<KWSPoints>() {
        @Override
        public KWSPoints createFromParcel(Parcel in) {
            return new KWSPoints(in);
        }

        @Override
        public KWSPoints[] newArray(int size) {
            return new KWSPoints[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalReceived);
        dest.writeInt(total);
        dest.writeInt(totalPointsReceivedInCurrentApp);
        dest.writeInt(availableBalance);
        dest.writeInt(pending);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        totalReceived = SAJsonParser.getInt(jsonObject, "totalReceived");
        total = SAJsonParser.getInt(jsonObject, "total");
        totalPointsReceivedInCurrentApp = SAJsonParser.getInt(jsonObject, "totalPointsReceivedInCurrentApp");
        availableBalance = SAJsonParser.getInt(jsonObject, "availableBalance");
        pending = SAJsonParser.getInt(jsonObject, "pending");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[]{
                "totalReceived", totalReceived,
                "total", total,
                "totalPointsReceivedInCurrentApp", totalPointsReceivedInCurrentApp,
                "availableBalance", availableBalance,
                "pending", pending
        });
    }
}
