package com.example.vpe_lab1_2;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permissions {
    private static final int REQUEST_PERMISSION_WRITE = 1001;
    public static boolean permissionGranted;

    public static boolean isExternalStorageWriteable(){
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    public static boolean checkPermissions(Activity context) {
        if(!isExternalStorageReadable() || !isExternalStorageWriteable()){
            Toast.makeText(context, "Внешнее хранилище не доступно", Toast.LENGTH_LONG).show();
            return false;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE);
            return false;
        }
        return true;
    }

    public static void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, Activity context) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(context, "Разрешения получены", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Необходимо дать разрешения", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
