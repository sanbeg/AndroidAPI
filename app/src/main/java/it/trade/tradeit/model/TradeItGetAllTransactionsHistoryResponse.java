package it.trade.tradeit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItGetAllTransactionsHistoryResponse extends TradeItResponse {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    @SerializedName("transactionHistoryDetailsList")
    @Expose
    public List<TransactionDetails> transactionHistoryDetailsList = new ArrayList<TransactionDetails>();

    public class TransactionDetails {
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("symbol")
        @Expose
        public String symbol;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("amount")
        @Expose
        public Double amount;
        @SerializedName("action")
        @Expose
        public String action;
        @SerializedName("type")
        @Expose
        public String type;

        @Override
        public String toString() {
            return "TransactionDetails{" +
                    "description='" + description + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", date='" + date + '\'' +
                    ", amount=" + amount +
                    ", action='" + action + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TradeItGetAllTransactionsHistoryResponse{" +
                "accountNumber='" + accountNumber + '\'' +
                ", transactionHistoryDetailsList=" + transactionHistoryDetailsList +
                "}, " + super.toString();
    }
}
