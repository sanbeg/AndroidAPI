package it.trade.tradeit.API;

import it.trade.tradeit.model.TradeItOAuthLinkRequest;
import it.trade.tradeit.model.TradeItOAuthLinkResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TradeItAPI {
    @POST("/api/v1/user/oAuthLink")
    Call<TradeItOAuthLinkResponse> oAuthLink(@Body TradeItOAuthLinkRequest request);


//    private static final String BASE_URL = "https://ems.qa.tradingticket.com/api/v1";
//    public static String apiKey;
//
//    public TradeItAPI(String apiKey) {
//        this.apiKey = apiKey;
//    }
//
//    private static AsyncHttpClient client = new AsyncHttpClient();
//
//    public static void oAuthLink(String broker,
//                                 String username,
//                                 String password,
//                                 JsonHttpResponseHandler responseHandler) {
//
//        String url = getAbsoluteUrl("/user/oAuthLink");
//
//        RequestParams requestParams = new RequestParams();
//        requestParams.put("apiKey", apiKey);
//        requestParams.put("id", username);
//        requestParams.put("password", password);
//        requestParams.put("broker", broker);
//        requestParams.put("broker", broker);
//
//        client.get(url, requestParams, responseHandler);
//    }
//
//    public static void authenticate(String userToken, String userId) {
//
//    }
//
//    private static String getAbsoluteUrl(String relativeUrl) {
//        return BASE_URL + relativeUrl;
//    }
}
