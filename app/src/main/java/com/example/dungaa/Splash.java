package com.example.dungaa;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    ImageView logo;
    TextView hello,dunga;
    RelativeLayout layout;
    private static int splashTimeOut=6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo=findViewById(R.id.splash_logo);
        hello=findViewById(R.id.hello);
        dunga=findViewById(R.id.splash_dunga);
        layout=findViewById(R.id.layout);
        Typeface myTypeFace=Typeface.createFromAsset(getAssets(),"fonts/chapline.otf");
        dunga.setTypeface(myTypeFace);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(Splash.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        },splashTimeOut);

        Animation mysplash= AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        logo.startAnimation(mysplash);
        hello.startAnimation(mysplash);
        dunga.startAnimation(mysplash);
        layout.startAnimation(mysplash);
    }
}
