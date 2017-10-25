package ru.maksim.rxsample;


import ru.maksim.rxsample.api.NetworkProvider;

public class NetworkRequestWrapper {

    private NetworkProvider.DataListener mDataListener;

    public void setDataListener(NetworkProvider.DataListener dataListener) {
        mDataListener = dataListener;
    }

    public void requestData(String request, boolean successEmulation) {
        NetworkProvider networkProvider = new NetworkProvider();
        networkProvider.requestData(request, mDataListener, successEmulation);
    }
}
