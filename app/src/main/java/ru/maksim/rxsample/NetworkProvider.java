package ru.maksim.rxsample;

/**
 * Created by maksim on 17.10.17.
 */

public class NetworkProvider {

    public void requestData(String request, DataListener dataListener, boolean successEmulation) {
        new Thread(() -> {
            try {
                Thread.sleep(MainActivity.DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (successEmulation) {
                    dataListener.onDataRequested(request + " - new data from the network");
                } else {
                    dataListener.onError();
                }
            }
        }).start();
    }

    /**
     * Created by maksim on 17.10.17.
     */

    interface DataListener {

        void onDataRequested(String data);

        void onError();
    }
}
