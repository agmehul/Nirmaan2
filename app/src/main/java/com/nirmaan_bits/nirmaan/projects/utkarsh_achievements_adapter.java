package com.nirmaan_bits.nirmaan.projects;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
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

import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class utkarsh_achievements_adapter extends RecyclerView.Adapter<utkarsh_achievements_adapter.ContactViewHolder> {
    Context context;
    List<ach> achs;
    private  utkarsh_achievements_adapter.OnItemClickListener mListener;
    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    private DatabaseReference databaseReference_contacts;
    boolean excom = false;
    public utkarsh_achievements_adapter(Context context, List<ach> TempList) {

        this.achs = TempList;

        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public utkarsh_achievements_adapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ach_layout, parent, false);

        utkarsh_achievements_adapter.ContactViewHolder viewHolder = new utkarsh_achievements_adapter.ContactViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final utkarsh_achievements_adapter.ContactViewHolder holder, final int position) {


        final ach ach = achs.get(position);
        holder.content.setText(ach.getContent());
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//creating a popup menu
                final PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.option_menu_achievements);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                mListener.onDeleteClick(position);
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
        return achs.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder /*implements
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener*/ {

        TextView content ;
        public TextView buttonViewOption;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.pretext);
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
    }

public interface OnItemClickListener {

        void onEditClick(int position) ;

        void onDeleteClick(int position);
    }

    public  void setOnItemClickListener(utkarsh_achievements_adapter.OnItemClickListener listener) {
        mListener = listener;
    }

}
