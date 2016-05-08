package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItRelinkAccountRequest extends TradeItRequestWithKey {
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

    public TradeItRelinkAccountRequest(TradeItLinkedAccount linkedAccount, String brokerLoginId, String password) {
        this.id = brokerLoginId;
        this.password = password;
        this.broker = linkedAccount.broker;
        this.userId = linkedAccount.userId;
    }

    public TradeItRelinkAccountRequest(String brokerLoginId, String password, String broker, String linkedAccountUserId) {
        this.id = brokerLoginId;
        this.password = password;
        this.broker = broker;
        this.userId = linkedAccountUserId;
    }

    @Override
    public String toString() {
        return "TradeItRelinkAccountRequest{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", broker='" + broker + '\'' +
                ", userId='" + userId + '\'' +
                "}, " + super.toString();
    }
}
