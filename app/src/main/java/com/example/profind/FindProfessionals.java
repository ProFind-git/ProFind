package com.example.profind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FindProfessionals extends AppCompatActivity {
    private EditText mSearchField;
    private Button mSearchBtn;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_professionals);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUserDatabase= FirebaseDatabase.getInstance().getReference("profdata");

        mSearchField=(EditText)findViewById(R.id.search_field);
        mSearchBtn=(Button) findViewById(R.id.search_btn);
        mResultList=(RecyclerView)findViewById(R.id.Result_list);

        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText =mSearchField.getText().toString().toLowerCase();


                firebaseProfSearch(searchText);

            }

        });



    }


    private void firebaseProfSearch(String searchText) {
        if(TextUtils.isEmpty(searchText))
        {
            Toast.makeText(FindProfessionals.this,"Please type specific category",Toast.LENGTH_SHORT).show();
        }else{
        Toast.makeText(FindProfessionals.this,"Search Initiated",Toast.LENGTH_SHORT).show();

        Query firebaseSearchQuery=mUserDatabase.orderByChild("category").startAt(searchText).endAt(searchText+"\uf8ff");

//         FirebaseRecyclerAdapter<Professionals,ProfessionalsViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Professionals, ProfessionalsViewHolder>(
//
//                Professionals.class,
//                R.layout.list_layout,
//                ProfessionalsViewHolder.class,
//                mUserDatabase
//        ) {
//            @Override
//            protected void onBindViewHolder(@NonNull ProfessionalsViewHolder holder, int position, @NonNull Professionals model) {
//                holder.setDetails(model.getFirstName(),model.getCategory());
//
//            }
//
//            @NonNull
//            @Override
//            public ProfessionalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//        };

        FirebaseRecyclerOptions<Professionals> options= new FirebaseRecyclerOptions.Builder<Professionals>().setQuery(firebaseSearchQuery,Professionals.class).build();
        FirebaseRecyclerAdapter firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Professionals,ProfessionalsViewHolder>(options){
            @NonNull
            @Override
            public ProfessionalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
                return new ProfessionalsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProfessionalsViewHolder holder, int position, @NonNull Professionals model) {
                holder.setDetails(model.getFirstName(),model.getMobileNo(),model.getCategory(),model.getCity());
            }
        };
        firebaseRecyclerAdapter.startListening();
        mResultList.setAdapter(firebaseRecyclerAdapter);}
    }


    public static class ProfessionalsViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public ProfessionalsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setDetails(String profName, String phone, String profession,String city){
            TextView _name=(TextView)mView.findViewById(R.id.name_text);
            TextView _category=(TextView)mView.findViewById(R.id.profession);
            TextView _mobile=(TextView)mView.findViewById(R.id.phone);
            TextView _city=(TextView)mView.findViewById(R.id.city);

            _name.setText(profName);
            _mobile.setText(phone);
            _category.setText(profession);
            _city.setText(city);
        }

//
//        public void setDetails(String name,String mobileNo, String category, String city) {
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,WelcomeActivity.class));
                break;
        }

        return true;
    }
}
