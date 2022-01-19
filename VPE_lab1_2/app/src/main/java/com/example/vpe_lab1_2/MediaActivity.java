package com.example.vpe_lab1_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class MediaActivity extends AppCompatActivity {
    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private VideoView videoView;
    private MediaPlayer mediaPlayer;
    private String mediaFileType = "";
    private static final int REQUEST_MEDIA = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_layout);

        playButton = findViewById(R.id.media_play);
        pauseButton = findViewById(R.id.media_pause);
        stopButton = findViewById(R.id.media_stop);
        videoView = findViewById(R.id.videoView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_MEDIA:
                if (mediaFileType == "audio/*") {
                    mediaPlayer = MediaPlayer.create(this, data.getData());
                    mediaPlayer.setOnCompletionListener(mp -> stopPlay());
                }
                else {
                    videoView.setVideoURI(data.getData());
                }
                break;
        }

        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Permissions.onRequestPermissionResult(requestCode, permissions, grantResults, this);
    }

    public void selectMedia(View viewButton) {
        if(!Permissions.permissionGranted && !Permissions.checkPermissions(this)){
            return;
        }

        if (viewButton.getId()==R.id.media_open_audio) {
            mediaFileType = "audio/*";
        }
        else {
            mediaFileType = "video/*";
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(mediaFileType);
        startActivityForResult(intent, REQUEST_MEDIA);
    }

    private void stopPlay(){
        mediaPlayer.stop();
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        try {
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
            playButton.setEnabled(true);
        }
        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void play(View view){
        if (mediaFileType == "audio/*") {
            mediaPlayer.start();
        } else {
            videoView.start();
        }

        playButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
    }

    public void pause(View view){
        if (mediaFileType == "audio/*") {
            mediaPlayer.pause();
        } else {
            videoView.pause();
        }

        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    public void stop(View view){
        if (mediaFileType == "audio/*") {
            stopPlay();
        } else {
            videoView.stopPlayback();
            videoView.resume();
        }

        playButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
    }
}