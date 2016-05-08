package it.trade.tradeitapi.model;

import com.google.gson.annotations.SerializedName;

public enum TradeItEnvironment {
    @SerializedName("PRODUCTION")
    PRODUCTION("https://ems.tradingticket.com/"),

    @SerializedName("QA")
    QA("https://ems.qa.tradingticket.com/");

    private String baseUrl;

    private TradeItEnvironment(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}