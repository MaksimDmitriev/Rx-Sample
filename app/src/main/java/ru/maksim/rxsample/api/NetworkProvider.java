package ru.maksim.rxsample.api;

import ru.maksim.rxsample.MainActivity;

public class NetworkProvider {

    public void requestData(String request, DataListener dataListener, boolean successEmulation) {
        new Thread(() -> {
            try {
                Thread.sleep(MainActivity.DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (dataListener != null) {
                    if (successEmulation) {
                        dataListener.onDataRequested(request + " - new data from the network");
                    } else {
                        dataListener.onError();
                    }
                }
            }
        }).start();
    }

    public interface DataListener {

        void onDataRequested(String data);

        void onError();
    }
}
