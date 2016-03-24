package it.trade.tradeit.API;

import it.trade.tradeit.model.TradeItAuthenticateRequest;
import it.trade.tradeit.model.TradeItAuthenticateResponse;
import it.trade.tradeit.model.TradeItOAuthLinkRequest;
import it.trade.tradeit.model.TradeItOAuthLinkResponse;
import it.trade.tradeit.model.TradeItPlaceStockOrEtfOrderRequest;
import it.trade.tradeit.model.TradeItPlaceStockOrEtfOrderResponse;
import it.trade.tradeit.model.TradeItPreviewStockOrEtfOrderRequest;
import it.trade.tradeit.model.TradeItPreviewStockOrEtfOrderResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TradeItAPI {
    @POST("/api/v1/user/oAuthLink")
    Call<TradeItOAuthLinkResponse> oAuthLink(@Body TradeItOAuthLinkRequest request);

    @POST("/api/v1/user/authenticate")
    Call<TradeItAuthenticateResponse> authenticate(@Body TradeItAuthenticateRequest request);

    @POST("/api/v1/order/previewStockOrEtfOrder")
    Call<TradeItPreviewStockOrEtfOrderResponse> previewStockOrEtfOrder(@Body TradeItPreviewStockOrEtfOrderRequest request);

    @POST("/api/v1/order/placeStockOrEtfOrder")
    Call<TradeItPlaceStockOrEtfOrderResponse> placeStockOrEtfOrder(@Body TradeItPlaceStockOrEtfOrderRequest request);

}
