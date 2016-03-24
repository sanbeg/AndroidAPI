package it.trade.tradeit.model;

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
    public String srv;

    public TradeItAuthenticateRequest(String userToken, String userId, String srv) {
        this.userToken = userToken;
        this.userId = userId;
        this.srv = srv;
    }

    @Override
    public String toString() {
        return "TradeItAuthenticateRequest{" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                ", srv='" + srv + '\'' +
                "}, " + super.toString();
    }
}
