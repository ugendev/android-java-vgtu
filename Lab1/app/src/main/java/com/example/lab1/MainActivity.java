package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String strText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToListLayout(View view) {
        setContentView(R.layout.list_layout);

        ListView listView = findViewById(R.id.list_dropdown);

        final String[] fileNames = new String[] { "img_1.png", "img_2.png", "img_3.png", "img_4.png", "img_5.png", "img_6.png" };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, itemClicked, position, id) -> {
            Toast toast = Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(), Toast.LENGTH_SHORT);

            toast.setGravity(2,10,400);
            toast.show();

            TextView textView = (TextView) itemClicked;
            strText = textView.getText().toString();
            ImageView imageView = findViewById(R.id.list_img);

            switch (strText) {
                case "img_1.png":
                    imageView.setImageResource(R.drawable.img_1);
                    break;
                case "img_2.png":
                    imageView.setImageResource(R.drawable.img_2);
                    break;
                case "img_3.png":
                    imageView.setImageResource(R.drawable.img_3);
                    break;
                case "img_4.png":
                    imageView.setImageResource(R.drawable.img_4);
                    break;
                case "img_5.png":
                    imageView.setImageResource(R.drawable.img_5);
                    break;
                case "img_6.png":
                    imageView.setImageResource(R.drawable.img_6);
                    break;
            }
        });
    }

    public void onImageClick(View view) {
        Intent intent = new Intent(this, ImageViewActivity.class);
        intent.putExtra("fileName", strText);
        startActivity(intent);
    }

    public void goToTableLayout(View view) {
        setContentView(R.layout.table_layout);
    }

    public void goToMain(View view) {
        setContentView(R.layout.activity_main);
    }
}