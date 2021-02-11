package com.nirmaan_bits.nirmaan.projects;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nirmaan_bits.nirmaan.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class add_semplan extends AppCompatActivity{
DatabaseReference databaseReference;
EditText plan;
TextView deadlineTV;
ImageButton deadlineBt;
Button add_semplan;
private int mYear, mMonth, mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_semplan);
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




plan = findViewById(R.id.content);
deadlineTV = findViewById(R.id.deadlineTV);
deadlineBt = findViewById(R.id.deadlineBt);
add_semplan = findViewById(R.id.add_idea);

deadlineBt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(add_semplan.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        deadlineTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        deadlineTV.setTextColor(Color.WHITE);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
});


        add_semplan.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      if(plan.getText().toString().equals(""))
          Toast.makeText(add_semplan.this,"Enter plan", Toast.LENGTH_SHORT).show();
       else  {
            semplan sp = new semplan(plan.getText().toString(),"","no",deadlineTV.getText().toString());
        databaseReference.push().setValue(sp);
        Toast.makeText(add_semplan.this,"Semplan added successfully", Toast.LENGTH_SHORT).show();
       finish();}
    }
});

    }

}
