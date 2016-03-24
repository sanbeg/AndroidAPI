package it.trade.tradeit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItPlaceStockOrEtfOrderResponse extends TradeItResponse {
    @SerializedName("broker")
    @Expose
    public String broker;

    @SerializedName("confirmationMessage")
    @Expose
    public String confirmationMessage;

    @SerializedName("orderInfo")
    @Expose
    public OrderInfo orderInfo;

    @SerializedName("orderNumber")
    @Expose
    public String orderNumber;

    @SerializedName("timestamp")
    @Expose
    public String timestamp;

    public class OrderInfo {
        @SerializedName("action")
        @Expose
        public String action;

        @SerializedName("quantity")
        @Expose
        public Integer quantity;

        @SerializedName("symbol")
        @Expose
        public String symbol;

        @SerializedName("price")
        @Expose
        public Price price;

        @SerializedName("expiration")
        @Expose
        public String expiration;

        @Override
        public String toString() {
            return "OrderInfo{" +
                    "action='" + action + '\'' +
                    ", quantity=" + quantity +
                    ", symbol='" + symbol + '\'' +
                    ", price=" + price +
                    ", expiration='" + expiration + '\'' +
                    '}';
        }
    }

    public class Price {
        @SerializedName("type")
        @Expose
        public String type;

        @SerializedName("limitPrice")
        @Expose
        public Integer limitPrice;

        @SerializedName("stopPrice")
        @Expose
        public Integer stopPrice;

        @SerializedName("last")
        @Expose
        public Double last;

        @SerializedName("bid")
        @Expose
        public Double bid;

        @SerializedName("ask")
        @Expose
        public Double ask;

        @SerializedName("timestamp")
        @Expose
        public String timestamp;

        @Override
        public String toString() {
            return "Price{" +
                    "type='" + type + '\'' +
                    ", limitPrice=" + limitPrice +
                    ", stopPrice=" + stopPrice +
                    ", last=" + last +
                    ", bid=" + bid +
                    ", ask=" + ask +
                    ", timestamp='" + timestamp + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TradeItPlaceStockOrEtfOrderResponse{" +
                "broker='" + broker + '\'' +
                ", confirmationMessage='" + confirmationMessage + '\'' +
                ", orderInfo=" + orderInfo +
                ", orderNumber='" + orderNumber + '\'' +
                ", timestamp='" + timestamp + '\'' +
                "}, " + super.toString();
    }
}