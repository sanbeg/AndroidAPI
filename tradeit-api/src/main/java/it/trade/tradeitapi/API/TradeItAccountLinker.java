package it.trade.tradeitapi.API;

import it.trade.tradeitapi.model.TradeItLinkAccountResponse;
import it.trade.tradeitapi.model.TradeItAvailableBrokersResponse;
import it.trade.tradeitapi.model.TradeItLinkAccountRequest;
import it.trade.tradeitapi.model.TradeItEnvironment;
import it.trade.tradeitapi.model.TradeItLinkedAccount;
import it.trade.tradeitapi.model.TradeItRelinkAccountRequest;
import it.trade.tradeitapi.model.TradeItRequestWithKey;
import it.trade.tradeitapi.model.TradeItResponse;
import it.trade.tradeitapi.model.TradeItUnlinkAccountRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TradeItAccountLinker {
    private static final String TRADE_IT_SHARED_PREFS_KEY = "TRADE_IT_SHARED_PREFS_KEY";
    private static final String TRADE_IT_LINKED_ACCOUNTS_KEY = "TRADE_IT_LINKED_ACCOUNTS_KEY";
    private TradeItAccountLinkApi tradeItAccountLinkApi;
    private TradeItEnvironment environment;

    public TradeItAccountLinker(String apiKey, TradeItEnvironment environment) {
        TradeItRequestWithKey.API_KEY = apiKey;
        this.environment = environment;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(environment.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tradeItAccountLinkApi = retrofit.create(TradeItAccountLinkApi.class);
    }

    private TradeItAccountLinker() {
    }

    public void getAvailableBrokers(Callback<TradeItAvailableBrokersResponse> callback) {
        TradeItRequestWithKey request = new TradeItRequestWithKey();
        tradeItAccountLinkApi.getAvailableBrokers(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void linkBrokerAccount(TradeItLinkAccountRequest request, Callback<TradeItLinkAccountResponse> callback) {
        request.environment = environment;
        tradeItAccountLinkApi.linkAccount(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void relinkBrokerAccount(TradeItRelinkAccountRequest request, Callback<TradeItLinkAccountResponse> callback) {
        tradeItAccountLinkApi.relinkAccount(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void unlinkBrokerAccount(TradeItLinkedAccount linkedAccount, Callback<TradeItResponse> callback) {
        TradeItUnlinkAccountRequest request = new TradeItUnlinkAccountRequest(linkedAccount);
        tradeItAccountLinkApi.unlinkAccount(request).enqueue(new PassthroughCallback<>(callback));
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
