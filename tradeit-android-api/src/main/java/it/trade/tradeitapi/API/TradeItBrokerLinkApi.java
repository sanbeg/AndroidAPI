package it.trade.tradeitapi.API;

import it.trade.tradeitapi.model.TradeItLinkLoginRequest;
import it.trade.tradeitapi.model.TradeItLinkLoginResponse;
import it.trade.tradeitapi.model.TradeItAvailableBrokersResponse;
import it.trade.tradeitapi.model.TradeItOAuthAccessTokenRequest;
import it.trade.tradeitapi.model.TradeItOAuthAccessTokenResponse;
import it.trade.tradeitapi.model.TradeItOAuthLoginPopupUrlForMobileRequest;
import it.trade.tradeitapi.model.TradeItOAuthLoginPopupUrlForMobileResponse;
import it.trade.tradeitapi.model.TradeItOAuthLoginPopupUrlForTokenUpdateRequest;
import it.trade.tradeitapi.model.TradeItOAuthLoginPopupUrlForTokenUpdateResponse;
import it.trade.tradeitapi.model.TradeItRelinkLoginRequest;
import it.trade.tradeitapi.model.TradeItRequestWithKey;
import it.trade.tradeitapi.model.TradeItResponse;
import it.trade.tradeitapi.model.TradeItUnlinkLoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TradeItBrokerLinkApi {
    @POST("/api/v1/user/getOAuthLoginPopupUrlForMobile")
    Call<TradeItOAuthLoginPopupUrlForMobileResponse> getOAuthLoginPopupUrlForMobile(@Body TradeItOAuthLoginPopupUrlForMobileRequest request);

    @POST("/api/v1/user/getOAuthLoginPopupURLForTokenUpdate")
    Call<TradeItOAuthLoginPopupUrlForTokenUpdateResponse> getOAuthLoginPopupURLForTokenUpdate(@Body TradeItOAuthLoginPopupUrlForTokenUpdateRequest request);

    @POST("/api/v1/user/getOAuthAccessToken")
    Call<TradeItOAuthAccessTokenResponse> getOAuthAccessToken(@Body TradeItOAuthAccessTokenRequest request);

    @POST("/api/v1/user/oAuthLink")
    Call<TradeItLinkLoginResponse> linkLogin(@Body TradeItLinkLoginRequest request);

    @POST("/api/v1/user/oAuthUpdate")
    Call<TradeItLinkLoginResponse> relinkLogin(@Body TradeItRelinkLoginRequest request);

    @POST("/api/v1/preference/getStocksOrEtfsBrokerList")
    Call<TradeItAvailableBrokersResponse> getAvailableBrokers(@Body TradeItRequestWithKey request);

    @POST("/api/v1/user/oAuthDelete")
    Call<TradeItResponse> unlinkLogin(@Body TradeItUnlinkLoginRequest request);
}
