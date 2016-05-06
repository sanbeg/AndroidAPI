package it.trade.tradeitapi.API;

import it.trade.tradeitapi.model.TradeItEnvironment;
import it.trade.tradeitapi.model.TradeItOAuthLinkRequest;
import it.trade.tradeitapi.model.TradeItOAuthLinkResponse;
import it.trade.tradeitapi.model.TradeItOAuthUpdateRequest;
import it.trade.tradeitapi.model.TradeItRequestWithKey;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class TradeItBrokerLinkApiClient {
    private TradeItBrokerLinkApi tradeItBrokerLinkApi;

    public TradeItBrokerLinkApiClient(String apiKey, TradeItEnvironment environment) {
        TradeItRequestWithKey.API_KEY = apiKey;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(environment.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tradeItBrokerLinkApi = retrofit.create(TradeItBrokerLinkApi.class);
    }

    private TradeItBrokerLinkApiClient() {}

    public Call<TradeItOAuthLinkResponse> oAuthLink(@Body TradeItOAuthLinkRequest request) {
        return tradeItBrokerLinkApi.oAuthLink(request);
    }

    public Call<TradeItOAuthLinkResponse> oAuthUpdate(@Body TradeItOAuthUpdateRequest request) {
        return tradeItBrokerLinkApi.oAuthUpdate(request);
    }
}
