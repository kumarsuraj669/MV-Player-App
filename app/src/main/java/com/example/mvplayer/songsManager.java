package com.example.mvplayer;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class songsManager {
    final String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
    private String[] pattern;

    public songsManager(String[] pattern){
        this.pattern = pattern;
    }

    public ArrayList<HashMap<String, String>> getPlayList(){
        if(MEDIA_PATH != null) {
            File home = new File(MEDIA_PATH);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0 ){
                for (File file: listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        return songsList;
    }

    private void scanDirectory(File dir){
        if (dir != null) {
            File[] listFiles = dir.listFiles();
            if(listFiles != null && listFiles.length > 0) {
                for (File file: listFiles){
                    if (file.isDirectory()){
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
    }

    private void addSongToList(File song){
        for (int i=0; i< pattern.length; i++){
            if (song.getName().endsWith(pattern[i])){
                HashMap<String, String> songMap = new HashMap<>();
                songMap.put("songTitle", song.getName());
                songMap.put("songPath", song.getPath());
                songsList.add(songMap);
            }
        }
    }
}
