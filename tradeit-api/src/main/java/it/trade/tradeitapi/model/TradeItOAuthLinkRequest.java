package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthLinkRequest extends TradeItRequestWithKey {

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

    public TradeItOAuthLinkRequest(String id, String password, String broker) {
        this.id = id;
        this.password = password;
        this.broker = broker;
    }

    @Override
    public String toString() {
        return "TradeItOAuthLinkRequest{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", broker='" + broker + '\'' +
                "}, " + super.toString();
    }
}