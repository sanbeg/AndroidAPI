package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItPreviewStockOrEtfOrderResponse extends TradeItResponse {
    @SerializedName("ackWarningsList")
    @Expose
    public List<String> ackWarningsList = new ArrayList<String>();

    @SerializedName("orderDetails")
    @Expose
    public OrderDetails orderDetails;

    @SerializedName("orderId")
    @Expose
    public String orderId;

    @SerializedName("warningsList")
    @Expose
    public List<String> warningsList = new ArrayList<String>();

    public class OrderDetails {
        @SerializedName("orderSymbol")
        @Expose
        public String orderSymbol;

        @SerializedName("orderAction")
        @Expose
        public String orderAction;

        @SerializedName("orderQuantity")
        @Expose
        public Double orderQuantity;

        @SerializedName("orderExpiration")
        @Expose
        public String orderExpiration;

        @SerializedName("orderPrice")
        @Expose
        public String orderPrice;

        @SerializedName("orderValueLabel")
        @Expose
        public String orderValueLabel;

        @SerializedName("orderMessage")
        @Expose
        public String orderMessage;

        @SerializedName("lastPrice")
        @Expose
        public String lastPrice;

        @SerializedName("bidPrice")
        @Expose
        public String bidPrice;

        @SerializedName("askPrice")
        @Expose
        public String askPrice;

        @SerializedName("timestamp")
        @Expose
        public String timestamp;

        @SerializedName("buyingPower")
        @Expose
        public Double buyingPower;

        @SerializedName("availableCash")
        @Expose
        public Double availableCash;

        @SerializedName("estimatedOrderCommission")
        @Expose
        public Double estimatedOrderCommission;

        @SerializedName("longHoldings")
        @Expose
        public Double longHoldings;

        @SerializedName("shortHoldings")
        @Expose
        public Double shortHoldings;

        @SerializedName("estimatedOrderValue")
        @Expose
        public Double estimatedOrderValue;

        @SerializedName("estimatedTotalValue")
        @Expose
        public Double estimatedTotalValue;

        @Override
        public String toString() {
            return "OrderDetails{" +
                    "orderSymbol='" + orderSymbol + '\'' +
                    ", orderAction='" + orderAction + '\'' +
                    ", orderQuantity=" + orderQuantity +
                    ", orderExpiration='" + orderExpiration + '\'' +
                    ", orderPrice='" + orderPrice + '\'' +
                    ", orderValueLabel='" + orderValueLabel + '\'' +
                    ", orderMessage='" + orderMessage + '\'' +
                    ", lastPrice='" + lastPrice + '\'' +
                    ", bidPrice='" + bidPrice + '\'' +
                    ", askPrice='" + askPrice + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", buyingPower=" + buyingPower +
                    ", availableCash=" + availableCash +
                    ", estimatedOrderCommission=" + estimatedOrderCommission +
                    ", longHoldings=" + longHoldings +
                    ", shortHoldings=" + shortHoldings +
                    ", estimatedOrderValue=" + estimatedOrderValue +
                    ", estimatedTotalValue=" + estimatedTotalValue +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TradeItPreviewStockOrEtfOrderResponse{" +
                "ackWarningsList=" + ackWarningsList +
                ", orderDetails=" + orderDetails +
                ", orderId=" + orderId +
                ", warningsList=" + warningsList +
                "}, " + super.toString();
    }
}
