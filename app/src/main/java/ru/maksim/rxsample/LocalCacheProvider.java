package ru.maksim.rxsample;

/**
 * Created by maksim on 17.10.17.
 */

public class LocalCacheProvider {

    public String getLocalData(String request, boolean successEmulation) {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return successEmulation ? (request + " local data") : null;
    }
}
