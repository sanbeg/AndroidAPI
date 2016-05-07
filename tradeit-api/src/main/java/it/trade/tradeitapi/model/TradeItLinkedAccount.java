package it.trade.tradeitapi.model;

import com.google.gson.annotations.SerializedName;

/*
==========
Shared Preferences
==========
import android.content.Context;
import android.content.SharedPreferences;


*/

public class TradeItLinkedAccount {
    @SerializedName("label")
    public String label;

    @SerializedName("broker")
    public String broker;

    @SerializedName("userToken")
    public String userToken = "";

    @SerializedName("userId")
    public String userId = "";

    @SerializedName("")
    public TradeItEnvironment environment = TradeItEnvironment.QA;

    @SerializedName("")
    public String apiKey = "";

    private static final String TRADE_IT_PREFS_KEY = "TRADE_IT_PREFS_KEY";

    public TradeItLinkedAccount(TradeItOAuthLinkRequest oAuthLinkRequest, TradeItOAuthLinkResponse oAuthLinkResponse) {
        this.broker = oAuthLinkRequest.broker;
        this.apiKey = oAuthLinkRequest.apiKey;
        this.environment = oAuthLinkRequest.environment;
        this.userId = oAuthLinkResponse.userId;
        this.userToken = oAuthLinkResponse.userToken;
    }

    public TradeItLinkedAccount(String broker, String apiKey, TradeItEnvironment environment, String userToken, String userId) {
        this.broker = broker;
        this.apiKey = apiKey;
        this.environment = environment;
        this.userId = userId;
        this.userToken = userToken;
    }

    private TradeItLinkedAccount() {}

//    public void save(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_PREFS_KEY, Context.MODE_PRIVATE);
//
//        Gson gson = new Gson();
//        String linkedAccountJson = gson.toJson(this);
//
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsKey", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("TOKEN KEY", "SECRET TOEKN ZOMG!!!!!");
//        editor.commit();
//    }
//
//    public static List<TradeItLinkedAccount> getLinkedAccounts() {
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsKey", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("TOKEN KEY", "SECRET TOEKN ZOMG!!!!!");
//        editor.commit();
//
//        String token = sharedPreferences.getString("TOKEN KEY", "");
//    }

//    public void updateLink(String username, String password) {
//
//    }


    @Override
    public String toString() {
        return "TradeItLinkedAccount{" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                ", environment=" + environment +
                ", apiKey='" + apiKey + '\'' +
                ", broker='" + broker + '\'' +
                '}';
    }
}
