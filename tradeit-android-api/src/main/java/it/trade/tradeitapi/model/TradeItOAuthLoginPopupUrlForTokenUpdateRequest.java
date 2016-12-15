package it.trade.tradeitapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by guillaumedebavelaere on 12/15/16.
 */

public class TradeItOAuthLoginPopupUrlForTokenUpdateRequest extends TradeItOAuthLoginPopupUrlForMobileRequest {

    @SerializedName("userId")
    @Expose
    public String userId;

    public TradeItOAuthLoginPopupUrlForTokenUpdateRequest(String broker, String interAppAddressCallback, String userId) {
        super(broker, interAppAddressCallback);
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TradeItOAuthLoginPopupUrlForTokenUpdateRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
