package kws.superawesome.tv.kwssdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.json.KWSBaseObject;
import kws.superawesome.tv.kwssdk.json.KWSJsonParser;

/**
 * Created by gabriel.coman on 28/07/16.
 */
public class KWSPoints extends KWSBaseObject implements Parcelable {

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
        totalReceived = KWSJsonParser.getInt(jsonObject, "totalReceived");
        total = KWSJsonParser.getInt(jsonObject, "total");
        totalPointsReceivedInCurrentApp = KWSJsonParser.getInt(jsonObject, "totalPointsReceivedInCurrentApp");
        availableBalance = KWSJsonParser.getInt(jsonObject, "availableBalance");
        pending = KWSJsonParser.getInt(jsonObject, "pending");
    }

    @Override
    public JSONObject writeToJson() {
        return KWSJsonParser.newObject(new Object[]{
                "totalReceived", totalReceived,
                "total", total,
                "totalPointsReceivedInCurrentApp", totalPointsReceivedInCurrentApp,
                "availableBalance", availableBalance,
                "pending", pending
        });
    }

    @Override
    public boolean isValid () {
        return true;
    }
}
