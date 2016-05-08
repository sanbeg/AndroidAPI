package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItResponse {
    // Refer to https://www.trade.it/documentation/api#ErrorHandling for error codes
    @SerializedName("code")
    @Expose
    public TradeItErrorCode code;

    @SerializedName("longMessages")
    @Expose
    public List<String> longMessages = new ArrayList<String>();

    @SerializedName("shortMessage")
    @Expose
    public String shortMessage;

    @SerializedName("status")
    @Expose
    public TradeItResponseStatus status;

    @SerializedName("token")
    @Expose
    public String sessionToken;

    @Override
    public String toString() {
        return "TradeItResponse{" +
                "code=" + code +
                ", longMessages=" + longMessages +
                ", shortMessage='" + shortMessage + '\'' +
                ", status='" + status + '\'' +
                ", token='" + sessionToken + '\'' +
                '}';
    }
}