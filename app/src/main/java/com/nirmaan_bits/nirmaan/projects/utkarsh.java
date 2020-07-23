package com.nirmaan_bits.nirmaan.projects;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.nirmaan_bits.nirmaan.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class utkarsh extends AppCompatActivity implements View.OnClickListener {
    private Button utk1,utk2,utk3,utk4,utk5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utkarsh_);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

switch (ProjectsFragment.project){

    case 1:
        collapsingToolbarLayout.setTitle("GB Baas");
    break;
    case 2:
        collapsingToolbarLayout.setTitle("PKP");
        break;
    case 3:
        collapsingToolbarLayout.setTitle("SAP");
        break;
    case 4:
        collapsingToolbarLayout.setTitle("PCD");
        break;
    case 5:
        collapsingToolbarLayout.setTitle("SKO");
        break;
    case 6:
        collapsingToolbarLayout.setTitle("Utkarsh");
        break;
    case 7:
        collapsingToolbarLayout.setTitle("Disha");
        break;
    case 8:
        collapsingToolbarLayout.setTitle("Unnati 1");
        break;
    case 9:
        collapsingToolbarLayout.setTitle("Unnati 2");
        break;
    case 10:
        collapsingToolbarLayout.setTitle("Youth");
        break;

        default:break;




}
        utk1 = findViewById(R.id.utkarsh1);
        utk2 = findViewById(R.id.utkarsh2);
        utk3 = findViewById(R.id.utkarsh3);
        utk4 = findViewById(R.id.utkarsh4);
        utk5 = findViewById(R.id.utkarsh5);

        utk1.setOnClickListener(this);

        utk2.setOnClickListener(this);

        utk3.setOnClickListener(this);

        utk4.setOnClickListener(this);

        utk5.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId())  {
                case R.id.utkarsh1 :
                    i=new Intent(this,utkarsh_aim.class);
                    startActivity(i);
                    break;
                case R.id.utkarsh2 :
                    i=new Intent(this,utkarsh_semPlan.class);
                    startActivity(i);
                    break;
                case R.id.utkarsh3 :
                    i=new Intent(this,utkarsh_members.class);
                    startActivity(i);
                    break;

                case R.id.utkarsh4:
                    i=new Intent(this,progress.class);
                    startActivity(i);
                    break;

                case R.id.utkarsh5:
                    i=new Intent(this,achievements.class);
                    startActivity(i);
                    break;
default:break;
            }

        }



}
