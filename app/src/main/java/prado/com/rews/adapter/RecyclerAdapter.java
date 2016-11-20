package prado.com.rews.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import prado.com.rews.R;
import prado.com.rews.helper.LoadSubmissions;
import prado.com.rews.helper.LoadSubmissionsResult;
import prado.com.rews.model.ImageDownloaded;
import prado.com.rews.model.Noticia;
import prado.com.rews.view.ArticleActivity;

/**
 * Created by Prado on 07/09/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Noticia> array;
    private Activity activity;
    private ImageLoader imageLoader;
    private LoadSubmissions loadSubmissions;
    private int ACTIVITY_RESULT = 0;

    public RecyclerAdapter(List<Noticia> array, Activity activity) {
        this.array = array;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater =
                (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fragment_card, parent, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        ViewHolder viewHolder = new ViewHolder(view);

        DisplayImageOptions displayImageOptions =
                new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration ImgConfig =
                new ImageLoaderConfiguration.Builder(view.getContext()).defaultDisplayImageOptions(displayImageOptions)
                        .build();

        imageLoader = ImageLoader.getInstance();

        if (!imageLoader.isInited()) {
            imageLoader.init(ImgConfig);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (array.get(position).getRank() == position) {
            holder.text.setText(array.get(position).getTitle());
            if (position != 0 && position != array.size() - 1) {
                holder.cardView.setContentPadding(0, 10, 0, 0);
            }
            holder.cardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    Intent intent = new Intent(activity, ArticleActivity.class);
                    intent.putExtra("URL", array.get(position).getUrl());
                    activity.startActivityForResult(intent, ACTIVITY_RESULT);
                }
            });

            holder.share.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Notícia: " + array.get(position).getTitle() + "\n\nLink: " + array.get(position).getUrl());
                    sendIntent.setType("text/plain");
                    activity.startActivity(sendIntent);
                }
            });

            loadSubmissions = (LoadSubmissions) new LoadSubmissions(new LoadSubmissionsResult() {

                @Override
                public void onSuccess(final ImageDownloaded imageDownloaded) {
                    if (imageDownloaded.getId().equals(array.get(position).getId())) {
                        holder.imageView.setImageBitmap(imageDownloaded.getBitmap());
                    }
                }
            }, activity, array.get(position), imageLoader, holder.progressBar).execute();
        }
    }

    @Override
    public void onViewRecycled(final ViewHolder holder) {
        super.onViewRecycled(holder);
        loadSubmissions.cancel(true);
        holder.cardView.setBackgroundColor(Color.WHITE);
        holder.text.setText("");
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView text;
        private CardView cardView;
        private ImageView favoriteImage;
        private ImageView like;
        private ImageView dislike;
        private ImageView share;
        private ProgressBar progressBar;

        ViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.imageCard);
            text = (TextView) view.findViewById(R.id.titleText);
            cardView = (CardView) view.findViewById(R.id.cardView);
            favoriteImage = (ImageView) view.findViewById(R.id.favorite);
            like = (ImageView) view.findViewById(R.id.voteup);
            dislike = (ImageView) view.findViewById(R.id.votedown);
            share = (ImageView) view.findViewById(R.id.share);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_card);
        }
    }
}
