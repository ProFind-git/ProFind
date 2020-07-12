package com.example.profind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText registeredEmail;
    private Button resetPasswordBtn;
    private TextView goBack;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        registeredEmail=(EditText)findViewById(R.id.Forgot_password_email);
        resetPasswordBtn=(Button)findViewById(R.id.reset_password_btn);
        goBack=(TextView)findViewById(R.id.forgot_password_goback);

        firebaseAuth=FirebaseAuth.getInstance();


        registeredEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobacki=new Intent(ForgotPassword.this,ProLoginRegisterActivity.class);
                startActivity(gobacki);
            }
        });

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPasswordBtn.setEnabled(false);
                resetPasswordBtn.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.sendPasswordResetEmail(registeredEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "email sent successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(ForgotPassword.this, error, Toast.LENGTH_SHORT).show();

                        }
                        resetPasswordBtn.setEnabled(true);
                        resetPasswordBtn.setTextColor(Color.rgb(255,255,255));
                    }
                });

            }
        });

    }


    private void checkInputs(){
        if(TextUtils.isEmpty(registeredEmail.getText())){
            resetPasswordBtn.setEnabled(false);
            resetPasswordBtn.setTextColor(Color.argb(50,255,255,255));
        }else{
            resetPasswordBtn.setEnabled(true);
            resetPasswordBtn.setTextColor(Color.rgb(255,255,255));

        }
    }
}
