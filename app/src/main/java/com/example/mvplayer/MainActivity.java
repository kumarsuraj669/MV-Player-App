package com.example.mvplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    RecyclerView listView;
    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int permissionCode = 100;
    boolean PERMISSION_FLAG=false;
    String[] musicExtension = {".mp3", ".wav", ".aac", ".flac", ".ogg", ".wma"};
    ArrayList<HashMap<String, String>> playList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PERMISSION_FLAG = checkPermission();
        if (!PERMISSION_FLAG){
            ActivityCompat.requestPermissions(this, permission, permissionCode);
            this.recreate();    // this method reloads the activity
        }
        else {
            getSongs(musicExtension);
            listView = findViewById(R.id.recycler);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.addItemDecoration(new DividerItemDecoration(this, Configuration.ORIENTATION_PORTRAIT));
            musicItemAdapter adapter = new musicItemAdapter(this, playList);
            listView.setAdapter(adapter);
        }
    }


    public boolean checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.permissionCode ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                PERMISSION_FLAG = true;
            }
            else {
                PERMISSION_FLAG = false;
            }
        }
    }

    public void getSongs(String[] extensions){
        songsManager manager = new songsManager(extensions);
        playList = manager.getPlayList();
    }
}