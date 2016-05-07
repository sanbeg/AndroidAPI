package it.trade.tradeitapi.API;

import it.trade.tradeitapi.model.TradeItEnvironment;
import it.trade.tradeitapi.model.TradeItOAuthLinkRequest;
import it.trade.tradeitapi.model.TradeItOAuthLinkResponse;
import it.trade.tradeitapi.model.TradeItOAuthUpdateRequest;
import it.trade.tradeitapi.model.TradeItRequestWithKey;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TradeItAccountLinker {
    private TradeItAccountLinkApi tradeItAccountLinkApi;
//    private String apiKey;
    private TradeItEnvironment environment;

    public TradeItAccountLinker(String apiKey, TradeItEnvironment environment) {
//        this.apiKey = apiKey;
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

    public void linkBrokerAccount(TradeItOAuthLinkRequest request, final Callback<TradeItOAuthLinkResponse> callback) {
//        request.apiKey = apiKey;
        request.environment = environment;
        tradeItAccountLinkApi.oAuthLink(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void relinkBrokerAccount(TradeItOAuthUpdateRequest request, Callback<TradeItOAuthLinkResponse> callback) {
//        request.apiKey = apiKey;
        tradeItAccountLinkApi.oAuthUpdate(request).enqueue(new PassthroughCallback<>(callback));
    }

//    - (void) getAvailableBrokersWithCompletionBlock:(void (^)(NSArray *)) completionBlock {
//        TradeItBrokerListRequest * brokerListRequest = [[TradeItBrokerListRequest alloc] initWithApiKey:self.apiKey];
//        NSMutableURLRequest *request = buildJsonRequest(brokerListRequest, @"preference/getStocksOrEtfsBrokerList", self.environment);
//
//        [self sendEMSRequest:request withCompletionBlock:^(TradeItResult * tradeItResult, NSMutableString * jsonResponse) {
//
//            if([tradeItResult isKindOfClass: [TradeItErrorResult class]]){
//                NSLog(@"Could not fetch broker list, got error result%@ ", tradeItResult);
//            }
//            else if ([tradeItResult.status isEqual:@"SUCCESS"]){
//                TradeItBrokerListResult* successResult = (TradeItBrokerListResult*) buildResult([TradeItBrokerListResult alloc],jsonResponse);
//
//                completionBlock(successResult.brokerList);
//
//                return;
//            }
//            else if ([tradeItResult.status isEqual:@"ERROR"]){
//                NSLog(@"Could not fetch broker list, got error result%@ ", tradeItResult);
//            }
//
//            completionBlock(nil);
//        }];
//    }

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
