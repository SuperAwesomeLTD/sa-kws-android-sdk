package kws.superawesome.tv.kwssdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import tv.superawesome.lib.sajsonparser.JSONSerializable;
import tv.superawesome.lib.sajsonparser.SAJsonParser;

/**
 * Created by gabriel.coman on 28/07/16.
 */
public class KWSAddress implements Parcelable, JSONSerializable {

    public String street;
    public String city;
    public String postCode;
    public String country;


    protected KWSAddress(Parcel in) {
        street = in.readString();
        city = in.readString();
        postCode = in.readString();
        country = in.readString();
    }

    public KWSAddress(JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    public static final Creator<KWSAddress> CREATOR = new Creator<KWSAddress>() {
        @Override
        public KWSAddress createFromParcel(Parcel in) {
            return new KWSAddress(in);
        }

        @Override
        public KWSAddress[] newArray(int size) {
            return new KWSAddress[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(postCode);
        dest.writeString(country);
    }

    @Override
    public void readFromJson(JSONObject jsonObject) {
        street = SAJsonParser.getString(jsonObject, "street");
        city = SAJsonParser.getString(jsonObject, "city");
        postCode = SAJsonParser.getString(jsonObject, "postCode");
        country = SAJsonParser.getString(jsonObject, "country");
    }

    @Override
    public JSONObject writeToJson() {
        return SAJsonParser.newObject(new Object[]{
                "street", street,
                "city", city,
                "postCode", postCode,
                "country", country
        });
    }

    @Override
    public boolean isValid () {
        return true;
    }
}
