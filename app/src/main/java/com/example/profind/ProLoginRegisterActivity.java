package com.example.profind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProLoginRegisterActivity extends AppCompatActivity {

    private Button ProfessionalLoginButton;
    private Button ProfessionalRegisterButton;
    private TextView ProfessionalRegisterLink;
    private TextView professionalStatus;
    private EditText EmailProfessional;
    private EditText PasswordProfessional;
    private ProgressDialog loadingBar;
    private EditText ProfessionalName;
    private TextView ProfessionalForgotPassword;

    public static boolean onForgotPassword;

    public boolean profileCompleted=false;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;


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
        ProfessionalName=(EditText)findViewById(R.id.Name_Professional);
        ProfessionalForgotPassword=(TextView)findViewById(R.id.Forgot_Password_Professional);
        firebaseFirestore=FirebaseFirestore.getInstance();

        mAuth=FirebaseAuth.getInstance();

        loadingBar=new ProgressDialog(this);

        ProfessionalName.setVisibility(View.INVISIBLE);
        ProfessionalName.setEnabled(false);

        ProfessionalRegisterButton.setVisibility(View.INVISIBLE);
        ProfessionalRegisterButton.setEnabled(false);

        ProfessionalRegisterLink.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ProfessionalLoginButton.setVisibility(v.INVISIBLE);
                ProfessionalRegisterLink.setVisibility(v.INVISIBLE);
                ProfessionalForgotPassword.setVisibility(v.INVISIBLE);

                professionalStatus.setText("Register as Professional");
                ProfessionalRegisterButton.setVisibility(v.VISIBLE);
                ProfessionalRegisterButton.setEnabled(true);
                ProfessionalName.setVisibility(v.VISIBLE);
                ProfessionalName.setEnabled(true);

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



        ProfessionalForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForgotPassword=true;
                Intent forgotPassword=new Intent(ProLoginRegisterActivity.this,ForgotPassword.class);
                startActivity(forgotPassword);
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
                        Intent exist=new Intent(ProLoginRegisterActivity.this,MainActivity.class);
                        startActivity(exist);


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
                        Map<Object,String> userdata=new HashMap<>();
                        userdata.put("name",ProfessionalName.getText().toString());

                        firebaseFirestore.collection("PROFESSIONALS").add(userdata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ProLoginRegisterActivity.this,"Name saved",Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();


                                }
                                else {
                                    Toast.makeText(ProLoginRegisterActivity.this,"Name not Saved",Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }
                            }
                        });


                        Toast.makeText(ProLoginRegisterActivity.this,"Successfully Registered as a Professional",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Intent non_exist=new Intent(ProLoginRegisterActivity.this,Complete_UR_Profile.class);
                        startActivity(non_exist);

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

