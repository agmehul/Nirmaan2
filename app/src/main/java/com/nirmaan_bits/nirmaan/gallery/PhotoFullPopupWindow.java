package com.nirmaan_bits.nirmaan.gallery;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nirmaan_bits.nirmaan.Constants;
import com.nirmaan_bits.nirmaan.GlideApp;
import com.nirmaan_bits.nirmaan.R;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;
import com.nirmaan_bits.nirmaan.projects.ProjectsFragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.nirmaan_bits.nirmaan.gallery.ImagesActivity.mUploads;

public class PhotoFullPopupWindow extends PopupWindow {

    View view;
    Context mContext;
    PhotoView photoView;
    ProgressBar loading;
    ViewGroup parent;
    private static PhotoFullPopupWindow instance = null;
    ImageButton delete,download;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
   // LinearLayout image_panel;





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public PhotoFullPopupWindow(Context ctx, int layout, View v, String imageUrl, Bitmap bitmap, final int position, final String event) {
        super(((LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate( R.layout.popup_photo_full, null), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mStorage = FirebaseStorage.getInstance();

        switch (galleryFragment.project){

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

        if (Build.VERSION.SDK_INT >= 21) {
            setElevation(5.0f);
        }
        this.mContext = ctx;
        this.view = getContentView();
        ImageButton closeButton = (ImageButton) this.view.findViewById(R.id.ib_close);
        setOutsideTouchable(true);

        setFocusable(true);
        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dismiss();
            }
        });
        //---------Begin customising this popup--------------------

        photoView = (PhotoView) view.findViewById(R.id.image);
        loading = (ProgressBar) view.findViewById(R.id.loading);
        delete = view.findViewById(R.id.delete);
        download = view.findViewById(R.id.download);
        //image_panel = view.findViewById(R.id.image_panel);
        if(ProjectsFragment.project == MyFirebaseSrevice.userProp){
            delete.setVisibility(View.VISIBLE);
        }

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Upload selectedItem = mUploads.get(position);

                StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());

                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImagesActivity.downloadFile(mContext, event +  System.currentTimeMillis(),".jpg",DIRECTORY_DOWNLOADS,uri.toString());
                        Toast.makeText(mContext, "Downloading...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                        Toast.makeText(mContext, String.format("Failure: %s", exception.getMessage()), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog("Delete?", "Are you sure you want to delete the image", "cancelMethod1","okMethod1",position);
                }
        }
        );

        photoView.setMaximumScale(6);
        parent = (ViewGroup) photoView.getParent();
        // ImageUtils.setZoomable(imageView);
        //----------------------------
        if (bitmap != null) {
            loading.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= 16) {
                parent.setBackground(new BitmapDrawable(mContext.getResources(), com.nirmaan_bits.nirmaan.Constants.fastblur(Bitmap.createScaledBitmap(bitmap, 50, 50, true))));// ));
            } else {
                onPalette(Palette.from(bitmap).generate());

            }
            photoView.setImageBitmap(bitmap);
        } else {
            loading.setIndeterminate(true);
            loading.setVisibility(View.VISIBLE);

            GlideApp.with(ctx).asBitmap()
                    .load(imageUrl)

                    //.error(R.drawable.no_image)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            loading.setIndeterminate(false);
                            loading.setBackgroundColor(Color.LTGRAY);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            if (Build.VERSION.SDK_INT >= 16) {
                                parent.setBackground(new BitmapDrawable(mContext.getResources(), Constants.fastblur(Bitmap.createScaledBitmap(resource, 50, 50, true))));// ));
                            } else {
                                onPalette(Palette.from(resource).generate());

                            }
                            photoView.setImageBitmap(resource);

                            loading.setVisibility(View.GONE);
                            return false;
                        }
                    })



                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(photoView);

            showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        //------------------------------

    }
    public void customDialog(String title, String message, final String cancelMethod, final String okMethod, final int position){
        final androidx.appcompat.app.AlertDialog.Builder builderSingle = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        // builderSingle.setIcon(R.mipmap.ic_notification);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  Log.d(TAG, "onClick: Cancel Called.");

                        Toast.makeText(mContext, "Cancelled", Toast.LENGTH_SHORT).show();


                    }
                });

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Log.d(TAG, "onClick: OK Called.");

                        Upload selectedItem = mUploads.get(position);
                        final String selectedKey = selectedItem.getKey();

                        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
                        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mDatabaseRef.child(selectedKey).removeValue();
                                Toast.makeText(mContext, "Item deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dismiss();


                    }
                });


        builderSingle.show();
    }


    public void onPalette(Palette palette) {
        if (null != palette) {
            ViewGroup parent = (ViewGroup) photoView.getParent().getParent();
            parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
        }
    }

}