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


    /*@Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Contact> options= new FirebaseRecyclerOptions.Builder<Contact>()
                .setQuery(databaseReference,Contact.class)
                .build();

         adapter= new FirebaseRecyclerAdapter<Contact,ContactViewHolder>(options) {


                    @Override
                    protected void onBindViewHolder(@NonNull final ContactViewHolder holder, int position, @NonNull Contact model) {
                         musersId = getRef(position).getKey();


                        databaseReference.child(musersId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int visits =0;
                                if (dataSnapshot.child("history").getValue() != null){
                                    for (DataSnapshot snapshot1 : dataSnapshot.child("history").getChildren()) {
                                        if (snapshot1.child("status").getValue().equals("Present")){
                                            visits++;

                                        }
                                    }
                                }

                                holder.visits.setText("" + visits);

                                if (dataSnapshot.hasChild("pl")) {

                                     mName = dataSnapshot.child("name").getValue().toString();
                                     mNumb = dataSnapshot.child("num").getValue().toString();
                                     mYear = dataSnapshot.child("year").getValue().toString();

                                    holder.name.setText(mName);
                                    holder.year.setText(mYear);
                                    holder.contact.setText(mNumb);
                                    holder.pl.setText("PL");

                                     holder.call.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String no="tel:"+mNumb;

                                            Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse(no));
                                            startActivity(intent);
                                        }
                                    });


                                }

                                else {

                                    String mName = dataSnapshot.child("name").getValue().toString();
                                    final String mNumb = dataSnapshot.child("num").getValue().toString();
                                    String mYear = dataSnapshot.child("year").getValue().toString();



                                    holder.name.setText(mName);
                                    holder.year.setText(mYear);
                                    holder.contact.setText(mNumb);
                                    holder.pl.setText("");
                                    holder.call.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            String no="tel:"+mNumb;

                                            Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse(no));
                                            startActivity(intent);
                                        }
                                    });


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }



                    @NonNull
                    @Override
                    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
                        return new ContactViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);

        adapter.startListening();


    }




    public class ContactViewHolder extends RecyclerView.ViewHolder{

        TextView name, contact, year, pl, visits;
        ImageView call;


        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            visits = itemView.findViewById(R.id.visits_total);

            name = itemView.findViewById(R.id.member_name);
            contact = itemView.findViewById(R.id.member_numb);
            year = itemView.findViewById(R.id.member_year);
            pl = itemView.findViewById(R.id.member_pl);
            call = itemView.findViewById(R.id.call);
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(final ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add("Edit details").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            *//*adapter.getRef(getAdapterPosition()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(utkarsh_members.this,"item deleted",Toast.LENGTH_SHORT).show();
                                }
                            });
*//*
                            Intent intent = new Intent(utkarsh_members.this,edit_member.class);
                            intent.putExtra("name",mName);
                            intent.putExtra("num",mNumb);

                            intent.putExtra("year",mYear);
                            intent.putExtra("key",musersId);


                            startActivity(intent);




//do what u want
                            return true;
                        }
                    });
                }
            });


        }

    }*/

}
