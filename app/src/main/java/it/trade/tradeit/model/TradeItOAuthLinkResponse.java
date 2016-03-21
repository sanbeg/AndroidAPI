package it.trade.tradeit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItOAuthLinkResponse {

    @SerializedName("userToken")
    @Expose
    public String userToken;
    @SerializedName("userId")
    @Expose
    public String userId;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("longMessages")
    @Expose
    public List<String> longMessages = new ArrayList<String>();
    @SerializedName("shortMessages")
    @Expose
    public String shortMessages;
    @SerializedName("token")
    @Expose
    public String token;
}