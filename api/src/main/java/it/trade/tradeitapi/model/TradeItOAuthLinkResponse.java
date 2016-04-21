package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthLinkResponse extends TradeItResponse {
    @SerializedName("userToken")
    @Expose
    public String userToken;

    @SerializedName("userId")
    @Expose
    public String userId;

    @Override
    public String toString() {
        return "TradeItOAuthLinkResponse {" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                "}, " + super.toString();
    }
}