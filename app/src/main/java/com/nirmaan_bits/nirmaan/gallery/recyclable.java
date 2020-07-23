package com.nirmaan_bits.nirmaan.gallery;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nirmaan_bits.nirmaan.R;
import com.squareup.picasso.Picasso;

public class recyclable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclable);

    getIncomingIntent();

    }


    private void getIncomingIntent(){

    if (getIntent().hasExtra("image_url")){

        String imageUrl = getIntent().getStringExtra("image_url");
        String desc= getIntent().getStringExtra("desc");
        setImage(imageUrl,desc);

    }
    else
    {
        String desc= getIntent().getStringExtra("desc");

        setImage(desc);

    }

    }

    private void setImage (String imageurl,String desc)
    {

        TextView textView= findViewById(R.id.recycle_text);
        ImageView imageView=findViewById(R.id.recycle_image);

        Picasso.with(getApplicationContext()).load(imageurl).into(imageView);
        textView.setText(desc);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


    }

    private void setImage (String desc)
    {

        TextView textView= findViewById(R.id.recycle_text);

        textView.setText(desc);


    }


}
