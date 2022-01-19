package com.example.vpe_lab1_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TextFileActivity extends AppCompatActivity {
    private static final String FILE_NAME = "content.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_file_layout);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Permissions.onRequestPermissionResult(requestCode, permissions, grantResults, this);
    }

    private File getExternalPath() {
        return(new File(Environment.getExternalStorageDirectory(), FILE_NAME));
    }

    public void saveText(View view){
        if(!Permissions.permissionGranted){
            if (!Permissions.checkPermissions(this)) {
                return;
            }
        }

        FileOutputStream fos = null;

        try {
            EditText textBox = findViewById(R.id.text_file_input);
            String text = textBox.getText().toString();

            fos = new FileOutputStream(getExternalPath());
            fos.write(text.getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openFile(View view){
        if(!Permissions.permissionGranted) {
            if (!Permissions.checkPermissions(this)) {
                return;
            }
        }

        FileInputStream fin = null;
        TextView textView = findViewById(R.id.text_file_output);
        File file = getExternalPath();

        if(!file.exists()) return;
        try {
            fin = new FileInputStream(file);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            textView.setText(text);
        }
        catch(IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}