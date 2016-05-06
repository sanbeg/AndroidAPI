package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItAuthenticateRequest extends TradeItRequestWithKey {
    @SerializedName("userToken")
    @Expose
    public String userToken;

    @SerializedName("userId")
    @Expose
    public String userId;

    @SerializedName("srv")
    @Expose
    public String serverUuid;

    public TradeItAuthenticateRequest(TradeItBrokerLink brokerLink) {
        this.userToken = brokerLink.userToken;
        this.userId = brokerLink.userId;
    }

    private TradeItAuthenticateRequest() {}

    @Override
    public String toString() {
        return "TradeItAuthenticateRequest{" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                ", srv='" + serverUuid + '\'' +
                "}, " + super.toString();
    }
}
