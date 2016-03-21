package it.trade.tradeit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import it.trade.tradeit.API.TradeItClient;

public class TradingActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String API_KEY = "tradeit-test-api-key";

    TextView outputTextView;
    Button tradeButton;
    EditText usernameEditText;
    EditText passwordEditText;
    Spinner brokerSpinner;
    TradeItClient tradeItClient;
    String selectedBroker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tradeItClient = new TradeItClient(API_KEY);

        outputTextView = (TextView) findViewById(R.id.output_textview);
        outputTextView.setMovementMethod(new ScrollingMovementMethod());

        usernameEditText = (EditText) findViewById(R.id.username_edittext);
        passwordEditText = (EditText) findViewById(R.id.password_edittext);

        tradeButton = (Button) findViewById(R.id.trade_button);
        tradeButton.setOnClickListener(this);


        brokerSpinner = (Spinner) findViewById(R.id.broker_spinner);
        brokerSpinner.setOnItemSelectedListener(this);





        String[] brokers = getResources().getStringArray(R.array.brokers_array);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, brokers);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brokerSpinner.setAdapter(dataAdapter);





//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.brokers_array, android.R.layout.simple_spinner_item);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        brokerSpinner.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("LOLWUT", "ID: " + item.getItemId() + ", TITLE: " + item.getTitle());

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Log.i("LOLWUT", "BROKER: " + selectedBroker);

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        link(selectedBroker, username, password);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedBroker = parent.getItemAtPosition(position).toString();

        if (selectedBroker.equals("Dummy")) {
            usernameEditText.setText("dummy");
            passwordEditText.setText("dummy");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedBroker = "";
    }

    protected void testBroker() {
        // TODO:
    }

    private void link(String broker, String username, String password) {
        outputTextView.setText("HERE WE GO...");
        outputTextView.append("\n");

        JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {
            String responseString = "";

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                outputTextView.append("\n=====\n\nSTATUS: " + statusCode + "\n");

                try {
                    responseString = response.toString(2);
                } catch(Exception e) {
                    responseString = "COULD NOT PARSE: " + e;
                }

                outputTextView.append(responseString + "\n");

                testBroker();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                outputTextView.append("\n=====\n\nSTATUS: " + statusCode + "\n");

                try {
                    responseString = errorResponse.toString(2);
                } catch(Exception e) {
                    responseString = "COULD NOT PARSE: " + e;
                }

                outputTextView.append(responseString + "\n");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                outputTextView.append("\n=====\n\nSTATUS: " + statusCode);
                outputTextView.append("\nERROR: " + responseString + "\nException: " + throwable + "\n");
            }
        };

        tradeItClient.oAuthLink(broker, username, password, jsonHttpResponseHandler);
    }
}
