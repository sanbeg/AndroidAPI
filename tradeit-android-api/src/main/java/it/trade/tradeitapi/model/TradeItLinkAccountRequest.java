package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItLinkAccountRequest extends TradeItRequestWithKey {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("broker")
    @Expose
    public String broker;

    public TradeItEnvironment environment;

    public TradeItLinkAccountRequest(String brokerLoginId, String password, String broker) {
        this.id = brokerLoginId;
        this.password = password;
        this.broker = broker;
    }

    @Override
    public String toString() {
        return "TradeItLinkAccountRequest{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", broker='" + broker + '\'' +
                "}, " + super.toString();
    }
}