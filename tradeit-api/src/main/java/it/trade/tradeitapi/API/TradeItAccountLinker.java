package it.trade.tradeitapi.API;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import it.trade.tradeitapi.exception.TradeItDeleteLinkedAccountException;
import it.trade.tradeitapi.exception.TradeItKeystoreServiceCreateKeyException;
import it.trade.tradeitapi.exception.TradeItRetrieveLinkedAccountException;
import it.trade.tradeitapi.exception.TradeItSaveLinkedAccountException;
import it.trade.tradeitapi.exception.TradeItUpdateLinkedAccountException;
import it.trade.tradeitapi.model.TradeItAvailableBrokersResponse;
import it.trade.tradeitapi.model.TradeItEnvironment;
import it.trade.tradeitapi.model.TradeItLinkAccountRequest;
import it.trade.tradeitapi.model.TradeItLinkAccountResponse;
import it.trade.tradeitapi.model.TradeItLinkedAccount;
import it.trade.tradeitapi.model.TradeItRelinkAccountRequest;
import it.trade.tradeitapi.model.TradeItRequestWithKey;
import it.trade.tradeitapi.model.TradeItResponse;
import it.trade.tradeitapi.model.TradeItUnlinkAccountRequest;
import it.trade.tradeitapi.utils.TradeItKeystoreService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TradeItAccountLinker {
    private static final String TRADE_IT_LINKED_ACCOUNTS_ALIAS = "TRADE_IT_LINKED_ACCOUNTS_ALIAS";
    private static final String TRADE_IT_SHARED_PREFS_KEY = "TRADE_IT_SHARED_PREFS_KEY";
    private static final String TRADE_IT_LINKED_ACCOUNTS_KEY = "TRADE_IT_LINKED_ACCOUNTS_KEY";
    private TradeItAccountLinkApi tradeItAccountLinkApi;
    private TradeItEnvironment environment;
    private static TradeItKeystoreService tradeItKeystoreService = new TradeItKeystoreService(TRADE_IT_LINKED_ACCOUNTS_ALIAS);

    public TradeItAccountLinker(String apiKey, TradeItEnvironment environment) {
        TradeItRequestWithKey.API_KEY = apiKey;
        this.environment = environment;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(environment.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.tradeItAccountLinkApi = retrofit.create(TradeItAccountLinkApi.class);
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

    public static void initKeyStore(Context context) throws TradeItKeystoreServiceCreateKeyException {
            tradeItKeystoreService.createKeyIfNotExists(context);
    }

    public static void saveLinkedAccount(Context context, TradeItLinkedAccount linkedAccount, String accountLabel) throws TradeItSaveLinkedAccountException {
        try {
            linkedAccount.label = accountLabel;
            linkedAccount.uuid = UUID.randomUUID().toString();
            Gson gson = new Gson();
            String linkedAccountJson = gson.toJson(linkedAccount);

            SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
            Set<String> linkedAccountEncryptedJsonSet = sharedPreferences.getStringSet(TRADE_IT_LINKED_ACCOUNTS_KEY, new HashSet<String>());
            String encryptedString = tradeItKeystoreService.encryptString(linkedAccountJson);
            linkedAccountEncryptedJsonSet.add(encryptedString);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(TRADE_IT_LINKED_ACCOUNTS_KEY, linkedAccountEncryptedJsonSet);
            editor.commit();
        } catch (Exception e) {
            throw new TradeItSaveLinkedAccountException("Error saving linkedAccount for accountLabel: " + accountLabel, e);
        }
    }

    public static List<TradeItLinkedAccount> getLinkedAccounts(Context context) throws TradeItRetrieveLinkedAccountException {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
            Set<String> linkedAccountEncryptedJsonSet =  sharedPreferences.getStringSet(TRADE_IT_LINKED_ACCOUNTS_KEY, new HashSet<String>());

            List<TradeItLinkedAccount> linkedAccountList = new ArrayList<>();
            Gson gson = new Gson();

            for (String linkedAccountEncryptedJson : linkedAccountEncryptedJsonSet) {
                String linkedAccountJson = tradeItKeystoreService.decryptString(linkedAccountEncryptedJson);
                TradeItLinkedAccount linkedAccount = gson.fromJson(linkedAccountJson, TradeItLinkedAccount.class);
                linkedAccountList.add(linkedAccount);
            }

            return linkedAccountList;

        } catch (Exception e) {
            throw new TradeItRetrieveLinkedAccountException("Error getting linkedAccounts ", e);
        }
    }

    public static void updateLinkedAccount(Context context, TradeItLinkedAccount linkedAccount) throws TradeItUpdateLinkedAccountException {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
            Set<String> linkedAccountEncryptedJsonSet =  sharedPreferences.getStringSet(TRADE_IT_LINKED_ACCOUNTS_KEY, new HashSet<String>());
            Gson gson = new Gson();

            for (String linkedAccountEncryptedJson : linkedAccountEncryptedJsonSet) {
                String linkedAccountJson = tradeItKeystoreService.decryptString(linkedAccountEncryptedJson);
                if (linkedAccountJson.contains(linkedAccount.uuid)) {
                    linkedAccountJson = gson.toJson(linkedAccount);
                    String encryptedString = tradeItKeystoreService.encryptString(linkedAccountJson);
                    linkedAccountEncryptedJsonSet.remove(linkedAccountEncryptedJson);
                    linkedAccountEncryptedJsonSet.add(encryptedString);
                    break;
                }
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(TRADE_IT_LINKED_ACCOUNTS_KEY, linkedAccountEncryptedJsonSet);
            editor.commit();
        } catch (Exception e) {
            throw new TradeItUpdateLinkedAccountException("Error updating linkedAccount " + linkedAccount.uuid, e);
        }
    }

    public static void deleteLinkedAccount(Context context, TradeItLinkedAccount linkedAccount) throws TradeItDeleteLinkedAccountException {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
            Set<String> linkedAccountEncryptedJsonSet =  sharedPreferences.getStringSet(TRADE_IT_LINKED_ACCOUNTS_KEY, new HashSet<String>());

            for (String linkedAccountEncryptedJson : linkedAccountEncryptedJsonSet) {
                String linkedAccountJson = tradeItKeystoreService.decryptString(linkedAccountEncryptedJson);
                if (linkedAccountJson.contains(linkedAccount.uuid)) {
                    linkedAccountEncryptedJsonSet.remove(linkedAccountEncryptedJson);
                    break;
                }
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(TRADE_IT_LINKED_ACCOUNTS_KEY, linkedAccountEncryptedJsonSet);
            editor.commit();
        } catch (Exception e) {
            throw new TradeItDeleteLinkedAccountException("Error deleting linkedAccount "+ linkedAccount.uuid, e);
        }
    }

    public static void deleteAllLinkedAccounts(Context context) throws TradeItDeleteLinkedAccountException {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(TRADE_IT_SHARED_PREFS_KEY, Context.MODE_PRIVATE);
            Set<String> linkedAccountEncryptedJsonSet =  sharedPreferences.getStringSet(TRADE_IT_LINKED_ACCOUNTS_KEY, new HashSet<String>());

            linkedAccountEncryptedJsonSet.clear();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(TRADE_IT_LINKED_ACCOUNTS_KEY, linkedAccountEncryptedJsonSet);
            editor.commit();
        } catch (Exception e) {
            throw new TradeItDeleteLinkedAccountException("Error deleting all linkedAccounts ", e);
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
