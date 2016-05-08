package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItGetAllOrderStatusRequest extends TradeItRequestWithSession {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    public TradeItGetAllOrderStatusRequest(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "TradeItGetAllOrderStatusRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                "}, " + super.toString();
    }
}
