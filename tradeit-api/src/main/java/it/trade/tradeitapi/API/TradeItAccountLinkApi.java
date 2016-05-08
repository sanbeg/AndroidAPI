package it.trade.tradeitapi.API;

import it.trade.tradeitapi.model.TradeItAvailableBrokersResponse;
import it.trade.tradeitapi.model.TradeItOAuthLinkRequest;
import it.trade.tradeitapi.model.TradeItOAuthLinkResponse;
import it.trade.tradeitapi.model.TradeItOAuthUpdateRequest;
import it.trade.tradeitapi.model.TradeItRequestWithKey;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TradeItAccountLinkApi {
    @POST("/api/v1/user/oAuthLink")
    Call<TradeItOAuthLinkResponse> oAuthLink(@Body TradeItOAuthLinkRequest request);

    @POST("/api/v1/user/oAuthUpdate")
    Call<TradeItOAuthLinkResponse> oAuthUpdate(@Body TradeItOAuthUpdateRequest request);

    @POST("/api/v1/preference/getStocksOrEtfsBrokerList")
    Call<TradeItAvailableBrokersResponse> getAvailableBrokers(@Body TradeItRequestWithKey request);
}
