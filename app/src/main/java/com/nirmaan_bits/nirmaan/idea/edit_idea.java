package com.nirmaan_bits.nirmaan.idea;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class edit_idea extends AppCompatActivity{
DatabaseReference databaseReference;
EditText content;
Spinner project;
Button edit_idea;
String content_text,project_text,key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_idea);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ideas");
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("content")!=null && bundle.getString("project")!=null && bundle.getString("key")!=null)
        {
            content_text = bundle.getString("content");
            project_text = bundle.getString("project");
            key = bundle.getString("key");
        }

content = findViewById(R.id.content);
project = findViewById(R.id.project_idea);
edit_idea = findViewById(R.id.edit_idea);
content.setText(content_text);
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
        if(project_text.equals("All nirmaan")){
            project.setSelection(0);}
        else if (project_text.equals("GB BASS")){
            project.setSelection(1);}
        else if (project_text.equals("DISHA")){
            project.setSelection(2);}
        else if (project_text.equals("PKP")){
            project.setSelection(3);}
        else if (project_text.equals("SAP")){
            project.setSelection(4);}
        else if (project_text.equals("PCD")){
            project.setSelection(5);}
        else if (project_text.equals("SKO")){
            project.setSelection(6);}
        else if (project_text.equals("UTKARSH")){
            project.setSelection(7);}
        else if (project_text.equals("UNNATI 1")){
            project.setSelection(8);}
        else if (project_text.equals("UNNATI 2")){
            project.setSelection(9);}
        else if (project_text.equals("YOUTH")){
            project.setSelection(10);}


        edit_idea.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      if(content.getText().toString().equals(""))
          Toast.makeText(edit_idea.this,"Enter idea", Toast.LENGTH_SHORT).show();
       else  {
            //idea sp = new idea(content.getText().toString(),formattedDate,project.getSelectedItem().toString(),0,"");
        databaseReference.child(key).child("content").setValue(content.getText().toString());
        databaseReference.child(key).child("project").setValue(project.getSelectedItem().toString());
        Toast.makeText(edit_idea.this,"Idea edited successfully", Toast.LENGTH_SHORT).show();
       finish();}
    }
});
    }

}
