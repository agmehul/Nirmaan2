package com.nirmaan_bits.nirmaan.projects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.R;


public class utkarsh_semPlan extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<plan_holder> options= new FirebaseRecyclerOptions.Builder<plan_holder>()
                .setQuery(databaseReference,plan_holder.class)
                .build();



        FirebaseRecyclerAdapter<plan_holder, utkarsh_semPlan.PlanViewHolder> adapter=
                new FirebaseRecyclerAdapter<plan_holder, utkarsh_semPlan.PlanViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull final utkarsh_semPlan.PlanViewHolder holder, int position, @NonNull plan_holder model) {
                        String musersId = getRef(position).getKey();

                        databaseReference.child(musersId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    String mName = dataSnapshot.child("plan").getValue().toString();

                                    holder.plan.setText(mName);




                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }





        @NonNull
        @Override
        public utkarsh_semPlan.PlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_card,viewGroup,false);
            return new utkarsh_semPlan.PlanViewHolder(view);
        }
    };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

}


public static class PlanViewHolder extends RecyclerView.ViewHolder{

    TextView plan ;



    public PlanViewHolder(@NonNull View itemView)
    {
        super(itemView);

        plan = itemView.findViewById(R.id.pretext);


    }

}

}
