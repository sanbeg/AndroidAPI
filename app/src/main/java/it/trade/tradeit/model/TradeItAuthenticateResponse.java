package it.trade.tradeit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItAuthenticateResponse extends TradeItResponse {
    @SerializedName("accounts")
    @Expose
    public List<Account> accounts = new ArrayList<Account>();

    public class Account {
        @SerializedName("accountNumber")
        @Expose
        public String accountNumber;

        @SerializedName("name")
        @Expose
        public String name;

        @Override
        public String toString() {
            return "Account{" +
                    "accountNumber='" + accountNumber + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TradeItAuthenticateResponse{" +
                "token='" + token + '\'' +
                ", accounts=" + accounts +
                "}, " + super.toString();
    }
}