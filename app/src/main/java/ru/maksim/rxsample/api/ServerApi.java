package ru.maksim.rxsample.api;


public interface ServerApi {

    String TAG = "ServerApi";

    String getData(String url);

    String getBalance(String url);
}
