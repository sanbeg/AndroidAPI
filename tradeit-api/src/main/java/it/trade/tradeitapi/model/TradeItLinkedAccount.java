package it.trade.tradeitapi.model;

import com.google.gson.annotations.SerializedName;

/*
==========
Shared Preferences
==========
import android.content.Context;
import android.content.SharedPreferences;


*/

public class TradeItLinkedAccount {
    @SerializedName("label")
    public String label = "";

    @SerializedName("broker")
    public String broker = "";

    @SerializedName("userToken")
    public String userToken = "";

    @SerializedName("userId")
    public String userId = "";

    @SerializedName("")
    public TradeItEnvironment environment;

    @SerializedName("")
    public String apiKey = "";

    public TradeItLinkedAccount(TradeItOAuthLinkRequest oAuthLinkRequest, TradeItOAuthLinkResponse oAuthLinkResponse) {
        this.broker = oAuthLinkRequest.broker;
        this.apiKey = oAuthLinkRequest.apiKey;
        this.environment = oAuthLinkRequest.environment;
        this.userId = oAuthLinkResponse.userId;
        this.userToken = oAuthLinkResponse.userToken;
    }

//    public TradeItLinkedAccount(String broker, String apiKey, TradeItEnvironment environment, String userToken, String userId) {
//        this.broker = broker;
//        this.apiKey = apiKey;
//        this.environment = environment;
//        this.userId = userId;
//        this.userToken = userToken;
//    }

    private TradeItLinkedAccount() {}

//    public void update(TradeItOAuthLinkResponse response) {
//
//    }


    @Override
    public String toString() {
        return "TradeItLinkedAccount{" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                ", environment=" + environment +
                ", apiKey='" + apiKey + '\'' +
                ", broker='" + broker + '\'' +
                '}';
    }
}
