package com.nirmaan_bits.nirmaan.projects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.nirmaan_bits.nirmaan.MainActivity;
import com.nirmaan_bits.nirmaan.R;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class utkarsh_semPlan extends AppCompatActivity implements utkarsh_semplan_adapter.OnItemClickListener{

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private FloatingActionButton fb_semplan;
    List<semplan> list = new ArrayList<>();
    utkarsh_semplan_adapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        //Toast.makeText(this, "on create called of utkarsh semplan "+MainActivity.if_pl+" " +MyFirebaseSrevice.userProp, Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utkarsh_activity_sem_plan);
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
        fb_semplan = findViewById(R.id.add_sem);
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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    String plan = dataSnapshot.child("plan").getValue().toString();
                    String complete = dataSnapshot.child("complete").getValue().toString();
                    String weight = dataSnapshot.child("weight").getValue().toString();
                    semplan splan = new semplan(plan,key,complete,weight);
                    list.add(splan);
                }
                adapter = new utkarsh_semplan_adapter(utkarsh_semPlan.this, list);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(utkarsh_semPlan.this);

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
            adapter.notifyDataSetChanged();
        //}
        //else Toast.makeText(utkarsh_semPlan.this,"Only PL's are allowed to delete",Toast.LENGTH_SHORT).show();
    }

}
