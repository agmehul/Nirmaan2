package com.nirmaan_bits.nirmaan.note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.nirmaan_bits.nirmaan.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class noteActivity extends Fragment {
    RecyclerView noteLists;
    FirebaseFirestore fStore;
    FirestoreRecyclerAdapter<Note,NoteViewHolder> noteAdapter;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.content_main, container, false);

       setHasOptionsMenu(true);
        fStore = FirebaseFirestore.getInstance();

        Query query = fStore.collection("notes").orderBy("date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Note> allNotes = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class)
                .build();


        noteAdapter = new FirestoreRecyclerAdapter<Note, NoteViewHolder>(allNotes) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, final int i, @NonNull final Note note) {
                noteViewHolder.noteTitle.setText(note.getTitle());
                noteViewHolder.noteContent.setText(note.getContent());
                noteViewHolder.noteDate.setText(note.getDate());
                noteViewHolder.noteAuthor.setText(note.getAuthor());
                noteViewHolder.noteProject.setText(note.getProject());
                final int code = getRandomColor();
                noteViewHolder.mCardView.setCardBackgroundColor(noteViewHolder.view.getResources().getColor(code,null));
                final String docId = noteAdapter.getSnapshots().getSnapshot(i).getId();

                noteViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), NoteDetails.class);
                        i.putExtra("title",note.getTitle());
                        i.putExtra("content",note.getContent());
                        i.putExtra("code",code);
                        i.putExtra("noteId",docId);
                        v.getContext().startActivity(i);
                    }
                });

                ImageView menuIcon = noteViewHolder.view.findViewById(R.id.menuIcon);
                menuIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final String docId = noteAdapter.getSnapshots().getSnapshot(i).getId();
                        PopupMenu menu = new PopupMenu(v.getContext(),v);
                        menu.setGravity(Gravity.END);
                        menu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Intent i = new Intent(v.getContext(), EditNote.class);
                                i.putExtra("title",note.getTitle());
                                i.putExtra("content",note.getContent());
                                i.putExtra("noteId",docId);
                                startActivity(i);
                                return false;
                            }
                        });

                        menu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                DocumentReference docRef = fStore.collection("notes").document(docId);
                                docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // com.nirmaan_bits.nirmaan.note deleted
                                        Toast.makeText(getContext(), "Note Deleted.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Error in Deleting Note.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return false;
                            }
                        });

                        menu.show();

                    }
                });



            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view_layout,parent,false);
                return new NoteViewHolder(view);
            }
        };



        noteLists = view.findViewById(R.id.notelist);

        noteLists.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        noteLists.setAdapter(noteAdapter);




    FloatingActionButton fab = view.findViewById(R.id.addNoteFloat);
        fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(view.getContext(), AddNote.class));
            /*((AppCompatActivity)getActivity()).overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
            ((AppCompatActivity)getActivity()).finish();*/
        }
    });

       return view;
}

/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.settings){
            Toast.makeText(getContext(), "Settings Menu is Clicked.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }*/

    public class NoteViewHolder extends RecyclerView.ViewHolder{
                TextView noteTitle,noteContent,noteAuthor,noteDate,noteProject;
                View view;
                CardView mCardView;

                public NoteViewHolder(@NonNull View itemView) {
                    super(itemView);

                    noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);
            noteAuthor = itemView.findViewById(R.id.author);
            noteDate = itemView.findViewById(R.id.date);
            mCardView = itemView.findViewById(R.id.noteCard);
            noteProject = itemView.findViewById(R.id.project_note);
            view = itemView;
        }
    }

    private int getRandomColor() {

        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.blue);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.skyblue);
        colorCode.add(R.color.lightPurple);
        colorCode.add(R.color.lightGreen);
        colorCode.add(R.color.gray);
        colorCode.add(R.color.pink);
        colorCode.add(R.color.red);
        colorCode.add(R.color.greenlight);
        colorCode.add(R.color.notgreen);

        Random randomColor = new Random();
        int number = randomColor.nextInt(colorCode.size());
        return colorCode.get(number);

    }

    @Override
    public void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (noteAdapter != null) {
            noteAdapter.stopListening();
        }
    }

}

