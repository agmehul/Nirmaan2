package com.nirmaan_bits.nirmaan.idea;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nirmaan_bits.nirmaan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class add_idea extends AppCompatActivity{
DatabaseReference databaseReference;
EditText content;
Spinner project;
Button add_idea;
String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_idea);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ideas");


content = findViewById(R.id.content);
project = findViewById(R.id.project_idea);
add_idea = findViewById(R.id.add_idea);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("All nirmaan");
        categories.add("GB BASS");
        categories.add("DISHA");
        categories.add("PKP");
        categories.add("SAP");
        categories.add("PCD");
        categories.add("SKO");
        categories.add("UTKARSH");
        categories.add("UNNATI 1");
        categories.add("UNNATI 2");
        categories.add("YOUTH");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_semplan, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        project.setAdapter(dataAdapter);



        add_idea.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      if(content.getText().toString().equals(""))
          Toast.makeText(add_idea.this,"Enter idea", Toast.LENGTH_SHORT).show();
       else  {
          Date c = Calendar.getInstance().getTime();
          System.out.println("Current time => " + c);

          SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
          String formattedDate = df.format(c);
            idea sp = new idea(currentuser,content.getText().toString(),formattedDate,project.getSelectedItem().toString(),0,"");
        databaseReference.push().setValue(sp);
        Toast.makeText(add_idea.this,"Idea added successfully", Toast.LENGTH_SHORT).show();
       finish();}
    }
});

    }

}
