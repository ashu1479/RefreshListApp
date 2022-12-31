package com.ashwini.refreshlistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.splashscreen.SplashScreenViewProvider;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

public class SplashScreenActivity extends AppCompatActivity {
    Handler handler;
    SplashScreen splashScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashScreen = SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_splash_screen);
        keepSplashScreenFor5Seconds();
    }
    private void keepSplashScreenFor5Seconds() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            View content = (View)findViewById(android.R.id.content);
            content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },5000);
                    return false;
                }
            });

            // custom exit on splashScreen
            splashScreen.setOnExitAnimationListener(new SplashScreen.OnExitAnimationListener() {
                                                        @Override
                                                        public void onSplashScreenExit(@NonNull SplashScreenViewProvider splashScreenViewProvider) {
                                                            @SuppressLint("ObjectAnimatorBinding") ObjectAnimator anim = ObjectAnimator.ofFloat(splashScreenViewProvider, "rotation", 0f, 90f);
                                                            anim.setDuration(5000);                  // Duration in milliseconds
                                                            anim.start();

                                                        }
                                                    }
            );
        }else{
            handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },5000);
        }
    }
}