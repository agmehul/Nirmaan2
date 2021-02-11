package com.nirmaan_bits.nirmaan.docs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
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

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {

    List<String> downloadUrlList;
    List<String> pdfNameList;
    StorageReference storageReference;
    String folder_name;
    String folder_key;
    //int folder_size;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DatabaseReference databaseReference ;

    public PdfAdapter(List<String> pdfUrlList, List<String> pdfNameList, String folder_name,String folder_key) {
        this.downloadUrlList = pdfUrlList;
        this.pdfNameList = pdfNameList;
        this.folder_name = folder_name;
        this.folder_key = folder_key;
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_view, parent, false);
        //storageRef = FirebaseStorage.getInstance().getReference();
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
        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, final int position) {

        holder.pdfDisplayName.setText(pdfNameList.get(position));
        String url = pdfNameList.get(position);
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            holder.imageView.setImageResource(R.drawable.ic_google_docs);
        }
        else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            holder.imageView.setImageResource(R.drawable.ic_ppt);
        }else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            holder.imageView.setImageResource(R.drawable.ic_excel);
        }
    }

    @Override
    public int getItemCount() {
        return pdfNameList.size();
    }

    public class PdfViewHolder extends RecyclerView.ViewHolder {

        TextView pdfDisplayName;
        ImageView imageView;


        public PdfViewHolder(@NonNull final View itemView) {
            super(itemView);

            pdfDisplayName = itemView.findViewById(R.id.pdfDisplayName_tv);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(downloadUrlList.get(getAdapterPosition())));
                    Intent j = Intent.createChooser(intent, "Choose an application to open with:");
                    v.getContext().startActivity(j);
                }
            });
            if(ProjectsFragment.project == MyFirebaseSrevice.userProp){
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    final Dialog dialog = new Dialog(itemView.getContext());
                    dialog.setContentView(R.layout.pdfdel_dialog);
                    dialog.setTitle("Delete File");

                    TextView text = dialog.findViewById(R.id.del_text);
                    text.setText(R.string.pdf_del_prompt);

                    Button cancelDelete = dialog.findViewById(R.id.pdf_delCancel);
                    Button confirmDelete = dialog.findViewById(R.id.pdf_delConfirm);

                    cancelDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    confirmDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final ProgressDialog progressDialog = new ProgressDialog(itemView.getContext());

                            progressDialog.setMessage("Deleting File...");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            StorageReference pdfRef = storageReference.getStorage().getReferenceFromUrl(downloadUrlList.get(getAdapterPosition()));
                            pdfRef.delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            db.collection(ProjectsFragment.project + "_" + folder_name).document(pdfNameList.get(getAdapterPosition()))
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            Log.d("delTest", "onSuccess: ");
                                                            progressDialog.dismiss();
                                                            dialog.dismiss();
                                                            Snackbar.make(itemView, "File deleted Successfully!", Snackbar.LENGTH_SHORT).show();
                                                            pdfNameList.remove(getAdapterPosition());
                                                            downloadUrlList.remove(getAdapterPosition());
                                                            notifyItemRemoved(getAdapterPosition());
                                                            databaseReference.child(folder_key).child("size").setValue(pdfNameList.size());

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d("delFail", "onFailure: " + e.getLocalizedMessage());
                                                            progressDialog.dismiss();
                                                            dialog.dismiss();
                                                        }
                                                    });

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    });
                        }
                    });
                    dialog.show();

                    return true;
                }
            });
        }
        }
    }

}
