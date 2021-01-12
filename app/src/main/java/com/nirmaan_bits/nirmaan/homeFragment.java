package com.nirmaan_bits.nirmaan;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class homeFragment extends Fragment {

    //DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("notification");
    private TextView project1;
    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    private TextView name;
    private TextView greeting;
    private TextView pl;
    private Uri uri = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhotoUrl();
    DatabaseReference databaseReference;
    ImageView profile;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View homeview = inflater.inflate(R.layout.fragment_gallery, container, false);
        greeting =  homeview.findViewById(R.id.guest_greeting);
        profile = homeview.findViewById(R.id.profile_image);
        Glide.with(getActivity())
                .load(uri)
                .placeholder(R.mipmap.ic_launcher)
                // .fit()
                .centerCrop()
                .into(profile);


        project1 = homeview.findViewById(R.id.project_name);
        String currentuserName = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();

        name = homeview.findViewById(R.id.name);
        name.setText(currentuserName.toUpperCase());
        pl = homeview.findViewById(R.id.pl);

        if(currentuser.substring(0,9).matches("[A-Za-z0-9]+")){
            databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(currentuser.substring(0,9));
            databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if (snapshot.child("pl").getValue() != null) {
                        pl.setText("PL");
                    }
                    //Todo : obtain tasks of every user from firebase "users" node.
                    // Then arrange them according to the earliest deadline first.
                    String project_firebase = snapshot.child("project").getValue().toString();
                    project1.setText(project_firebase.toUpperCase());
                    greeting.setVisibility(View.GONE);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}
        ImageButton feed=homeview.findViewById(R.id.feedback);

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),feedback.class);
                startActivity(intent);
            }
        });
        ImageButton contact=homeview.findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),contact_activity.class);
                startActivity(intent);
            }
        });
        ImageButton about=homeview.findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),About.class);
                startActivity(intent);
            }
        });


        return homeview;

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
