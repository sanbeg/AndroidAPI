package it.trade.tradeitapi.model;

import com.google.gson.annotations.SerializedName;

public class TradeItLinkedLogin {
    @SerializedName("label")
    public String label = "";

    @SerializedName("broker")
    public String broker = "";

    @SerializedName("userToken")
    public String userToken = "";

    @SerializedName("userId")
    public String userId = "";

    @SerializedName("apiKey")
    public String apiKey = "";

    @SerializedName("uuid")
    public String uuid = "";

    public TradeItLinkedLogin(TradeItLinkLoginRequest linkLoginRequest, TradeItLinkLoginResponse linkLoginResponse) {
        this.broker = linkLoginRequest.broker;
        this.apiKey = linkLoginRequest.apiKey;
        this.userId = linkLoginResponse.userId;
        this.userToken = linkLoginResponse.userToken;
    }

    public TradeItLinkedLogin(String broker, TradeItOAuthAccessTokenRequest oAuthAccessTokenRequest,
                              TradeItOAuthAccessTokenResponse oAuthAccessTokenResponse) {
        this.broker = broker;
        this.apiKey = oAuthAccessTokenRequest.apiKey;
        this.userId = oAuthAccessTokenResponse.userId;
        this.userToken = oAuthAccessTokenResponse.userToken;
    }

    private TradeItLinkedLogin() {}

    public void update(TradeItLinkLoginResponse linkLoginResponse) {
        this.userId = linkLoginResponse.userId;
        this.userToken = linkLoginResponse.userToken;
    }

    @Override
    public String toString() {
        return "TradeItLinkedLogin{" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", broker='" + broker + '\'' +
                ", label='" + label + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
