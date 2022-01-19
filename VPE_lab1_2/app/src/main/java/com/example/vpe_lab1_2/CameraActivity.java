package com.example.vpe_lab1_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_IMAGE = 2;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Permissions.onRequestPermissionResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageView = findViewById(R.id.camera_img_view);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CAMERA:
                Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(thumbnailBitmap);
                break;
            case REQUEST_IMAGE:
                imageView.setImageURI(data.getData());
                break;
        }
    }

    public void onClickViewImage(View view) {
        if(!Permissions.permissionGranted){
            Permissions.checkPermissions(this);
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public void openCamera(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }
}