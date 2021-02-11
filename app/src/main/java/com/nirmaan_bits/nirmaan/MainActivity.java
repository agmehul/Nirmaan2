package com.nirmaan_bits.nirmaan;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;

import java.util.Objects;

import com.nirmaan_bits.nirmaan.gallery.galleryFragment;
import com.nirmaan_bits.nirmaan.idea.idea_main;
import com.nirmaan_bits.nirmaan.note.noteActivity;
import com.nirmaan_bits.nirmaan.projects.ProjectsFragment;


import static com.nirmaan_bits.nirmaan.R.style.splashScreenTheme;


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    TextView userDisplayName,userDisplayEmail;
    public static GoogleSignInClient mGoogleSignInClient;
    public static FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    private String currentuserName = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    private FirebaseAnalytics mFirebaseAnalytics =  FirebaseAnalytics.getInstance(this);
    //public static final String MY_PREFS_NAME = "MyPrefsFile";


    /*private  DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference().child("Projects").child("gbcb").child("members");
    private  DatabaseReference databaseReference3= FirebaseDatabase.getInstance().getReference().child("Projects").child("sap").child("members");
    private  DatabaseReference databaseReference4= FirebaseDatabase.getInstance().getReference().child("Projects").child("pcd").child("members");
    private  DatabaseReference databaseReference5= FirebaseDatabase.getInstance().getReference().child("Projects").child("sko").child("members");
    private  DatabaseReference databaseReference6= FirebaseDatabase.getInstance().getReference().child("Projects").child("utkarsh").child("members");
    private  DatabaseReference databaseReference7= FirebaseDatabase.getInstance().getReference().child("Projects").child("disha").child("members");
    private  DatabaseReference databaseReference8= FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati1").child("members");
    private  DatabaseReference databaseReference9= FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati2").child("members");
    private  DatabaseReference databaseReference10= FirebaseDatabase.getInstance().getReference().child("Projects").child("youth").child("members");
    private  DatabaseReference databaseReference11= FirebaseDatabase.getInstance().getReference().child("Projects").child("prd").child("members");*/

    BottomNavigationView bottomNav;
    public static ViewPager viewPager;
    public  static int visits =0 ,total = 1,if_pl =0;
    public static  String project = "guest";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(splashScreenTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //userDisplayName.setText(currentuserName);
        //userDisplayEmail.setText(currentuser);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        String arr[] = currentuserName.split(" ", 2);

        String first_name = arr[0];   //first name
        getSupportActionBar().setTitle("Hello, " + first_name);
        navigationView = findViewById(R.id.nested);
        View headerView = navigationView.getHeaderView(0);
        userDisplayName= headerView.findViewById(R.id.userDisplayName);
        userDisplayEmail =headerView.findViewById(R.id.userDisplayEmail);
        userDisplayName.setText(currentuserName);
        userDisplayEmail.setText(currentuser);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();




        bottomNav =findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListen);








        /*SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        MyFirebaseSrevice.userProp= prefs.getInt("connect1", 0);*/


        /*databaseReference1.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(Objects.requireNonNull(snapshot.child("email").getValue()).toString(), currentuser)) {
                        if (snapshot.child("pl").getValue() != null) {
                            // pl.setText("PL");
                            if_pl = 1;

                        }
                        visits =0;
                        total =0;
                        if (snapshot.child("history").getValue() != null){
                            for (DataSnapshot snapshot1 : snapshot.child("history").getChildren()) {
                                if (snapshot1.child("status").getValue().equals("Present")){
                                    visits++;

                                }
                                total++;
                            }
                        }
                        if (snapshot.child("history").getValue() == null)total =1;

                        //visits_tv.setText(""+visits);
                        //visits_in_per.setText("" + (visits/total)*100+"%");


                        mFirebaseAnalytics.setUserProperty("project", "gbbaas");
                        MyFirebaseSrevice.userProp = 1;
                        project = "GB BASS";
                        // GalleryFragment.findIfPl();



                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putInt("connect1", 1);
                        editor.apply();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/


        /*databaseReference2.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(Objects.requireNonNull(snapshot.child("email").getValue()).toString(), currentuser)) {
                        if (snapshot.child("pl").getValue() != null) {
                            // pl.setText("PL");
                            if_pl = 1;

                        }
                        visits =0;
                        total =0;
                        if (snapshot.child("history").getValue() != null){
                            for (DataSnapshot snapshot1 : snapshot.child("history").getChildren()) {
                                if (snapshot1.child("status").getValue().equals("Present")){
                                    visits++;

                                }
                                total++;
                            }
                        }
                        if (snapshot.child("history").getValue() == null)total =1;
                        mFirebaseAnalytics.setUserProperty("project", "gbcb");
                        MyFirebaseSrevice.userProp = 2;
                        //  GalleryFragment.findIfPl();

                        project = "PKP";
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putInt("connect1", 2);
                        editor.apply();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        databaseReference3.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(Objects.requireNonNull(snapshot.child("email").getValue()).toString(), currentuser)) {
                        if (snapshot.child("pl").getValue() != null) {
                            // pl.setText("PL");
                            if_pl = 1;

                        }
                        visits =0;
                        total =0;
                        if (snapshot.child("history").getValue() != null){
                            for (DataSnapshot snapshot1 : snapshot.child("history").getChildren()) {
                                if (snapshot1.child("status").getValue().equals("Present")){
                                    visits++;

                                }
                                total++;
                            }
                        }
                        if (snapshot.child("history").getValue() == null)total =1;
                        mFirebaseAnalytics.setUserProperty("project", "sap");

                        MyFirebaseSrevice.userProp = 3;
                        //GalleryFragment.findIfPl();
                        project = "SAP";
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putInt("connect1", 3);
                        editor.apply();break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        databaseReference4.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(Objects.requireNonNull(snapshot.child("email").getValue()).toString(), currentuser)) {
                        if (snapshot.child("pl").getValue() != null) {
                            // pl.setText("PL");
                            if_pl = 1;

                        }
                        visits =0;
                        total =0;
                        if (snapshot.child("history").getValue() != null){
                            for (DataSnapshot snapshot1 : snapshot.child("history").getChildren()) {
                                if (snapshot1.child("status").getValue().equals("Present")){
                                    visits++;

                                }
                                total++;
                            }
                        }
                        if (snapshot.child("history").getValue() == null)total =1;
                        mFirebaseAnalytics.setUserProperty("project", "pcd");
                        MyFirebaseSrevice.userProp = 4;
                        //GalleryFragment.findIfPl();
                        project = "PCD";

                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putInt("connect1", 4);
                        editor.apply();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        databaseReference5.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(Objects.requireNonNull(snapshot.child("email").getValue()).toString(), currentuser)) {
                        if (snapshot.child("pl").getValue() != null) {
                            // pl.setText("PL");
                            if_pl = 1;

                        }
                        visits =0;
                        total =0;
                        if (snapshot.child("history").getValue() != null){
                            for (DataSnapshot snapshot1 : snapshot.child("history").getChildren()) {
                                if (snapshot1.child("status").getValue().equals("Present")){
                                    visits++;

                                }
                                total++;
                            }
                        }
                        if (snapshot.child("history").getValue() == null)total =1;
                        mFirebaseAnalytics.setUserProperty("project", "sko");

                        MyFirebaseSrevice.userProp = 5;
                        //GalleryFragment.findIfPl();
                        project = "SKO";

                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putInt("connect1", 5);
                        editor.apply();
                        break;
                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        databaseReference7.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(Objects.requireNonNull(snapshot.child("email").getValue()).toString(), currentuser)) {
                        if (snapshot.child("pl").getValue() != null) {
                            // pl.setText("PL");
                            if_pl = 1;

                        }
                        visits =0;
                        total =0;
                        if (snapshot.child("history").getValue() != null){
                            for (DataSnapshot snapshot1 : snapshot.child("history").getChildren()) {
                                if (snapshot1.child("status").getValue().equals("Present")){
                                    visits++;

                                }
                                total++;
                            }
                        }
                        if (snapshot.child("history").getValue() == null)total =1;
                        mFirebaseAnalytics.setUserProperty("project", "disha");

                        MyFirebaseSrevice.userProp = 7;
                        // GalleryFragment.findIfPl();
                        project = "DISHA";

                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putInt("connect1", 7);
                        editor.apply();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        databaseReference8.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(Objects.requireNonNull(snapshot.child("email").getValue()).toString(), currentuser)) {
                        if (snapshot.child("pl").getValue() != null) {
                            // pl.setText("PL");
                            if_pl = 1;

                        }
                        visits =0;
                        total =0;
                        if (snapshot.child("history").getValue() != null){
                            for (DataSnapshot snapshot1 : snapshot.child("history").getChildren()) {
                                if (snapshot1.child("status").getValue().equals("Present")){
                                    visits++;

                                }
                                total++;
                            }
                        }
                        if (snapshot.child("history").getValue() == null)total =1;
                        mFirebaseAnalytics.setUserProperty("project", "unnati1");

                        MyFirebaseSrevice.userProp = 8;
                        //  GalleryFragment.findIfPl();

                        project = "UNNATI-1";
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putInt("connect1", 8);
                        editor.apply();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        databaseReference9.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(Objects.requireNonNull(snapshot.child("email").getValue()).toString(), currentuser)) {
                        if (snapshot.child("pl").getValue() != null) {
                            // pl.setText("PL");
                            if_pl = 1;

                        }
                        visits =0;
                        total =0;
                        if (snapshot.child("history").getValue() != null){
                            for (DataSnapshot snapshot1 : snapshot.child("history").getChildren()) {
                                if (snapshot1.child("status").getValue().equals("Present")){
                                    visits++;

                                }
                                total++;
                            }
                        }
                        if (snapshot.child("history").getValue() == null)total =1;
                        mFirebaseAnalytics.setUserProperty("project", "unnati2");
                        MyFirebaseSrevice.userProp = 9;
                        //GalleryFragment.findIfPl();
                        project = "UNNATI-2";

                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putInt("connect1", 9);
                        editor.apply();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        databaseReference6.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(Objects.requireNonNull(snapshot.child("email").getValue()).toString(), currentuser)) {
                        if (snapshot.child("pl").getValue() != null) {
                            // pl.setText("PL");
                            if_pl = 1;

                        }
                        visits =0;
                        total =0;
                        if (snapshot.child("history").getValue() != null){
                            for (DataSnapshot snapshot1 : snapshot.child("history").getChildren()) {
                                if (snapshot1.child("status").getValue().equals("Present")){
                                    visits++;

                                }
                                total++;
                            }
                        }
                        if (snapshot.child("history").getValue() == null)total =1;
                        mFirebaseAnalytics.setUserProperty("project", "utkarsh");
                        MyFirebaseSrevice.userProp = 6;
                        //  GalleryFragment.findIfPl();
                        project = "UTKARSH";

                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putInt("connect1", 6);
                        editor.apply();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        databaseReference10.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(Objects.requireNonNull(snapshot.child("email").getValue()).toString(), currentuser)) {
                        if (snapshot.child("pl").getValue() != null) {
                            // pl.setText("PL");
                            if_pl = 1;

                        }
                        visits =0;
                        total =0;
                        if (snapshot.child("history").getValue() != null){
                            for (DataSnapshot snapshot1 : snapshot.child("history").getChildren()) {
                                if (snapshot1.child("status").getValue().equals("Present")){
                                    visits++;

                                }
                                total++;
                            }
                        }
                        if (snapshot.child("history").getValue() == null)total =1;
                        mFirebaseAnalytics.setUserProperty("project", "youth");
                        MyFirebaseSrevice.userProp = 10;
                        project = "YOUTH";

                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putInt("connect1", 10);
                        editor.apply();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        databaseReference11.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (Objects.equals(Objects.requireNonNull(snapshot.child("email").getValue()).toString(), currentuser)) {
                        if (snapshot.child("pl").getValue() != null) {
                            // pl.setText("PL");
                            if_pl = 1;

                        }
                        visits =0;
                        total =0;
                        if (snapshot.child("history").getValue() != null){
                            for (DataSnapshot snapshot1 : snapshot.child("history").getChildren()) {
                                if (snapshot1.child("status").getValue().equals("Present")){
                                    visits++;

                                }
                                total++;
                            }
                        }
                        if (snapshot.child("history").getValue() == null)total =1;
                        mFirebaseAnalytics.setUserProperty("project", "prd");
                        MyFirebaseSrevice.userProp = 11;
                        // GalleryFragment.findIfPl();

                        project = "PRD";
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putInt("connect1", 11);
                        editor.apply();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/


        viewPager = findViewById(R.id.pager); //Init Viewpager
        setupFm(getSupportFragmentManager(), viewPager); //Setup Fragment
        viewPager.setCurrentItem(0); //Set Currrent Item When Activity Start
        viewPager.addOnPageChangeListener(new PageChange()); //Listeners For Viewpager When Page Changed

        //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new GalleryFragment()).commit();


        mAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("767599280120-rf9ranprhbrhp41oeej8430j6ti79pji.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(MainActivity.this), gso);


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
                    Intent intent=new Intent(MainActivity.this,SignIn.class);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (databaseReference != null && valueEventListener != null) {
            databaseReference.removeEventListener(valueEventListener);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(this, "On start called", Toast.LENGTH_SHORT).show();
        mAuth.addAuthStateListener(mAuthListener);
        if(currentuser.substring(0,9).matches("[A-Za-z0-9]+")){
            databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(currentuser.substring(0,9));
            valueEventListener= databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Toast.makeText(MainActivity.this, "Onresume called inside value event", Toast.LENGTH_SHORT).show();
                    if(snapshot.exists()){
                        if (snapshot.child("pl").getValue() != null) {
                            if_pl = 1;
                        }

                        String project_firebase = snapshot.child("project").getValue().toString();
                        mFirebaseAnalytics.setUserProperty("project", project_firebase);
                        switch (project_firebase) {
                            case "gbbaas":
                                MyFirebaseSrevice.userProp = 1;
                                break;
                            case "gbcb":
                                MyFirebaseSrevice.userProp = 2;
                                break;
                            case "sap":
                                MyFirebaseSrevice.userProp = 3;
                                break;
                            case "pcd":
                                MyFirebaseSrevice.userProp = 4;
                                break;
                            case "sko":
                                MyFirebaseSrevice.userProp = 5;
                                break;
                            case "utkarsh":
                                MyFirebaseSrevice.userProp = 6;
                                break;
                            case "disha":
                                MyFirebaseSrevice.userProp = 7;
                                break;
                            case "unnati1":
                                MyFirebaseSrevice.userProp = 8;
                                break;
                            case "unnati2":
                                MyFirebaseSrevice.userProp = 9;
                                break;
                            case "youth":
                                MyFirebaseSrevice.userProp = 10;
                                break;
                            case "prd":
                                MyFirebaseSrevice.userProp = 11;
                                break;
                            default:
                                MyFirebaseSrevice.userProp = -1;
                                break;
                        }
                        /*if(project_firebase.equals("gbcb"))
                            project = "PKP";
                        else*/
                            project = project_firebase.toUpperCase();
                    }
                    else{
                        MyFirebaseSrevice.userProp = -1;
                        project = "guest";
                        if_pl = 0;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else {
            MyFirebaseSrevice.userProp = -1;
            project = "guest";
            if_pl = 0;
        }
    }

    public static void setupFm(FragmentManager fragmentManager, ViewPager viewPager){
        FragmentAdapter Adapter = new FragmentAdapter(fragmentManager);
        //Add All Fragment To List
        Adapter.add(new homeFragment(), "Home");
        Adapter.add(new ProjectsFragment(), "Projects");
        Adapter.add(new galleryFragment(), "Gallery");
        Adapter.add(new noteActivity(), "Notes");
        Adapter.add(new idea_main(), "Ideas");

        viewPager.setAdapter(Adapter);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListen=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()){
                case R.id.home:
                    viewPager.setCurrentItem(0);

                    break;

                case R.id.project:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.gallery:
                    viewPager.setCurrentItem(2);
                    break;

                case R.id.ideas:
                    viewPager.setCurrentItem(4);
                    break;
                case R.id.notes:
                    viewPager.setCurrentItem(3);
                    break;

            }


            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment, Objects.requireNonNull(aba)).commit();
            return true;
        }

    };
    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        closeDrawer();
        switch (menuItem.getItemId()){
            case R.id.attendance:
                Intent intent_docs=new Intent(MainActivity.this,com.nirmaan_bits.nirmaan.attendance.Mark_attendance_activity.class);
                startActivity(intent_docs);
                break;
            case R.id.developers:
                Intent intent_developers=new Intent(MainActivity.this,developers_activity.class);
                startActivity(intent_developers);
                break;
            case R.id.logout:
                mAuth.signOut();
                mGoogleSignInClient.signOut();
                break;
            case R.id.rating:
                rateApp();
                break;
            case R.id.shareapp:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                break;
            default:Toast.makeText(this,"coming soon", Toast.LENGTH_LONG).show();
        }

        return true;

    }

    public class PageChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    bottomNav.setSelectedItemId(R.id.home);
                    break;
                case 1:
                    bottomNav.setSelectedItemId(R.id.project);
                    break;
                case 2:
                    bottomNav.setSelectedItemId(R.id.gallery);
                    break;
                case 4:
                    bottomNav.setSelectedItemId(R.id.ideas);
                    break;
                case 3:
                    bottomNav.setSelectedItemId(R.id.notes);
                    break;


            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }



    private void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }
    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

}