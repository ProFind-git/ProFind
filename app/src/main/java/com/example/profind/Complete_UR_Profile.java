package com.example.profind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Complete_UR_Profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        Spinner spinner;
        EditText firstname,lastname,city,workinghours,languages;
        EditText mobileno;
    EditText experience;
        EditText about;
        Button finish;


        FirebaseDatabase firebaseDatabase;
        DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete__u_r__profile);


        spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.professions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        firstname=(EditText)findViewById(R.id.f_name);
        lastname=(EditText)findViewById(R.id.l_name);
        mobileno=(EditText)findViewById(R.id.Ph_no);
        experience=(EditText)findViewById(R.id.experience);
        about=(EditText)findViewById(R.id.about);
        city=(EditText)findViewById(R.id.city);
        workinghours=(EditText)findViewById(R.id.w_hrs);
        languages=(EditText)findViewById(R.id.languages);
        finish=(Button)findViewById(R.id.finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase=FirebaseDatabase.getInstance();
                reference=firebaseDatabase.getReference("profdata");


                String fname=firstname.getEditableText().toString();
                String lname=lastname.getEditableText().toString();
                String place=city.getEditableText().toString();
                String mobile=mobileno.getEditableText().toString();
                String exp=experience.getEditableText().toString();
                String lang=languages.getEditableText().toString();
                String desc=about.getEditableText().toString();
                String category=spinner.getSelectedItem().toString();

                profDataHelperClass helperClass=new profDataHelperClass(fname,lname,mobile,exp,desc,category);

                reference.child(mobile).setValue(helperClass);

                Intent exist=new Intent(Complete_UR_Profile.this,MainActivity.class);
                startActivity(exist);

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String profession_category=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),profession_category,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
