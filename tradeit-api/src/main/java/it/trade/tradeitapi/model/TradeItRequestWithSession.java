package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItRequestWithSession extends TradeItRequestWithKey {
    @SerializedName("token")
    @Expose
    public String sessionToken;

    public TradeItRequestWithSession() {}

    @Override
    public String toString() {
        return "TradeItRequestWithSession{" +
                "token='" + sessionToken + '\'' +
                "}, " + super.toString();
    }
}
