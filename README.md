#Android Trade.it API
Android library that wraps the Trade.it API using the [Retrofit 2](http://square.github.io/retrofit/) library.

Detailed API documentation can be found [here](https://www.trade.it/api).

For example usage, see the example app included with the library.

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
    // Check that the http call was successful before proceeding
    if (response.isSuccessful()) {
      TradeItAvailableBrokersResponse brokersResponse = response.body();

      // Check the status of the API response
      if (linkAccountResponse.status == TradeItResponseStatus.SUCCESS) {
        // Let user choose a broker based on the the brokers in brokersResponse.brokerList
      }
    }
  }
});
```
Link (authorize) a user's account:
```
// Create a request from the user's login credentials
final TradeItLinkAccountRequest linkAccountRequest = new TradeItLinkAccountRequest("broker_account_username", "broker_account_password", "Fidelity");

accountLinker.linkBrokerAccount(linkAccountRequest, new CallbackWithError<TradeItLinkAccountResponse>() {
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
Authenticate a user session and get the user's accounts with the broker:
```
// Initialize an instance of the API client from the TradeItLinkedAccount instance
TradeItApiClient tradeItApiClient = new TradeItApiClient(linkedAccount);

tradeItApiClient.authenticate(new CallbackWithError<TradeItAuthenticateResponse>() {
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
