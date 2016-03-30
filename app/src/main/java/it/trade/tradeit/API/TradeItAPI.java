package it.trade.tradeit.API;

import it.trade.tradeit.model.TradeItAnswerSecurityQuestionRequest;
import it.trade.tradeit.model.TradeItAuthenticateRequest;
import it.trade.tradeit.model.TradeItAuthenticateResponse;
import it.trade.tradeit.model.TradeItCancelOrderRequest;
import it.trade.tradeit.model.TradeItGetAccountOverviewRequest;
import it.trade.tradeit.model.TradeItGetAccountOverviewResponse;
import it.trade.tradeit.model.TradeItGetAllOrderStatusRequest;
import it.trade.tradeit.model.TradeItGetAllTransactionsHistoryRequest;
import it.trade.tradeit.model.TradeItGetAllTransactionsHistoryResponse;
import it.trade.tradeit.model.TradeItGetPositionsRequest;
import it.trade.tradeit.model.TradeItGetPositionsResponse;
import it.trade.tradeit.model.TradeItGetSingleOrderStatusRequest;
import it.trade.tradeit.model.TradeItOAuthLinkRequest;
import it.trade.tradeit.model.TradeItOAuthLinkResponse;
import it.trade.tradeit.model.TradeItOAuthUpdateRequest;
import it.trade.tradeit.model.TradeItOrderStatusResponse;
import it.trade.tradeit.model.TradeItPlaceStockOrEtfOrderRequest;
import it.trade.tradeit.model.TradeItPlaceStockOrEtfOrderResponse;
import it.trade.tradeit.model.TradeItPreviewStockOrEtfOrderRequest;
import it.trade.tradeit.model.TradeItPreviewStockOrEtfOrderResponse;
import it.trade.tradeit.model.TradeItRequestWithSession;
import it.trade.tradeit.model.TradeItResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TradeItAPI {
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
