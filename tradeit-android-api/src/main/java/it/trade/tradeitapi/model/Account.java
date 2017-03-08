package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("accountBaseCurrency")
    @Expose
    public String accountBaseCurrency;

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", accountBaseCurrency='" + accountBaseCurrency + '\'' +
                '}';
    }
}
