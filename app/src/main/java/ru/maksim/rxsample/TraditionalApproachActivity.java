package ru.maksim.rxsample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;

public class TraditionalApproachActivity extends AppCompatActivity {

    private static final String TAG = "TraditionalApproach";

    /*
        What's wrong with the code below?
        1. NetworkProvider.DataListener has an implicit reference to this activity > memory leak
        2. When a config change occurs in the middle of the request, MainActivity is re-created,
        and a new text watcher is added to the editText. Then beforeTextChanged, onTextChanged,
        and afterTextChanged are called. Which means a new data request.
        We finally get two responses (from the state before and after the config change)



        local data retrieval
        3.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traditional_approach);
        EditText editText = findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    Log.d(TAG, "afterTextChanged requesting");
                    requestData(editText);
                } else {
                    Log.d(TAG, "afterTextChanged - nothing to request");
                }
            }
        });
        findViewById(R.id.btn).setOnClickListener(view -> requestData(editText));
    }

    private void requestData(EditText editText) {
        String request = editText.getText().toString();
        NetworkProvider networkProvider = new NetworkProvider();
        networkProvider.requestData(request, new NetworkProvider.DataListener() {
            @Override
            public void onDataRequested(String data) {
                Log.d(TAG, "networkProvider onDataRequested data: " + data);
            }

            @Override
            public void onError() {
                Log.d(TAG, "networkProvider onError");
                LocalCacheProvider localCacheProvider = new LocalCacheProvider();
                new Thread(() -> {
                    String localCacheResponse = localCacheProvider.getLocalData(request,
                                                                      ((RadioButton) findViewById(
                                                                              R.id.local_cache)).isChecked()
                    );
                    if (localCacheResponse == null) {
                        Snackbar.make(findViewById(R.id.container), "Failed to get data", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "localCacheProvider response: " + localCacheResponse);
                    }

                }).start();
            }
        }, ((RadioButton) findViewById(R.id.network)).isChecked());
    }
}
