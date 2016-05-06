package it.trade.tradeitapi.API;

import java.util.UUID;

import it.trade.tradeitapi.model.TradeItAnswerSecurityQuestionRequest;
import it.trade.tradeitapi.model.TradeItAuthenticateRequest;
import it.trade.tradeitapi.model.TradeItAuthenticateResponse;
import it.trade.tradeitapi.model.TradeItBrokerLink;
import it.trade.tradeitapi.model.TradeItCancelOrderRequest;
import it.trade.tradeitapi.model.TradeItGetAccountOverviewRequest;
import it.trade.tradeitapi.model.TradeItGetAccountOverviewResponse;
import it.trade.tradeitapi.model.TradeItGetAllOrderStatusRequest;
import it.trade.tradeitapi.model.TradeItGetAllTransactionsHistoryRequest;
import it.trade.tradeitapi.model.TradeItGetAllTransactionsHistoryResponse;
import it.trade.tradeitapi.model.TradeItGetPositionsRequest;
import it.trade.tradeitapi.model.TradeItGetPositionsResponse;
import it.trade.tradeitapi.model.TradeItGetSingleOrderStatusRequest;
import it.trade.tradeitapi.model.TradeItOrderStatusResponse;
import it.trade.tradeitapi.model.TradeItPlaceStockOrEtfOrderRequest;
import it.trade.tradeitapi.model.TradeItPlaceStockOrEtfOrderResponse;
import it.trade.tradeitapi.model.TradeItPreviewStockOrEtfOrderRequest;
import it.trade.tradeitapi.model.TradeItPreviewStockOrEtfOrderResponse;
import it.trade.tradeitapi.model.TradeItRequestWithKey;
import it.trade.tradeitapi.model.TradeItRequestWithSession;
import it.trade.tradeitapi.model.TradeItResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class TradeItApiClient {
    private TradeItApi tradeItApi;
    private String serverUuid;
    private TradeItBrokerLink tradeItBrokerLink;
    private String sessionToken;

    public TradeItApiClient(TradeItBrokerLink tradeItBrokerLink) {
        this.tradeItBrokerLink = tradeItBrokerLink;
        TradeItRequestWithKey.API_KEY = tradeItBrokerLink.apiKey;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(tradeItBrokerLink.environment.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tradeItApi = retrofit.create(TradeItApi.class);
    }

    private TradeItApiClient() {}

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    private void injectSession(TradeItRequestWithSession request) {
        request.sessionToken = sessionToken;
    }

    public Call<TradeItAuthenticateResponse> authenticate() {
        if (serverUuid == null) {
            serverUuid = UUID.randomUUID().toString();
        }

        TradeItAuthenticateRequest authenticateRequest = new TradeItAuthenticateRequest(tradeItBrokerLink);
        authenticateRequest.serverUuid = serverUuid;

        // TODO: GET TOKEN HERE AND SAVE IT SOMEHOW!!!!!!!
        // custom authenticate Call/Callback wrappers

        return tradeItApi.authenticate(authenticateRequest);
    }

    public Call<TradeItAuthenticateResponse> answerSecurityQuestion(TradeItAnswerSecurityQuestionRequest request) {
        request.serverUuid = serverUuid;
        injectSession(request);
        return tradeItApi.answerSecurityQuestion(request);
    }

    public Call<TradeItResponse> keepSessionAlive(TradeItRequestWithSession request) {
        injectSession(request);
        return tradeItApi.keepSessionAlive(request);
    }

    public Call<TradeItPreviewStockOrEtfOrderResponse> previewStockOrEtfOrder(TradeItPreviewStockOrEtfOrderRequest request) {
        injectSession(request);
        return tradeItApi.previewStockOrEtfOrder(request);
    }

    public Call<TradeItPlaceStockOrEtfOrderResponse> placeStockOrEtfOrder(TradeItPlaceStockOrEtfOrderRequest request) {
        injectSession(request);
        return tradeItApi.placeStockOrEtfOrder(request);
    }

    public Call<TradeItGetAccountOverviewResponse> getAccountOverview(TradeItGetAccountOverviewRequest request) {
        injectSession(request);
        return tradeItApi.getAccountOverview(request);
    }

    public Call<TradeItGetPositionsResponse> getPositions(TradeItGetPositionsRequest request) {
        injectSession(request);
        return tradeItApi.getPositions(request);
    }

    public Call<TradeItOrderStatusResponse> getAllOrderStatus(@Body TradeItGetAllOrderStatusRequest request) {
        injectSession(request);
        return tradeItApi.getAllOrderStatus(request);
    }

    public Call<TradeItOrderStatusResponse> getSingleOrderStatus(@Body TradeItGetSingleOrderStatusRequest request) {
        injectSession(request);
        return tradeItApi.getSingleOrderStatus(request);
    }

    public Call<TradeItOrderStatusResponse> cancelOrder(@Body TradeItCancelOrderRequest request) {
        injectSession(request);
        return tradeItApi.cancelOrder(request);
    }

    public Call<TradeItGetAllTransactionsHistoryResponse> getAllTransactionsHistory(@Body TradeItGetAllTransactionsHistoryRequest request) {
        injectSession(request);
        return tradeItApi.getAllTransactionsHistory(request);
    }
}
