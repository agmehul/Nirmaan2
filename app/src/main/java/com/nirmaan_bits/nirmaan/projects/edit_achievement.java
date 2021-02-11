package com.nirmaan_bits.nirmaan.projects;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nirmaan_bits.nirmaan.R;

import java.util.Calendar;

public class edit_achievement extends AppCompatActivity{
    DatabaseReference databaseReference;
    EditText content;
    Button edit_achievement;
    String content_text,key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_achievements);
        switch (ProjectsFragment.project){

            case 1:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("gbbaas").child("achievements");
                break;
            case 2:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("gbcb").child("achievements");
                break;
            case 3:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("sap").child("achievements");
                break;
            case 4:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("pcd").child("achievements");
                break;
            case 5:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("sko").child("achievements");
                break;
            case 6:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("utkarsh").child("achievements");
                break;
            case 7:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("disha").child("achievements");
                break;
            case 8:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati1").child("achievements");
                break;
            case 9:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati2").child("achievements");
                break;
            case 10:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("youth").child("achievements");
                break;
        }

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("content")!=null &&  bundle.getString("key")!=null  )
        {
            content_text = bundle.getString("content");
            key =  bundle.getString("key");
        }


content = findViewById(R.id.content);

edit_achievement = findViewById(R.id.edit_achievement);
content.setText(content_text);


        edit_achievement.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      if(content.getText().toString().equals(""))
          Toast.makeText(edit_achievement.this,"Enter achievement", Toast.LENGTH_SHORT).show();
       else  {
          ach ach = new ach(content.getText().toString(),"");
        databaseReference.child(key).setValue(ach);
        Toast.makeText(edit_achievement.this,"Achievement edited successfully", Toast.LENGTH_SHORT).show();
        finish();}
    }

});

    }

}
