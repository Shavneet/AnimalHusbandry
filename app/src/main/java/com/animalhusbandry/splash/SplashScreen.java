package com.animalhusbandry.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.animalhusbandry.R;
import com.animalhusbandry.dashboard.DashboardActivity;
import com.animalhusbandry.login.LoginActivity;
import com.animalhusbandry.utils.SessionManager;

public class SplashScreen extends AppCompatActivity {

    ImageView splashImageView;
    private static int SPLASH_TIME_OUT = 3000;
    public SharedPreferences sharedPreferences;
    public String strUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
       /* splashImageView = (ImageView) findViewById(R.id.splashImageView);
        Glide.with(this).load(R.drawable.giphy).into(splashImageView);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginCredentials();
            }
        }, SPLASH_TIME_OUT);

}

    private void checkLoginCredentials() {

        SessionManager session=new SessionManager(getBaseContext());
        session.checkLogin();
        if (session.isLoggedIn()){
            Intent i = new Intent(SplashScreen.this, DashboardActivity.class);
            startActivity(i);
            finish();
        }
        else {
            Intent i = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}