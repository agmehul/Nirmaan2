package com.nirmaan_bits.nirmaan.gallery;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.nirmaan_bits.nirmaan.R;
import com.squareup.picasso.Picasso;

public class create_folder extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_folder);

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
        switch (galleryFragment.project){

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
        switch (galleryFragment.project){

            case 1:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery").child("gbbaas");
                break;
            case 2:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery").child("gbcb");
                break;
            case 3:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery").child("sap");
                break;
            case 4:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery").child("pcd");
                break;
            case 5:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery").child("sko");
                break;
            case 6:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery").child("utkarsh");
                break;
            case 7:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery").child("disha");
                break;
            case 8:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery").child("unnati1");
                break;
            case 9:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery").child("unnati2");
                break;
            case 10:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery").child("youth");
                break;
            case 11:
                mStorageRef = FirebaseStorage.getInstance().getReference().child("Gallery").child("all nirmaan");
                break;
        }



        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(create_folder.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(create_folder.this, "Upload successful", Toast.LENGTH_LONG).show();
                            finish();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    folder_upload folder_upload = new folder_upload(mEditTextFileName.getText().toString().trim(),
                                            uri.toString());
                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadId).setValue(folder_upload);
// Got the download URL for 'users/me/profile.png' in uri
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(create_folder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openImagesActivity() {
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
    }
}
