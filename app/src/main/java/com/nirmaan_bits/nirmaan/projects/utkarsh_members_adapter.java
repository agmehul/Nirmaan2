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
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.Contact;
import com.nirmaan_bits.nirmaan.MainActivity;
import com.nirmaan_bits.nirmaan.R;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class utkarsh_members_adapter extends RecyclerView.Adapter<utkarsh_members_adapter.ContactViewHolder> {
    Context context;
    List<Contact> members;
    private  utkarsh_members_adapter.OnItemClickListener mListener;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference_user;
    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    private DatabaseReference databaseReference_contacts;
    boolean excom = false;
    public utkarsh_members_adapter(Context context, List<Contact> TempList) {

        this.members = TempList;

        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public utkarsh_members_adapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        utkarsh_members_adapter.ContactViewHolder viewHolder = new utkarsh_members_adapter.ContactViewHolder(view);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull final utkarsh_members_adapter.ContactViewHolder holder, final int position) {
        switch (ProjectsFragment.project){

            case 1:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("gbbaas").child("members");
                break;
            case 2:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("gbcb").child("members");
                break;
            case 3:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("sap").child("members");
                break;
            case 4:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("pcd").child("members");
                break;
            case 5:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("sko").child("members");
                break;
            case 6:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("utkarsh").child("members");
                break;
            case 7:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("disha").child("members");
                break;
            case 8:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati1").child("members");
                break;
            case 9:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati2").child("members");
                break;
            case 10:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("youth").child("members");
                break;
            case 11:
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Projects").child("prd").child("members");
                break;
        }
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
        holder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no = "tel:" + member.getNum();
                String url = "https://api.whatsapp.com/send?phone="+"91 "+no;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

        holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//creating a popup menu
                final PopupMenu popup = new PopupMenu(context, holder.textViewOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.option_menu_members);
                //adding click listener


                if(member.getPl().equals(""))
                    popup.getMenu().getItem(0).setTitle("Make PL");
                else
                    popup.getMenu().getItem(0).setTitle("Remove PL");
                if(!excom)popup.getMenu().getItem(0).setVisible(false);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                mListener.onDeleteClick(position);
                                break;
                            case R.id.makePl:
                                if(item.getTitle().equals("Make PL")) {
                                    databaseReference.child(member.getKey()).child("pl").setValue("yes");
                                    databaseReference_user = FirebaseDatabase.getInstance().getReference().child("users").child(member.getEmail().substring(0,9));
                                    databaseReference_user.child("pl").setValue("yes");
                                } else {
                                    databaseReference.child(member.getKey()).child("pl").removeValue();
                                    databaseReference_user = FirebaseDatabase.getInstance().getReference().child("users").child(member.getEmail().substring(0,9));
                                    databaseReference_user.child("pl").removeValue();
                                }
                                break;
                            case R.id.edit:
                                mListener.onEditClick(position);
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
        return members.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{

        TextView name, contact, year, pl, visits,textViewOptions;
        ImageView call,whatsapp;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            visits = itemView.findViewById(R.id.visits_total);

            name = itemView.findViewById(R.id.member_name);
            contact = itemView.findViewById(R.id.member_numb);
            year = itemView.findViewById(R.id.member_year);
            pl = itemView.findViewById(R.id.member_pl);
            call = itemView.findViewById(R.id.call);
            whatsapp = itemView.findViewById(R.id.whatsapp);
            textViewOptions =itemView.findViewById(R.id.textViewOptions);
            //itemView.setOnCreateContextMenuListener(this);
            if(MainActivity.project == "guest"){
                contact.setVisibility(View.INVISIBLE);
                call.setVisibility(View.GONE);
                whatsapp.setVisibility(View.GONE);
                visits.setVisibility(View.INVISIBLE);
            }

            if(MainActivity.if_pl == 1 && ProjectsFragment.project == MyFirebaseSrevice.userProp)textViewOptions.setVisibility(View.VISIBLE);
            if(currentuser.substring(0,9).matches("[A-Za-z0-9]+")){
                databaseReference_contacts= FirebaseDatabase.getInstance().getReference().child("contactsMailIndexed").child(currentuser.substring(0,9));
                databaseReference_contacts.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            textViewOptions.setVisibility(View.VISIBLE);
                            excom=true;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });}
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
        MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Edit Details");
        MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");


        doWhatever.setOnMenuItemClickListener(this);
        delete.setOnMenuItemClickListener(this);
    }*/


    }

public interface OnItemClickListener {

        void onEditClick(int position);

        void onDeleteClick(int position);
    }


    public  void setOnItemClickListener(utkarsh_members_adapter.OnItemClickListener listener) {
        mListener = listener;
    }

}
