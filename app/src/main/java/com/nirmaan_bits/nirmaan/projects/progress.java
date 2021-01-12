package com.nirmaan_bits.nirmaan.projects;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.R;

import java.util.Objects;


public class progress extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private  double prog=0;
    private ProgressBar progressBar;
    private TextView progno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
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

        databaseReference.keepSynced(true);
        recyclerView=findViewById(R.id.progress_recycle);
        progressBar=findViewById(R.id.progbar);
        progno=findViewById(R.id.progno);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<plan_holder> options= new FirebaseRecyclerOptions.Builder<plan_holder>()
                .setQuery(databaseReference,plan_holder.class)
                .build();



        FirebaseRecyclerAdapter<plan_holder, progress.PlanViewHolder> adapter=
                new FirebaseRecyclerAdapter<plan_holder, progress.PlanViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull final progress.PlanViewHolder holder, int position, @NonNull plan_holder model) {
                        String musersId = getRef(position).getKey();

                        assert musersId != null;
                        databaseReference.child(musersId).addValueEventListener(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(Objects.requireNonNull(dataSnapshot.child("complete").getValue()).toString().equals("yes")) {
                                    String mName = Objects.requireNonNull(dataSnapshot.child("plan").getValue()).toString();
                                    double pt=Double.parseDouble(Objects.requireNonNull(dataSnapshot.child("weight").getValue()).toString());

                                    holder.plan.setText(mName);
                                    holder.image.setVisibility(View.VISIBLE);

                                    prog+=pt*10;

                                    progressBar.setProgress((int) prog);
                                    String ab= Integer.toString((int) prog)+"%";
                                    progno.setText(ab);

                                }

                                else {

                                    holder.card.setVisibility(View.GONE);
                                    ViewGroup.LayoutParams params=holder.linearLayout.getLayoutParams();
                                    ViewGroup.MarginLayoutParams params1= (ViewGroup.MarginLayoutParams) holder.card.getLayoutParams();
                                    params.height=0;
                                    params.width=0;
                                    params1.setMargins(0,0,0,0);
                                    holder.linearLayout.setLayoutParams(params);
                                    holder.card.setLayoutParams(params1);
                                    holder.linearLayout.setVisibility(View.GONE);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }





                    @NonNull
                    @Override
                    public progress.PlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_card,viewGroup,false);
                        return new progress.PlanViewHolder(view);
                    }
                };
        recyclerView.setAdapter(adapter);

        adapter.startListening();


    }


    public static class PlanViewHolder extends RecyclerView.ViewHolder{

        TextView plan ;
        ImageView image;
        CardView card;
        LinearLayout linearLayout;

        PlanViewHolder(@NonNull View itemView)
        {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.planCardlinear);
            plan = itemView.findViewById(R.id.pretext);
            image=itemView.findViewById(R.id.edit_complete);
            card=itemView.findViewById(R.id.planCard);
        }

    }


}
