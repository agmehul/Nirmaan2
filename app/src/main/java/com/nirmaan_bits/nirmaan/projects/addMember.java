package com.nirmaan_bits.nirmaan.projects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nirmaan_bits.nirmaan.R;

public class addMember extends AppCompatActivity {
DatabaseReference databaseReference;
Query latest;
Integer sno;
EditText name,num,year,email;
Button add_member;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
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


        /* latest = databaseReference.orderByKey().limitToLast(1);
latest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String latestKey = childSnapshot.getKey();
                     sno = Integer.valueOf(latestKey) + 1;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(addMember.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

final project_member pm = new project_member();
name = findViewById(R.id.name);
num = findViewById(R.id.ph_no);
year = findViewById(R.id.year);
email = findViewById(R.id.email);

add_member = findViewById(R.id.add_member);


add_member.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      if(name.getText().toString().equals(""))
          Toast.makeText(addMember.this,"Enter name", Toast.LENGTH_SHORT).show();
       else if(num.getText().toString().equals(""))
            Toast.makeText(addMember.this,"Enter phone no.", Toast.LENGTH_SHORT).show();
       else if(year.getText().toString().equals(""))
            Toast.makeText(addMember.this,"Enter year", Toast.LENGTH_SHORT).show();
      else if(email.getText().toString().equals(""))
          Toast.makeText(addMember.this,"Enter email", Toast.LENGTH_SHORT).show();
        else  {
            pm.setName( name.getText().toString());
        pm.setNum( num.getText().toString());
        pm.setYear( year.getText().toString());
        pm.setEmail( email.getText().toString());
        databaseReference.push().setValue(pm);
        name.setText("");
        num.setText("");
        year.setText("");
          email.setText("");

          Toast.makeText(addMember.this,"Member added successfully", Toast.LENGTH_SHORT).show();}
    }
});

    }
}
