package com.example.profind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    Intent welcomeIntent=new Intent(SplashActivity.this,WelcomeActivity.class);
//                    startActivity(welcomeIntent);
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    if (currentUser == null) {
                        Intent welcomeIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                        startActivity(welcomeIntent);

                    } else {
                        Intent mainIntent = new Intent(SplashActivity.this, Complete_UR_Profile.class);
                        startActivity(mainIntent);
                    }

                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

}

//   @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        if (currentUser == null) {
//            Intent welcomeIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
//            startActivity(welcomeIntent);
//
//        } else {
//            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(mainIntent);
//        }
//    }
//}

