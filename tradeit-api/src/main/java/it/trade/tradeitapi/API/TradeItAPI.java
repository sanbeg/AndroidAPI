package it.trade.tradeitapi.API;

import it.trade.tradeitapi.model.TradeItAnswerSecurityQuestionRequest;
import it.trade.tradeitapi.model.TradeItAuthenticateRequest;
import it.trade.tradeitapi.model.TradeItAuthenticateResponse;
import it.trade.tradeitapi.model.TradeItCancelOrderRequest;
import it.trade.tradeitapi.model.TradeItGetAccountOverviewRequest;
import it.trade.tradeitapi.model.TradeItGetAccountOverviewResponse;
import it.trade.tradeitapi.model.TradeItGetAllOrderStatusRequest;
import it.trade.tradeitapi.model.TradeItGetAllTransactionsHistoryRequest;
import it.trade.tradeitapi.model.TradeItGetAllTransactionsHistoryResponse;
import it.trade.tradeitapi.model.TradeItGetPositionsRequest;
import it.trade.tradeitapi.model.TradeItGetPositionsResponse;
import it.trade.tradeitapi.model.TradeItGetSingleOrderStatusRequest;
import it.trade.tradeitapi.model.TradeItOAuthLinkRequest;
import it.trade.tradeitapi.model.TradeItOAuthLinkResponse;
import it.trade.tradeitapi.model.TradeItOAuthUpdateRequest;
import it.trade.tradeitapi.model.TradeItOrderStatusResponse;
import it.trade.tradeitapi.model.TradeItPlaceStockOrEtfOrderRequest;
import it.trade.tradeitapi.model.TradeItPlaceStockOrEtfOrderResponse;
import it.trade.tradeitapi.model.TradeItPreviewStockOrEtfOrderRequest;
import it.trade.tradeitapi.model.TradeItPreviewStockOrEtfOrderResponse;
import it.trade.tradeitapi.model.TradeItRequestWithSession;
import it.trade.tradeitapi.model.TradeItResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TradeItApi {
    @POST("/api/v1/user/oAuthLink")
    Call<TradeItOAuthLinkResponse> oAuthLink(@Body TradeItOAuthLinkRequest request);

    @POST("/api/v1/user/authenticate")
    Call<TradeItAuthenticateResponse> authenticate(@Body TradeItAuthenticateRequest request);

    @POST("/api/v1/user/answerSecurityQuestion")
    Call<TradeItAuthenticateResponse> answerSecurityQuestion(@Body TradeItAnswerSecurityQuestionRequest request);

    @POST("/api/v1/user/oAuthUpdate")
    Call<TradeItOAuthLinkResponse> oAuthUpdate(@Body TradeItOAuthUpdateRequest request);

    @POST("/api/v1/user/keepSessionAlive")
    Call<TradeItResponse> keepSessionAlive(@Body TradeItRequestWithSession request);

    @POST("/api/v1/order/previewStockOrEtfOrder")
    Call<TradeItPreviewStockOrEtfOrderResponse> previewStockOrEtfOrder(@Body TradeItPreviewStockOrEtfOrderRequest request);

    @POST("/api/v1/order/placeStockOrEtfOrder")
    Call<TradeItPlaceStockOrEtfOrderResponse> placeStockOrEtfOrder(@Body TradeItPlaceStockOrEtfOrderRequest request);

    @POST("/api/v1/balance/getAccountOverview")
    Call<TradeItGetAccountOverviewResponse> getAccountOverview(@Body TradeItGetAccountOverviewRequest request);

    @POST("/api/v1/position/getPositions")
    Call<TradeItGetPositionsResponse> getPositions(@Body TradeItGetPositionsRequest request);

    @POST("/api/v1/order/getAllOrderStatus")
    Call<TradeItOrderStatusResponse> getAllOrderStatus(@Body TradeItGetAllOrderStatusRequest request);

    @POST("/api/v1/order/getSingleOrderStatus")
    Call<TradeItOrderStatusResponse> getSingleOrderStatus(@Body TradeItGetSingleOrderStatusRequest request);

    @POST("/api/v1/order/cancelOrder")
    Call<TradeItOrderStatusResponse> cancelOrder(@Body TradeItCancelOrderRequest request);

    @POST("/api/v1/account/getAllTransactionsHistory")
    Call<TradeItGetAllTransactionsHistoryResponse> getAllTransactionsHistory(@Body TradeItGetAllTransactionsHistoryRequest request);
}
