package it.trade.tradeitapi.API;

import it.trade.tradeitapi.model.TradeItAnswerSecurityQuestionRequest;
import it.trade.tradeitapi.model.TradeItAuthenticateRequest;
import it.trade.tradeitapi.model.TradeItAuthenticateResponse;
import it.trade.tradeitapi.model.TradeItCancelOrderRequest;
import it.trade.tradeitapi.model.TradeItEnvironment;
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
import it.trade.tradeitapi.model.TradeItRequestWithKey;
import it.trade.tradeitapi.model.TradeItRequestWithSession;
import it.trade.tradeitapi.model.TradeItResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class TradeItAPIService implements TradeItAPI {
    private TradeItAPI tradeItAPI;

    public TradeItAPIService(String apiKey, TradeItEnvironment environment) {
        TradeItRequestWithKey.API_KEY = apiKey;

        // TODO: TURN OFF LOGGING
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(environment.getBaseUrl())
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
