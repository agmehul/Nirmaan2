package com.nirmaan_bits.nirmaan.projects;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.Contact;
import com.nirmaan_bits.nirmaan.MainActivity;
import com.nirmaan_bits.nirmaan.R;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;
import com.nirmaan_bits.nirmaan.idea.idea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class utkarsh_members extends AppCompatActivity implements utkarsh_members_adapter.OnItemClickListener {

    private RecyclerView recyclerView;

    private DatabaseReference databaseReference;
    private FloatingActionButton fb;
    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    private DatabaseReference databaseReference_contacts;
    boolean excom = false;
    List<Contact> list = new ArrayList<>();
    List<String> list_keys = new ArrayList<>();
    utkarsh_members_adapter adapter;




    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utkarsh_activity_members);
        list.clear();
        list_keys.clear();
        fb = findViewById(R.id.addMember);
        if(MainActivity.if_pl == 1 && ProjectsFragment.project == MyFirebaseSrevice.userProp) {
            fb.setVisibility(View.VISIBLE);
        }
        if(currentuser.substring(0,9).matches("[A-Za-z0-9]+")){
            databaseReference_contacts= FirebaseDatabase.getInstance().getReference().child("contactsMailIndexed").child(currentuser.substring(0,9));
            databaseReference_contacts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        fb.setVisibility(View.VISIBLE);
                        excom=true;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });}

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(utkarsh_members.this,addMember.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.member_recycle);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(utkarsh_members.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new utkarsh_members_adapter(utkarsh_members.this, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(utkarsh_members.this);
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
        Query plsFirst = databaseReference.orderByChild("pl");
        plsFirst.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String key = dataSnapshot.getKey();
                if(!list_keys.contains(key)) {
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
                    list_keys.add(key);
                    adapter.notifyItemInserted(list.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
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
                int ideaIndex = list_keys.indexOf(key);
                if (ideaIndex > -1) {
                    list.set(ideaIndex, member);
                    adapter.notifyItemChanged(ideaIndex);
                } else {
                   // Log.w(TAG, "onChildChanged:unknown_child:" + key);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int ideaIndex = list_keys.indexOf(key);
                if (ideaIndex > -1) {
                    // Replace with the new data
                    list_keys.remove(ideaIndex);
                    list.remove(ideaIndex);

                    // Update the RecyclerView
                    adapter.notifyItemRemoved(ideaIndex);
                } else {
                    //Log.w(TAG, "onChildRemoved:unknown_child:" + key);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onEditClick(int position){
        if((MainActivity.if_pl == 1 && ProjectsFragment.project == MyFirebaseSrevice.userProp)|| excom){
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
        if((MainActivity.if_pl == 1 && ProjectsFragment.project == MyFirebaseSrevice.userProp)|| excom) {
            databaseReference.child(list.get(position).getKey()).removeValue();
            /*int ideaIndex = list_keys.indexOf(list.get(position).getKey());
            list_keys.remove(ideaIndex);
            list.remove(ideaIndex);
            adapter.notifyItemRemoved(ideaIndex);*/
        }
        else Toast.makeText(utkarsh_members.this,"Only PL's are allowed to delete members",Toast.LENGTH_SHORT).show();

    }

}
