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

public class TradeItBrokerLinkAPIService {
    private TradeItBrokerLinkAPI tradeItBrokerLinkAPI;

    public TradeItBrokerLinkAPIService(String apiKey, TradeItEnvironment environment) {
        TradeItRequestWithKey.API_KEY = apiKey;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(environment.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tradeItBrokerLinkAPI = retrofit.create(TradeItBrokerLinkAPI.class);
    }

    private TradeItBrokerLinkAPIService() {}

    public Call<TradeItOAuthLinkResponse> oAuthLink(@Body TradeItOAuthLinkRequest request) {
        return tradeItBrokerLinkAPI.oAuthLink(request);
    }

    public Call<TradeItOAuthLinkResponse> oAuthUpdate(@Body TradeItOAuthUpdateRequest request) {
        return tradeItBrokerLinkAPI.oAuthUpdate(request);
    }
}
