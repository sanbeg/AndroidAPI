package it.trade.tradeitapi.model;

public enum TradeItEnvironment {
    PRODUCTION("https://ems.tradingticket.com/"),
    QA("https://ems.qa.tradingticket.com/");

    private String baseUrl;

    private TradeItEnvironment(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}