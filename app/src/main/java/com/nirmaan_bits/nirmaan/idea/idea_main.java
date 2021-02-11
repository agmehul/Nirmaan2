package com.nirmaan_bits.nirmaan.idea;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.MainActivity;
import com.nirmaan_bits.nirmaan.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class idea_main extends Fragment implements idea_adapter.OnItemClickListener{
    private static final String TAG = "idea_main";
    String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private FloatingActionButton fb_addidea;
    List<idea> list = new ArrayList<>();
    List<String> list_keys = new ArrayList<>();
    idea_adapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.idea_main, container, false);
        list.clear();
        list_keys.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ideas");

        recyclerView=view.findViewById(R.id.ideaRecycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fb_addidea = view.findViewById(R.id.add_idea);
        if(MainActivity.project !="guest") {
            fb_addidea.setVisibility(View.VISIBLE);
        }



        fb_addidea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),add_idea.class);
                startActivity(intent);
            }
        });
        adapter = new idea_adapter(getActivity(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(idea_main.this);
        Query mostUpvotedQuery = databaseReference.orderByChild("upvotes");
        mostUpvotedQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                String key = dataSnapshot.getKey();
                if(!list_keys.contains(key)) {
                    idea idea = dataSnapshot.getValue(idea.class);
                    idea.setKey(key);
                    list.add(idea);
                    list_keys.add(key);
                    adapter.notifyItemInserted(list.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                String key = dataSnapshot.getKey();
                idea idea = dataSnapshot.getValue(idea.class);
                idea.setKey(key);
                int ideaIndex = list_keys.indexOf(key);
                if (ideaIndex > -1) {
                    list.set(ideaIndex, idea);
                    adapter.notifyItemChanged(ideaIndex);
                } else {
                    Log.w(TAG, "onChildChanged:unknown_child:" + key);
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                String key = dataSnapshot.getKey();
                int ideaIndex = list_keys.indexOf(key);
                if (ideaIndex > -1) {
                    // Replace with the new data
                    list_keys.remove(ideaIndex);
                    list.remove(ideaIndex);

                    // Update the RecyclerView
                    adapter.notifyItemRemoved(ideaIndex);
                } else {
                    Log.w(TAG, "onChildRemoved:unknown_child:" + key);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "postIdeas:onCancelled", databaseError.toException());
                Toast.makeText(getActivity(), "Failed to load ideas.",
                        Toast.LENGTH_SHORT).show();
            }
        });
//        Query mostUpvotedQuery = databaseReference.orderByChild("upvotes");
//        mostUpvotedQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String key = dataSnapshot.getKey();
//                    String content = dataSnapshot.child("content").getValue().toString();
//                    String time = dataSnapshot.child("time").getValue().toString();
//                    String project = dataSnapshot.child("project").getValue().toString();
//                    Integer upvotes = dataSnapshot.child("upvotes").getValue(Integer.class);
//                    idea idea = new idea(content,time,project,upvotes,key);
//                    list.add(idea);
//                }
//                Collections.reverse(list);
//                adapter = new idea_adapter(idea_main.this, list);
//                recyclerView.setAdapter(adapter);
//                adapter.setOnItemClickListener(idea_main.this);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onEditClick(int position) throws IOException {
        if(list.get(position).getAuthor().equals(currentuser)){
            Intent intent = new Intent(getActivity(),edit_idea.class);

            intent.putExtra("content",list.get(position).getContent());
            intent.putExtra("project",list.get(position).getProject());
            intent.putExtra("key",list.get(position).getKey());

            startActivity(intent);
    }
        else  Toast.makeText(getActivity(),"You are not allowed to edit other's ideas",Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDeleteClick(int position) {
        if(list.get(position).getAuthor().equals(currentuser)) {
            databaseReference.child(list.get(position).getKey()).removeValue();
        }
        else Toast.makeText(getActivity(),"You are not allowed to delete other's ideas",Toast.LENGTH_SHORT).show();
    }

}
