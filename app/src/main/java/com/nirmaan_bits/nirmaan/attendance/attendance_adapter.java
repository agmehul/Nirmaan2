package com.nirmaan_bits.nirmaan.attendance;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.R;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class attendance_adapter extends RecyclerView.Adapter<attendance_adapter.ViewHolder> {

    Context context;
    List<attn_member> attend_membs;
    private DatabaseReference databaseReference;
    private ValueEventListener mDBListener;

    public attendance_adapter(Context context, List<attn_member> TempList) {

        this.attend_membs = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_member, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        switch (MyFirebaseSrevice.userProp){

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


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String memb = attend_membs.get(position).getName();
        String status = attend_membs.get(position).getStatus();
        final String key = attend_membs.get(position).getKey();

        holder.Name.setText(memb);
        holder.status.setText(status);

        holder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date c = Calendar.getInstance().getTime();
                //System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                 String formattedDate = df.format(c);

                 DatabaseReference dr = databaseReference.child(key).child("history").child(formattedDate);
                history_status hs = new history_status(formattedDate,"Present");
                dr.setValue(hs);
            }
        });
        holder.absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date c = Calendar.getInstance().getTime();
                //System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c);
                DatabaseReference dr = databaseReference.child(key).child("history").child(formattedDate);
                history_status hs = new history_status(formattedDate,"Absent");
                dr.setValue(hs);
            }
        });
        holder.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), history.class);
                intent.putExtra("key",key);
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {

        return attend_membs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Name;
        public TextView status;

        public ImageButton present;

        public ImageButton absent;
        public ImageButton history;

        public ViewHolder(View itemView) {

            super(itemView);

            Name =  itemView.findViewById(R.id.member_name);
            status =  itemView.findViewById(R.id.status1);
            present = itemView.findViewById(R.id.present);
            absent = itemView.findViewById(R.id.absent);
            history = itemView.findViewById(R.id.history);


        }
    }

}
