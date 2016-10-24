package prado.com.rews.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import prado.com.rews.R;

/**
 * Created by julianodotto on 19/10/16.
 */
public class SplashActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        Handler handler = new Handler();
        handler.postDelayed(this, 5000);
    }

    @Override
    public void run(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}