package com.example.colorfulimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.SystemClock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class SaveFile {
    public static File saveFile(Activity myAcivity, Bitmap bitmap) throws IOException {
        String externalStorageState = Environment.getExternalStorageState();
        File file = null;
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)){
            File picturesDirectory = myAcivity.getExternalFilesDir("ColorAppPictures");
            Date currentDate = new Date();
            long elapsedTime = SystemClock.elapsedRealtime();
            String uniquImageName = "/" + currentDate + "_" + elapsedTime + ".png";

            file = new File(picturesDirectory + uniquImageName);
            long remainingSpace = picturesDirectory.getFreeSpace();
            long requiredSpace = bitmap.getByteCount();
            if (requiredSpace * 1.8 < remainingSpace){
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    boolean isImageSaveSuccesfully = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    if (isImageSaveSuccesfully){
                        return file;
                    } else {
                        throw new IOException("The Image is not save successfully");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                throw new IOException("There is no enough");
            }
        }else {
            throw new IOException("This device does not have an external storage");
        }
        return file;
    }
}
