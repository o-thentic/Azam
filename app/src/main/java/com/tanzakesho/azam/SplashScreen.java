package com.tanzakesho.azam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

public class SplashScreen extends Activity {

    private final int SLEEP_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Thread() {
            @Override
            public void run() {
                int count = 0;

                try {
                    super.run();
                    while(count < SLEEP_TIME){
                        sleep(100);
                        count += 100;
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    endSplashScreen();
                }

            }
        }.start();

    }

    public void endSplashScreen(){

        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMan.getActiveNetworkInfo();
        System.out.println("This network is " + networkInfo.isConnected());

        if (networkInfo != null && networkInfo.isAvailable()) {
            startActivity(new Intent(SplashScreen.this, Home.class));
            finish();

        } else {
            Toast.makeText(this, "Connect To The Internet\nAnd Restart The App", Toast.LENGTH_LONG).show();
        }

    }
}
