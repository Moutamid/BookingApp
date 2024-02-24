package com.moutimid.bookingapp.authetications;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.bookingapp.R;


public class SplashActivity extends AppCompatActivity {

    private static int DELAY_TIME = 2800;

    ImageView imageView;
    TextView app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView = findViewById(R.id.imageView2);
        app_name = findViewById(R.id.app_name);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        },DELAY_TIME);
    }
}