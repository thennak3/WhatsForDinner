package com.murdoch.ict376.whatsfordinner.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ImageConverter {

    public static byte[] DecodeBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,blob);
        return blob.toByteArray();
    }

    public static Bitmap EncodeBitmap(byte[] blob)
    {
        if(blob == null)
            return null;
        return BitmapFactory.decodeByteArray(blob,0,blob.length);
    }
}
