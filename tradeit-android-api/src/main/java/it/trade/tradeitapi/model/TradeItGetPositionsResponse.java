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

    @Override
    public String toString() {
        return "TradeItGetPositionsResponse{" +
                "currentPage=" + currentPage +
                ", positions=" + positions +
                ", totalPages=" + totalPages +
                "}, " + super.toString();
    }
}
