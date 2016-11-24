package prado.com.rews.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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

import net.dean.jraw.RedditClient;
import net.dean.jraw.models.Contribution;
import net.dean.jraw.models.Listing;

import java.util.List;
import java.util.Map;

import prado.com.rews.R;
import prado.com.rews.helper.LoadSubmissions;
import prado.com.rews.helper.LoadSubmissionsResult;
import prado.com.rews.helper.OnClickListener;
import prado.com.rews.helper.SendSubmission;
import prado.com.rews.model.ImageDownloaded;
import prado.com.rews.model.Noticia;
import prado.com.rews.view.MainActivity;

/**
 * Created by Prado on 07/09/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Noticia> array;
    private Activity activity;
    private ImageLoader imageLoader;
    private LoadSubmissions loadSubmissions;
    private ViewHolder holder;

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
        this.holder = holder;
        if (array.get(position).getRank() == position) {
            holder.text.setText(array.get(position).getTitle());

            try {
                final List<Contribution> upVotes = ((MainActivity) activity).getRedditVotes().get("upvoted");
                final List<Contribution> downvotes = ((MainActivity) activity).getRedditVotes().get("downvoted");
                final List<Contribution> favorites = ((MainActivity) activity).getRedditVotes().get("saved");

                boolean changed = false;
                for (Contribution contribution : upVotes) {
                    if (array.get(position).getId().equals(contribution.getId())) {
                        array.get(position).setUpvoted(true);
                        changed = true;
                    }
                }

                for (Contribution contribution : downvotes) {
                    if (array.get(position).getId().equals(contribution.getId())) {
                        array.get(position).setDownvoted(true);
                        changed = true;
                    }
                }

                for (Contribution contribution : favorites) {
                    if (array.get(position).getId().equals(contribution.getId())) {
                        array.get(position).setFavorited(true);
                        changed = true;
                    }
                }

                if (changed) {
                    notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (array.get(position).isUpvoted()) {
                holder.like.setImageResource(R.drawable.ic_arrowupliked);
            } else if (array.get(position).isDownvoted()) {
                holder.dislike.setImageResource(R.drawable.ic_arrowdowndisliked);
            } else if (array.get(position).isFavorited()) {
                holder.favoriteImage.setImageResource(R.drawable.ic_star2);
            }

            if (position != 0 && position != array.size() - 1) {
                holder.cardView.setContentPadding(0, 10, 0, 0);
            }

            holder.cardView.setOnClickListener(new OnClickListener(activity, position) {{
                setOnClickType("START");
            }});
            holder.share.setOnClickListener(new OnClickListener(activity, position) {{
                setOnClickType("SEND");
            }});

            loadSubmissions = (LoadSubmissions) new LoadSubmissions(new LoadSubmissionsResult() {

                @Override
                public void onSuccess(final ImageDownloaded imageDownloaded) {
                    if (imageDownloaded.getId().equals(array.get(position).getId())) {
                        holder.imageView.setImageBitmap(imageDownloaded.getBitmap());
                        notifyItemChanged(position, holder.imageView);
                    }
                }

                @Override
                public void onSuccess(final Map<String, Listing<Contribution>> listing) {

                }
            }, activity, array.get(position), imageLoader, holder.progressBar).execute();
        }

        final RedditClient redditClient = ((MainActivity) activity).getRedditClient();

        holder.like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (redditClient != null) {
                    SendSubmission sendSubmission;
                    if (!array.get(position).isDownvoted()) {
                        if (!array.get(position).isUpvoted()) {
                            sendSubmission = new SendSubmission(redditClient, array.get(position), "1");
                            holder.like.setImageResource(R.drawable.ic_arrowupliked);
                            array.get(position).setUpvoted(true);
                            notifyItemChanged(position, holder.like);
                        } else {
                            sendSubmission = new SendSubmission(redditClient, array.get(position), "0");
                            holder.like.setImageResource(R.drawable.ic_arrowup);
                            array.get(position).setUpvoted(false);
                            notifyItemChanged(position, holder.like);
                        }
                        sendSubmission.execute();
                    } else {
                        showDialogWithError("downvote", position);
                    }
                } else {
                    removeVoteType("upvote");
                }
            }
        });

        holder.dislike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (redditClient != null) {
                    if (!array.get(position).isUpvoted()) {
                        SendSubmission sendSubmission;
                        if (!array.get(position).isDownvoted()) {
                            sendSubmission = new SendSubmission(redditClient, array.get(position), "-1");
                            holder.dislike.setImageResource(R.drawable.ic_arrowdowndisliked);
                            array.get(position).setDownvoted(true);
                            notifyItemChanged(position, holder.dislike);
                        } else {
                            sendSubmission = new SendSubmission(redditClient, array.get(position), "0");
                            holder.dislike.setImageResource(R.drawable.ic_arrowdown);
                            array.get(position).setDownvoted(false);
                            notifyItemChanged(position, holder.dislike);
                        }
                        sendSubmission.execute();
                    } else {
                        showDialogWithError("upvote", position);
                    }
                } else {
                    removeVoteType("downvote");
                }
            }
        });

        holder.favoriteImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (redditClient != null) {
                    SendSubmission sendSubmission;
                    if (!array.get(position).isFavorited()) {
                        sendSubmission = new SendSubmission(redditClient, array.get(position), "2");
                        holder.favoriteImage.setImageResource(R.drawable.ic_star2);
                        array.get(position).setFavorited(true);
                        notifyItemChanged(position, holder.favoriteImage);
                    } else {
                        sendSubmission = new SendSubmission(redditClient, array.get(position), "0");
                        holder.favoriteImage.setImageResource(R.drawable.ic_star);
                        array.get(position).setFavorited(false);
                        notifyItemChanged(position, holder.favoriteImage);
                    }
                    sendSubmission.execute();
                } else {
                    removeVoteType("favorite");
                }
            }
        });
    }

    @Override
    public void onViewRecycled(final ViewHolder holder) {
        super.onViewRecycled(holder);
        loadSubmissions.cancel(true);
        holder.text.setText("");
        holder.like.setImageResource(R.drawable.ic_arrowup);
        holder.dislike.setImageResource(R.drawable.ic_arrowdown);
        holder.favoriteImage.setImageResource(R.drawable.ic_star);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    private void removeVoteType(String type) {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Log in to " + type + " this post.");
        builder.setNegativeButton("No, thanks", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        builder.setPositiveButton("Log In", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                ViewPager viewPager = (ViewPager) activity.findViewById(R.id.viewPager);
                viewPager.setCurrentItem(1, true);
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDialogWithError(final String type, final int position) {
        final AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("You have to remove your " + type + " first.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
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
