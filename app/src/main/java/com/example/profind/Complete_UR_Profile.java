package com.example.profind;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Complete_UR_Profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        Spinner spinner;
        EditText firstname,lastname,city,workinghours,languages;
        EditText mobileno;
        EditText experience;
        EditText about;
        Button finish;
        ImageView profilePic;
        Uri imageUri;


        FirebaseDatabase firebaseDatabase;
        DatabaseReference reference;
        FirebaseStorage storage;
        StorageReference storageReference;

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
        profilePic=findViewById(R.id.profilePic);


        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();


//        profilePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                choosePicture();
//            }
//        });

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
                String category=spinner.getSelectedItem().toString().toLowerCase();

                profDataHelperClass helperClass=new profDataHelperClass(fname,lname,place,mobile,exp,desc,category);

                reference.child(mobile).setValue(helperClass);

                Intent exist=new Intent(Complete_UR_Profile.this,MainActivity.class);
                startActivity(exist);

            }
        });


    }

//    private void choosePicture() {
//        Intent intent=new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,1);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode ==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
//            imageUri=data.getData();
//            profilePic.setImageURI(imageUri);
//            uploadPicture();
//
//        }
//    }

//    private void uploadPicture() {
//        final String randomKey= UUID.randomUUID().toString();
//        StorageReference riversRef = storageReference.child("images/"+randomKey);
//
//        riversRef.putFile(imageUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Toast.makeText(Complete_UR_Profile.this,"Image Uploaded",Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                        // ...
//                        Toast.makeText(Complete_UR_Profile.this,"Image Upload Failed",Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String profession_category=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),profession_category,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
