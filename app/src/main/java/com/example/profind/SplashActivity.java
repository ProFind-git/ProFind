package com.example.profind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
                    sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    Intent welcomeIntent=new Intent(SplashActivity.this,WelcomeActivity.class);
//                    startActivity(welcomeIntent);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference profess = FirebaseDatabase.getInstance().getReference().child("Professional");
                    DatabaseReference custom = FirebaseDatabase.getInstance().getReference().child("Customer");

                    if (user != null) {
                        profess.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Intent intent = new Intent(SplashActivity.this, FindProfessionals.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                    }
                    else{
                        Intent startIntent = new Intent(SplashActivity.this,WelcomeActivity.class);
                        startActivity(startIntent);
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

