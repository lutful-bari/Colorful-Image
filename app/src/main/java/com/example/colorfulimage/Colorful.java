package com.example.colorfulimage;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Colorful {
    private Bitmap bitmap;
    private float redColorValue;
    private float greenColorValue;
    private float blueColorValue;

    public Colorful(Bitmap bitmap, float redColorValue, float greenColorValue, float blueColorValue) {
        this.bitmap = bitmap;
        setRedColorValue(redColorValue);
        setGreenColorValue(greenColorValue);
        setBlueColorValue(blueColorValue);
    }

    public void setRedColorValue(float redColorValue) {
        if (redColorValue >= 0 && redColorValue <= 1){
            this.redColorValue = redColorValue;
        }
    }

    public void setGreenColorValue(float greenColorValue) {
        if (greenColorValue >= 0 && greenColorValue <= 1){
            this.greenColorValue = greenColorValue;
        }
    }

    public void setBlueColorValue(float blueColorValue) {
        if (blueColorValue >= 0 && blueColorValue <= 1){
            this.blueColorValue = blueColorValue;
        }
    }

    public float getRedColorValue() {
        return redColorValue;
    }

    public float getGreenColorValue() {
        return greenColorValue;
    }

    public float getBlueColorValue() {
        return blueColorValue;
    }

    public Bitmap ReturnColorizeTheBitmap(){
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        Bitmap.Config bitmapConfiq = bitmap.getConfig();
        Bitmap myBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, bitmapConfiq);

        for (int row = 0; row < bitmapWidth; row++){
            for (int colum = 0; colum < bitmapHeight; colum++){
                int pixelColor = bitmap.getPixel(row, colum);

                pixelColor = Color.argb(Color.alpha(pixelColor),
                        (int)redColorValue * Color.red(pixelColor),
                        (int) greenColorValue * Color.green(pixelColor),
                        (int) blueColorValue * Color.blue(pixelColor));
                myBitmap.setPixel(bitmapWidth, bitmapHeight, pixelColor);
            }
        }
        return myBitmap;
    }

}
