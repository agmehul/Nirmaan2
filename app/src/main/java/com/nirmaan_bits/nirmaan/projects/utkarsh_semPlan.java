package com.nirmaan_bits.nirmaan.projects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.MainActivity;
import com.nirmaan_bits.nirmaan.R;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;
import com.nirmaan_bits.nirmaan.idea.idea;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class utkarsh_semPlan extends AppCompatActivity implements utkarsh_semplan_adapter.OnItemClickListener{
    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference_contacts;
    private RecyclerView recyclerView;
    private FloatingActionButton fb_semplan;
    List<semplan> list = new ArrayList<>();
    List<String> list_keys = new ArrayList<>();
    utkarsh_semplan_adapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(this, "on create called of utkarsh semplan "+MainActivity.if_pl+" " +MyFirebaseSrevice.userProp, Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utkarsh_activity_sem_plan);
        list.clear();
        list_keys.clear();
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

        recyclerView=findViewById(R.id.planRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new utkarsh_semplan_adapter(utkarsh_semPlan.this, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(utkarsh_semPlan.this);
        fb_semplan = findViewById(R.id.add_sem);
        if(currentuser.substring(0,9).matches("[A-Za-z0-9]+")){
            databaseReference_contacts= FirebaseDatabase.getInstance().getReference().child("contactsMailIndexed").child(currentuser.substring(0,9));
            databaseReference_contacts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        if (snapshot.child("position").getValue() != null) {
                            fb_semplan.setVisibility(View.VISIBLE);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });}
        if(MainActivity.if_pl == 1 && ProjectsFragment.project == MyFirebaseSrevice.userProp) {
            fb_semplan.setVisibility(View.VISIBLE);
        }



        fb_semplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(utkarsh_semPlan.this,add_semplan.class);
                startActivity(intent);
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String key = dataSnapshot.getKey();
                if(!list_keys.contains(key)) {
                    String plan = dataSnapshot.child("plan").getValue().toString();
                    String complete = dataSnapshot.child("complete").getValue().toString();
                    String deadline = dataSnapshot.child("weight").getValue().toString();
                    semplan splan = new semplan(plan,key,complete,deadline);
                    list.add(splan);
                    list_keys.add(key);
                    adapter.notifyItemInserted(list.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String key = dataSnapshot.getKey();
                String plan = dataSnapshot.child("plan").getValue().toString();
                String complete = dataSnapshot.child("complete").getValue().toString();
                String deadline = dataSnapshot.child("weight").getValue().toString();
                semplan splan = new semplan(plan,key,complete,deadline);
                int ideaIndex = list_keys.indexOf(key);
                if (ideaIndex > -1) {
                    list.set(ideaIndex, splan);
                    adapter.notifyItemChanged(ideaIndex);
                } else {
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
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerOptions<plan_holder> options= new FirebaseRecyclerOptions.Builder<plan_holder>()
//                .setQuery(databaseReference,plan_holder.class)
//                .build();
//
//
//
//        FirebaseRecyclerAdapter<plan_holder, utkarsh_semPlan.PlanViewHolder> adapter=
//                new FirebaseRecyclerAdapter<plan_holder, utkarsh_semPlan.PlanViewHolder>(options) {
//
//                    @Override
//                    protected void onBindViewHolder(@NonNull final utkarsh_semPlan.PlanViewHolder holder, int position, @NonNull plan_holder model) {
//                        String musersId = getRef(position).getKey();
//
//                        databaseReference.child(musersId).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                                    String mName = dataSnapshot.child("plan").getValue().toString();
//
//                                    holder.plan.setText(mName);
//
//
//
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
//                    }
//
//
//
//
//
//        @NonNull
//        @Override
//        public utkarsh_semPlan.PlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//
//            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_card,viewGroup,false);
//            return new utkarsh_semPlan.PlanViewHolder(view);
//        }
//    };
//
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//
//}


//public static class PlanViewHolder extends RecyclerView.ViewHolder{
//
//        TextView plan ;
//
//
//
//        public PlanViewHolder(@NonNull View itemView)
//        {
//            super(itemView);
//
//            plan = itemView.findViewById(R.id.pretext);
//
//
//        }
//
//    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onEditClick(int position){
        //if(MainActivity.if_pl == 1 && ProjectsFragment.project == MyFirebaseSrevice.userProp){
            Intent intent = new Intent(utkarsh_semPlan.this,edit_semplan.class);

            intent.putExtra("plan",list.get(position).getPlan());
            intent.putExtra("complete",list.get(position).getComplete());
            intent.putExtra("weight",list.get(position).getWeight());
            intent.putExtra("key",list.get(position).getKey());


            startActivity(intent);
    //}
        //else  Toast.makeText(utkarsh_semPlan.this,"Only PL's are allowed to edit",Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDeleteClick(int position) {
        //if(MainActivity.if_pl == 1 && ProjectsFragment.project == MyFirebaseSrevice.userProp) {
            databaseReference.child(list.get(position).getKey()).removeValue();
            //adapter.notifyDataSetChanged();
        //}
        //else Toast.makeText(utkarsh_semPlan.this,"Only PL's are allowed to delete",Toast.LENGTH_SHORT).show();
    }

}
