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

    @SerializedName("environment")
    public TradeItEnvironment environment;

    @SerializedName("apiKey")
    public String apiKey = "";

    @SerializedName("uuid")
    public String uuid = "";

    public TradeItLinkedLogin(TradeItLinkLoginRequest linkLoginRequest, TradeItLinkLoginResponse linkLoginResponse) {
        this.broker = linkLoginRequest.broker;
        this.apiKey = linkLoginRequest.apiKey;
        this.environment = linkLoginRequest.environment;
        this.userId = linkLoginResponse.userId;
        this.userToken = linkLoginResponse.userToken;
    }

    public TradeItLinkedLogin(String broker, TradeItOAuthAccessTokenRequest oAuthAccessTokenRequest,
                              TradeItOAuthAccessTokenResponse oAuthAccessTokenResponse) {
        this.broker = broker;
        this.apiKey = oAuthAccessTokenRequest.apiKey;
        this.environment = oAuthAccessTokenRequest.environment;
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
                ", environment=" + environment +
                ", apiKey='" + apiKey + '\'' +
                ", broker='" + broker + '\'' +
                ", label='" + label + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
