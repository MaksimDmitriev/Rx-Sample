package ru.maksim.rxsample.api;

import ru.maksim.rxsample.MainActivity;

/**
 * Created by maksim on 17.10.17.
 */

public class LocalCacheProvider {

    public String getLocalData(String request, boolean successEmulation) {
        try {
            Thread.sleep(MainActivity.DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return successEmulation ? (request + " local data") : null;
    }
}
