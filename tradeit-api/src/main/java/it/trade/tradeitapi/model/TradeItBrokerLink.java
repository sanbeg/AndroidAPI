package it.trade.tradeitapi.model;

import it.trade.tradeitapi.API.TradeItBrokerLinkApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
==========
Shared Preferences
==========
import android.content.Context;
import android.content.SharedPreferences;

SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsKey", Context.MODE_PRIVATE);
SharedPreferences.Editor editor = sharedPreferences.edit();
editor.putString("TOKEN KEY", "SECRET TOEKN ZOMG!!!!!");
editor.commit();

String token = sharedPreferences.getString("TOKEN KEY", "");
*/

public class TradeItBrokerLink {
    public String broker;
    public String userToken = "";
    public String userId = "";
    public TradeItEnvironment environment = TradeItEnvironment.QA;
    public String apiKey = "";

    private TradeItBrokerLinkApiClient brokerLinkAPIClient;
    private String username;
    private String password;

    public TradeItBrokerLink(String broker, String username, String password, String apiKey, TradeItEnvironment environment) {
        this.broker = broker;
        this.username = username;
        this.password = password;
        this.apiKey = apiKey;
        this.environment = environment;
        this.brokerLinkAPIClient = new TradeItBrokerLinkApiClient(apiKey, environment);
    }

    private TradeItBrokerLink() {}

//    public void saveLink() {
//
//    }
//
//    public static List<TradeItBrokerLink> getBrokerLinkList() {
//
//    }
//
//    public void updateLink() {
//
//    }


    public void link(final TradeItBrokerLinkCallback callback) {
        TradeItOAuthLinkRequest oAuthLinkRequest = new TradeItOAuthLinkRequest(username, password, broker, apiKey);
        Call<TradeItOAuthLinkResponse> call = brokerLinkAPIClient.oAuthLink(oAuthLinkRequest);

        final TradeItBrokerLink self = this;

        call.enqueue(new Callback<TradeItOAuthLinkResponse>() {
            @Override
            public void onResponse(Call<TradeItOAuthLinkResponse> call, Response<TradeItOAuthLinkResponse> response) {
                if (response.isSuccessful()) {
                    TradeItOAuthLinkResponse oAuthLinkResponse = response.body();

                    if (oAuthLinkResponse.status.equals("SUCCESS")) {
                        userId = oAuthLinkResponse.userId;
                        userToken = oAuthLinkResponse.userToken;
                        callback.onLinkSuccess(self);
                    } else {
                        callback.onLinkFailed(oAuthLinkResponse);
                    }
                } else {
                    TradeItOAuthLinkResponse oAuthLinkResponse = new TradeItOAuthLinkResponse();
                    oAuthLinkResponse.status = "ERROR";
                    oAuthLinkResponse.code = 100;
                    oAuthLinkResponse.longMessages.add(response.message());

                    callback.onLinkFailed(oAuthLinkResponse);
                }
            }

            @Override
            public void onFailure(Call<TradeItOAuthLinkResponse> call, Throwable t) {
                TradeItOAuthLinkResponse oAuthLinkResponse = new TradeItOAuthLinkResponse();
                oAuthLinkResponse.status = "ERROR";
                oAuthLinkResponse.code = 100;
                oAuthLinkResponse.longMessages.add(t.getMessage());

                callback.onLinkFailed(oAuthLinkResponse);
            }
        });
    }

    public interface TradeItBrokerLinkCallback {
        void onLinkSuccess(TradeItBrokerLink successfulBrokerLink);
        void onLinkFailed(TradeItOAuthLinkResponse response);
    }

    @Override
    public String toString() {
        return "TradeItBrokerLink{" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                ", environment=" + environment +
                ", apiKey='" + apiKey + '\'' +
                ", broker='" + broker + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
