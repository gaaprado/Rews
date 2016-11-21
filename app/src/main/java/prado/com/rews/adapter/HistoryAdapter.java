package prado.com.rews.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.dean.jraw.models.Contribution;
import net.dean.jraw.models.Listing;

import prado.com.rews.R;

/**
 * Created by Gabriel on 16/11/2016.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Listing<Contribution> noticiaList;
    private Context context;

    public HistoryAdapter(Listing<Contribution> noticiaList, Context context) {
        this.noticiaList = noticiaList;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(noticiaList.get(position).getFullName());

        /*
        holder.title.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("URL", noticiaList.get(position);
                context.startActivity(intent);
            }
        });*/

        holder.image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                //FIXME Mudar para inglês o texto do alertdialog.
                AlertDialog alerta;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Desfavoritar:");
                builder.setMessage("Você tem certeza que deseja desfavoritar essa notícia?");
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //TODO no onclick sucesso remove o item do array
                        Toast.makeText(context, "Desfavoritado", Toast.LENGTH_SHORT).show();
                    }
                });
                alerta = builder.create();
                alerta.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticiaList.size();
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
