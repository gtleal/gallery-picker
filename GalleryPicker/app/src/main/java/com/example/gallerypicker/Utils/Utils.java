package com.example.gallerypicker.Utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Utils {

    public static ArrayList<String> getImagesFromDevice(Context context) {

        ArrayList<String> paths = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        try {

            Cursor cursor = context.getContentResolver().
                    query(uri, projection, null, null, null);

            int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

            while (cursor.moveToNext()) {
                paths.add(cursor.getString(column_index_data));
            }
        }
        catch (Exception e){
            Log.e("getImagesFromDevice()", e.getMessage());
        }

        Collections.reverse(paths);
        return paths;
    }
}
