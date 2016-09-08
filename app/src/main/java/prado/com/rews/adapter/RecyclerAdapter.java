package prado.com.rews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import prado.com.rews.R;
import prado.com.rews.controller.FragmentWeb;
import prado.com.rews.interfaces.ItemTouchHelperAdapter;

/**
 * Created by Prado on 07/09/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter{

    private ArrayList<Submission> array;
    private FragmentTransaction ft;
    private Context context;
    private List<String> urls;

    public RecyclerAdapter(Listing <Submission> array, FragmentTransaction ft, Context context, List<String>urls){

        this.array = new ArrayList<Submission>();
        this.ft = ft;
        this.context = context;
        this.urls = urls;

        for(int i=0; i<array.size(); i++){
            this.array.add(array.get(i));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_list, parent, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.text.setText(this.array.get(position).getTitle());

        switch(position){
            case 0:
                holder.imageView.setImageResource(R.drawable.cannae);
                break;
            case 1:
                holder.imageView.setImageResource(R.drawable.duterte);
                break;
            case 2:
                holder.imageView.setImageResource(R.drawable.burka);
                break;
            case 3:
                holder.imageView.setImageResource(R.drawable.fund);
                break;
            default:
                holder.imageView.setImageResource(R.drawable.imagenf);
                break;
        }

        /*final ImageLoader img = ImageLoader.getInstance();
        img.init(ImageLoaderConfiguration.createDefault(context));
        ImageSize targetSize = new ImageSize(300, 300);
        img.loadImage(urls.get(position), targetSize, null, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                try{
                    img.displayImage(imageUri, holder.imageView);
                }catch(Exception e){

                }
            }
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.imageView.setImageResource(R.drawable.imagenf);
            }
        });
        */

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
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView text;
        private CardView cardView;

        public ViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.imageCard);
            text      = (TextView)  view.findViewById(R.id.titleText);
            cardView  = (CardView)  view.findViewById(R.id.cardView);
        }
    }

}
