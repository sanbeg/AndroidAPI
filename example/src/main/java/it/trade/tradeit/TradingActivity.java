package it.trade.tradeit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import it.trade.tradeitapi.API.TradeItApiClient;
import it.trade.tradeitapi.model.TradeItAuthenticateResponse;
import it.trade.tradeitapi.model.TradeItBrokerLink;
import it.trade.tradeitapi.model.TradeItEnvironment;
import it.trade.tradeitapi.model.TradeItGetAccountOverviewRequest;
import it.trade.tradeitapi.model.TradeItGetAccountOverviewResponse;
import it.trade.tradeitapi.model.TradeItGetAllOrderStatusRequest;
import it.trade.tradeitapi.model.TradeItGetAllTransactionsHistoryRequest;
import it.trade.tradeitapi.model.TradeItGetAllTransactionsHistoryResponse;
import it.trade.tradeitapi.model.TradeItGetPositionsRequest;
import it.trade.tradeitapi.model.TradeItGetPositionsResponse;
import it.trade.tradeitapi.model.TradeItOAuthLinkResponse;
import it.trade.tradeitapi.model.TradeItOrderStatusResponse;
import it.trade.tradeitapi.model.TradeItPlaceStockOrEtfOrderRequest;
import it.trade.tradeitapi.model.TradeItPlaceStockOrEtfOrderResponse;
import it.trade.tradeitapi.model.TradeItPreviewStockOrEtfOrderRequest;
import it.trade.tradeitapi.model.TradeItPreviewStockOrEtfOrderResponse;
import it.trade.tradeitapi.model.TradeItRequestWithSession;
import it.trade.tradeitapi.model.TradeItResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TradingActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String API_KEY = "tradeit-test-api-key";

    TextView outputTextView;
    Button tradeButton;
    Button clearButton;
    EditText usernameEditText;
    EditText passwordEditText;
    Spinner brokerSpinner;
    Spinner actionSpinner;

    TradeItApiClient tradeItApiClient;
    String selectedBroker;
    String selectedAction;
    String accountNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        outputTextView = (TextView) findViewById(R.id.output_textview);
        outputTextView.setMovementMethod(new ScrollingMovementMethod());

        usernameEditText = (EditText) findViewById(R.id.username_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);

        tradeButton = (Button) findViewById(R.id.go_button);
        tradeButton.setOnClickListener(this);

        clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(this);

        brokerSpinner = (Spinner) findViewById(R.id.broker_spinner);
        brokerSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.brokers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brokerSpinner.setAdapter(adapter);

        actionSpinner = (Spinner) findViewById(R.id.action_spinner);
        actionSpinner.setOnItemSelectedListener(this);
        adapter = ArrayAdapter.createFromResource(this, R.array.actions_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_button: {
                outputTextView.setText("...");
                break;
            }
            case R.id.go_button: {
                switch (selectedAction) {
                    case "Auth":
                        testAuthentication();
                        break;
                    case "Trade":
                        testTrading(accountNumber);
                        break;
                    case "Account":
                        testAccount(accountNumber);
                        break;
                }

                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.action_spinner: {
                selectedAction = parent.getItemAtPosition(position).toString();

                break;
            }
            case R.id.broker_spinner: {
                selectedBroker = parent.getItemAtPosition(position).toString();

                if (selectedBroker.equals("Dummy")) {
                    usernameEditText.setText("dummy");
                    passwordEditText.setText("dummy");
                }

                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void testAuthentication() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        link(selectedBroker, username, password);
    }

    private void testTrading(String accountNumber) {
        preview();
    }

    private void testAccount(String accountNumber) {
        balances(accountNumber);
        positions(accountNumber);
        orders(accountNumber);
        transactions(accountNumber);
    }

    private void link(String broker, String username, String password) {
        TradeItBrokerLink brokerLink = new TradeItBrokerLink(broker, username, password, API_KEY, TradeItEnvironment.QA);
        appendRequest(brokerLink);
        brokerLink.link(new TradeItBrokerLink.TradeItBrokerLinkCallback() {

            @Override
            public void onLinkSuccess(TradeItBrokerLink successfulBrokerLink) {
                appendResponse(successfulBrokerLink);
                tradeItApiClient = new TradeItApiClient(successfulBrokerLink);
                auth();
            }

            @Override
            public void onLinkFailed(TradeItOAuthLinkResponse response) {
                appendResponse(response);
            }
        });
    }

    private void auth() {
        appendRequest("AUTHENTICATING...");
        tradeItApiClient.authenticate(new CallbackWithError<TradeItAuthenticateResponse>() {
            public void onResponse(Call<TradeItAuthenticateResponse> call, Response<TradeItAuthenticateResponse> response) {
                TradeItAuthenticateResponse authResponse = response.body();
                appendResponse(authResponse);

                accountNumber = authResponse.accounts.get(0).accountNumber;

                ping();
            }
        });
    }

    private void orders(String accountNumber) {
        TradeItGetAllOrderStatusRequest ordersRequest = new TradeItGetAllOrderStatusRequest(accountNumber);
        appendRequest(ordersRequest);

        tradeItApiClient.getAllOrderStatus(ordersRequest, new CallbackWithError<TradeItOrderStatusResponse>() {
            public void onResponse(Call<TradeItOrderStatusResponse> call, Response<TradeItOrderStatusResponse> response) {
                TradeItOrderStatusResponse ordersResponse = response.body();
                appendResponse(ordersResponse);
            }
        });
    }

    private void transactions(String accountNumber) {
        TradeItGetAllTransactionsHistoryRequest transactionsRequest = new TradeItGetAllTransactionsHistoryRequest(accountNumber);
        appendRequest(transactionsRequest);

        tradeItApiClient.getAllTransactionsHistory(transactionsRequest, new CallbackWithError<TradeItGetAllTransactionsHistoryResponse>() {
            public void onResponse(Call<TradeItGetAllTransactionsHistoryResponse> call, Response<TradeItGetAllTransactionsHistoryResponse> response) {
                TradeItGetAllTransactionsHistoryResponse transactionsResponse = response.body();
                appendResponse(transactionsResponse);
            }
        });
    }

    private void positions(String accountNumber) {
        TradeItGetPositionsRequest positionsRequest = new TradeItGetPositionsRequest(accountNumber, null);
        appendRequest(positionsRequest);

        tradeItApiClient.getPositions(positionsRequest, new CallbackWithError<TradeItGetPositionsResponse>() {
            public void onResponse(Call<TradeItGetPositionsResponse> call, Response<TradeItGetPositionsResponse> response) {
                TradeItGetPositionsResponse positionsResponse = response.body();
                appendResponse(positionsResponse);
            }
        });
    }

    private void balances(String accountNumber) {
        TradeItGetAccountOverviewRequest balanceRequest = new TradeItGetAccountOverviewRequest(accountNumber);
        appendRequest(balanceRequest);

        tradeItApiClient.getAccountOverview(balanceRequest, new CallbackWithError<TradeItGetAccountOverviewResponse>() {
            public void onResponse(Call<TradeItGetAccountOverviewResponse> call, Response<TradeItGetAccountOverviewResponse> response) {
                TradeItGetAccountOverviewResponse balanceResponse = response.body();
                appendResponse(balanceResponse);
            }
        });
    }

    private void ping() {
        TradeItRequestWithSession pingRequest = new TradeItRequestWithSession();
        appendRequest(pingRequest);

        tradeItApiClient.keepSessionAlive(pingRequest, new CallbackWithError<TradeItResponse>() {
            public void onResponse(Call<TradeItResponse> call, Response<TradeItResponse> response) {
                TradeItResponse pingResponse = response.body();
                appendResponse(pingResponse);
            }
        });
    }

    private void preview() {
        TradeItPreviewStockOrEtfOrderRequest previewRequest = new TradeItPreviewStockOrEtfOrderRequest(accountNumber,
                                                                                                       "buy",
                                                                                                       "1",
                                                                                                       "CMG",
                                                                                                       "stopLimit",
                                                                                                       "400",
                                                                                                       "600",
                                                                                                       "day");

        appendRequest(previewRequest);

        tradeItApiClient.previewStockOrEtfOrder(previewRequest, new CallbackWithError<TradeItPreviewStockOrEtfOrderResponse>() {
            public void onResponse(Call<TradeItPreviewStockOrEtfOrderResponse> call, Response<TradeItPreviewStockOrEtfOrderResponse> response) {
                TradeItPreviewStockOrEtfOrderResponse previewResponse = response.body();
                appendResponse(previewResponse);

                if (previewResponse.status.equals("REVIEW_ORDER")) {
                    trade(previewResponse.orderId);
                }
            }
        });
    }

    private void trade(String orderId) {
        TradeItPlaceStockOrEtfOrderRequest tradeRequest = new TradeItPlaceStockOrEtfOrderRequest(orderId);
        appendRequest(tradeRequest);

        tradeItApiClient.placeStockOrEtfOrder(tradeRequest, new CallbackWithError<TradeItPlaceStockOrEtfOrderResponse>() {
            public void onResponse(Call<TradeItPlaceStockOrEtfOrderResponse> call, Response<TradeItPlaceStockOrEtfOrderResponse> response) {
                TradeItPlaceStockOrEtfOrderResponse tradeResponse = response.body();
                appendResponse(tradeResponse);
            }
        });
    }

    private abstract class CallbackWithError<T> implements Callback<T> {
        public void onFailure(Call call, Throwable t) {
            appendError(t);
        }
    }

    private void appendError(Throwable t) {
        outputTextView.append("\n=====\n\nERROR: " + t + "\n");
    }

    private void appendRequest(Object request) {
        outputTextView.append("\n=====\n\nREQUEST: " + request + "\n");
    }

    private void appendResponse(Object response) {
        outputTextView.append("\n=====\n\nRESPONSE: " + response + "\n");
    }
}