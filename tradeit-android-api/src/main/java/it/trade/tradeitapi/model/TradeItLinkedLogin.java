package it.trade.tradeitapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TradeItLinkedLogin implements Parcelable {
    @SerializedName("label")
    public String label = "";

    @SerializedName("broker")
    public String broker = "";

    @SerializedName("userToken")
    public String userToken = "";

    @SerializedName("userId")
    public String userId = "";

    @SerializedName("apiKey")
    public String apiKey = "";

    @SerializedName("uuid")
    public String uuid = "";

    public TradeItLinkedLogin(TradeItLinkLoginRequest linkLoginRequest, TradeItLinkLoginResponse linkLoginResponse) {
        this.broker = linkLoginRequest.broker;
        this.apiKey = linkLoginRequest.apiKey;
        this.userId = linkLoginResponse.userId;
        this.userToken = linkLoginResponse.userToken;
    }

    public TradeItLinkedLogin(String broker, TradeItOAuthAccessTokenRequest oAuthAccessTokenRequest,
                              TradeItOAuthAccessTokenResponse oAuthAccessTokenResponse) {
        this.broker = broker;
        this.apiKey = oAuthAccessTokenRequest.apiKey;
        this.userId = oAuthAccessTokenResponse.userId;
        this.userToken = oAuthAccessTokenResponse.userToken;
    }

    private TradeItLinkedLogin() {}

    public void update(TradeItLinkLoginResponse linkLoginResponse) {
        this.userId = linkLoginResponse.userId;
        this.userToken = linkLoginResponse.userToken;
    }

    @Override
    public String toString() {
        return "TradeItLinkedLogin{" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", broker='" + broker + '\'' +
                ", label='" + label + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.label);
        dest.writeString(this.broker);
        dest.writeString(this.userToken);
        dest.writeString(this.userId);
        dest.writeString(this.apiKey);
        dest.writeString(this.uuid);
    }

    protected TradeItLinkedLogin(Parcel in) {
        this.label = in.readString();
        this.broker = in.readString();
        this.userToken = in.readString();
        this.userId = in.readString();
        this.apiKey = in.readString();
        this.uuid = in.readString();
    }

    public static final Parcelable.Creator<TradeItLinkedLogin> CREATOR = new Parcelable.Creator<TradeItLinkedLogin>() {
        @Override
        public TradeItLinkedLogin createFromParcel(Parcel source) {
            return new TradeItLinkedLogin(source);
        }

        @Override
        public TradeItLinkedLogin[] newArray(int size) {
            return new TradeItLinkedLogin[size];
        }
    };
}
