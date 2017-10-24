package ru.maksim.rxsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final long DELAY = 6000L;
    /*
       go to the network.
       if there is a response from the network, show it to the user.
       if there is no data from the network within N (say 5 seconds), go to the local storage.
       Show local data to the user.
       if there is nothing in the local storage, show an error
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tradApproach(View view) {
        startActivity(new Intent(this, TraditionalApproachActivity.class));
    }

    public void rxApproach(View view) {
        startActivity(new Intent(this, RxApproachActivity.class));
    }
}
