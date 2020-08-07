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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CustLoginRegisterActivity extends AppCompatActivity {
    private Button CustomerLoginButton;
    private Button CustomerRegisterButton;
    private TextView CustomerRegisterLink;
    private TextView CustomerStatus;

    private EditText EmailCustomer;
    private EditText PasswordCustomer;

    private ProgressDialog loadingBar;
    private EditText CustomerName;
    private TextView CustomerForgotPassword;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_login_register);

        CustomerRegisterButton = (Button) findViewById(R.id.Customer_Register_btn);
        CustomerLoginButton = (Button) findViewById(R.id.Customer_Login_btn);
        CustomerRegisterLink = (TextView) findViewById(R.id.Register_Customer_Link);
        CustomerStatus = (TextView) findViewById(R.id.Customer_Status);

        EmailCustomer = (EditText) findViewById(R.id.Email_Customer);
        PasswordCustomer = (EditText) findViewById(R.id.Password_Customer);
        CustomerName=(EditText)findViewById(R.id.Name_Customer);
        CustomerForgotPassword=(TextView)findViewById(R.id.Forgot_Password_Customer);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        loadingBar = new ProgressDialog(this);

        CustomerName.setVisibility(View.INVISIBLE);
        CustomerName.setEnabled(false);

        CustomerRegisterButton.setVisibility(View.INVISIBLE);
        CustomerRegisterButton.setEnabled(false);

        CustomerRegisterLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CustomerLoginButton.setVisibility(v.INVISIBLE);
                CustomerRegisterLink.setVisibility(v.INVISIBLE);
                CustomerForgotPassword.setVisibility(v.INVISIBLE);

                CustomerStatus.setText("Register Customer");
                CustomerRegisterButton.setVisibility(v.VISIBLE);
                CustomerRegisterButton.setEnabled(true);
                CustomerName.setVisibility(v.VISIBLE);
                CustomerName.setEnabled(true);

                Toast.makeText(CustLoginRegisterActivity.this,"Please Check your Connectivity",Toast.LENGTH_LONG).show();

            }
        });
        CustomerRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailCustomer.getText().toString();
                String password = PasswordCustomer.getText().toString();

                RegisterCustomer(email, password);
            }
        });


        CustomerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailCustomer.getText().toString();
                String password = PasswordCustomer.getText().toString();

                SignInCustomer(email,password);
            }
        });

        CustomerForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onForgotPassword=true;
                Intent forgotPassword=new Intent(CustLoginRegisterActivity.this,ForgotPassword.class);
                startActivity(forgotPassword);
            }
        });
    }



    private void SignInCustomer(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(CustLoginRegisterActivity.this, "Please fill your email", Toast.LENGTH_SHORT).show();

        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(CustLoginRegisterActivity.this, "Please choose your password", Toast.LENGTH_SHORT).show();
        } else {

            loadingBar.setTitle("Customer Login");
            loadingBar.setMessage("Please wait..Checking Your Credentials");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(CustLoginRegisterActivity.this, "Successfully Logged in  as a Customer", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Intent exist=new Intent(CustLoginRegisterActivity.this,FindProfessionals.class);
                        startActivity(exist);
                    } else {
                        Toast.makeText(CustLoginRegisterActivity.this, "Login Unsuccessfull ,please login again...", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });


        }


    }

    private void RegisterCustomer(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(CustLoginRegisterActivity.this, "Please fill your email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(CustLoginRegisterActivity.this, "Please choose your password", Toast.LENGTH_SHORT).show();
        } else {

            loadingBar.setTitle("Customer Registration");
            loadingBar.setMessage("Please wait..");
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Map<Object,String> userdata=new HashMap<>();
                        userdata.put("name",CustomerName.getText().toString());

                        firebaseFirestore.collection("CUSTOMERS").add(userdata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(CustLoginRegisterActivity.this,"Name saved",Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();


                                }
                                else {
                                    Toast.makeText(CustLoginRegisterActivity.this,"Name not Saved",Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }
                            }

                    });

                        Toast.makeText(CustLoginRegisterActivity.this, "Successfully Registered as a Customer", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        String customerName=CustomerName.getEditableText().toString();
                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Customer").child(user.getUid());
                        ref.child("name").setValue(customerName);
                        Intent cust=new Intent(CustLoginRegisterActivity.this,FindProfessionals.class);
                        startActivity(cust);
                    }
                    else {
                        Toast.makeText(CustLoginRegisterActivity.this, "Registration Unsuccessfull ,please register again...", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });


        }

    }
}