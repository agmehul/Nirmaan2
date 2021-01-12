package com.nirmaan_bits.nirmaan.projects;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nirmaan_bits.nirmaan.R;

import java.util.ArrayList;
import java.util.List;

public class edit_semplan extends AppCompatActivity{
DatabaseReference databaseReference;
EditText plan,weight;
Spinner complete;
Button edit_semplan;
String plan_text,complete_text,weight_text,key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_semplan);
        switch (ProjectsFragment.project){

            case 1:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("gbbaas").child("semplan");
                break;
            case 2:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("gbcb").child("semplan");
                break;
            case 3:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("sap").child("semplan");
                break;
            case 4:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("pcd").child("semplan");
                break;
            case 5:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("sko").child("semplan");
                break;
            case 6:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("utkarsh").child("semplan");
                break;
            case 7:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("disha").child("semplan");
                break;
            case 8:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati1").child("semplan");
                break;
            case 9:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati2").child("semplan");
                break;
            case 10:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("youth").child("semplan");
                break;
        }

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("plan")!=null && bundle.getString("complete")!=null &&bundle.getString("weight")!=null &&  bundle.getString("key")!=null  )
        {
            plan_text = bundle.getString("plan");
            complete_text = bundle.getString("complete");
            weight_text = bundle.getString("weight");
            key =  bundle.getString("key");


            //TODO here get the string stored in the string variable and do
            // setText() on userName
        }


plan = findViewById(R.id.content);
weight = findViewById(R.id.edit_weight);
complete = findViewById(R.id.edit_complete);
edit_semplan = findViewById(R.id.edit_semplan);
plan.setText(plan_text);
weight.setText(weight_text);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("no");
        categories.add("yes");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_semplan, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_item_semplan);

        // attaching data adapter to spinner
        complete.setAdapter(dataAdapter);
        if(complete_text.equals("yes")){
            complete.setSelection(1);}



        edit_semplan.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      if(plan.getText().toString().equals(""))
          Toast.makeText(edit_semplan.this,"Enter plan", Toast.LENGTH_SHORT).show();
       else if(weight.getText().toString().equals(""))
            Toast.makeText(edit_semplan.this,"Enter weight", Toast.LENGTH_SHORT).show();
       else  {
            semplan sp = new semplan(plan.getText().toString(),"",complete.getSelectedItem().toString(),weight.getText().toString());
        databaseReference.child(key).setValue(sp);
        Toast.makeText(edit_semplan.this,"Semplan edited successfully", Toast.LENGTH_SHORT).show();
        finish();}
    }

});

    }

}
