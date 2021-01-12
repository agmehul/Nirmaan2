package com.nirmaan_bits.nirmaan.projects;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.Contact;
import com.nirmaan_bits.nirmaan.MainActivity;
import com.nirmaan_bits.nirmaan.R;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class utkarsh_members extends AppCompatActivity implements utkarsh_members_adapter.OnItemClickListener {

    private RecyclerView recyclerView;

    private DatabaseReference databaseReference;
    private FloatingActionButton fb;


    List<Contact> list = new ArrayList<>();
    utkarsh_members_adapter adapter;




    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utkarsh_activity_members);
        fb = findViewById(R.id.addMember);
        if(MainActivity.if_pl == 1 && ProjectsFragment.project == MyFirebaseSrevice.userProp) {
            fb.setVisibility(View.VISIBLE);
        }

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(utkarsh_members.this,addMember.class);
                startActivity(intent);
            }
        });






        recyclerView = findViewById(R.id.member_recycle);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(  utkarsh_members.this));
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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();

                    String  mName = dataSnapshot.child("name").getValue().toString();
                    String  mNumb = dataSnapshot.child("num").getValue().toString();
                    String  mYear = dataSnapshot.child("year").getValue().toString();
                    String  email = dataSnapshot.child("email").getValue().toString();
                    String pl;

                 if( dataSnapshot.child("pl").getValue() != null)    pl = "PL";
                 else pl = "";
                    int visits =0;
                    int total =0;
                    if (dataSnapshot.child("history").getValue() != null){
                        for (DataSnapshot snapshot1 : dataSnapshot.child("history").getChildren()) {
                            if (snapshot1.child("status").getValue().equals("Present")){
                                visits++;

                            }
                            total++;
                        }
                    }
                    if (dataSnapshot.child("history").getValue() == null){total =1;}

                    Contact member = new Contact(mName,mYear,mNumb,pl,key,"VISITS : " + visits+" || "+(visits/total)*100+"%",email);


                    list.add(member);
                }
                adapter = new utkarsh_members_adapter(utkarsh_members.this, list);

                recyclerView.setAdapter(adapter);
                 adapter.setOnItemClickListener(utkarsh_members.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onWhatEverClick(int position) throws IOException {
        if(MainActivity.if_pl == 1 && ProjectsFragment.project == MyFirebaseSrevice.userProp){
        Intent intent = new Intent(utkarsh_members.this,edit_member.class);

        intent.putExtra("name",list.get(position).getName());
        intent.putExtra("num",list.get(position).getNum());

        intent.putExtra("year",list.get(position).getYear());
        intent.putExtra("key",list.get(position).getKey());
        intent.putExtra("email",list.get(position).getEmail());


        startActivity(intent);}
        else  Toast.makeText(utkarsh_members.this,"Only PL's are allowed to edit details",Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDeleteClick(int position) {
        if(MainActivity.if_pl == 1 && ProjectsFragment.project == MyFirebaseSrevice.userProp) {
            databaseReference.child(list.get(position).getKey()).removeValue();
            adapter.notifyDataSetChanged();
        }
        else Toast.makeText(utkarsh_members.this,"Only PL's are allowed to delete members",Toast.LENGTH_SHORT).show();

    }

}
