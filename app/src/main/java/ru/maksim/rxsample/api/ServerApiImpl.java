package ru.maksim.rxsample.api;


import android.util.Log;

public class ServerApiImpl implements ServerApi {

    @Override
    public String getData(String url) {
        String result = null;
        try {
            Log.d(TAG, "getting data on " + Thread.currentThread().getId());
            Thread.sleep(4_000L);
            result = "User data from " + url;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getBalance(String url) {
        String result = null;
        try {
            Log.d(TAG, "getting balance on " + Thread.currentThread().getId());
            Thread.sleep(4_000L);
            result = "Balance from " + url;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
