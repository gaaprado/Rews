package prado.com.rews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.dean.jraw.models.Contribution;
import net.dean.jraw.models.Listing;

import java.util.List;

import prado.com.rews.R;
import prado.com.rews.model.Noticia;
import prado.com.rews.view.ArticleActivity;
import prado.com.rews.view.MainActivity;

/**
 * Created by Gabriel on 16/11/2016.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Contribution> contributionList;
    private Context context;

    public HistoryAdapter(Listing<Contribution> contributionList, Context context) {
        this.contributionList = contributionList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.history_list, parent, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (contributionList != null) {
            try {
                List<Noticia> noticiaList = ((MainActivity) context).getNoticiaList();
                for (final Noticia noticia : noticiaList) {
                    if (noticia.getId().equals(contributionList.get(position).getId())) {
                        holder.title.setText(noticia.getTitle());
                        holder.title.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(final View v) {
                                Intent intent = new Intent(context, ArticleActivity.class);
                                intent.putExtra("URL", noticia.getUrl());
                                context.startActivity(intent);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return contributionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;

        ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.news_image);
            title = (TextView) view.findViewById(R.id.news_title);
        }
    }
}
sc