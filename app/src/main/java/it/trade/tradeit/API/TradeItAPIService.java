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
import it.trade.tradeit.model.TradeItRequestWithKey;
import it.trade.tradeit.model.TradeItRequestWithSession;
import it.trade.tradeit.model.TradeItResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class TradeItAPIService implements TradeItAPI {
    public static TradeItAPI tradeItAPI;

    public TradeItAPIService(String apiKey) {
        TradeItRequestWithKey.API_KEY = apiKey;

        // TODO: TURN OFF LOGGING
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ems.qa.tradingticket.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tradeItAPI = retrofit.create(TradeItAPI.class);
    }

    private TradeItAPIService() {}

    public Call<TradeItOAuthLinkResponse> oAuthLink(@Body TradeItOAuthLinkRequest request) {
        return tradeItAPI.oAuthLink(request);
    }

    public Call<TradeItAuthenticateResponse> authenticate(@Body TradeItAuthenticateRequest request) {
        return tradeItAPI.authenticate(request);
    }

    public Call<TradeItAuthenticateResponse> answerSecurityQuestion(@Body TradeItAnswerSecurityQuestionRequest request) {
        return tradeItAPI.answerSecurityQuestion(request);
    }

    public Call<TradeItOAuthLinkResponse> oAuthUpdate(@Body TradeItOAuthUpdateRequest request) {
        return tradeItAPI.oAuthUpdate(request);
    }

    public Call<TradeItResponse> keepSessionAlive(@Body TradeItRequestWithSession request) {
        return tradeItAPI.keepSessionAlive(request);
    }

    public Call<TradeItPreviewStockOrEtfOrderResponse> previewStockOrEtfOrder(@Body TradeItPreviewStockOrEtfOrderRequest request) {
        return tradeItAPI.previewStockOrEtfOrder(request);
    }

    public Call<TradeItPlaceStockOrEtfOrderResponse> placeStockOrEtfOrder(@Body TradeItPlaceStockOrEtfOrderRequest request) {
        return tradeItAPI.placeStockOrEtfOrder(request);
    }

    public Call<TradeItGetAccountOverviewResponse> getAccountOverview(@Body TradeItGetAccountOverviewRequest request) {
        return tradeItAPI.getAccountOverview(request);
    }

    public Call<TradeItGetPositionsResponse> getPositions(@Body TradeItGetPositionsRequest request) {
        return tradeItAPI.getPositions(request);
    }

    public Call<TradeItOrderStatusResponse> getAllOrderStatus(@Body TradeItGetAllOrderStatusRequest request) {
        return tradeItAPI.getAllOrderStatus(request);
    }

    public Call<TradeItOrderStatusResponse> getSingleOrderStatus(@Body TradeItGetSingleOrderStatusRequest request) {
        return tradeItAPI.getSingleOrderStatus(request);
    }

    public Call<TradeItOrderStatusResponse> cancelOrder(@Body TradeItCancelOrderRequest request) {
        return tradeItAPI.cancelOrder(request);
    }

    public Call<TradeItGetAllTransactionsHistoryResponse> getAllTransactionsHistory(@Body TradeItGetAllTransactionsHistoryRequest request) {
        return tradeItAPI.getAllTransactionsHistory(request);
    }
}
