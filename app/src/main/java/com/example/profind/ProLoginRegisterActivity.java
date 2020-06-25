package com.example.profind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ProLoginRegisterActivity extends AppCompatActivity {

    private Button ProfessionalLoginButton;
    private Button ProfessionalRegisterButton;
    private TextView ProfessionalRegisterLink;
    private TextView professionalStatus;
    private EditText EmailProfessional;
    private EditText PasswordProfessional;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_login_register);



        ProfessionalLoginButton=(Button)findViewById(R.id.Professional_Login_btn);
        ProfessionalRegisterButton=(Button)findViewById(R.id.Professional_Register_btn);
        ProfessionalRegisterLink=(TextView)findViewById(R.id.Register_Professional_Link);
        professionalStatus=(TextView)findViewById(R.id.Professional_Status);

        EmailProfessional=(EditText)findViewById(R.id.Email_Professional);
        PasswordProfessional=(EditText)findViewById(R.id.Password_Professional);

        mAuth=FirebaseAuth.getInstance();

        loadingBar=new ProgressDialog(this);

        ProfessionalRegisterButton.setVisibility(View.INVISIBLE);
        ProfessionalRegisterButton.setEnabled(false);

        ProfessionalRegisterLink.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ProfessionalLoginButton.setVisibility(v.INVISIBLE);
                ProfessionalRegisterLink.setVisibility(v.INVISIBLE);

                professionalStatus.setText("Register as Professional");
                ProfessionalRegisterButton.setVisibility(v.VISIBLE);
                ProfessionalRegisterButton.setEnabled(true);

            }
        });


        ProfessionalRegisterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String email=EmailProfessional.getText().toString();
                String password=PasswordProfessional.getText().toString();

                RegisterProfessional(email,password);
            }
        });


        ProfessionalLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String email=EmailProfessional.getText().toString();
                String password=PasswordProfessional.getText().toString();

                SignInProfessional(email,password);

            }
        });




    }

    private void SignInProfessional(String email, String password)
    {

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(ProLoginRegisterActivity.this,"Please fill your email",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(ProLoginRegisterActivity.this,"Please choose your password",Toast.LENGTH_SHORT).show();
        }
        else
            {

            loadingBar.setTitle("Professional Login");
            loadingBar.setMessage("Please wait..checking your credentials");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(ProLoginRegisterActivity.this,"Successfully Logged In as a Professional",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Intent ProfessionalIntent=new Intent(ProLoginRegisterActivity.this,ProfessionalsMapsActivity.class);
                        startActivity(ProfessionalIntent);
                    }

                    else
                        {
                        Toast.makeText(ProLoginRegisterActivity.this,"Login Unsuccessfull ,please login again...",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });

        }


    }

    private void RegisterProfessional(String email, String password)
    {

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(ProLoginRegisterActivity.this,"Please fill your email",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(ProLoginRegisterActivity.this,"Please choose your password",Toast.LENGTH_SHORT).show();
        }
        else
            {

            loadingBar.setTitle("Professional Registration");
            loadingBar.setMessage("Please wait..");
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(ProLoginRegisterActivity.this,"Successfully Registered as a Professional",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Intent ProfessionalIntent=new Intent(ProLoginRegisterActivity.this,ProfessionalsMapsActivity.class);
                        startActivity(ProfessionalIntent);
                    }

                    else
                        {
                        Toast.makeText(ProLoginRegisterActivity.this,"Registration Unsuccessfull ,please register again...",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });

        }
    }
}

