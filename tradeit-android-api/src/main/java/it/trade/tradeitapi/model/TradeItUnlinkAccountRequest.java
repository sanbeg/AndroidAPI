package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItUnlinkAccountRequest extends TradeItRequestWithKey {
    @SerializedName("userToken")
    @Expose
    public String userToken;

    @SerializedName("userId")
    @Expose
    public String userId;

    public TradeItUnlinkAccountRequest(TradeItLinkedAccount linkedAccount) {
        this.userToken = linkedAccount.userToken;
        this.userId = linkedAccount.userId;
    }

    private TradeItUnlinkAccountRequest() {}

    @Override
    public String toString() {
        return "TradeItAuthenticateRequest{" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                "}, " + super.toString();
    }
}