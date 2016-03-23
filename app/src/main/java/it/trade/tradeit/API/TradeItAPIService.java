package it.trade.tradeit.API;

import it.trade.tradeit.model.TradeItOAuthLinkRequest;
import it.trade.tradeit.model.TradeItOAuthLinkResponse;
import it.trade.tradeit.model.TradeItRequestWithKey;
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


        // TODO: REMOVE THIS LOGGING
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ems.qa.tradingticket.com/api/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tradeItAPI = retrofit.create(TradeItAPI.class);
    }

    private TradeItAPIService() {}

    public Call<TradeItOAuthLinkResponse> oAuthLink(@Body TradeItOAuthLinkRequest request) {
        return tradeItAPI.oAuthLink(request);
    }
}
