package it.trade.tradeitapi.API;

import it.trade.tradeitapi.model.TradeItLinkAccountRequest;
import it.trade.tradeitapi.model.TradeItLinkAccountResponse;
import it.trade.tradeitapi.model.TradeItAvailableBrokersResponse;
import it.trade.tradeitapi.model.TradeItRelinkAccountRequest;
import it.trade.tradeitapi.model.TradeItRequestWithKey;
import it.trade.tradeitapi.model.TradeItResponse;
import it.trade.tradeitapi.model.TradeItUnlinkAccountRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TradeItAccountLinkApi {
    @POST("/api/v1/user/oAuthLink")
    Call<TradeItLinkAccountResponse> linkAccount(@Body TradeItLinkAccountRequest request);

    @POST("/api/v1/user/oAuthUpdate")
    Call<TradeItLinkAccountResponse> relinkAccount(@Body TradeItRelinkAccountRequest request);

    @POST("/api/v1/preference/getStocksOrEtfsBrokerList")
    Call<TradeItAvailableBrokersResponse> getAvailableBrokers(@Body TradeItRequestWithKey request);

    @POST("/api/v1/user/oAuthDelete")
    Call<TradeItResponse> unlinkAccount(@Body TradeItUnlinkAccountRequest request);
}
