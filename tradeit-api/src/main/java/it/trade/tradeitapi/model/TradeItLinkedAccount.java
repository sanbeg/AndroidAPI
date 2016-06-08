package it.trade.tradeitapi.model;

import com.google.gson.annotations.SerializedName;

public class TradeItLinkedAccount {
    @SerializedName("label")
    public String label = "";

    @SerializedName("broker")
    public String broker = "";

    @SerializedName("userToken")
    public String userToken = "";

    @SerializedName("userId")
    public String userId = "";

    @SerializedName("environment")
    public TradeItEnvironment environment;

    @SerializedName("apiKey")
    public String apiKey = "";

    @SerializedName("uUID")
    public String uUID = "";

    public TradeItLinkedAccount(TradeItLinkAccountRequest linkAccountRequest, TradeItLinkAccountResponse linkAccountResponse) {
        this.broker = linkAccountRequest.broker;
        this.apiKey = linkAccountRequest.apiKey;
        this.environment = linkAccountRequest.environment;
        this.userId = linkAccountResponse.userId;
        this.userToken = linkAccountResponse.userToken;
    }

    private TradeItLinkedAccount() {}

    public void update(TradeItLinkAccountResponse linkAccountResponse) {
        this.userId = linkAccountResponse.userId;
        this.userToken = linkAccountResponse.userToken;
    }

    @Override
    public String toString() {
        return "TradeItLinkedAccount{" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                ", environment=" + environment +
                ", apiKey='" + apiKey + '\'' +
                ", broker='" + broker + '\'' +
                ", label='" + label + '\'' +
                ", uUID='" + uUID + '\'' +
                '}';
    }
}
