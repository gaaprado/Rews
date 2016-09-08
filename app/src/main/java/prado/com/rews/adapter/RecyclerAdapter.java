package prado.com.rews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;

import java.util.ArrayList;
import java.util.Collections;

import prado.com.rews.R;
import prado.com.rews.controller.FragmentWeb;
import prado.com.rews.interfaces.ItemTouchHelperAdapter;
import prado.com.rews.model.ImageDownloaded;

/**
 * Created by Prado on 07/09/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter{

    private ArrayList<Submission> array;
    private FragmentTransaction ft;
    private ArrayList <ImageDownloaded> images;
    private View view;

    public RecyclerAdapter(Listing <Submission> array, FragmentTransaction ft, ArrayList<ImageDownloaded>images){

        this.array  = new ArrayList<Submission>();
        this.ft     = ft;
        this.images = images;

        for(int i=0; i<array.size(); i++){
            this.array.add(array.get(i));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        view                    = inflater.inflate(R.layout.fragment_list, parent, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        ViewHolder viewHolder   = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.text.setText(this.array.get(position).getTitle());

        if(!images.isEmpty()){
            for(int i = 0; i < images.size(); i++){
                if(array.get(position).getId().equals(images.get(i).getId())){
                    holder.imageView.setImageBitmap(images.get(i).getBitmap());
                    break;
                }
            }
        }

        /*holder.favoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.favoriteImage.setColorFilter(Color.BLUE);
            }
        });*/


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.replace(R.id.content_main, new FragmentWeb(array.get(position).getUrl()));
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.array.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(this.array, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(this.array, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        this.array.remove(position);
        this.images.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView text;
        private CardView cardView;
        //private ImageView favoriteImage;


        public ViewHolder(View view) {
            super(view);

            imageView     = (ImageView) view.findViewById(R.id.imageCard);
            text          = (TextView)  view.findViewById(R.id.titleText);
            cardView      = (CardView)  view.findViewById(R.id.cardView);
            //favoriteImage = (ImageView) view.findViewById(R.id.favorite);
        }
    }

}
