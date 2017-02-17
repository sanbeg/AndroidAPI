package it.trade.tradeitapi.API;

import java.util.UUID;

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
import it.trade.tradeitapi.model.TradeItLinkedLogin;
import it.trade.tradeitapi.model.TradeItOrderStatusResponse;
import it.trade.tradeitapi.model.TradeItPlaceStockOrEtfOrderRequest;
import it.trade.tradeitapi.model.TradeItPlaceStockOrEtfOrderResponse;
import it.trade.tradeitapi.model.TradeItPreviewStockOrEtfOrderRequest;
import it.trade.tradeitapi.model.TradeItPreviewStockOrEtfOrderResponse;
import it.trade.tradeitapi.model.TradeItRequestWithKey;
import it.trade.tradeitapi.model.TradeItRequestWithSession;
import it.trade.tradeitapi.model.TradeItResponse;
import it.trade.tradeitapi.model.TradeItResponseStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TradeItApiClient {
    private TradeItApi tradeItApi;
    private String serverUuid;
    private TradeItLinkedLogin tradeItLinkedLogin;
    private String sessionToken;

    public TradeItApiClient(TradeItLinkedLogin tradeItLinkedLogin, TradeItEnvironment environment) {
        this.tradeItLinkedLogin = tradeItLinkedLogin;
        TradeItRequestWithKey.API_KEY = tradeItLinkedLogin.apiKey;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(environment.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tradeItApi = retrofit.create(TradeItApi.class);
    }

    private TradeItApiClient() {}

    private void injectSession(TradeItRequestWithSession request) {
        request.sessionToken = sessionToken;
    }

    public TradeItLinkedLogin getTradeItLinkedLogin() {
        return this.tradeItLinkedLogin;
    }

    public void setTradeItLinkedLogin(TradeItLinkedLogin tradeItLinkedLogin) {
        this.tradeItLinkedLogin = tradeItLinkedLogin;
    }

    public void authenticate(final Callback<TradeItAuthenticateResponse> callback) {
        if (serverUuid == null) {
            serverUuid = UUID.randomUUID().toString();
        }

        TradeItAuthenticateRequest authenticateRequest = new TradeItAuthenticateRequest(tradeItLinkedLogin);
        authenticateRequest.serverUuid = serverUuid;

        tradeItApi.authenticate(authenticateRequest).enqueue(new Callback<TradeItAuthenticateResponse>() {
            public void onResponse(Call<TradeItAuthenticateResponse> call, Response<TradeItAuthenticateResponse> response) {
                if (response.isSuccessful()) {
                    TradeItAuthenticateResponse authenticateResponse = response.body();
                    if (authenticateResponse.status == TradeItResponseStatus.SUCCESS || authenticateResponse.status == TradeItResponseStatus.INFORMATION_NEEDED) {
                        sessionToken = authenticateResponse.sessionToken;
                    }
                }

                callback.onResponse(call, response);
            }

            public void onFailure(Call<TradeItAuthenticateResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void answerSecurityQuestion(TradeItAnswerSecurityQuestionRequest request, Callback<TradeItAuthenticateResponse> callback) {
        request.serverUuid = serverUuid;
        injectSession(request);
        tradeItApi.answerSecurityQuestion(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void keepSessionAlive(TradeItRequestWithSession request, Callback<TradeItResponse> callback) {
        injectSession(request);
        tradeItApi.keepSessionAlive(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void closeSession(TradeItRequestWithSession request, Callback<TradeItResponse> callback) {
        injectSession(request);
        tradeItApi.keepSessionAlive(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void previewStockOrEtfOrder(TradeItPreviewStockOrEtfOrderRequest request, Callback<TradeItPreviewStockOrEtfOrderResponse> callback) {
        injectSession(request);
        tradeItApi.previewStockOrEtfOrder(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void placeStockOrEtfOrder(TradeItPlaceStockOrEtfOrderRequest request, Callback<TradeItPlaceStockOrEtfOrderResponse> callback) {
        injectSession(request);
        tradeItApi.placeStockOrEtfOrder(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void getAccountOverview(TradeItGetAccountOverviewRequest request, Callback<TradeItGetAccountOverviewResponse> callback) {
        injectSession(request);
        tradeItApi.getAccountOverview(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void getPositions(TradeItGetPositionsRequest request, Callback<TradeItGetPositionsResponse> callback) {
        injectSession(request);
        tradeItApi.getPositions(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void getAllOrderStatus(TradeItGetAllOrderStatusRequest request, Callback<TradeItOrderStatusResponse> callback) {
        injectSession(request);
        tradeItApi.getAllOrderStatus(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void getSingleOrderStatus(TradeItGetSingleOrderStatusRequest request, Callback<TradeItOrderStatusResponse> callback) {
        injectSession(request);
        tradeItApi.getSingleOrderStatus(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void cancelOrder(TradeItCancelOrderRequest request, Callback<TradeItOrderStatusResponse> callback) {
        injectSession(request);
        tradeItApi.cancelOrder(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void getAllTransactionsHistory(TradeItGetAllTransactionsHistoryRequest request, final Callback<TradeItGetAllTransactionsHistoryResponse> callback) {
        injectSession(request);

        tradeItApi.getAllTransactionsHistory(request).enqueue(new PassthroughCallback<>(callback));
    }

    private class PassthroughCallback<T> implements Callback<T> {
        Callback<T> callback;

        PassthroughCallback(Callback<T> callback) {
            this.callback = callback;
        }

        public void onResponse(Call<T> call, Response<T> response) {
            callback.onResponse(call, response);
        }

        public void onFailure(Call<T> call, Throwable t) {
            callback.onFailure(call, t);
        }
    }
}