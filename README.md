#Android Trade.it API Client
Android library that wraps the Trade.it API using the [Retrofit 2](http://square.github.io/retrofit/) library.

Detailed API documentation can be found here: https://www.trade.it/api.

The JCenter repo can be found here: https://bintray.com/tradeit/maven/tradeit-android-api/view

For example usage, see the example app included with the library.  It is ready to build and load onto a device or the simulator and has basic functionality to link an account, authenticate, make a trade, and fetch account details like balances and positions.

#Quick Start
To link a user's account and to manage linked accounts, use the `TradeItAccountLinker`:
```
// In order to initialize the account linker, obtain an API key from Trade.it, or test with "tradeit-test-api-key"
TradeItAccountLinker accountLinker = new TradeItAccountLinker("tradeit-test-api-key", TradeItEnvironment.QA);
```
Query which brokers are available for your key:
```
accountLinker.getAvailableBrokers(new Callback<TradeItAvailableBrokersResponse>() {
  public void onResponse(Call<TradeItAvailableBrokersResponse> call, Response<TradeItAvailableBrokersResponse> response) {
    // Check that the http request was successful (status code 2XX) before proceeding
    if (response.isSuccessful()) {
      TradeItAvailableBrokersResponse brokersResponse = response.body();

      // Check the status of the API response
      if (linkAccountResponse.status == TradeItResponseStatus.SUCCESS) {
        // Let user choose a broker based on the the brokers in brokersResponse.brokerList
      }
    } else { // Server returned non-2XX status (e.g. 404, 503, etc.) }
  }
  
  public void onFailure(Call call, Throwable t) {
    // Something went wrong trying to send the request, check that the device is connected to the internet...
  }
});
```
Link (authorize) a user's account:
```
// Create a request from the user's login credentials
final TradeItLinkAccountRequest linkAccountRequest = new TradeItLinkAccountRequest("broker_account_username", "broker_account_password", "Fidelity");

accountLinker.linkBrokerAccount(linkAccountRequest, new Callback<TradeItLinkAccountResponse>() {
  public void onResponse(Call<TradeItLinkAccountResponse> call, Response<TradeItLinkAccountResponse> response) {
    if (response.isSuccessful()) {
      TradeItLinkAccountResponse linkAccountResponse = response.body();

      if (linkAccountResponse.status == TradeItResponseStatus.SUCCESS) {
        // The user's linked account is encapsulated in a TradeItLinkedAccount object.
        // This object is initialized from a TradeItLinkAccountRequest and the corresponding successful TradeItLinkAccountResponse.
        // TradeItLinkedAccount is annotated for gson serialization so that it can be saved on a device for ongoing use.
        TradeItLinkedAccount linkedAccount = new TradeItLinkedAccount(linkAccountRequest, linkAccountResponse);
      }
    }
  }
});
```
Initialize the keystore in order to save/update/load/delete previous linked accounts in the device:
```
TradeItAccountLinker.initKeyStore(context); 
```
Save a new linked account:
```
TradeItLinkedAccount linkedAccount = ... // previous linkedAccount
TradeItAccountLinker.saveLinkedAccount(context, linkedAccount, "MyAccount1");
```
Update a stored linked account:
```
TradeItLinkedAccount linkedAccount = ... // previous linkedAccount
TradeItAccountLinker.updateLinkedAccount(context, linkedAccount);
```
Get the stored linked accounts:
```
List<TradeItLinkedAccount> tradeItLinkedAccountsList =  TradeItAccountLinker.getLinkedAccounts(context);
```
Delete a stored linked account:
```
TradeItLinkedAccount linkedAccount = ... // previous linkedAccount
TradeItAccountLinker.deleteLinkedAccount(context, linkedAccount);
```
Delete all stored linked accounts:
```
TradeItAccountLinker.deleteAllLinkedAccount(context);
```
Authenticate a user session and get the user's accounts with the broker:
```
// Initialize an instance of the API client from the TradeItLinkedAccount instance
TradeItApiClient tradeItApiClient = new TradeItApiClient(linkedAccount);

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
```
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
