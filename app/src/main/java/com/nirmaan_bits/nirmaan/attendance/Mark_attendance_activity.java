package com.nirmaan_bits.nirmaan.attendance;


import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.R;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class Mark_attendance_activity extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference databaseReference_user;
  //  ProgressDialog progressDialog;
    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    List<attn_member> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter ;
    private ValueEventListener mDBListener;



    public Mark_attendance_activity() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (currentuser.substring(0, 9).matches("[A-Za-z0-9]+")) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentuser.substring(0, 9));
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        if (snapshot.child("pl").getValue() != null) {
                            setContentView(R.layout.fragment_mark_attendance_fragment);

                            // Inflate the layout for this fragment
                            recyclerView = findViewById(R.id.recycler_view);

                            recyclerView.setHasFixedSize(true);

                            recyclerView.setLayoutManager(new LinearLayoutManager(Mark_attendance_activity.this));

           /* progressDialog = new ProgressDialog(getActivity());

            progressDialog.setMessage("Loading Data");

            progressDialog.show();*/
                            adapter = new attendance_adapter(Mark_attendance_activity.this, list);
                            recyclerView.setAdapter(adapter);
                            switch (MyFirebaseSrevice.userProp) {

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
                            mDBListener = databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    list.clear();
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        String key = dataSnapshot.getKey();

                                        String name = dataSnapshot.child("name").getValue().toString();
                                        Date c = Calendar.getInstance().getTime();
                                        //System.out.println("Current time => " + c);
                                        attn_member attn_member;
                                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                        String formattedDate = df.format(c);
                                        if (dataSnapshot.child("history").child(formattedDate).child("status").getValue() != null) {
                                            String status = dataSnapshot.child("history").child(formattedDate).child("status").getValue().toString();
                                            attn_member = new attn_member(name, status, key);
                                            list.add(attn_member);
                                        } else {
                                            attn_member = new attn_member(name, "", key);
                                            list.add(attn_member);
                                        }
                                    }

                                    adapter.notifyDataSetChanged();


                                    //  progressDialog.dismiss();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // progressDialog.dismiss();

                                }
                            });
                        }

                    } else {
                        setContentView(R.layout.not_pl_mark_attend);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {
            setContentView(R.layout.not_pl_mark_attend);
        }

    }
}
