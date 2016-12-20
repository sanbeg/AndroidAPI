package it.trade.tradeitapi.model;

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

    @SerializedName("challengeImage")
    @Expose
    public String challengeImage;

    @SerializedName("informationType")
    @Expose
    public String informationType;

    @SerializedName("securityQuestion")
    @Expose
    public String securityQuestion;

    @SerializedName("securityQuestionOptions")
    @Expose
    public List<String> securityQuestionOptions;

    @Override
    public String toString() {
        return "TradeItAuthenticateResponse{" +
                "accounts=" + accounts +
                ", challengeImage='" + challengeImage + '\'' +
                ", informationType='" + informationType + '\'' +
                ", securityQuestion='" + securityQuestion + '\'' +
                ", securityQuestionOptions=" + securityQuestionOptions +
                "}, " + super.toString();
    }
}