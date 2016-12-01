package it.trade.tradeitapi.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthLoginPopupUrlForMobileRequest extends TradeItRequestWithKey {

    @SerializedName("broker")
    @Expose
    public String broker;

    @SerializedName("interAppAddressCallback")
    @Expose
    public String interAppAddressCallback;


    public TradeItOAuthLoginPopupUrlForMobileRequest(String broker, String interAppAddressCallback) {
        this.broker = broker;
        this.interAppAddressCallback = interAppAddressCallback;
    }

    @Override
    public String toString() {
        return "TradeItOAuthLoginPopupUrlForMobileRequest{" +
                "broker='" + broker + '\'' +
                ", interAppAddressCallback='" + interAppAddressCallback + '\'' +
                '}';
    }
}
