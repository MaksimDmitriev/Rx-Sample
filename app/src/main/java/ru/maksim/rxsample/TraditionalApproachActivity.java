package ru.maksim.rxsample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import ru.maksim.rxsample.api.LocalCacheProvider;
import ru.maksim.rxsample.api.NetworkProvider;

public class TraditionalApproachActivity extends AppCompatActivity implements NetworkProvider.DataListener {

    private static final String TAG = "TraditionalApproach";

    private NetworkRequestWrapper mNetworkRequestWrapper = new NetworkRequestWrapper();
    private TextView mResultTextView;
    private EditText mRequestEditText;

    /*
        What's wrong with the code below?
        + 1. NetworkProvider.DataListener has an implicit reference to this activity > memory leak
        + 2. When a config change occurs in the middle of a data request, MainActivity is re-created,
        and a new text watcher is added to the editText. Then beforeTextChanged, onTextChanged,
        and afterTextChanged are called. Which means a new data request.
        We finally get two responses (from the state before and after the config change).
        3. The UI and business logic are in the same class, MainActivity
        4. onStart() (hence, mNetworkRequestWrapper.setDataListener(this)) is called after
        mRequestEditText.addTextChangedListener() when the activity is re-created.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traditional_approach);
        mResultTextView = findViewById(R.id.result_text_view);
        mRequestEditText = findViewById(R.id.editText);
        mRequestEditText.addTextChangedListener(new TextWatcher() {
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
                    requestData(mRequestEditText.getText().toString());
                } else {
                    Log.d(TAG, "afterTextChanged - nothing to request");
                }
            }
        });
        findViewById(R.id.btn).setOnClickListener(view -> requestData(mRequestEditText.getText().toString()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNetworkRequestWrapper.setDataListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mNetworkRequestWrapper.setDataListener(null);
    }

    private void requestData(String request) {
        mNetworkRequestWrapper.requestData(
                request,
                ((RadioButton) findViewById(R.id.network)).isChecked()
        );
    }

    @Override
    public void onDataRequested(String data) {
        mResultTextView.setText(data);
    }

    @Override
    public void onError() {
        LocalCacheRetinalTask localCacheRetinalTask = new LocalCacheRetinalTask(
                this,
                ((RadioButton) findViewById(R.id.local_cache)).isChecked()
        );
        localCacheRetinalTask.execute(mRequestEditText.getText().toString());
    }

    private static class LocalCacheRetinalTask extends AsyncTask<String, Void, String> {

        private final boolean mSuccessEmulation;
        private WeakReference<Activity> mActivityWeakReference;

        LocalCacheRetinalTask(Activity activity, boolean successEmulation) {
            mActivityWeakReference = new WeakReference<>(activity);
            mSuccessEmulation = successEmulation;
        }

        @Override
        protected String doInBackground(String... params) {
            LocalCacheProvider localCacheProvider = new LocalCacheProvider();
            return localCacheProvider.getLocalData(params[0], mSuccessEmulation);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Activity activity = mActivityWeakReference.get();
            if (activity != null) {
                if (result == null) {
                    Snackbar.make(
                            activity.findViewById(R.id.container),
                            "Failed to get data from cache",
                            Snackbar.LENGTH_LONG
                    );
                } else {
                    TextView resultTextView = activity.findViewById(R.id.result_text_view);
                    resultTextView.setText(result);
                }
            } else {
                Log.d(TAG, "The activity has died. Unable to show the result: " + result);
            }
        }
    }
}
