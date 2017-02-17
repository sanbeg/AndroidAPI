package it.trade.tradeit;

import android.content.Context;
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

import java.util.List;

import it.trade.tradeitapi.API.TradeItBrokerLinker;
import it.trade.tradeitapi.API.TradeItApiClient;
import it.trade.tradeitapi.exception.TradeItDeleteLinkedLoginException;
import it.trade.tradeitapi.exception.TradeItKeystoreServiceCreateKeyException;
import it.trade.tradeitapi.exception.TradeItRetrieveLinkedLoginException;
import it.trade.tradeitapi.exception.TradeItSaveLinkedLoginException;
import it.trade.tradeitapi.exception.TradeItUpdateLinkedLoginException;
import it.trade.tradeitapi.model.TradeItAuthenticateResponse;
import it.trade.tradeitapi.model.TradeItAvailableBrokersResponse;
import it.trade.tradeitapi.model.TradeItEnvironment;
import it.trade.tradeitapi.model.TradeItGetAccountOverviewRequest;
import it.trade.tradeitapi.model.TradeItGetAccountOverviewResponse;
import it.trade.tradeitapi.model.TradeItGetAllOrderStatusRequest;
import it.trade.tradeitapi.model.TradeItGetAllTransactionsHistoryRequest;
import it.trade.tradeitapi.model.TradeItGetAllTransactionsHistoryResponse;
import it.trade.tradeitapi.model.TradeItGetPositionsRequest;
import it.trade.tradeitapi.model.TradeItGetPositionsResponse;
import it.trade.tradeitapi.model.TradeItLinkLoginRequest;
import it.trade.tradeitapi.model.TradeItLinkLoginResponse;
import it.trade.tradeitapi.model.TradeItLinkedLogin;
import it.trade.tradeitapi.model.TradeItOrderStatusResponse;
import it.trade.tradeitapi.model.TradeItPlaceStockOrEtfOrderRequest;
import it.trade.tradeitapi.model.TradeItPlaceStockOrEtfOrderResponse;
import it.trade.tradeitapi.model.TradeItPreviewStockOrEtfOrderRequest;
import it.trade.tradeitapi.model.TradeItPreviewStockOrEtfOrderResponse;
import it.trade.tradeitapi.model.TradeItRelinkLoginRequest;
import it.trade.tradeitapi.model.TradeItRequestWithSession;
import it.trade.tradeitapi.model.TradeItResponse;
import it.trade.tradeitapi.model.TradeItResponseStatus;
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
    TradeItBrokerLinker brokerLinker;

    String selectedBroker;
    String selectedAction;
    String accountNumber = "";

    Context context;

    TradeItLinkedLogin linkedLogin;

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

        brokerLinker = new TradeItBrokerLinker(API_KEY, TradeItEnvironment.QA);

        context = getApplication();

        try {
            TradeItBrokerLinker.initKeyStore(context);
        } catch (TradeItKeystoreServiceCreateKeyException e) {
            appendError(e);
        }
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
                    case "Brokers":
                        testAvailableBrokers();
                        break;
                    case "Link/Auth":
                        testLinkingAndAuthentication();
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

    private void testLinkingAndAuthentication() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        link(selectedBroker, username, password);
    }

    private void testTrading(String accountNumber) {
        preview(accountNumber);
    }

    private void testAccount(String accountNumber) {
        balances(accountNumber);
        positions(accountNumber);
        orders(accountNumber);
        transactions(accountNumber);
    }

    private void testAvailableBrokers() {
        appendRequest("GETTING AVAILABLE BROKERS...");
        brokerLinker.getAvailableBrokers(new CallbackWithSuccessAndError<TradeItAvailableBrokersResponse>());
    }

    private void link(String broker, final String username, final String password) {
        final TradeItLinkLoginRequest linkLoginRequest = new TradeItLinkLoginRequest(username, password, broker);
        appendRequest(linkLoginRequest);

        brokerLinker.linkBrokerAccount(linkLoginRequest, new CallbackWithError<TradeItLinkLoginResponse>() {
            public void onResponse(Call<TradeItLinkLoginResponse> call, Response<TradeItLinkLoginResponse> response) {
                if (response.isSuccessful()) {
                    TradeItLinkLoginResponse linkLoginResponse = response.body();
                    appendResponse(linkLoginResponse);

                    if (linkLoginResponse.status == TradeItResponseStatus.SUCCESS) {
                        linkedLogin = new TradeItLinkedLogin(linkLoginRequest, linkLoginResponse);
                        relink(linkedLogin, username, password);
                        saveLink(linkedLogin, username, password);
                        retrieveLinks();
                        updateLink(linkedLogin);
                        deleteLink(linkedLogin);
                    }
                } else {
                    appendResponse(response.raw());
                }
            }
        });
    }

    private void relink(final TradeItLinkedLogin linkedLogin, String username, String password) {
        TradeItRelinkLoginRequest relinkLoginRequest = new TradeItRelinkLoginRequest(linkedLogin, username, password);
        appendRequest(relinkLoginRequest);

        brokerLinker.relinkBrokerAccount(relinkLoginRequest, new CallbackWithError<TradeItLinkLoginResponse>() {
            public void onResponse(Call<TradeItLinkLoginResponse> call, Response<TradeItLinkLoginResponse> response) {
                if (response.isSuccessful()) {
                    TradeItLinkLoginResponse authResponse = response.body();
                    appendResponse(authResponse);

                    if (authResponse.status == TradeItResponseStatus.SUCCESS) {
                        linkedLogin.update(authResponse);
                        auth(linkedLogin);
                    }
                } else {
                    appendResponse(response.raw());
                }
            }
        });
    }

    private void saveLink(final TradeItLinkedLogin linkedLogin, String username, String password) {
        appendInternalOperation("SAVING LINKED ACCOUNT...");
        try {
            TradeItBrokerLinker.saveLinkedLogin(context, linkedLogin, "labelGivenByTheUser");
            appendSimpleMessage("account saved, uuid: " + linkedLogin.uuid);
        }
        catch (TradeItSaveLinkedLoginException e) {
            appendError(e);
        }
    }

    private void retrieveLinks() {
        try {
            appendInternalOperation("RETRIEVING LINKED ACCOUNTS...");
            List<TradeItLinkedLogin> tradeItLinkedLoginsList =  TradeItBrokerLinker.getLinkedLogins(context);
            appendSimpleMessage(tradeItLinkedLoginsList);
        }
        catch (TradeItRetrieveLinkedLoginException e) {
            appendError(e);
        }
    }

    private void updateLink(final TradeItLinkedLogin linkedLogin) {
        linkedLogin.label = "updateLabel";
        try {
            appendInternalOperation("UPDATING LINKED ACCOUNT " + linkedLogin.uuid + " WITH A NEW LABEL: "+ linkedLogin.label);
            TradeItBrokerLinker.updateLinkedLogin(context, linkedLogin);
            List<TradeItLinkedLogin> tradeItLinkedLoginsList =  TradeItBrokerLinker.getLinkedLogins(context);
            appendSimpleMessage(tradeItLinkedLoginsList);
        } catch (TradeItUpdateLinkedLoginException e) {
            appendError(e);
        } catch (TradeItRetrieveLinkedLoginException e) {
            appendError(e);
        }
    }

    private void deleteLink(final TradeItLinkedLogin linkedLogin) {
        try {
            appendInternalOperation("DELETING LINKED ACCOUNT " + linkedLogin.uuid);
            TradeItBrokerLinker.deleteLinkedLogin(context, linkedLogin);
            appendInternalOperation("ARE THERE ANY LINKED ACCOUNTS ANYMORE ? ...");
            List<TradeItLinkedLogin> tradeItLinkedLoginsList =  TradeItBrokerLinker.getLinkedLogins(context);
            appendSimpleMessage(tradeItLinkedLoginsList.size() == 0 ? "NO!" : "Yes!: " + tradeItLinkedLoginsList);
        }
        catch (TradeItRetrieveLinkedLoginException e) {
            appendError(e);
        } catch (TradeItDeleteLinkedLoginException e) {
            appendError(e);
        }
    }
    private void auth(TradeItLinkedLogin linkedLogin) {
        tradeItApiClient = new TradeItApiClient(linkedLogin);

        appendRequest("AUTHENTICATING...");
        tradeItApiClient.authenticate(new CallbackWithError<TradeItAuthenticateResponse>() {
            public void onResponse(Call<TradeItAuthenticateResponse> call, Response<TradeItAuthenticateResponse> response) {
                if (response.isSuccessful()) {
                    TradeItAuthenticateResponse authResponse = response.body();
                    appendResponse(authResponse);

                    if (authResponse.status == TradeItResponseStatus.SUCCESS) {
                        // TODO: Test closeSession

                        accountNumber = authResponse.accounts.get(0).accountNumber;
                        ping();
                    }
                } else {
                    appendResponse(response.raw());
                }
            }
        });
    }

    private void orders(String accountNumber) {
        TradeItGetAllOrderStatusRequest ordersRequest = new TradeItGetAllOrderStatusRequest(accountNumber);
        appendRequest(ordersRequest);

        tradeItApiClient.getAllOrderStatus(ordersRequest, new CallbackWithSuccessAndError<TradeItOrderStatusResponse>());
    }

    private void transactions(String accountNumber) {
        TradeItGetAllTransactionsHistoryRequest transactionsRequest = new TradeItGetAllTransactionsHistoryRequest(accountNumber);
        appendRequest(transactionsRequest);

        tradeItApiClient.getAllTransactionsHistory(transactionsRequest, new CallbackWithSuccessAndError<TradeItGetAllTransactionsHistoryResponse>());
    }

    private void positions(String accountNumber) {
        TradeItGetPositionsRequest positionsRequest = new TradeItGetPositionsRequest(accountNumber, null);
        appendRequest(positionsRequest);

        tradeItApiClient.getPositions(positionsRequest, new CallbackWithSuccessAndError<TradeItGetPositionsResponse>());
    }

    private void balances(String accountNumber) {
        TradeItGetAccountOverviewRequest balanceRequest = new TradeItGetAccountOverviewRequest(accountNumber);
        appendRequest(balanceRequest);

        tradeItApiClient.getAccountOverview(balanceRequest, new CallbackWithSuccessAndError<TradeItGetAccountOverviewResponse>());
    }

    private void ping() {
        TradeItRequestWithSession pingRequest = new TradeItRequestWithSession();
        appendRequest(pingRequest);

        tradeItApiClient.keepSessionAlive(pingRequest, new CallbackWithSuccessAndError<TradeItResponse>());
    }

    private void preview(String accountNumber) {
        TradeItPreviewStockOrEtfOrderRequest previewRequest = new TradeItPreviewStockOrEtfOrderRequest(accountNumber,
                                                                                                       "buy",
                                                                                                       "1",
                                                                                                       "CMG",
                                                                                                       "stopLimit",
                                                                                                       "300",
                                                                                                       "600",
                                                                                                       "day");

        appendRequest(previewRequest);

        tradeItApiClient.previewStockOrEtfOrder(previewRequest, new CallbackWithError<TradeItPreviewStockOrEtfOrderResponse>() {
            public void onResponse(Call<TradeItPreviewStockOrEtfOrderResponse> call, Response<TradeItPreviewStockOrEtfOrderResponse> response) {
                if (response.isSuccessful()) {
                    TradeItPreviewStockOrEtfOrderResponse previewResponse = response.body();
                    appendResponse(previewResponse);

                    if (previewResponse.status.equals(TradeItResponseStatus.REVIEW_ORDER)) {
                        trade(previewResponse.orderId);
                    }
                } else {
                    appendResponse(response.raw());
                }
            }
        });
    }

    private void trade(String orderId) {
        TradeItPlaceStockOrEtfOrderRequest tradeRequest = new TradeItPlaceStockOrEtfOrderRequest(orderId);
        appendRequest(tradeRequest);

        tradeItApiClient.placeStockOrEtfOrder(tradeRequest, new CallbackWithSuccessAndError<TradeItPlaceStockOrEtfOrderResponse>());
    }

    private abstract class CallbackWithError<T> implements Callback<T> {
        public void onFailure(Call call, Throwable t) {
            appendError(t);
        }
    }

    private class CallbackWithSuccessAndError<T> extends CallbackWithError<T> {
        public void onResponse(Call<T> call, Response<T> response) {
            if (response.isSuccessful()) {
                T tradeItResponse = response.body();
                appendResponse(tradeItResponse);
            } else {
                appendResponse(response.raw());
            }
        }
    }

    private void appendError(Throwable t) {
        outputTextView.append("\n=====\n\nERROR: " + t + "\n");
    }

    private void appendInternalOperation(Object operation) {
        outputTextView.append("\n=====\n\nINTERNAL OPERATION: " + operation + "\n");
    }

    private void appendSimpleMessage(Object message) {
        outputTextView.append("\n" + message + "\n");
    }

    private void appendRequest(Object request) {
        outputTextView.append("\n=====\n\nREQUEST: " + request + "\n");
    }

    private void appendResponse(Object response) {
        outputTextView.append("\n=====\n\nRESPONSE: " + response + "\n");
    }
}