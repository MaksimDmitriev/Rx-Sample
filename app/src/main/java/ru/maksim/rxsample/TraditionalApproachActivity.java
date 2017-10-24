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
                Log.d(TAG, "afterTextChanged");
            }
        });
        findViewById(R.id.btn).setOnClickListener(view -> {
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

        });
    }
}
