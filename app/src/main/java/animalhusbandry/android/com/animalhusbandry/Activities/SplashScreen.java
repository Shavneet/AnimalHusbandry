package animalhusbandry.android.com.animalhusbandry.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import animalhusbandry.android.com.animalhusbandry.R;

public class SplashScreen extends AppCompatActivity {

    ImageView splashImageView;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
       /* splashImageView = (ImageView) findViewById(R.id.splashImageView);
        Glide.with(this).load(R.drawable.giphy).into(splashImageView);*/


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}

