package com.example.ramakantkushwaha.advancedcustomgallery;

/**
 * Created by ramakant.kushwaha on 10/28/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageUtils {

    public static HashMap<String,ArrayList<String>> getImagesFromExternal(Context context){
        HashMap<String,ArrayList<String>> folders = new HashMap<>();

        String pictureCols[] = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA ,MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, pictureCols,
                null, null,  MediaStore.Images.Media._ID+" DESC");

        cursor.moveToFirst();

        ArrayList<String> uriList = null;
        try {
            for (int index = 0; index < cursor.getCount(); index++) {

                String id = cursor.getString(cursor.getColumnIndex(pictureCols[0]));
                String imageUri = cursor.getString(cursor.getColumnIndex(pictureCols[1]));
                String bucket = cursor.getString(cursor.getColumnIndex(pictureCols[2]));

                if(folders.containsKey(bucket)){
                    folders.get(bucket).add(imageUri);
                }else{
                    uriList = new ArrayList<>();
                    uriList.add(imageUri);
                    folders.put(bucket, uriList);
                }
                cursor.moveToPosition(index+1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        System.out.print(folders.size());
        return folders;
    }
    public static DisplayImageOptions universalImageConfiguration(final int width,final int height) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.placeholder)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .postProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bmp) {
                        return Bitmap.createScaledBitmap(bmp,width , height, false);
                    }
                })
                .build();
        return options;
    }

}