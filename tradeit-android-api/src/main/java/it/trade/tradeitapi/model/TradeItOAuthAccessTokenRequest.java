package it.trade.tradeitapi.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthAccessTokenRequest extends TradeItRequestWithKey {

    @SerializedName("oAuthVerifier")
    @Expose
    public String oAuthVerifier;

    public TradeItEnvironment environment;

    public TradeItOAuthAccessTokenRequest(String oAuthVerifier) {
        this.oAuthVerifier = oAuthVerifier;
    }

    @Override
    public String toString() {
        return "TradeItOAuthAccessTokenRequest{" +
                "oAuthVerifier='" + oAuthVerifier + '\'' +
                '}';
    }
}
