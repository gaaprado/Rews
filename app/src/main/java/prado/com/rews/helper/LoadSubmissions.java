package prado.com.rews.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import prado.com.rews.R;
import prado.com.rews.interfaces.AsyncResponseResult;
import prado.com.rews.model.ImageDownloaded;
import prado.com.rews.model.Noticia;

/**
 * Created by Prado on 25/08/2016.
 */

public class LoadSubmissions extends AsyncTask<Void, Void, ImageDownloaded> {

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
    protected ImageDownloaded doInBackground(Void... params) {

        ImageSize targetSize = new ImageSize(300, 300);

        try {
            Bitmap bmp = imageLoader.loadImageSync(noticia.getImgur(), targetSize, null);

            if (bmp != null) {
                return new ImageDownloaded(noticia.getId(), bmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap errorImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.imagenf);

        return new ImageDownloaded(noticia.getId(), errorImage);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(ImageDownloaded imageDownloaded) {
        delegate.processFinish(imageDownloaded);
    }
}