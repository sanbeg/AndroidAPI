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

public class TradingActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    TextView outputTextView;
    Button tradeButton;
    EditText usernameEditText;
    EditText passwordEditText;
    Spinner brokerSpinner;

    String selectedBroker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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

        trade(selectedBroker, username, password);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedBroker = parent.getItemAtPosition(position).toString();

        if (selectedBroker == "Dummy") {
            usernameEditText.setText("dummy");
            passwordEditText.setText("dummy");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedBroker = "";
    }

    private void trade(String broker, String username, String password) {
        outputTextView.setText("HERE WE GO...");
        outputTextView.append("\n");

        // TODO: ADD SERVICE CALLS TO
        // https://github.com/obaro/SimpleWebAPI/blob/master/app/src/main/java/com/sample/foo/simplewebapi/MainActivity.java
        // http://www.androidauthority.com/use-remote-web-api-within-android-app-617869/
        // https://www.raywenderlich.com/78578/android-tutorial-for-beginners-part-3
        // http://code.tutsplus.com/tutorials/android-sdk-making-remote-api-calls--mobile-17568

    }
}
