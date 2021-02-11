package com.nirmaan_bits.nirmaan.projects;

import android.content.Context;
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
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.MainActivity;
import com.nirmaan_bits.nirmaan.R;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class utkarsh_semplan_adapter extends RecyclerView.Adapter<utkarsh_semplan_adapter.ContactViewHolder> {
    Context context;
    List<semplan> semplans;
    private  utkarsh_semplan_adapter.OnItemClickListener mListener;
    private DatabaseReference databaseReference;
    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    private DatabaseReference databaseReference_contacts;
    boolean excom = false;
    public utkarsh_semplan_adapter(Context context, List<semplan> TempList) {

        this.semplans = TempList;

        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public utkarsh_semplan_adapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_card, parent, false);

        utkarsh_semplan_adapter.ContactViewHolder viewHolder = new utkarsh_semplan_adapter.ContactViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final utkarsh_semplan_adapter.ContactViewHolder holder, final int position) {
        switch (ProjectsFragment.project){
            case 1:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("gbbaas").child("semplan");
                break;
            case 2:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("gbcb").child("semplan");
                break;
            case 3:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("sap").child("semplan");
                break;
            case 4:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("pcd").child("semplan");
                break;
            case 5:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("sko").child("semplan");
                break;
            case 6:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("utkarsh").child("semplan");
                break;
            case 7:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("disha").child("semplan");
                break;
            case 8:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati1").child("semplan");
                break;
            case 9:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati2").child("semplan");
                break;
            case 10:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("youth").child("semplan");
                break;
        }

        final semplan semplan = semplans.get(position);
        holder.plan.setText(semplan.getPlan());
        if(semplan.getWeight().equals("No deadline")){
            holder.deadline.setVisibility(View.GONE);
            holder.deadline_label.setVisibility(View.GONE);
        }
        holder.deadline.setText(semplan.getWeight());
        if(semplans.get(position).getComplete().equals("yes")){
        holder.check.setVisibility(View.VISIBLE);}
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//creating a popup menu
                final PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.option_menu_semplan);
                //adding click listener
                        if(semplan.getComplete().equals("no"))
                        popup.getMenu().getItem(0).setTitle("Mark as Done");
                        else
                            popup.getMenu().getItem(0).setTitle("Mark as Undone");
                if(!excom)popup.getMenu().getItem(0).setVisible(false);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                mListener.onDeleteClick(position);
                                break;
                            case R.id.mark_as_done:
                                if(item.getTitle().equals("Mark as Done")) {
                                    databaseReference.child(semplans.get(position).getKey()).child("complete").setValue("yes");
                                } else {
                                    databaseReference.child(semplans.get(position).getKey()).child("complete").setValue("no");
                                }
                                break;
                            case R.id.edit:
                                mListener.onEditClick(position);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return semplans.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder /*implements
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener*/ {

        TextView plan ;
        ImageView check;
        public TextView buttonViewOption;
        TextView deadline,deadline_label;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            deadline = itemView.findViewById(R.id.deadline);
            deadline_label = itemView.findViewById(R.id.deadline_label);
            plan = itemView.findViewById(R.id.pretext);
            check = itemView.findViewById(R.id.edit_complete);
            buttonViewOption = itemView.findViewById(R.id.textViewOptions);
            //itemView.setOnCreateContextMenuListener(this);
            if(currentuser.substring(0,9).matches("[A-Za-z0-9]+")){
                databaseReference_contacts= FirebaseDatabase.getInstance().getReference().child("contactsMailIndexed").child(currentuser.substring(0,9));
                databaseReference_contacts.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                                buttonViewOption.setVisibility(View.VISIBLE);
                                excom = true;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });}
            if(MainActivity.if_pl == 1 && ProjectsFragment.project == MyFirebaseSrevice.userProp)
            buttonViewOption.setVisibility(View.VISIBLE);
        }

        /*@Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onEditClick(position);
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
        MenuItem edit = menu.add(Menu.NONE, 1, 1, "Edit");
        MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");


        edit.setOnMenuItemClickListener(this);
        delete.setOnMenuItemClickListener(this);
    }*/


    }

public interface OnItemClickListener {

        void onEditClick(int position) ;

        void onDeleteClick(int position);
    }

    public  void setOnItemClickListener(utkarsh_semplan_adapter.OnItemClickListener listener) {
        mListener = listener;
    }

}
