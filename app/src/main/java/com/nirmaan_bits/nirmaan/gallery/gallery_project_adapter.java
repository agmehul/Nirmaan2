package com.nirmaan_bits.nirmaan.gallery;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.nirmaan_bits.nirmaan.R;

import java.util.List;

public class gallery_project_adapter extends RecyclerView.Adapter<gallery_project_adapter.ViewHolder>{

    private List<String> list;
    private Context mCtx;
    private OnItemClickListener mListener;

    public gallery_project_adapter(List<String> list, Context mCtx) {
        this.list = list;
        this.mCtx = mCtx;
    }
    @NonNull
    @Override
    public gallery_project_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_projects_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final gallery_project_adapter.ViewHolder holder, int position) {
        String project = list.get(position);
        holder.project.setText(project);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView project;


        public ViewHolder(View itemView) {
            super(itemView);

            project = itemView.findViewById(R.id.galleryProjectTV);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(gallery_project_adapter.OnItemClickListener listener) {
        mListener = listener;
    }
}
