package ru.maksim.rxsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.maksim.rxsample.api.ServerApi;
import ru.maksim.rxsample.api.ServerApiImpl;


public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        findViewById(R.id.get_data).setOnClickListener((View view) -> {

            String url = "data_url";
            Observable<String> observable = Observable.just(url).flatMap(s -> Observable.fromCallable(() -> {
                ServerApi serverApi = new ServerApiImpl();
                String result = serverApi.getData(s);
                return result;
            }).subscribeOn(Schedulers.io()).doOnNext(
                    s1 -> Log.d(ServerApi.TAG, "the data from the server is " + s1)
            ));
            observable.test().awaitTerminalEvent();
        });
    }

    private int doWork(int sleepMilli) {
        try {
            Log.d("ServerApi", "getting data on " + Thread.currentThread().getId());
            Thread.sleep(sleepMilli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
