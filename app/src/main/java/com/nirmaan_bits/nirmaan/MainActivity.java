package com.nirmaan_bits.nirmaan;

import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nirmaan_bits.nirmaan.Service.MyFirebaseSrevice;

import java.util.Objects;

import com.nirmaan_bits.nirmaan.attendance.Mark_attendance_fragment;
import com.nirmaan_bits.nirmaan.gallery.HomeFragment;
import com.nirmaan_bits.nirmaan.note.noteActivity;
import com.nirmaan_bits.nirmaan.projects.ProjectsFragment;


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends AppCompatActivity {

    private String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
   // private String currentuserName = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();


    private FirebaseAnalytics mFirebaseAnalytics =  FirebaseAnalytics.getInstance(this);
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private DatabaseReference databaseReference;
    private DatabaseReference mDbr;

    private  DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference().child("Projects").child("gbbaas").child("members");
    private  DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference().child("Projects").child("gbcb").child("members");
    private  DatabaseReference databaseReference3= FirebaseDatabase.getInstance().getReference().child("Projects").child("sap").child("members");
    private  DatabaseReference databaseReference4= FirebaseDatabase.getInstance().getReference().child("Projects").child("pcd").child("members");
    private  DatabaseReference databaseReference5= FirebaseDatabase.getInstance().getReference().child("Projects").child("sko").child("members");
    private  DatabaseReference databaseReference6= FirebaseDatabase.getInstance().getReference().child("Projects").child("utkarsh").child("members");
    private  DatabaseReference databaseReference7= FirebaseDatabase.getInstance().getReference().child("Projects").child("disha").child("members");
    private  DatabaseReference databaseReference8= FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati1").child("members");
    private  DatabaseReference databaseReference9= FirebaseDatabase.getInstance().getReference().child("Projects").child("unnati2").child("members");
    private  DatabaseReference databaseReference10= FirebaseDatabase.getInstance().getReference().child("Projects").child("youth").child("members");
    private  DatabaseReference databaseReference11= FirebaseDatabase.getInstance().getReference().child("Projects").child("prd").child("members");

    BottomNavigationView bottomNav;
    public static ViewPager viewPager;
    public  static int visits =0 ,total = 1,if_pl;
    public static  String project = "Guest";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       bottomNav =findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListen);








        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        MyFirebaseSrevice.userProp= prefs.getInt("connect1", 0);


    databaseReference1.addValueEventListener(new ValueEventListener() {
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
    });


    databaseReference2.addValueEventListener(new ValueEventListener() {
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
        });


        viewPager = findViewById(R.id.pager); //Init Viewpager
        setupFm(getSupportFragmentManager(), viewPager); //Setup Fragment
        viewPager.setCurrentItem(0); //Set Currrent Item When Activity Start
        viewPager.addOnPageChangeListener(new PageChange()); //Listeners For Viewpager When Page Changed

     //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new GalleryFragment()).commit();



    }
    public static void setupFm(FragmentManager fragmentManager, ViewPager viewPager){
        FragmentAdapter Adapter = new FragmentAdapter(fragmentManager);
        //Add All Fragment To List
        Adapter.add(new GalleryFragment(), "Home");
        Adapter.add(new ProjectsFragment(), "Projects");
        Adapter.add(new HomeFragment(), "Gallery");
        Adapter.add(new noteActivity(), "Notes");
        Adapter.add(new Mark_attendance_fragment(), "Mark Attendance");

        viewPager.setAdapter(Adapter);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListen=new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

       // Fragment aba=null;
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

      case R.id.attendance:
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
                    bottomNav.setSelectedItemId(R.id.attendance);
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





}
