package com.nirmaan_bits.nirmaan.projects;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class utkarsh_achievements extends AppCompatActivity implements utkarsh_achievements_adapter.OnItemClickListener{
    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference_contacts;
    private RecyclerView recyclerView;
    private FloatingActionButton fb_achievements;
    List<ach> list = new ArrayList<>();
    List<String> list_keys = new ArrayList<>();
    utkarsh_achievements_adapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(this, "on create called of utkarsh semplan "+MainActivity.if_pl+" " +MyFirebaseSrevice.userProp, Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utkarsh_activity_sem_plan);
        list.clear();
        list_keys.clear();
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

        recyclerView=findViewById(R.id.planRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new utkarsh_achievements_adapter(utkarsh_achievements.this, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(utkarsh_achievements.this);
        fb_achievements = findViewById(R.id.add_sem);
        if(currentuser.substring(0,9).matches("[A-Za-z0-9]+")){
            databaseReference_contacts= FirebaseDatabase.getInstance().getReference().child("contactsMailIndexed").child(currentuser.substring(0,9));
            databaseReference_contacts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        if (snapshot.child("position").getValue() != null) {
                            fb_achievements.setVisibility(View.VISIBLE);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });}
        if(MainActivity.if_pl == 1 && ProjectsFragment.project == MyFirebaseSrevice.userProp) {
            fb_achievements.setVisibility(View.VISIBLE);
        }



        fb_achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(utkarsh_achievements.this,add_achievements.class);
                startActivity(intent);
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String key = dataSnapshot.getKey();
                if(!list_keys.contains(key)) {
                    String content = dataSnapshot.child("content").getValue().toString();
                    ach ach = new ach(content,key);
                    list.add(ach);
                    list_keys.add(key);
                    adapter.notifyItemInserted(list.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String key = dataSnapshot.getKey();
                String content = dataSnapshot.child("content").getValue().toString();
                ach ach = new ach(content,key);
                int ideaIndex = list_keys.indexOf(key);
                if (ideaIndex > -1) {
                    list.set(ideaIndex, ach);
                    adapter.notifyItemChanged(ideaIndex);
                } else {
                    //Log.w(TAG, "onChildChanged:unknown_child:" + key);
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
            Intent intent = new Intent(utkarsh_achievements.this,edit_achievement.class);

            intent.putExtra("content",list.get(position).getContent());
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
        //}
        //else Toast.makeText(utkarsh_semPlan.this,"Only PL's are allowed to delete",Toast.LENGTH_SHORT).show();
    }

}