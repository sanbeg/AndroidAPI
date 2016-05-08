package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItGetPositionsResponse extends TradeItResponse {
    @SerializedName("currentPage")
    @Expose
    public Integer currentPage;

    @SerializedName("positions")
    @Expose
    public List<Position> positions = new ArrayList<Position>();

    @SerializedName("totalPages")
    @Expose
    public Integer totalPages;

    public class Position {
        @SerializedName("costbasis")
        @Expose
        public Double costbasis;

        @SerializedName("holdingType")
        @Expose
        public String holdingType;

        @SerializedName("lastPrice")
        @Expose
        public Double lastPrice;

        @SerializedName("quantity")
        @Expose
        public Double quantity;

        @SerializedName("symbol")
        @Expose
        public String symbol;

        @SerializedName("symbolClass")
        @Expose
        public String symbolClass;

        @SerializedName("todayGainLossDollar")
        @Expose
        public Double todayGainLossDollar;

        @SerializedName("todayGainLossPercentage")
        @Expose
        public Double todayGainLossPercentage;

        @SerializedName("totalGainLossDollar")
        @Expose
        public Double totalGainLossDollar;

        @SerializedName("totalGainLossPercentage")
        @Expose
        public Double totalGainLossPercentage;

        @Override
        public String toString() {
            return "Position{" +
                    "costbasis=" + costbasis +
                    ", holdingType='" + holdingType + '\'' +
                    ", lastPrice=" + lastPrice +
                    ", quantity=" + quantity +
                    ", symbol='" + symbol + '\'' +
                    ", symbolClass='" + symbolClass + '\'' +
                    ", todayGainLossDollar=" + todayGainLossDollar +
                    ", todayGainLossPercentage=" + todayGainLossPercentage +
                    ", totalGainLossDollar=" + totalGainLossDollar +
                    ", totalGainLossPercentage=" + totalGainLossPercentage +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TradeItGetPositionsResponse{" +
                "currentPage=" + currentPage +
                ", positions=" + positions +
                ", totalPages=" + totalPages +
                "}, " + super.toString();
    }
}
