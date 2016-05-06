package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthLinkRequest {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("broker")
    @Expose
    public String broker;

    @SerializedName("apiKey")
    @Expose
    public String apiKey;

    public TradeItOAuthLinkRequest(String id, String password, String broker, String apiKey) {
        this.id = id;
        this.password = password;
        this.broker = broker;
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "TradeItOAuthLinkRequest{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", broker='" + broker + '\'' +
                ", apiKey='" + apiKey + '\'' +
                "}";
    }
}