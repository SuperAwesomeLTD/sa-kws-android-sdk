package kws.superawesome.tv.kwssdk.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import kws.superawesome.tv.kwssdk.json.KWSBaseObject;
import kws.superawesome.tv.kwssdk.json.KWSJsonParser;

/**
 * Created by gabriel.coman on 28/07/16.
 */
public class KWSAddress extends KWSBaseObject implements Parcelable {

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
        street = KWSJsonParser.getString(jsonObject, "street");
        city = KWSJsonParser.getString(jsonObject, "city");
        postCode = KWSJsonParser.getString(jsonObject, "postCode");
        country = KWSJsonParser.getString(jsonObject, "country");
    }

    @Override
    public JSONObject writeToJson() {
        return KWSJsonParser.newObject(new Object[]{
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
