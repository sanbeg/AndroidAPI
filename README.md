#Android Trade.it API Client
Android library that wraps the Trade.it API using the [Retrofit 2](http://square.github.io/retrofit/) library.

Detailed API documentation can be found here: https://www.trade.it/api.

The JCenter repo can be found here: https://bintray.com/tradeit/maven/tradeit-android-api/view

For example usage, see the example app included with the library.  It is ready to build and load onto a device or the simulator and has basic functionality to link a broker account, authenticate, make a trade, and fetch account details like balances and positions.

#Quick Start
To link a user's broker account and to manage linked logins, use the `TradeItBrokerLinker`:
```Java
// In order to initialize the broker linker, obtain an API key from Trade.it, or test with "tradeit-test-api-key"
TradeItBrokerLinker brokerLinker = new TradeItBrokerLinker("tradeit-test-api-key", TradeItEnvironment.QA);
```
Query which brokers are available for your key:
```Java
brokerLinker.getAvailableBrokers(new Callback<TradeItAvailableBrokersResponse>() {
  public void onResponse(Call<TradeItAvailableBrokersResponse> call, Response<TradeItAvailableBrokersResponse> response) {
    // Check that the http request was successful (status code 2XX) before proceeding
    if (response.isSuccessful()) {
      TradeItAvailableBrokersResponse brokersResponse = response.body();

      // Check the status of the API response
      if (linkLoginResponse.status == TradeItResponseStatus.SUCCESS) {
        // Let user choose a broker based on the the brokers in brokersResponse.brokerList
      }
    } else { // Server returned non-2XX status (e.g. 404, 503, etc.) }
  }
  
  public void onFailure(Call call, Throwable t) {
    // Something went wrong trying to send the request, check that the device is connected to the internet...
  }
});
```
Link (authorize) a user's broker account: There are several steps to follow:
```Java
// Create a request to get the Popup URL
final TradeItOAuthLoginPopupUrlForMobileRequest request = new TradeItOAuthLoginPopupUrlForMobileRequest(broker, deepLinkCallback);
brokerLinker.getOAuthLoginPopupUrlForMobile(request, new Callback<TradeItOAuthLoginPopupUrlForMobileResponse>() {
    public void onResponse(Call<TradeItOAuthLoginPopupUrlForMobileResponse> call, Response<TradeItOAuthLoginPopupUrlForMobileResponse> response) {
        if (response.isSuccessful()) {
              TradeItOAuthLoginPopupUrlForMobileResponse oAuthLoginPopupUrlForMobileResponse = response.body(); 
              if (oAuthLoginPopupUrlForMobileResponse.status == TradeItResponseStatus.SUCCESS) {
                // You have to redirect the user to this url
                String oAuthURL = response.body().oAuthURL
              }
        } else { // Server returned non-2XX status (e.g. 404, 503, etc.) }
    }
    
    public void onFailure(Call call, Throwable t) {
          // Something went wrong trying to send the request, check that the device is connected to the internet...
    }
});
```
Once the user is authenticated via the popup, your deep link is called.
You have to get the oAuthVerifier from your deep linking intent:
```Java
String oAuthVerifier = intent.getData().getQueryParameter("oAuthVerifier");
```
And the last step is to get the user oAuth token that is generated given credentials for a broker. 
The token may be used to authenticate the user in the future without them having to re-enter their credentials. And because of this the userId/userToken should be handled like a username/password. 
```Java
final TradeItOAuthAccessTokenRequest request = new TradeItOAuthAccessTokenRequest(oAuthVerifier);
brokerLinker.getOAuthAccessToken(request, new Callback<TradeItOAuthAccessTokenResponse>() {
    public void onResponse(Call<TradeItOAuthAccessTokenResponse> call, Response<TradeItOAuthAccessTokenResponse> response) {
        if (response.isSuccessful()) {
              TradeItOAuthAccessTokenResponse oAuthAccessTokenResponse = response.body(); 
              if (oAuthAccessTokenResponse.status == TradeItResponseStatus.SUCCESS) {
                //saving the oAuth access token on the device (encrypted) to be used in the future
                TradeItLinkedLogin linkedLogin = new TradeItLinkedLogin(broker, request, oAuthAccessTokenResponse);
                try {
                    TradeItAccountLinker.saveLinkedLogin(context, linkedLogin, "My account label");
                } catch(TradeItSaveLinkedLoginException e) {
                    //handle the exception
                }
                
              }
        } else { // Server returned non-2XX status (e.g. 404, 503, etc.) }
    }
        
    public void onFailure(Call call, Throwable t) {
          // Something went wrong trying to send the request, check that the device is connected to the internet...
    }
});
```
A word about the storage of the TradeItLinkedLogin:

Initialize the keystore in order to save/update/load/delete previous linked logins in the device:
```Java
TradeItBrokerLinker.initKeyStore(context); 
```
Save a new linked login:
```Java
TradeItLinkedLogin linkedLogin = ... // previous linkedLogin
TradeItBrokerLinker.saveLinkedLogin(context, linkedLogin, "MyAccount1");
```
Update a stored linked login:
```Java
TradeItLinkedLogin linkedLogin = ... // previous linkedLogin
TradeItBrokerLinker.updateLinkedLogin(context, linkedLogin);
```
Get the stored linked logins:
```Java
List<TradeItLinkedLogin> tradeItLinkedLoginsList =  TradeItBrokerLinker.getLinkedLogins(context);
```
Delete a stored linked login:
```Java
TradeItLinkedLogin linkedLogin = ... // previous linkedLogin
TradeItBrokerLinker.deleteLinkedLogin(context, linkedLogin);
```
Delete all stored linked logins:
```Java
TradeItBrokerLinker.deleteAllLinkedLogin(context);
```
Authenticate a user session thanks to the linkedLogin and get the user's accounts with the broker:
```Java
// Initialize an instance of the API client from the TradeItLinkedLogin instance
TradeItApiClient tradeItApiClient = new TradeItApiClient(linkedLogin);

tradeItApiClient.authenticate(new Callback<TradeItAuthenticateResponse>() {
  public void onResponse(Call<TradeItAuthenticateResponse> call, Response<TradeItAuthenticateResponse> response) {
  if (response.isSuccessful()) {
    TradeItAuthenticateResponse authResponse = response.body();

    if (authResponse.status == TradeItResponseStatus.SUCCESS) {
      // tradeItApiClient is now authenticated and can be used to trade and view account information.
      // The trading accounts associated with the linked user account are available in authResponse.accounts
    } else if (authResponse.status == TradeItResponseStatus.INFORMATION_NEEDED) {
      // The broker is requesting a security question be answered by the user to authenticate.
      // Display the question authResponse.securityQuestion to the user.
      // Use tradeItApiClient.answerSecurityQuestion to submit the user's answer to the broker.
    }
  }
});
```
Submit a trade to the broker for preview:
```Java
TradeItPreviewStockOrEtfOrderRequest previewRequest = new TradeItPreviewStockOrEtfOrderRequest();
previewRequest.accountNumber = authResponse.accounts.get(0).accountNumber;
previewRequest.orderAction = "buy";
previewRequest.orderQuantity = "1";
previewRequest.orderSymbol = "CMG";
previewRequest.orderPriceType = "stopLimit";
previewRequest.orderLimitPrice = "300";
previewRequest.orderStopPrice = "600";
previewRequest.orderExpiration = "day";

tradeItApiClient.previewStockOrEtfOrder(previewRequest, new CallbackWithError<TradeItPreviewStockOrEtfOrderResponse>() {
  public void onResponse(Call<TradeItPreviewStockOrEtfOrderResponse> call, Response<TradeItPreviewStockOrEtfOrderResponse> response) {
    if (response.isSuccessful()) {
      TradeItPreviewStockOrEtfOrderResponse previewResponse = response.body();

      if (previewResponse.status.equals(TradeItResponseStatus.REVIEW_ORDER)) {
        // Present the order preview to the user for confirmation.
        // Upon receiving user's confirmation, the trade can be completed with tradeItApiClient.trade
      }
    }
  }
});
```

#Support

More information can be found at https://www.trade.it/

Contact the team at: support@trade.it
