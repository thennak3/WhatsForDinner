package com.murdoch.ict376.whatsfordinner.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;


import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.murdoch.ict376.whatsfordinner.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageHelper {

    Activity activity;
    private ArrayList<Image> images = new ArrayList<>();
    public ImageHelper(Activity activity)
    {
        this.activity = activity;
    }


    //The following adapted from here
    // https://stackoverflow.com/questions/3331527/resize-a-large-bitmap-file-to-scaled-output-file-on-android
    public Bitmap LoadImage(Image image)
    {
        Uri uri = Uri.fromFile(new File(image.getPath()));

        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000;
            in = activity.getContentResolver().openInputStream(uri);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in,null,options);
            in.close();

            int scale = 1;
            while((options.outWidth * options.outHeight) * (1/Math.pow(scale,2)) > IMAGE_MAX_SIZE) {
                scale++;
            }

            Bitmap resultBitmap = null;

            in = activity.getContentResolver().openInputStream(uri);
            if(scale > 1)
            {
                options = new BitmapFactory.Options();
                options.inSampleSize = scale;
                resultBitmap = BitmapFactory.decodeStream(in, null,options);

                int height = resultBitmap.getHeight();
                int width = resultBitmap.getWidth();

                double y = Math.sqrt(IMAGE_MAX_SIZE / (((double)width) / height));
                double x = (y/height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(resultBitmap, (int)x, (int) y,true);
                resultBitmap.recycle();
                resultBitmap = scaledBitmap;
            }
            else
                resultBitmap = BitmapFactory.decodeStream(in);
            in.close();

            return resultBitmap;

        } catch(IOException io)
        {
            return null;
        }

    }

    public ArrayList<Image> GetImages()
    {
        return images;
    }

    public int GetCount()
    {
        return images.size();
    }

    public void SetImages(ArrayList<Image> images)
    {
        this.images = images;
    }

}
