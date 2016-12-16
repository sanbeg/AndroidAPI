package it.trade.tradeitapi.API;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import it.trade.tradeitapi.exception.TradeItDeleteLinkedLoginException;
import it.trade.tradeitapi.exception.TradeItKeystoreServiceCreateKeyException;
import it.trade.tradeitapi.exception.TradeItRetrieveLinkedLoginException;
import it.trade.tradeitapi.exception.TradeItSaveLinkedLoginException;
import it.trade.tradeitapi.exception.TradeItUpdateLinkedLoginException;
import it.trade.tradeitapi.model.TradeItAvailableBrokersResponse;
import it.trade.tradeitapi.model.TradeItEnvironment;
import it.trade.tradeitapi.model.TradeItLinkLoginRequest;
import it.trade.tradeitapi.model.TradeItLinkLoginResponse;
import it.trade.tradeitapi.model.TradeItLinkedLogin;
import it.trade.tradeitapi.model.TradeItOAuthAccessTokenRequest;
import it.trade.tradeitapi.model.TradeItOAuthAccessTokenResponse;
import it.trade.tradeitapi.model.TradeItOAuthLoginPopupUrlForMobileRequest;
import it.trade.tradeitapi.model.TradeItOAuthLoginPopupUrlForMobileResponse;
import it.trade.tradeitapi.model.TradeItOAuthLoginPopupUrlForTokenUpdateRequest;
import it.trade.tradeitapi.model.TradeItOAuthLoginPopupUrlForTokenUpdateResponse;
import it.trade.tradeitapi.model.TradeItRelinkLoginRequest;
import it.trade.tradeitapi.model.TradeItRequestWithKey;
import it.trade.tradeitapi.model.TradeItResponse;
import it.trade.tradeitapi.model.TradeItUnlinkLoginRequest;
import it.trade.tradeitapi.utils.TradeItKeystoreService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TradeItBrokerLinker {
    private static final String TRADE_IT_LINKED_BROKERS_ALIAS = "TRADE_IT_LINKED_BROKERS_ALIAS";
    public static final String TRADE_IT_SHARED_PREFS_KEY = "TRADE_IT_SHARED_PREFS_KEY";
    private static final String TRADE_IT_LINKED_BROKERS_KEY = "TRADE_IT_LINKED_BROKERS_KEY";
    private TradeItBrokerLinkApi tradeItBrokerLinkApi;
    private TradeItEnvironment environment;
    private static TradeItKeystoreService tradeItKeystoreService = new TradeItKeystoreService(TRADE_IT_LINKED_BROKERS_ALIAS);

    public TradeItBrokerLinker(String apiKey, TradeItEnvironment environment) {
        TradeItRequestWithKey.API_KEY = apiKey;
        this.environment = environment;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(environment.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.tradeItBrokerLinkApi = retrofit.create(TradeItBrokerLinkApi.class);
    }

    private TradeItBrokerLinker() {
    }

    public void getAvailableBrokers(Callback<TradeItAvailableBrokersResponse> callback) {
        TradeItRequestWithKey request = new TradeItRequestWithKey();
        tradeItBrokerLinkApi.getAvailableBrokers(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void getOAuthLoginPopupUrlForMobile(TradeItOAuthLoginPopupUrlForMobileRequest request, Callback<TradeItOAuthLoginPopupUrlForMobileResponse> callback) {
        tradeItBrokerLinkApi.getOAuthLoginPopupUrlForMobile(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void getOAuthLoginPopupUrlForTokenUpdate(TradeItOAuthLoginPopupUrlForTokenUpdateRequest request, Callback<TradeItOAuthLoginPopupUrlForTokenUpdateResponse> callback) {
        tradeItBrokerLinkApi.getOAuthLoginPopupURLForTokenUpdate(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void getOAuthAccessToken(TradeItOAuthAccessTokenRequest request, Callback<TradeItOAuthAccessTokenResponse> callback) {
        request.environment = environment;
        tradeItBrokerLinkApi.getOAuthAccessToken(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void linkBrokerAccount(TradeItLinkLoginRequest request, Callback<TradeItLinkLoginResponse> callback) {
        request.environment = environment;
        tradeItBrokerLinkApi.linkLogin(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void relinkBrokerAccount(TradeItRelinkLoginRequest request, Callback<TradeItLinkLoginResponse> callback) {
        tradeItBrokerLinkApi.relinkLogin(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void unlinkBrokerAccount(TradeItLinkedLogin linkedLogin, Callback<TradeItResponse> callback) {
        TradeItUnlinkLoginRequest request = new TradeItUnlinkLoginRequest(linkedLogin);
        tradeItBrokerLinkApi.unlinkLogin(request).enqueue(new PassthroughCallback<>(callback));
    }

    public static void initKeyStore(Context context) throws TradeItKeystoreServiceCreateKeyException {
            tradeItKeystoreService.createKeyIfNotExists(context);
    }

    public static void saveLinkedLogin(Context context, TradeItLinkedLogin linkedLogin, String accountLabel) throws TradeItSaveLinkedLoginException {
        try {
            linkedLogin.label = accountLabel;
            linkedLogin.uuid = UUID.randomUUID().toString();
            Gson gson = new Gson();
            String linkedLoginJson = gson.toJson(linkedLogin);

            SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
            Set<String> linkedLoginEncryptedJsonSet = sharedPreferences.getStringSet(TRADE_IT_LINKED_BROKERS_KEY, new HashSet<String>());
            String encryptedString = tradeItKeystoreService.encryptString(linkedLoginJson);
            linkedLoginEncryptedJsonSet.add(encryptedString);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(TRADE_IT_LINKED_BROKERS_KEY, linkedLoginEncryptedJsonSet);
            editor.commit();
        } catch (Exception e) {
            throw new TradeItSaveLinkedLoginException("Error saving linkedLogin for accountLabel: " + accountLabel, e);
        }
    }

    public static List<TradeItLinkedLogin> getLinkedLogins(Context context) throws TradeItRetrieveLinkedLoginException {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
            Set<String> linkedLoginEncryptedJsonSet =  sharedPreferences.getStringSet(TRADE_IT_LINKED_BROKERS_KEY, new HashSet<String>());

            List<TradeItLinkedLogin> linkedLoginList = new ArrayList<>();
            Gson gson = new Gson();

            for (String linkedLoginEncryptedJson : linkedLoginEncryptedJsonSet) {
                String linkedLoginJson = tradeItKeystoreService.decryptString(linkedLoginEncryptedJson);
                TradeItLinkedLogin linkedLogin = gson.fromJson(linkedLoginJson, TradeItLinkedLogin.class);
                linkedLoginList.add(linkedLogin);
            }

            return linkedLoginList;

        } catch (Exception e) {
            throw new TradeItRetrieveLinkedLoginException("Error getting linkedLogins ", e);
        }
    }

    public static void updateLinkedLogin(Context context, TradeItLinkedLogin linkedLogin) throws TradeItUpdateLinkedLoginException {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
            Set<String> linkedLoginEncryptedJsonSet =  sharedPreferences.getStringSet(TRADE_IT_LINKED_BROKERS_KEY, new HashSet<String>());
            Gson gson = new Gson();

            for (String linkedLoginEncryptedJson : linkedLoginEncryptedJsonSet) {
                String linkedLoginJson = tradeItKeystoreService.decryptString(linkedLoginEncryptedJson);
                if (linkedLoginJson.contains(linkedLogin.uuid)) {
                    linkedLoginJson = gson.toJson(linkedLogin);
                    String encryptedString = tradeItKeystoreService.encryptString(linkedLoginJson);
                    linkedLoginEncryptedJsonSet.remove(linkedLoginEncryptedJson);
                    linkedLoginEncryptedJsonSet.add(encryptedString);
                    break;
                }
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(TRADE_IT_LINKED_BROKERS_KEY, linkedLoginEncryptedJsonSet);
            editor.commit();
        } catch (Exception e) {
            throw new TradeItUpdateLinkedLoginException("Error updating linkedLogin " + linkedLogin.uuid, e);
        }
    }

    public static void deleteLinkedLogin(Context context, TradeItLinkedLogin linkedLogin) throws TradeItDeleteLinkedLoginException {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
            Set<String> linkedLoginEncryptedJsonSet =  sharedPreferences.getStringSet(TRADE_IT_LINKED_BROKERS_KEY, new HashSet<String>());

            for (String linkedLoginEncryptedJson : linkedLoginEncryptedJsonSet) {
                String linkedLoginJson = tradeItKeystoreService.decryptString(linkedLoginEncryptedJson);
                if (linkedLoginJson.contains(linkedLogin.uuid)) {
                    linkedLoginEncryptedJsonSet.remove(linkedLoginEncryptedJson);
                    break;
                }
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(TRADE_IT_LINKED_BROKERS_KEY, linkedLoginEncryptedJsonSet);
            editor.commit();
        } catch (Exception e) {
            throw new TradeItDeleteLinkedLoginException("Error deleting linkedLogin "+ linkedLogin.uuid, e);
        }
    }

    public static void deleteAllLinkedLogins(Context context) throws TradeItDeleteLinkedLoginException {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
            Set<String> linkedLoginEncryptedJsonSet =  sharedPreferences.getStringSet(TRADE_IT_LINKED_BROKERS_KEY, new HashSet<String>());

            linkedLoginEncryptedJsonSet.clear();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(TRADE_IT_LINKED_BROKERS_KEY, linkedLoginEncryptedJsonSet);
            editor.commit();
        } catch (Exception e) {
            throw new TradeItDeleteLinkedLoginException("Error deleting all linkedLogins ", e);
        }
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
