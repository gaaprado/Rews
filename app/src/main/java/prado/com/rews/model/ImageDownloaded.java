package prado.com.rews.model;

import android.graphics.Bitmap;

/**
 * Created by Prado on 08/09/2016.
 */

public class ImageDownloaded {

    private String id;
    private Bitmap bitmap;

    public ImageDownloaded(String id, Bitmap bitmap){

        this.id     = id;
        this.bitmap = bitmap;

    }

    public String getId() {
        return id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

}

