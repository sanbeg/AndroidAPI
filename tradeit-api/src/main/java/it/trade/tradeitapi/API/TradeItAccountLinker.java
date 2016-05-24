package it.trade.tradeitapi.API;

import it.trade.tradeitapi.model.TradeItAvailableBrokersResponse;
import it.trade.tradeitapi.model.TradeItEnvironment;
import it.trade.tradeitapi.model.TradeItLinkAccountRequest;
import it.trade.tradeitapi.model.TradeItLinkAccountResponse;
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

/////////////////////////////////////////////////////////////

//    private static final String TRADE_IT_SHARED_PREFS_KEY = "TRADE_IT_SHARED_PREFS_KEY";
//    private static final String TRADE_IT_LINKED_ACCOUNTS_KEY = "TRADE_IT_LINKED_ACCOUNTS_KEY";
//
//    public static void saveLinkedAccount(Context context, TradeItLinkedAccount linkedAccount, String accountLabel) {
//        linkedAccount.label = accountLabel;
//
//        Gson gson = new Gson();
//        String linkedAccountJson = gson.toJson(linkedAccount);
//
//        // Generate UUID for KeyStore
//        // Add UUID to TradeItLinkedAccount (may have to add this to the class)
//        // Save linkedAccountJson into KeyStore using UUID as key
//
//        SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
//        Set<String> linkedAccountJsonSet =  sharedPreferences.getStringSet(TRADE_IT_LINKED_ACCOUNTS_KEY, new HashSet<String>());
//
//        // If the linkedAccountJson is stored in the KeyStore then you should just save the UUID in the SharedPreferences
//        linkedAccountJsonSet.add(linkedAccountJson);
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putStringSet(TRADE_IT_LINKED_ACCOUNTS_KEY, linkedAccountJsonSet);
//        editor.commit();
//    }
//
//    public static List<TradeItLinkedAccount> getLinkedAccounts(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
//        Set<String> linkedAccountJsonSet =  sharedPreferences.getStringSet(TRADE_IT_LINKED_ACCOUNTS_KEY, new HashSet<String>());
//
//        List<TradeItLinkedAccount> linkedAccountList = new ArrayList<>();
//
//        Gson gson = new Gson();
//
//        for (String linkedAccountJson : linkedAccountJsonSet) {
//            TradeItLinkedAccount linkedAccount = gson.fromJson(linkedAccountJson, TradeItLinkedAccount.class);
//            linkedAccountList.add(linkedAccount);
//        }
//
//        return linkedAccountList;
//    }
//
//    public static void updateLinkedAccount(Context context, TradeItLinkedAccount linkedAccount) {
//        // Serialize the TradeItLinkedAccount with gson.toJson
//        // Replace the old serialized JSON in the KeyStore with the new JSON using the UUID
//    }
//
//    public static void deleteLinkedAccount(Context context, TradeItLinkedAccount linkedAccount) {
//        // Delete the KeyStore value for the UUID of the linkedAccount.
//        // Delete the SharedPreferences value for the UUID of the linkedAccount:  linkedAccountJsonSet.remove(linkedAccount.uuid)
//    }

/////////////////////////////////////////////////////////////

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
