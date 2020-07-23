package com.nirmaan_bits.nirmaan.attendance;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nirmaan_bits.nirmaan.R;

import java.util.List;

public class history_adapter extends RecyclerView.Adapter<history_adapter.ViewHolder> {

    Context context;
    List<history_status> memb_history;

    public history_adapter(Context context, List<history_status> TempList) {

        this.memb_history = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        history_status status = memb_history.get(position);

        holder.date.setText(status.getDate());

        holder.status.setText(status.getStatus());

    }

    @Override
    public int getItemCount() {

        return memb_history.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView status;

        public ViewHolder(View itemView) {

            super(itemView);

            date = (TextView) itemView.findViewById(R.id.date);

            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}