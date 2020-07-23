package com.nirmaan_bits.nirmaan.gallery;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nirmaan_bits.nirmaan.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class ImagesActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private  StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private String event;



    public static List<Upload> mUploads;
    private static final int PICK_IMG = 1;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private int uploads = 0;
    private ProgressDialog progressDialog;
    int index = 0;
    // TextView textView;
    FloatingActionButton choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_image);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));


        mProgressCircle = findViewById(R.id.progress_circle);



        mUploads = new ArrayList<>();

        mAdapter = new ImageAdapter(ImagesActivity.this, mUploads);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(ImagesActivity.this);

        mStorage = FirebaseStorage.getInstance();
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("event_name")!= null)
        {
             event = bundle.getString("event_name");
            //TODO here get the string stored in the string variable and do
            // setText() on userName
        }

        switch (HomeFragment.project){

            case 1:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery1").child("gbbaas").child(event);
                break;
            case 2:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery1").child("gbcb").child(event);
                break;
            case 3:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery1").child("sap").child(event);
                break;
            case 4:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery1").child("pcd").child(event);
                break;
            case 5:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery1").child("sko").child(event);
                break;
            case 6:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery1").child("utkarsh").child(event);
                break;
            case 7:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery1").child("disha").child(event);
                break;
            case 8:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery1").child("unnati1").child(event);
                break;
            case 9:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery1").child("unnati2").child(event);
                break;
            case 10:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery1").child("youth").child(event);
                break;
            case 11:
                mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Gallery1").child("all nirmaan").child(event);
                break;
        }
        switch (HomeFragment.project){

            case 1:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery1").child("gbbaas").child(event);
                break;
            case 2:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery1").child("gbcb").child(event);
                break;
            case 3:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery1").child("sap").child(event);
                break;
            case 4:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery1").child("pcd").child(event);
                break;
            case 5:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery1").child("sko").child(event);
                break;
            case 6:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery1").child("utkarsh").child(event);
                break;
            case 7:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery1").child("disha").child(event);
                break;
            case 8:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery1").child("unnati1").child(event);
                break;
            case 9:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery1").child("unnati2").child(event);
                break;
            case 10:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery1").child("youth").child(event);
                break;
            case 11:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery1").child("all nirmaan").child(event);
                break;
        }
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading ..........");
        // textView = findViewById(R.id.alert);
        choose = findViewById(R.id.addToDoItemFAB);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMG);
            }
        });
        //send = findViewById(R.id.upload_image);
    }
  /*  public void choose(View view) {
        //we will pick images
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMG);

    }*/


    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMG) {
            ClipData clipData = data.getClipData();
            if (clipData != null){
                    int count = data.getClipData().getItemCount();

                    int CurrentImageSelect = 0;

                    while (CurrentImageSelect < count) {
                        Uri imageuri = data.getClipData().getItemAt(CurrentImageSelect).getUri();
                        ImageList.add(imageuri);
                        CurrentImageSelect = CurrentImageSelect + 1;
                    }
                    customDialog("Image Count", "You Have Selected "+ImageList.size()+ " Pictures", "cancelMethod1","okMethod1");
                    //  textView.setVisibility(View.VISIBLE);
                    //textView.setText("You Have Selected "+ ImageList.size() +" Pictures" );
                    //choose.setVisibility(View.GONE);
                }
            else {
                Uri uri = data.getData();
                ImageList.add(uri);
                customDialog("Image Count", "You Have Selected "+ImageList.size()+ " Pictures", "cancelMethod1","okMethod1");
                // your codefor single image selection
            }

            }

        }



   /* @SuppressLint("SetTextI18n")
    public void upload(View view) {
        textView.setVisibility(View.VISIBLE);

        textView.setText("Please Wait ... If Uploading takes Too much time please the button again ");
        progressDialog.show();
        final StorageReference ImageFolder =  FirebaseStorage.getInstance().getReference().child("ImageFolder");
        for (uploads=0; uploads < ImageList.size(); uploads++) {
            Uri Image  = ImageList.get(uploads);
            final StorageReference imagename = ImageFolder.child("image/"+Image.getLastPathSegment());

            imagename.putFile(ImageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = String.valueOf(uri);
                            SendLink(url);
                        }
                    });

                }
            });


        }


    }*/

    private void SendLink(String url) {

        Upload upload = new Upload(url);
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
int count = 1;
                progressDialog.dismiss();
                toastMessage("uploading...");
                // textView.setText("Image Uploaded Successfully");
                // send.setVisibility(View.GONE);
                ImageList.clear();
            }
        });;
        /*HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("link", url);
        mDatabaseRef.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                progressDialog.dismiss();
                toastMessage("Image uploaded successfully");
                // textView.setText("Image Uploaded Successfully");
                // send.setVisibility(View.GONE);
                ImageList.clear();
            }
        });*/


    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void cancelMethod1(){
        //Log.d(TAG, "cancelMethod1: Called.");
        ImageList.clear();
        toastMessage("Cancelled");}
    private void okMethod1(){
        // Log.d(TAG, "okMethod1: Called.");
        // textView.setVisibility(View.VISIBLE);

        //textView.setText("Please Wait ... If Uploading takes Too much time please the button again ");
        progressDialog.show();
        for (uploads=0; uploads < ImageList.size(); uploads++) {
            Uri Image  = ImageList.get(uploads);
            final StorageReference imagename = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(Image));

            imagename.putFile(ImageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String url = String.valueOf(uri);
                            SendLink(url);
                        }
                    });

                }
            });


        }




    }

    public void customDialog(String title, String message, final String cancelMethod, final String okMethod){
        final androidx.appcompat.app.AlertDialog.Builder builderSingle = new androidx.appcompat.app.AlertDialog.Builder(this);
        // builderSingle.setIcon(R.mipmap.ic_notification);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  Log.d(TAG, "onClick: Cancel Called.");

                        cancelMethod1();


                    }
                });

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Log.d(TAG, "onClick: OK Called.");

                        okMethod1();


                    }
                });


        builderSingle.show();
    }

    /**
     * customizable toast
     * @param message
     */
    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position,ImageView imageView,String url) {


        new PhotoFullPopupWindow(ImagesActivity.this, R.layout.popup_photo_full, imageView, url, null,position,event);

    }

    @Override
    public void onWhatEverClick(int position) throws IOException {
        Upload selectedItem = mUploads.get(position);

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());

        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
              downloadFile(ImagesActivity.this,event +  System.currentTimeMillis(),".jpg",DIRECTORY_DOWNLOADS,uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Toast.makeText(ImagesActivity.this, String.format("Failure: %s", exception.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        Upload selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(ImagesActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
    public static void downloadFile(Context context,String filename, String fileExtension,String destinationDirectory,String url){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,filename+fileExtension);
        downloadManager.enqueue(request);
    }
}
