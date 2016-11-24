package prado.com.rews.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import prado.com.rews.model.Noticia;
import prado.com.rews.view.ArticleActivity;
import prado.com.rews.view.MainActivity;

/**
 * Created by Gabriel on 21/11/2016.
 */

public class OnClickListener implements View.OnClickListener {

    private Context context;
    private String onClickType;
    private int position;

    public OnClickListener(Context context) {
        this.context = context;
    }

    public OnClickListener(Context context, int position) {
        this.context = context;
        this.position = position;
    }

    public void setOnClickType(String onClickType) {
        this.onClickType = onClickType;
    }

    public String getOnClickType() {
        return onClickType;
    }

    @Override
    public void onClick(final View view) {
        switch (getOnClickType()) {
            case "UNFAVORITE":
                removeFavorite();
                break;
            case "START":
                startArticleActivity();
                break;
            case "SEND":
                sendNotice();
                break;
        }
    }

    private void removeFavorite() {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Unfavorite:");
        builder.setMessage("Are you sure you want to unfavorite this?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(context, "Unfavorited", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void startArticleActivity() {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra("URL", ((MainActivity) context).getNoticiaList().get(position).getUrl());
        context.startActivity(intent);
    }

    private void sendNotice() {
        Noticia noticia = ((MainActivity) context).getNoticiaList().get(position);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Notícia: " + noticia.getTitle() + "\n\nLink: " +
                                               noticia.getUrl());
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
}
