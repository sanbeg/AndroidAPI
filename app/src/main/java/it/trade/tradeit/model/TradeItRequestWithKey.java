package it.trade.tradeit.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItRequestWithKey {
    public static String API_KEY;

    @SerializedName("apiKey")
    @Expose
    public String apiKey;

    public TradeItRequestWithKey() {
        this.apiKey = API_KEY;
    }
}
