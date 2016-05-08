package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItGetAccountOverviewResponse extends TradeItResponse {
    @SerializedName("availableCash")
    @Expose
    public Double availableCash;

    @SerializedName("buyingPower")
    @Expose
    public Double buyingPower;

    @SerializedName("dayAbsoluteReturn")
    @Expose
    public Double dayAbsoluteReturn;

    @SerializedName("dayPercentReturn")
    @Expose
    public Double dayPercentReturn;

    @SerializedName("totalAbsoluteReturn")
    @Expose
    public Double totalAbsoluteReturn;

    @SerializedName("totalPercentReturn")
    @Expose
    public Double totalPercentReturn;

    @SerializedName("totalValue")
    @Expose
    public Double totalValue;

    @Override
    public String toString() {
        return "TradeItGetAccountOverviewResponse{" +
                "availableCash=" + availableCash +
                ", buyingPower=" + buyingPower +
                ", dayAbsoluteReturn=" + dayAbsoluteReturn +
                ", dayPercentReturn=" + dayPercentReturn +
                ", totalAbsoluteReturn=" + totalAbsoluteReturn +
                ", totalPercentReturn=" + totalPercentReturn +
                ", totalValue=" + totalValue +
                "}, " + super.toString();
    }
}
