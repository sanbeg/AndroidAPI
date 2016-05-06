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

public class TradeItAPIService {
    private TradeItAPI tradeItAPI;
    private String serverUuid;
    private TradeItBrokerLink tradeItBrokerLink;
    private String sessionToken;

    public TradeItAPIService(TradeItBrokerLink tradeItBrokerLink) {
        this.tradeItBrokerLink = tradeItBrokerLink;
        TradeItRequestWithKey.API_KEY = tradeItBrokerLink.apiKey;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(tradeItBrokerLink.environment.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tradeItAPI = retrofit.create(TradeItAPI.class);
    }

    private TradeItAPIService() {}

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

        return tradeItAPI.authenticate(authenticateRequest);
    }

    public Call<TradeItAuthenticateResponse> answerSecurityQuestion(TradeItAnswerSecurityQuestionRequest request) {
        request.serverUuid = serverUuid;
        injectSession(request);
        return tradeItAPI.answerSecurityQuestion(request);
    }

    public Call<TradeItResponse> keepSessionAlive(TradeItRequestWithSession request) {
        injectSession(request);
        return tradeItAPI.keepSessionAlive(request);
    }

    public Call<TradeItPreviewStockOrEtfOrderResponse> previewStockOrEtfOrder(TradeItPreviewStockOrEtfOrderRequest request) {
        injectSession(request);
        return tradeItAPI.previewStockOrEtfOrder(request);
    }

    public Call<TradeItPlaceStockOrEtfOrderResponse> placeStockOrEtfOrder(TradeItPlaceStockOrEtfOrderRequest request) {
        injectSession(request);
        return tradeItAPI.placeStockOrEtfOrder(request);
    }

    public Call<TradeItGetAccountOverviewResponse> getAccountOverview(TradeItGetAccountOverviewRequest request) {
        injectSession(request);
        return tradeItAPI.getAccountOverview(request);
    }

    public Call<TradeItGetPositionsResponse> getPositions(TradeItGetPositionsRequest request) {
        injectSession(request);
        return tradeItAPI.getPositions(request);
    }

    public Call<TradeItOrderStatusResponse> getAllOrderStatus(@Body TradeItGetAllOrderStatusRequest request) {
        injectSession(request);
        return tradeItAPI.getAllOrderStatus(request);
    }

    public Call<TradeItOrderStatusResponse> getSingleOrderStatus(@Body TradeItGetSingleOrderStatusRequest request) {
        injectSession(request);
        return tradeItAPI.getSingleOrderStatus(request);
    }

    public Call<TradeItOrderStatusResponse> cancelOrder(@Body TradeItCancelOrderRequest request) {
        injectSession(request);
        return tradeItAPI.cancelOrder(request);
    }

    public Call<TradeItGetAllTransactionsHistoryResponse> getAllTransactionsHistory(@Body TradeItGetAllTransactionsHistoryRequest request) {
        injectSession(request);
        return tradeItAPI.getAllTransactionsHistory(request);
    }
}
