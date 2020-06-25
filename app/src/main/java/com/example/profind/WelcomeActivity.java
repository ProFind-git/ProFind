package com.example.profind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    Button WelcomeCustomerButton;
    Button WelcomeProfessionalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        WelcomeCustomerButton=(Button)findViewById(R.id.welcome_cust_btn);
        WelcomeProfessionalButton=(Button)findViewById(R.id.welcome_prof_btn);

        WelcomeCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterCustomerIntent=new Intent(WelcomeActivity.this,CustLoginRegisterActivity.class);
                startActivity(LoginRegisterCustomerIntent);
            }
        });

        WelcomeProfessionalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginRegisterProfessionalIntent=new Intent(WelcomeActivity.this,ProLoginRegisterActivity.class);
                startActivity(LoginRegisterProfessionalIntent);
            }
        });
    }
}
