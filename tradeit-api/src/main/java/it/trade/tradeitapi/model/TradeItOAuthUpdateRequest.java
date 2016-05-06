package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthUpdateRequest extends TradeItRequestWithKey {
    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("broker")
    @Expose
    public String broker;

    @SerializedName("userId")
    @Expose
    public String userId;

    public TradeItOAuthUpdateRequest(String id, String password, String broker, String userId) {
        this.id = id;
        this.password = password;
        this.broker = broker;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TradeItOAuthUpdateRequest{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", broker='" + broker + '\'' +
                ", userId='" + userId + '\'' +
                ", apiKey='" + apiKey + '\'' +
                "}";
    }
}
