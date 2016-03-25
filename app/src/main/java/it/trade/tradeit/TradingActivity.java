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

import it.trade.tradeit.API.TradeItAPIService;
import it.trade.tradeit.model.TradeItAuthenticateRequest;
import it.trade.tradeit.model.TradeItAuthenticateResponse;
import it.trade.tradeit.model.TradeItGetAccountOverviewRequest;
import it.trade.tradeit.model.TradeItGetAccountOverviewResponse;
import it.trade.tradeit.model.TradeItGetPositionsRequest;
import it.trade.tradeit.model.TradeItGetPositionsResponse;
import it.trade.tradeit.model.TradeItOAuthLinkRequest;
import it.trade.tradeit.model.TradeItOAuthLinkResponse;
import it.trade.tradeit.model.TradeItPlaceStockOrEtfOrderRequest;
import it.trade.tradeit.model.TradeItPlaceStockOrEtfOrderResponse;
import it.trade.tradeit.model.TradeItPreviewStockOrEtfOrderRequest;
import it.trade.tradeit.model.TradeItPreviewStockOrEtfOrderResponse;
import it.trade.tradeit.model.TradeItRequestWithSession;
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

    TradeItAPIService tradeItAPIService;
    String selectedBroker;
    String selectedAction;
    String sessionToken;
    String accountNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tradeItAPIService = new TradeItAPIService(API_KEY);

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
    }

    private void positions(String accountNumber) {
        TradeItGetPositionsRequest positionsRequest = new TradeItGetPositionsRequest(accountNumber, null);

        Call<TradeItGetPositionsResponse> call = tradeItAPIService.getPositions(positionsRequest);
        appendRequest(positionsRequest);

        call.enqueue(new CallbackWithError<TradeItGetPositionsResponse>() {
            @Override
            public void onResponse(Call<TradeItGetPositionsResponse> call, Response<TradeItGetPositionsResponse> response) {
                TradeItGetPositionsResponse positionsResponse = response.body();
                appendResponse(positionsResponse);
            }
        });
    }

    private void balances(String accountNumber) {
        TradeItGetAccountOverviewRequest balanceRequest = new TradeItGetAccountOverviewRequest(accountNumber);

        Call<TradeItGetAccountOverviewResponse> call = tradeItAPIService.getAccountOverview(balanceRequest);
        appendRequest(balanceRequest);

        call.enqueue(new CallbackWithError<TradeItGetAccountOverviewResponse>() {
            @Override
            public void onResponse(Call<TradeItGetAccountOverviewResponse> call, Response<TradeItGetAccountOverviewResponse> response) {
                TradeItGetAccountOverviewResponse balanceResponse = response.body();
                appendResponse(balanceResponse);
            }
        });
    }

    private void link(String broker, String username, String password) {
        TradeItOAuthLinkRequest oAuthLinkRequest = new TradeItOAuthLinkRequest(username, password, broker);
        Call<TradeItOAuthLinkResponse> call = tradeItAPIService.oAuthLink(oAuthLinkRequest);
        appendRequest(oAuthLinkRequest);

        call.enqueue(new CallbackWithError<TradeItOAuthLinkResponse>() {
            @Override
            public void onResponse(Call<TradeItOAuthLinkResponse> call, Response<TradeItOAuthLinkResponse> response) {
                TradeItOAuthLinkResponse oAuthLinkResponse = response.body();
                appendResponse(oAuthLinkResponse);

                // TODO: THIS SHOULD BE HANDLED INTERNAL TO THE SERVICE
                if (oAuthLinkResponse.status.equals("SUCCESS")) {
                    auth(oAuthLinkResponse.userToken, oAuthLinkResponse.userId);
                }
            }

//            @Override
//            public void onFailure(Call<TradeItOAuthLinkResponse> call, Throwable t) {
//                appendError(t);
//            }
        });
    }

    private void auth(String userToken, String userId) {
        TradeItAuthenticateRequest authRequest = new TradeItAuthenticateRequest(userToken, userId, Long.toString(System.currentTimeMillis()));
        Call<TradeItAuthenticateResponse> call = tradeItAPIService.authenticate(authRequest);
        appendRequest(authRequest);

        call.enqueue(new CallbackWithError<TradeItAuthenticateResponse>() {
            @Override
            public void onResponse(Call<TradeItAuthenticateResponse> call, Response<TradeItAuthenticateResponse> response) {
                TradeItAuthenticateResponse authResponse = response.body();
                appendResponse(authResponse);

                // TODO: THIS SHOULD BE HANDLED INTERNAL TO THE SERVICE
                TradeItRequestWithSession.SESSION_TOKEN = authResponse.token;
                accountNumber = authResponse.accounts.get(0).accountNumber;
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

        Call<TradeItPreviewStockOrEtfOrderResponse> call = tradeItAPIService.previewStockOrEtfOrder(previewRequest);
        appendRequest(previewRequest);

        call.enqueue(new CallbackWithError<TradeItPreviewStockOrEtfOrderResponse>() {
            @Override
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

        Call<TradeItPlaceStockOrEtfOrderResponse> call = tradeItAPIService.placeStockOrEtfOrder(tradeRequest);
        appendRequest(tradeRequest);

        call.enqueue(new CallbackWithError<TradeItPlaceStockOrEtfOrderResponse>() {
            @Override
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