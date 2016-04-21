package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItOrderStatusResponse extends TradeItResponse {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    @SerializedName("orderStatusDetailsList")
    @Expose
    public List<OrderStatusDetails> orderStatusDetailsList = new ArrayList<OrderStatusDetails>();

    public class OrderStatusDetails {
        @SerializedName("groupOrderType")
        @Expose
        public String groupOrderType;

        @SerializedName("orderExpiration")
        @Expose
        public String orderExpiration;

        @SerializedName("orderType")
        @Expose
        public String orderType;

        @SerializedName("groupOrderId")
        @Expose
        public String groupOrderId;

        @SerializedName("orderLegs")
        @Expose
        public List<OrderLeg> orderLegs = new ArrayList<OrderLeg>();

        @SerializedName("orderNumber")
        @Expose
        public String orderNumber;

        @SerializedName("orderStatus")
        @Expose
        public String orderStatus;

        @SerializedName("groupOrders")
        @Expose
        public List<OrderStatusDetails> groupOrders = new ArrayList<OrderStatusDetails>();

        @Override
        public String toString() {
            return "OrderStatusDetails{" +
                    "groupOrderType='" + groupOrderType + '\'' +
                    ", orderExpiration='" + orderExpiration + '\'' +
                    ", orderType='" + orderType + '\'' +
                    ", groupOrderId='" + groupOrderId + '\'' +
                    ", orderLegs=" + orderLegs +
                    ", orderNumber='" + orderNumber + '\'' +
                    ", orderStatus='" + orderStatus + '\'' +
                    ", groupOrders=" + groupOrders +
                    '}';
        }
    }

    public class OrderLeg {
        @SerializedName("priceInfo")
        @Expose
        public PriceInfo priceInfo;

        @SerializedName("fills")
        @Expose
        public List<Fill> fills = new ArrayList<Fill>();

        @SerializedName("symbol")
        @Expose
        public String symbol;

        @SerializedName("orderedQuantity")
        @Expose
        public Integer orderedQuantity;

        @SerializedName("filledQuantity")
        @Expose
        public Integer filledQuantity;

        @SerializedName("action")
        @Expose
        public String action;

        @Override
        public String toString() {
            return "OrderLeg{" +
                    "priceInfo=" + priceInfo +
                    ", fills=" + fills +
                    ", symbol='" + symbol + '\'' +
                    ", orderedQuantity=" + orderedQuantity +
                    ", filledQuantity=" + filledQuantity +
                    ", action='" + action + '\'' +
                    '}';
        }
    }

    public class PriceInfo {
        @SerializedName("conditionType")
        @Expose
        public String conditionType;

        @SerializedName("initialStopPrice")
        @Expose
        public Double initialStopPrice;

        @SerializedName("conditionSymbol")
        @Expose
        public String conditionSymbol;

        @SerializedName("trailPrice")
        @Expose
        public Double trailPrice;

        @SerializedName("conditionFollowPrice")
        @Expose
        public Double conditionFollowPrice;

        @SerializedName("limitPrice")
        @Expose
        public Double limitPrice;

        @SerializedName("triggerPrice")
        @Expose
        public Double triggerPrice;

        @SerializedName("conditionPrice")
        @Expose
        public Double conditionPrice;

        @SerializedName("bracketLimitPrice")
        @Expose
        public Double bracketLimitPrice;

        @SerializedName("type")
        @Expose
        public String type;

        @SerializedName("stopPrice")
        @Expose
        public Double stopPrice;

        @Override
        public String toString() {
            return "PriceInfo{" +
                    "conditionType='" + conditionType + '\'' +
                    ", initialStopPrice=" + initialStopPrice +
                    ", conditionSymbol='" + conditionSymbol + '\'' +
                    ", trailPrice=" + trailPrice +
                    ", conditionFollowPrice=" + conditionFollowPrice +
                    ", limitPrice=" + limitPrice +
                    ", triggerPrice=" + triggerPrice +
                    ", conditionPrice=" + conditionPrice +
                    ", bracketLimitPrice=" + bracketLimitPrice +
                    ", type='" + type + '\'' +
                    ", stopPrice=" + stopPrice +
                    '}';
        }
    }

    public class Fill {
        @SerializedName("timestampFormat")
        @Expose
        public String timestampFormat;

        @SerializedName("price")
        @Expose
        public Double price;

        @SerializedName("timestamp")
        @Expose
        public String timestamp;

        @SerializedName("quantity")
        @Expose
        public Integer quantity;

        @Override
        public String toString() {
            return "Fill{" +
                    "timestampFormat='" + timestampFormat + '\'' +
                    ", price=" + price +
                    ", timestamp='" + timestamp + '\'' +
                    ", quantity=" + quantity +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TradeItOrderStatusResponse{" +
                "accountNumber='" + accountNumber + '\'' +
                ", orderStatusDetailsList=" + orderStatusDetailsList +
                "}, " + super.toString();
    }
}
