package com.nirmaan_bits.nirmaan.gallery;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nirmaan_bits.nirmaan.R;

import java.util.ArrayList;
import java.util.List;

public class FoldersActivity extends AppCompatActivity implements FolderAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private FolderAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private FloatingActionButton create_folder ;


    private List<folder_upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folders_activity);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        mProgressCircle = findViewById(R.id.progress_circle);
        create_folder = findViewById(R.id.addToDoItemFAB);
        create_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),create_folder.class);
                startActivity(intent);
            }
        });


        mUploads = new ArrayList<>();

        mAdapter = new FolderAdapter(FoldersActivity.this, mUploads);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(FoldersActivity.this);

        mStorage = FirebaseStorage.getInstance();
        switch (HomeFragment.project){

            case 1:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery").child("gbbaas");
                break;
            case 2:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery").child("gbcb");
                break;
            case 3:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery").child("sap");
                break;
            case 4:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery").child("pcd");
                break;
            case 5:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery").child("sko");
                break;
            case 6:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery").child("utkarsh");
                break;
            case 7:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery").child("disha");
                break;
            case 8:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery").child("unnati1");
                break;
            case 9:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery").child("unnati2");
                break;
            case 10:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery").child("youth");
                break;
            case 11:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery").child("all nirmaan");
                break;
        }
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mUploads.clear();


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    folder_upload upload = postSnapshot.getValue(folder_upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FoldersActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
       // Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),ImagesActivity.class);
        folder_upload clicked_item = mUploads.get(position);
        intent.putExtra("event_name",clicked_item.getName());
        startActivity(intent);
    }

   /* @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(this, "Whatever click at position: " + position, Toast.LENGTH_SHORT).show();
    }
*/
    @Override
    public void onDeleteClick(int position) {
        folder_upload selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(FoldersActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}
