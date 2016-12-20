package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItUnlinkLoginRequest extends TradeItRequestWithKey {
    @SerializedName("userToken")
    @Expose
    public String userToken;

    @SerializedName("userId")
    @Expose
    public String userId;

    public TradeItUnlinkLoginRequest(TradeItLinkedLogin linkedLogin) {
        this.userToken = linkedLogin.userToken;
        this.userId = linkedLogin.userId;
    }

    private TradeItUnlinkLoginRequest() {}

    @Override
    public String toString() {
        return "TradeItAuthenticateRequest{" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                "}, " + super.toString();
    }
}