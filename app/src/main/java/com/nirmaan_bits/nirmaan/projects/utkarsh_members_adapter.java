package com.nirmaan_bits.nirmaan.projects;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.nirmaan_bits.nirmaan.Contact;
import com.nirmaan_bits.nirmaan.MainActivity;
import com.nirmaan_bits.nirmaan.R;

import java.io.IOException;
import java.util.List;

public class utkarsh_members_adapter extends RecyclerView.Adapter<utkarsh_members_adapter.ContactViewHolder> {
    Context context;
    List<Contact> members;
    private  utkarsh_members_adapter.OnItemClickListener mListener;


    public utkarsh_members_adapter(Context context, List<Contact> TempList) {

        this.members = TempList;

        this.context = context;
    }

    @NonNull
    @Override
    public utkarsh_members_adapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        utkarsh_members_adapter.ContactViewHolder viewHolder = new utkarsh_members_adapter.ContactViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull utkarsh_members_adapter.ContactViewHolder holder, int position) {
        final Contact member = members.get(position);
        holder.name.setText(member.getName());
        holder.year.setText(member.getYear());

        holder.contact.setText(member.getNum());
        holder.pl.setText(member.getPl());
        holder.visits.setText(member.getVisits());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no = "tel:" + member.getNum();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(no));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView name, contact, year, pl, visits;
        ImageView call;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            visits = itemView.findViewById(R.id.visits_total);

            name = itemView.findViewById(R.id.member_name);
            contact = itemView.findViewById(R.id.member_numb);
            year = itemView.findViewById(R.id.member_year);
            pl = itemView.findViewById(R.id.member_pl);
            call = itemView.findViewById(R.id.call);
            itemView.setOnCreateContextMenuListener(this);
            if(MainActivity.project == "Guest"){
                contact.setVisibility(View.INVISIBLE);
                call.setVisibility(View.GONE);
                visits.setVisibility(View.INVISIBLE);
            }




        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            try {
                                mListener.onWhatEverClick(position);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;

                    }
                }
            }
            return false;
        }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Edit Details");
        MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");


        doWhatever.setOnMenuItemClickListener(this);
        delete.setOnMenuItemClickListener(this);
    }


    }

public interface OnItemClickListener {

    void onWhatEverClick(int position) throws IOException;

    void onDeleteClick(int position);
}

    public  void setOnItemClickListener(utkarsh_members_adapter.OnItemClickListener listener) {
        mListener = listener;
    }

}
