package prado.com.rews.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import prado.com.rews.R;
import prado.com.rews.interfaces.AsyncResponseResult;
import prado.com.rews.model.Noticia;

/**
 * Created by Prado on 25/08/2016.
 */

public class LoadSubmissions extends AsyncTask<Void, Void, Bitmap> {

    private AsyncResponseResult delegate = null;
    private Context context;
    private Noticia noticia;
    private ImageLoader imageLoader;

    public LoadSubmissions(AsyncResponseResult delegate, Context context, Noticia noticia, ImageLoader imageLoader) {
        this.delegate = delegate;
        this.context = context;
        this.noticia = noticia;
        this.imageLoader = imageLoader;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        ImageSize targetSize = new ImageSize(300, 300);

        Bitmap bmp = imageLoader.loadImageSync(noticia.getImgUrl(), targetSize, null);

        if (bmp != null) {
            return bmp;
        }

        Bitmap errorImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.imagenf);

        return errorImage;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        delegate.processFinish(bitmap);
    }
}