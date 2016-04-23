package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItRequestWithSession extends TradeItRequestWithKey {
    public static String SESSION_TOKEN;

    @SerializedName("token")
    @Expose
    public String token;

    public TradeItRequestWithSession() {
        this.token = SESSION_TOKEN;
    }

    @Override
    public String toString() {
        return "TradeItRequestWithSession{" +
                "token='" + token + '\'' +
                "}, " + super.toString();
    }
}