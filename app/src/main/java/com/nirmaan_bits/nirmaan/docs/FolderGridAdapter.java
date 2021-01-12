package com.nirmaan_bits.nirmaan.docs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nirmaan_bits.nirmaan.R;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;
import com.nirmaan_bits.nirmaan.projects.ProjectsFragment;

import java.util.List;

public class FolderGridAdapter extends RecyclerView.Adapter<FolderGridAdapter.FolderViewHolder> {

    List<folder> folderNameList;
    //StorageReference storageRef;
    Context context;
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference databaseReference;
    public FolderGridAdapter(List<folder> folderNameList,Context context) {
        this.folderNameList = folderNameList;
        this.context = context;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folder_view, parent, false);
        //storageRef = FirebaseStorage.getInstance().getReference();
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
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, final int position) {

        holder.fName.setText(folderNameList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return folderNameList.size();
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder {

        TextView fName;

        public FolderViewHolder(@NonNull final View itemView) {
            super(itemView);

            fName = itemView.findViewById(R.id.folderName_textView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ViewDocActivity.class);
                    intent.putExtra("folderClicked", folderNameList.get(getAdapterPosition()).getName());
                    intent.putExtra("folderKey", folderNameList.get(getAdapterPosition()).getKey());
                    //intent.putExtra("folderSize", folderNameList.get(getAdapterPosition()).getSize());
                    v.getContext().startActivity(intent);

                }
            });
            if(ProjectsFragment.project == MyFirebaseSrevice.userProp){
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (folderNameList.get(getAdapterPosition()).getSize() == 0){
                        final Dialog dialog = new Dialog(itemView.getContext());
                        dialog.setContentView(R.layout.delete_dialog);
                        dialog.setTitle("Delete Folder");

                        TextView text = dialog.findViewById(R.id.dText);

                        text.setText(R.string.delete_prompt);

                        Button cancelDelete = dialog.findViewById(R.id.cancelDeletebtn);

                        cancelDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        Button confirmDelete = dialog.findViewById(R.id.delete_confirmbtn);

                        confirmDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                    databaseReference.child(folderNameList.get(getAdapterPosition()).getKey()).removeValue();

                                    //PrefConfig.deletePref(itemView.getContext(), folderNameList);
                                    //PrefConfig.writeListInPref(itemView.getContext(), folderNameList);
                                    dialog.dismiss();
                                    Log.d("delTest", "onClick: " + folderNameList.size());

                            }
                        });

                        dialog.show();


                        return true;
                    }else
                        Toast.makeText(context, "Folder cannot be deleted unless it's empty", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });}
        }
    }


}
