package it.trade.tradeitapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItPreviewStockOrEtfOrderResponse extends TradeItResponse implements Parcelable {
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

    public static class OrderDetails implements Parcelable {
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

        @Override
        public int describeContents() { return 0; }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.orderSymbol);
            dest.writeString(this.orderAction);
            dest.writeDouble(this.orderQuantity);
            dest.writeString(this.orderExpiration);
            dest.writeString(this.orderPrice);
            dest.writeString(this.orderValueLabel);
            dest.writeString(this.orderMessage);
            dest.writeString(this.lastPrice);
            dest.writeString(this.bidPrice);
            dest.writeString(this.askPrice);
            dest.writeString(this.timestamp);
            dest.writeDouble(this.buyingPower);
            dest.writeDouble(this.availableCash);
            dest.writeDouble(this.estimatedOrderCommission);
            dest.writeDouble(this.longHoldings);
            dest.writeDouble(this.shortHoldings);
            dest.writeDouble(this.estimatedOrderValue);
            dest.writeDouble(this.estimatedTotalValue);
        }

        public OrderDetails() {}

        protected OrderDetails(Parcel in) {
            this.orderSymbol = in.readString();
            this.orderAction = in.readString();
            this.orderQuantity = in.readDouble();
            this.orderExpiration = in.readString();
            this.orderPrice = in.readString();
            this.orderValueLabel = in.readString();
            this.orderMessage = in.readString();
            this.lastPrice = in.readString();
            this.bidPrice = in.readString();
            this.askPrice = in.readString();
            this.timestamp = in.readString();
            this.buyingPower = in.readDouble();
            this.availableCash = in.readDouble();
            this.estimatedOrderCommission = in.readDouble();
            this.longHoldings = in.readDouble();
            this.shortHoldings = in.readDouble();
            this.estimatedOrderValue = in.readDouble();
            this.estimatedTotalValue = in.readDouble();
        }

        public static final Parcelable.Creator<OrderDetails> CREATOR = new Parcelable.Creator<OrderDetails>() {
            @Override
            public OrderDetails createFromParcel(Parcel source) {
                return new OrderDetails(source);
            }

            @Override
            public OrderDetails[] newArray(int size) {
                return new OrderDetails[size];
            }
        };
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

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.ackWarningsList);
        dest.writeParcelable(this.orderDetails, flags);
        dest.writeString(this.orderId);
        dest.writeStringList(this.warningsList);
    }

    public TradeItPreviewStockOrEtfOrderResponse() {}

    protected TradeItPreviewStockOrEtfOrderResponse(Parcel in) {
        this.ackWarningsList = in.createStringArrayList();
        this.orderDetails = in.readParcelable(OrderDetails.class.getClassLoader());
        this.orderId = in.readString();
        this.warningsList = in.createStringArrayList();
    }

    public static final Parcelable.Creator<TradeItPreviewStockOrEtfOrderResponse> CREATOR = new Parcelable.Creator<TradeItPreviewStockOrEtfOrderResponse>() {
        @Override
        public TradeItPreviewStockOrEtfOrderResponse createFromParcel(Parcel source) {
            return new TradeItPreviewStockOrEtfOrderResponse(source);
        }

        @Override
        public TradeItPreviewStockOrEtfOrderResponse[] newArray(int size) {
            return new TradeItPreviewStockOrEtfOrderResponse[size];
        }
    };
}
