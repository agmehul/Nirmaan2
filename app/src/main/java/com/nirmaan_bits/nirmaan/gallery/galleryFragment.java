package com.nirmaan_bits.nirmaan.gallery;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nirmaan_bits.nirmaan.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class galleryFragment extends Fragment implements gallery_project_adapter.OnItemClickListener {
    public static int project =0;
    private RecyclerView recyclerView;

    private gallery_project_adapter adapter;
    //model object for our list data
    private List<String> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        loadRecyclerViewItem();




//        TextView all_nirmaan = view.findViewById(R.id.all_nirmaan);
//        TextView gbbaas =view.findViewById(R.id.gbbs);
//        TextView gbcb =view.findViewById(R.id.gbcb);
//        TextView sap =view.findViewById(R.id.sap);
//        TextView pcd =view.findViewById(R.id.pcd);
//        TextView sko =view.findViewById(R.id.sko);
//        TextView utk = view.findViewById(R.id.utkarsh);
//        TextView disha =view.findViewById(R.id.disha);
//        TextView un1 =view.findViewById(R.id.un1);
//        TextView un2 =view.findViewById(R.id.un2);
//        TextView youth = view.findViewById(R.id.youth);



//        gbbaas.setOnClickListener(new View.OnClickListener(){
//
//                                      @Override
//                                      public void onClick(View v){
//                                          Intent in= new Intent(getActivity(),FoldersActivity.class);
//                                          project =1;
//
//                                          startActivity(in);}
//
//                                  }
//
//
//
//        );
//
//        gbcb.setOnClickListener(new View.OnClickListener(){
//
//                                    @Override
//                                    public void onClick(View v){
//                                        Intent in= new Intent(getActivity(),FoldersActivity.class);
//                                        project =2;
//
//                                        startActivity(in);}
//                                }
//
//
//        );
//        sap.setOnClickListener(new View.OnClickListener(){
//
//                                   @Override
//                                   public void onClick(View v){
//                                       Intent in= new Intent(getActivity(),FoldersActivity.class);
//                                       project =3;
//
//                                       startActivity(in);}
//                               }
//
//
//        );
//        pcd.setOnClickListener(new View.OnClickListener(){
//
//                                   @Override
//                                   public void onClick(View v){
//                                       Intent in= new Intent(getActivity(),FoldersActivity.class);
//                                       project =4;
//
//                                       startActivity(in);}
//                               }
//
//
//        );
//        sko.setOnClickListener(new View.OnClickListener(){
//
//                                   @Override
//                                   public void onClick(View v){
//                                       Intent in= new Intent(getActivity(),FoldersActivity.class);
//                                       project =5;
//
//                                       startActivity(in);}
//                               }
//
//
//        );
//        utk.setOnClickListener(new View.OnClickListener(){
//
//                                   @Override
//                                   public void onClick(View v){
//                                       Intent in= new Intent(getActivity(),FoldersActivity.class);
//                                       project =6;
//
//                                       startActivity(in);}
//                               }
//
//
//        );
//        disha.setOnClickListener(new View.OnClickListener(){
//
//                                     @Override
//                                     public void onClick(View v){
//                                         Intent in= new Intent(getActivity(),FoldersActivity.class);
//                                         project =7;
//
//                                         startActivity(in);}
//                                 }
//
//
//        );
//        un1.setOnClickListener(new View.OnClickListener(){
//
//                                   @Override
//                                   public void onClick(View v){
//                                       Intent in= new Intent(getActivity(),FoldersActivity.class);
//                                       project =8;
//
//                                       startActivity(in);}
//                               }
//
//
//        );
//        un2.setOnClickListener(new View.OnClickListener(){
//
//                                   @Override
//                                   public void onClick(View v){
//                                       Intent in= new Intent(getActivity(),FoldersActivity.class);
//                                       project =9;
//
//                                       startActivity(in);}
//                               }
//
//
//        );
//        youth.setOnClickListener(new View.OnClickListener(){
//
//                                   @Override
//                                   public void onClick(View v){
//                                       Intent in= new Intent(getActivity(),FoldersActivity.class);
//                                       project =10;
//
//                                       startActivity(in);}
//                               }
//
//
//        );
//        all_nirmaan.setOnClickListener(new View.OnClickListener(){
//
//                                   @Override
//                                   public void onClick(View v){
//                                       Intent in= new Intent(getActivity(),FoldersActivity.class);
//                                       project =11;
//
//                                       startActivity(in);}
//                               }
//
//
//        );


        return view;
    }

    private void loadRecyclerViewItem() {
        list.clear();
        list.add("All nirmaan");
        list.add("GB BASS");
        list.add("PKP");
        list.add("SAP");
        list.add("PCD");
        list.add("SKO");
        list.add("UTKARSH");
        list.add("DISHA");
        list.add("UNNATI 1");
        list.add("UNNATI 2");
        list.add("YOUTH EMPLOYMENT");
        adapter = new gallery_project_adapter(list, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        Intent in= new Intent(getActivity(),FoldersActivity.class);
        project =position;
        startActivity(in);
    }
}


