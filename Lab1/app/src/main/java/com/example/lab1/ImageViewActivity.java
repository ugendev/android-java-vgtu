package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_view_activity);

        ImageView imageView1 = findViewById(R.id.image_view_img);
        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            String filename = arguments.get("fileName").toString();

            switch (filename) {
                case "img_1.png":
                    imageView1.setImageResource(R.drawable.img_1);
                    break;
                case "img_2.png":
                    imageView1.setImageResource(R.drawable.img_2);
                    break;
                case "img_3.png":
                    imageView1.setImageResource(R.drawable.img_3);
                    break;
                case "img_4.png":
                    imageView1.setImageResource(R.drawable.img_4);
                    break;
                case "img_5.png":
                    imageView1.setImageResource(R.drawable.img_5);
                    break;
                case "img_6.png":
                    imageView1.setImageResource(R.drawable.img_6);
                    break;
            }
        }
    }
}