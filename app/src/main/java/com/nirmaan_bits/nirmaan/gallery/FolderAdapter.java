package com.nirmaan_bits.nirmaan.gallery;

import android.content.Context;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nirmaan_bits.nirmaan.MainActivity;
import com.nirmaan_bits.nirmaan.R;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;
import com.nirmaan_bits.nirmaan.projects.ProjectsFragment;

import java.util.List;


public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ImageViewHolder> {
    private Context mContext;
    private List<folder_upload> mUploads;
    private OnItemClickListener mListener;

    public FolderAdapter(Context context, List<folder_upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.folder_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        folder_upload uploadCurrent = mUploads.get(position);
         holder.textViewName.setText(uploadCurrent.getName());
        Glide.with(mContext)
                .load(uploadCurrent.getImageUrl())
                 .placeholder(R.drawable.ic_nirmaan_logo)
                .centerCrop()
                .into(holder.imageView);
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.option_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                mListener.onDeleteClick(position);
                                //handle delete click
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener/*,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener */{
         public TextView textViewName;
        public ImageView imageView;
        public TextView buttonViewOption;

        public ImageViewHolder(View itemView) {
            super(itemView);

             textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            buttonViewOption = itemView.findViewById(R.id.textViewOptions);
            if(galleryFragment.project == MyFirebaseSrevice.userProp)
                buttonViewOption.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(this);
            //itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        /*@Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
          //  menu.setHeaderTitle("Select Action");
           // MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Do whatever");
            MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");

          //  doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        *//*case 1:
                            mListener.onWhatEverClick(position);
                            return true;*//*
                        case 1:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }*/
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

       // void onWhatEverClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}