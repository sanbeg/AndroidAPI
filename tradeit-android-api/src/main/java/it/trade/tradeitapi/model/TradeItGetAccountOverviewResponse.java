package it.trade.tradeitapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItGetAccountOverviewResponse extends TradeItResponse implements Parcelable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeItGetAccountOverviewResponse that = (TradeItGetAccountOverviewResponse) o;

        if (availableCash != null ? !availableCash.equals(that.availableCash) : that.availableCash != null)
            return false;
        if (buyingPower != null ? !buyingPower.equals(that.buyingPower) : that.buyingPower != null)
            return false;
        if (dayAbsoluteReturn != null ? !dayAbsoluteReturn.equals(that.dayAbsoluteReturn) : that.dayAbsoluteReturn != null)
            return false;
        if (dayPercentReturn != null ? !dayPercentReturn.equals(that.dayPercentReturn) : that.dayPercentReturn != null)
            return false;
        if (totalAbsoluteReturn != null ? !totalAbsoluteReturn.equals(that.totalAbsoluteReturn) : that.totalAbsoluteReturn != null)
            return false;
        if (totalPercentReturn != null ? !totalPercentReturn.equals(that.totalPercentReturn) : that.totalPercentReturn != null)
            return false;
        return totalValue != null ? totalValue.equals(that.totalValue) : that.totalValue == null;

    }

    @Override
    public int hashCode() {
        int result = availableCash != null ? availableCash.hashCode() : 0;
        result = 31 * result + (buyingPower != null ? buyingPower.hashCode() : 0);
        result = 31 * result + (dayAbsoluteReturn != null ? dayAbsoluteReturn.hashCode() : 0);
        result = 31 * result + (dayPercentReturn != null ? dayPercentReturn.hashCode() : 0);
        result = 31 * result + (totalAbsoluteReturn != null ? totalAbsoluteReturn.hashCode() : 0);
        result = 31 * result + (totalPercentReturn != null ? totalPercentReturn.hashCode() : 0);
        result = 31 * result + (totalValue != null ? totalValue.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.availableCash);
        dest.writeValue(this.buyingPower);
        dest.writeValue(this.dayAbsoluteReturn);
        dest.writeValue(this.dayPercentReturn);
        dest.writeValue(this.totalAbsoluteReturn);
        dest.writeValue(this.totalPercentReturn);
        dest.writeValue(this.totalValue);
    }

    public TradeItGetAccountOverviewResponse() {
    }

    protected TradeItGetAccountOverviewResponse(Parcel in) {
        this.availableCash = (Double) in.readValue(Double.class.getClassLoader());
        this.buyingPower = (Double) in.readValue(Double.class.getClassLoader());
        this.dayAbsoluteReturn = (Double) in.readValue(Double.class.getClassLoader());
        this.dayPercentReturn = (Double) in.readValue(Double.class.getClassLoader());
        this.totalAbsoluteReturn = (Double) in.readValue(Double.class.getClassLoader());
        this.totalPercentReturn = (Double) in.readValue(Double.class.getClassLoader());
        this.totalValue = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<TradeItGetAccountOverviewResponse> CREATOR = new Parcelable.Creator<TradeItGetAccountOverviewResponse>() {
        @Override
        public TradeItGetAccountOverviewResponse createFromParcel(Parcel source) {
            return new TradeItGetAccountOverviewResponse(source);
        }

        @Override
        public TradeItGetAccountOverviewResponse[] newArray(int size) {
            return new TradeItGetAccountOverviewResponse[size];
        }
    };
}
