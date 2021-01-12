package com.nirmaan_bits.nirmaan.docs;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nirmaan_bits.nirmaan.MainActivity;
import com.nirmaan_bits.nirmaan.R;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;
import com.nirmaan_bits.nirmaan.idea.idea;
import com.nirmaan_bits.nirmaan.projects.ProjectsFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class docActivity extends AppCompatActivity {

    private RecyclerView folderRecView;
    List<folder> folderNameList;
    //FirebaseStorage storage;
    String fName;
    String fKey;
    String pdfName;
    TextView selectedPdfName;
    FolderGridAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button cancelButton;
    Button choosePdf;
    AlertDialog alertDialog;
    Intent intent;
    private List<String> list_keys = new ArrayList<>();
    List<String> pdfNameList;
    List<Uri> uriList;
    List<String> downloadUrlList;//for uploading to firestore,will get after uploading to firebase storage
    DatabaseReference databaseReference;

    int counter;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_main);


        folderRecView = findViewById(R.id.fRecyclerView);
        folderRecView.setLayoutManager(new GridLayoutManager(this, 2));
        folderNameList = new ArrayList<>();
        adapter = new FolderGridAdapter(folderNameList,docActivity.this);
        folderRecView.setAdapter(adapter);
        //databaseReference = FirebaseDatabase.getInstance().getReference().child("FoldersForDocs");
        switch (ProjectsFragment.project){

            case 1:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("FoldersForDocs").child("gbbaas");
                break;
            case 2:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("FoldersForDocs").child("gbcb");
                break;
            case 3:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("FoldersForDocs").child("sap");
                break;
            case 4:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("FoldersForDocs").child("pcd");
                break;
            case 5:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("FoldersForDocs").child("sko");
                break;
            case 6:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("FoldersForDocs").child("utkarsh");
                break;
            case 7:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("FoldersForDocs").child("disha");
                break;
            case 8:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("FoldersForDocs").child("unnati1");
                break;
            case 9:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("FoldersForDocs").child("unnati2");
                break;
            case 10:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("FoldersForDocs").child("youth");
                break;
            case 11:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("FoldersForDocs").child("prd");
                break;
        }
        //folderNameList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String key = dataSnapshot.getKey();
                folder folder = dataSnapshot.getValue(folder.class);
                folder.setKey(key);
                Log.d("docactivity", "onChildAdded:" + dataSnapshot.getKey());
                //idea.setKey(key);
                //idea idea = new idea(content,time,project,upvotes,key);
                folderNameList.add(folder);
                list_keys.add(key);
                adapter.notifyItemInserted(folderNameList.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String key = dataSnapshot.getKey();
                folder folder = dataSnapshot.getValue(folder.class);
                folder.setKey(key);
                //idea.setKey(key);
                //idea idea = new idea(content,time,project,upvotes,key);
                int folder_nameIndex = list_keys.indexOf(key);
                if (folder_nameIndex > -1) {
                    // Replace with the new data
                    folderNameList.set(folder_nameIndex, folder);

                    // Update the RecyclerView
                    adapter.notifyItemChanged(folder_nameIndex);
                } else {
                    //Log.w(TAG, "onChildChanged:unknown_child:" + key);
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                String key = dataSnapshot.getKey();
                int folder_nameIndex = list_keys.indexOf(key);
                if (folder_nameIndex > -1) {
                    // Replace with the new data
                    list_keys.remove(folder_nameIndex);
                    folderNameList.remove(folder_nameIndex);

                    // Update the RecyclerView
                    adapter.notifyItemRemoved(folder_nameIndex);
                } else {
                    //Log.w(TAG, "onChildRemoved:unknown_child:" + key);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.w(TAG, "postIdeas:onCancelled", databaseError.toException());
                Toast.makeText(docActivity.this, "Failed to load folders.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //storage = FirebaseStorage.getInstance();

        pdfNameList = new ArrayList<>();
        uriList = new ArrayList<>();
        downloadUrlList = new ArrayList<>();

        FloatingActionButton fab = findViewById(R.id.fab);
        if(ProjectsFragment.project == MyFirebaseSrevice.userProp){fab.setVisibility(View.VISIBLE);}

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
                Log.d("fab", "onClick: ");
            }
        });

    }

    //FUNCTIONS

    private void showAlertDialog() {

        final AlertDialog.Builder docAlert = new AlertDialog.Builder(docActivity.this);
        View alertView = getLayoutInflater().inflate(R.layout.doc_dialog, null);
        final EditText folderName = alertView.findViewById(R.id.folderName_eT);
        cancelButton = alertView.findViewById(R.id.dialog_cancel);
        Button createFolder = alertView.findViewById(R.id.createFolderbtn);
        choosePdf = alertView.findViewById(R.id.choosePdfbtn);
        selectedPdfName = alertView.findViewById(R.id.none_TV);

        docAlert.setView(alertView);
        alertDialog = docAlert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        choosePdf.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //choose PDFs

                fName = folderName.getText().toString();
                Log.d("choosePdf", "onClick: ");
                if (fName.isEmpty()) {
                    folderName.setError("Please enter Folder name!");
                    folderName.requestFocus();
                    return;
                }

                intent = new Intent();
                intent.setType("application/pdf/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Please choose a PDF File"), 101);
                Log.d("size", "onClick: " + pdfNameList.size());

            }

        });


        createFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPDF(v);
                if (pdfNameList.size() != 0) {
                    folder folder = new folder(fName, 0);
                    fKey = databaseReference.push().getKey();
                    databaseReference.child(fKey).setValue(folder);
                }

                //PrefConfig.writeListInPref(getApplicationContext(), folderNameList);
                //adapter.notifyDataSetChanged();
                alertDialog.dismiss();

            }

        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfNameList.clear();
                uriList.clear();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }


    private void uploadPDF(View v) {
        if (pdfNameList.size() != 0) {

            choosePdf.setEnabled(false);
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Files...");
            progressDialog.setMessage("Uploaded 0/" + pdfNameList.size());
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();

            final StorageReference storageReference;
            switch (ProjectsFragment.project){

                case 1:
                    storageReference = FirebaseStorage.getInstance().getReference().child("FoldersForDocs").child("gbbaas");
                    break;
                case 2:
                    storageReference = FirebaseStorage.getInstance().getReference().child("FoldersForDocs").child("gbcb");
                    break;
                case 3:
                    storageReference = FirebaseStorage.getInstance().getReference().child("FoldersForDocs").child("sap");
                    break;
                case 4:
                    storageReference = FirebaseStorage.getInstance().getReference().child("FoldersForDocs").child("pcd");
                    break;
                case 5:
                    storageReference = FirebaseStorage.getInstance().getReference().child("FoldersForDocs").child("sko");
                    break;
                case 6:
                    storageReference = FirebaseStorage.getInstance().getReference().child("FoldersForDocs").child("utkarsh");
                    break;
                case 7:
                    storageReference = FirebaseStorage.getInstance().getReference().child("FoldersForDocs").child("disha");
                    break;
                case 8:
                    storageReference = FirebaseStorage.getInstance().getReference().child("FoldersForDocs").child("unnati1");
                    break;
                case 9:
                    storageReference = FirebaseStorage.getInstance().getReference().child("FoldersForDocs").child("unnati2");
                    break;
                case 10:
                    storageReference = FirebaseStorage.getInstance().getReference().child("FoldersForDocs").child("youth");
                    break;
                case 11:
                    storageReference = FirebaseStorage.getInstance().getReference().child("FoldersForDocs").child("prd");
                    break;
                default:
                    storageReference = FirebaseStorage.getInstance().getReference().child("FoldersForDocs").child("unknown");
            }

            for (int i = 0; i < pdfNameList.size(); i++) {
                final int finalI = i;

                storageReference.child(fName + "/").child(pdfNameList.get(i)).putFile(uriList.get(i)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            storageReference.child(fName + "/").child(pdfNameList.get(finalI)).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    counter++;
                                    progressDialog.setMessage("Uploaded " + counter + "/" + pdfNameList.size());
                                    if (task.isSuccessful()) {

                                        downloadUrlList.add(task.getResult().toString());
                                    } else {
                                        storageReference.child(fName + "/").child(pdfNameList.get(finalI)).delete();
                                        Toast.makeText(docActivity.this, "Sorry!, " + pdfNameList.get(finalI) + " could not be uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                    if (counter == pdfNameList.size()) {
                                        saveFileDataToFirestore(progressDialog);
                                    }
                                }
                            });

                        } else {
                            progressDialog.setMessage("Uploaded " + counter + "/" + pdfNameList.size());
                            counter++;
                            Toast.makeText(docActivity.this, pdfNameList.get(finalI) + " could not be uploaded!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

        } else {
            Toast.makeText(this, "Please add some PDF files!", Toast.LENGTH_SHORT).show();
        }
        counter = 0;
    }


    private void saveFileDataToFirestore(final ProgressDialog progressDialog) {


        Log.d("sizeTest", "saveFileDataToFirestore: " + downloadUrlList.size());
        progressDialog.setMessage("Saving uploaded files...");
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < downloadUrlList.size(); i++) {
            DocumentReference documentReference = db.collection(ProjectsFragment.project +"_"+fName).document(pdfNameList.get(i));
            map.put("url", downloadUrlList.get(i));
            map.put("name", pdfNameList.get(i));

            documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(docActivity.this, "Firestore data upload failed", Toast.LENGTH_SHORT).show();
                            Log.e("error", "onFailure: " + e.getMessage());
                        }
                    });


        }
        databaseReference.child(fKey).child("size").setValue(pdfNameList.size());
        pdfNameList.clear();
        uriList.clear();
        downloadUrlList.clear();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri uri = clipData.getItemAt(i).getUri();
                    if(!pdfNameList.contains(getFileName(uri))) {
                        pdfNameList.add(getFileName(uri));
                        uriList.add(uri);
                    }

                }
                selectedPdfName.setText(pdfNameList.toString());
            } else {
                Uri uri = data.getData();
                if(!pdfNameList.contains(getFileName(uri))) {
                    pdfNameList.add(getFileName(uri));
                    uriList.add(uri);
                }
                selectedPdfName.setText(pdfNameList.toString());
            }
        }

    }

    public String getFileName(Uri uri) {

        if (uri.toString().startsWith("content://")) {

            try {
                Cursor cursor = null;
                cursor = docActivity.this.getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (uri.toString().startsWith("file://")) {

            pdfName = new File(uri.toString()).getName();
        } else {
            pdfName = uri.toString();
        }

        return pdfName;
    }


}

