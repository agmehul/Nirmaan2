package com.nirmaan_bits.nirmaan.idea;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.MainActivity;
import com.nirmaan_bits.nirmaan.R;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class idea_adapter extends RecyclerView.Adapter<idea_adapter.ContactViewHolder> {
    Context context;
    List<idea> ideas;
    private  idea_adapter.OnItemClickListener mListener;
    String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    //String currentuser_name = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();

    public idea_adapter(Context context, List<idea> TempList) {
        Collections.reverse(TempList);
        this.ideas = TempList;

        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public idea_adapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.idea_item, parent, false);

        idea_adapter.ContactViewHolder viewHolder = new idea_adapter.ContactViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final idea_adapter.ContactViewHolder holder, final int position) {

        String key = ideas.get(position).getKey();
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("ideas").child(key);
        idea idea = ideas.get(position);
        holder.idea.setText(idea.getContent());
        holder.time.setText(idea.getTime());
        holder.project.setText(idea.getProject());
        holder.upvotes.setText("" +idea.getUpvotes());
        if (ideas.get(position).upvoters.containsKey(currentuser)) {
            holder.upvotes.setBackgroundDrawable(holder.upvotes.getContext().getResources().getDrawable(R.drawable.rounded_bt_upvote_colorp));
        } else {
            holder.upvotes.setBackgroundDrawable(holder.upvotes.getContext().getResources().getDrawable(R.drawable.rounded_bt_upvote));
        }

        holder.upvotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.project!="guest"){
                rootRef.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        idea p = mutableData.getValue(idea.class);
                        if (p == null) {
                            return Transaction.success(mutableData);
                        }

                        if (p.upvoters.containsKey(currentuser)) {
                            // Un-upvote the idea and remove self from upvoters
                            p.upvotes = p.upvotes - 1;
                            p.upvoters.remove(currentuser);
                        } else {
                            // upvote the idea and add self to upvoters
                            p.upvotes = p.upvotes + 1;
                            p.upvoters.put(currentuser, true);
                        }

                        // Set value and report transaction success
                        mutableData.setValue(p);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean committed,
                                           DataSnapshot currentData) {
                        // Transaction completed
                        //Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                    }
                });
            }else{
                    Toast.makeText(context, "Only members are allowed to vote", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ideas.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView idea ;
        Button upvotes;
        TextView time;
        TextView project;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            idea = itemView.findViewById(R.id.idea);
            upvotes = itemView.findViewById(R.id.upvotes);
            time = itemView.findViewById(R.id.time);
            project = itemView.findViewById(R.id.project);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            try {
                                mListener.onEditClick(position);
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
        MenuItem edit = menu.add(Menu.NONE, 1, 1, "Edit");
        MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");


        edit.setOnMenuItemClickListener(this);
        delete.setOnMenuItemClickListener(this);
    }


    }

public interface OnItemClickListener {

        void onEditClick(int position) throws IOException;

        void onDeleteClick(int position);
    }

    public  void setOnItemClickListener(idea_adapter.OnItemClickListener listener) {
        mListener = listener;
    }

}
