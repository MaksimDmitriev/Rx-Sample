package ru.maksim.rxsample;

import android.app.Application;
import android.content.Context;

/**
 * Created by maksim on 15.10.17.
 */

public class App extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getsContext() {
        return sContext;
    }
}
