# MusicPlayer

```java
package com.guozhe.android.musicplayer.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by guozhe on 2017. 6. 18..
 */

public class Music {
    private  Set<Item> items = null;
    private static Music instance = null;

    private  Music(){
        items = new HashSet<>();
    }
    public static Music getInstance(){
        if(instance == null)
                instance = new Music();
        return instance;
    }
    public List<Item> getItems(){
        return new ArrayList<>(items);
    }
    public void loader(Context context){
        items.clear();
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String proj[] = {MediaStore.Audio.Media._ID
                        ,MediaStore.Audio.Media.ALBUM_ID
                        ,MediaStore.Audio.Media.TITLE
                        ,MediaStore.Audio.Media.ARTIST};
        Cursor cursor = resolver.query(uri,proj,null,null,null);
        if(cursor != null){
            while (cursor.moveToNext()){
                Item item = new Item();
                item.id =getValue(cursor,proj[0]);
                item.albumId =getValue(cursor,proj[1]);
                item.title =getValue(cursor,proj[2]);
                item.artist =getValue(cursor,proj[3]);
                item.musicUri = makeMusicUri(item.id);
                item.albumart = makeAlbumUri(item.albumId);
                items.add(item);
            }
        }

    }
    private String getValue(Cursor cursor,String name){
        int index = cursor.getColumnIndex(name);
        return cursor.getString(index);
    }
    public class Item{
        public String id;
        public String albumId;
        public String title;
        public String artist;
        public boolean itemClicked =false;

        public Uri musicUri;
        public Uri albumart;
    }
    private Uri makeMusicUri(String musicUri){
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return Uri.withAppendedPath(contentUri,musicUri);
    }
    private Uri makeAlbumUri(String albumId){
        String albumUri = "content://media/external/audio/albumart/";
        return Uri.parse(albumUri+albumId);
    }
}
