package com.nirmaan_bits.nirmaan.projects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nirmaan_bits.nirmaan.R;

public class edit_member extends AppCompatActivity {
    String name,num,year,key,email;
    EditText name_mem,num_mem,year_mem,email_mem;
    DatabaseReference databaseReference;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_member);
        switch (ProjectsFragment.project){

            case 1:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("gbbaas").child("members");
                break;
            case 2:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("gbcb").child("members");
                break;
            case 3:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("sap").child("members");
                break;
            case 4:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("pcd").child("members");
                break;
            case 5:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("sko").child("members");
                break;
            case 6:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("utkarsh").child("members");
                break;
            case 7:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("disha").child("members");
                break;
            case 8:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati1").child("members");
                break;
            case 9:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati2").child("members");
                break;
            case 10:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("youth").child("members");
                break;
            case 11:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("prd").child("members");
                break;
        }
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("name")!=null && bundle.getString("num")!=null &&bundle.getString("year")!=null &&  bundle.getString("key")!=null  )
        {
            name = bundle.getString("name");
            num = bundle.getString("num");
            year = bundle.getString("year");
            key =  bundle.getString("key");
            email =  bundle.getString("email");


            //TODO here get the string stored in the string variable and do
            // setText() on userName
        }
        name_mem = findViewById(R.id.name);
        num_mem = findViewById(R.id.ph_no);
        year_mem = findViewById(R.id.year);
        edit = findViewById(R.id.add_member);
        email_mem = findViewById(R.id.email);



        name_mem.setText(name);
        num_mem.setText(num);
        year_mem.setText(year);
        email_mem.setText(email);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child(key).child("name").setValue(name_mem.getText().toString());
                databaseReference.child(key).child("num").setValue(num_mem.getText().toString());

                databaseReference.child(key).child("year").setValue(year_mem.getText().toString());
                databaseReference.child(key).child("email").setValue(email_mem.getText().toString());

                name_mem.setText("");
                num_mem.setText("");
                year_mem.setText("");
                email_mem.setText("");

                Toast.makeText(edit_member.this,"Member details edited successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
