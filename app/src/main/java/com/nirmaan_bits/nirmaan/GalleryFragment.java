package com.nirmaan_bits.nirmaan;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class GalleryFragment extends Fragment {

    private DatabaseReference databaseReference;
    public static DatabaseReference mDbr;

    private DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("notification");
   /* private  DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference().child("notification").child("gbbaas").child("members");
    private  DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference().child("notification").child("gbcb").child("members");
    private  DatabaseReference databaseReference3= FirebaseDatabase.getInstance().getReference().child("notification").child("sap").child("members");
    private  DatabaseReference databaseReference4= FirebaseDatabase.getInstance().getReference().child("notification").child("pcd").child("members");
    private  DatabaseReference databaseReference5= FirebaseDatabase.getInstance().getReference().child("notification").child("sko").child("members");
    private  DatabaseReference databaseReference6= FirebaseDatabase.getInstance().getReference().child("notification").child("utkarsh").child("members");
    private  DatabaseReference databaseReference7= FirebaseDatabase.getInstance().getReference().child("notification").child("disha").child("members");
    private  DatabaseReference databaseReference8= FirebaseDatabase.getInstance().getReference().child("notification").child("unnati1").child("members");
    private  DatabaseReference databaseReference9= FirebaseDatabase.getInstance().getReference().child("notification").child("unnati2").child("members");
    private  DatabaseReference databaseReference10= FirebaseDatabase.getInstance().getReference().child("notification").child("youth").child("members");
    private  DatabaseReference databaseReference11= FirebaseDatabase.getInstance().getReference().child("notification").child("prd").child("members");*/

   // static TextView project;
    static TextView project1;
    LinearLayout stats;

    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    private TextView name;
    TextView greeting;

   public static TextView visits_tv;
    public static TextView visits_in_per;

    private static TextView pl;
    private static String currentuserEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    private static String currentuserName = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
    private Uri uri = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhotoUrl();





    notf_member member = new notf_member(currentuser);
    public static GoogleSignInClient mGoogleSignInClient;
    public static FirebaseAuth mAuth;
   // ImageButton logout;
    //static Button select_project;
    FirebaseAuth.AuthStateListener mAuthListener;
    ImageView profile;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View homeview = inflater.inflate(R.layout.fragment_gallery, container, false);
      //  project = homeview.findViewById(R.id.project_1);
greeting =  homeview.findViewById(R.id.guest_greeting);
        profile = homeview.findViewById(R.id.profile_image);
        Glide.with(getActivity())
                .load(uri)
                .placeholder(R.mipmap.ic_launcher)
                // .fit()
                .centerCrop()
                .into(profile);


        project1 = homeview.findViewById(R.id.project_name);
        stats = homeview.findViewById(R.id.stats);


        name = homeview.findViewById(R.id.name);
        name.setText(currentuserName.toUpperCase());
        pl = homeview.findViewById(R.id.pl);
        visits_tv = homeview.findViewById(R.id.visits);
        visits_in_per = homeview.findViewById(R.id.visits_in_per);

        final Handler mUpdater = new Handler();
        Runnable mUpdateView = new Runnable() {
            @Override
            public void run() {
                visits_tv.setText("" +MainActivity.visits);
                visits_tv.invalidate();
                mUpdater.postDelayed(this, 1000);
            }
        };
        mUpdateView.run();
        final Handler mUpdater1 = new Handler();
        Runnable mUpdateView1 = new Runnable() {
            @Override
            public void run() {
                visits_in_per.setText("" + (int)(((double)MainActivity.visits/MainActivity.total)*100)+"%");
                visits_in_per.invalidate();
                mUpdater1.postDelayed(this, 1000);
            }
        };
        mUpdateView1.run();
        final Handler mUpdater2 = new Handler();
        Runnable mUpdateView2 = new Runnable() {
            @Override
            public void run() {
                if(MainActivity.if_pl==1)
                pl.setText("PL");
                pl.invalidate();
                mUpdater2.postDelayed(this, 1000);
            }
        };
        mUpdateView2.run();
        final Handler mUpdater3 = new Handler();
        Runnable mUpdateView3 = new Runnable() {
            @Override
            public void run() {

                project1.setText(MainActivity.project);
                project1.invalidate();
                mUpdater3.postDelayed(this, 1000);
                if(MainActivity.project!="Guest"){
                    stats.setVisibility(View.VISIBLE);
                    greeting.setVisibility(View.GONE);


                }

            }
        };
        mUpdateView3.run();




        ImageButton feed=homeview.findViewById(R.id.feedback);

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),feedback.class);
                startActivity(intent);
            }
        });
        ImageButton contact=homeview.findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),contact_activity.class);
                startActivity(intent);
            }
        });
        ImageButton about=homeview.findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),About.class);
                startActivity(intent);
            }
        });


        return homeview;




    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    public void onStart() {

        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //logout= Objects.requireNonNull(getView()).findViewById(R.id.signout);
        mAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("767599280120-rf9ranprhbrhp41oeej8430j6ti79pji.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()), gso);


        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                mGoogleSignInClient.signOut();

            }
        });*/

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null) {
                    Intent intent=new Intent(getActivity(),SignIn.class);
                    startActivity(intent);
                }
            }
        };


    }

}
