package it.trade.tradeitapi.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthLoginPopupUrlForMobileResponse extends TradeItResponse {
    @SerializedName("oAuthURL")
    @Expose
    public String oAuthURL;
}
