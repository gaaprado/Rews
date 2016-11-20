package prado.com.rews.view.fragment;

import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import prado.com.rews.R;
import prado.com.rews.view.MainActivity;

/**
 * Created by Prado on 24/08/2016.
 */

public class FragmentDialog extends DialogFragment {

    private TextView worldNews;
    private TextView brazilNews;
    private TextView usNews;
    private TextView canadaNews;

    public static FragmentDialog newInstance() {

        FragmentDialog frag = new FragmentDialog();
        frag.setStyle(STYLE_NO_TITLE, 0);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        worldNews = (TextView) view.findViewById(R.id.text_view_world);
        brazilNews = (TextView) view.findViewById(R.id.text_view_brazil);
        usNews = (TextView) view.findViewById(R.id.text_view_us);
        canadaNews = (TextView) view.findViewById(R.id.text_view_canada);
        worldNews.setOnClickListener(new OnTextViewClickListener("WORLDNEWS"));
        brazilNews.setOnClickListener(new OnTextViewClickListener("BRAZIL"));
        usNews.setOnClickListener(new OnTextViewClickListener("US"));
        canadaNews.setOnClickListener(new OnTextViewClickListener("CANADA"));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow()
                .setLayout(getResources().getDimensionPixelSize(R.dimen.dialogWid),
                        getResources().getDimensionPixelSize(R.dimen.dialogHei));
    }

    private class OnTextViewClickListener implements View.OnClickListener {

        private String button;

        OnTextViewClickListener(String button) {
            this.button = button;
        }

        @Override
        public void onClick(final View textView) {
            switch (button) {
                case "WORLDNEWS":
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("WorldNews");
                    ((MainActivity) getActivity()).setSubReddit("WorldNews");
                    worldNews.setTypeface(Typeface.DEFAULT_BOLD);
                    brazilNews.setTypeface(Typeface.DEFAULT);
                    usNews.setTypeface(Typeface.DEFAULT);
                    canadaNews.setTypeface(Typeface.DEFAULT);
                    break;
                case "BRAZIL":
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Brazil");
                    ((MainActivity) getActivity()).setSubReddit("Brazil");
                    worldNews.setTypeface(Typeface.DEFAULT);
                    brazilNews.setTypeface(Typeface.DEFAULT_BOLD);
                    usNews.setTypeface(Typeface.DEFAULT);
                    canadaNews.setTypeface(Typeface.DEFAULT);
                    break;
                case "US":
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("United States");
                    ((MainActivity) getActivity()).setSubReddit("United States");
                    worldNews.setTypeface(Typeface.DEFAULT);
                    brazilNews.setTypeface(Typeface.DEFAULT);
                    usNews.setTypeface(Typeface.DEFAULT_BOLD);
                    canadaNews.setTypeface(Typeface.DEFAULT);
                    break;
                case "CANADA":
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Canada");
                    ((MainActivity) getActivity()).setSubReddit("Canada");
                    worldNews.setTypeface(Typeface.DEFAULT);
                    brazilNews.setTypeface(Typeface.DEFAULT);
                    usNews.setTypeface(Typeface.DEFAULT);
                    canadaNews.setTypeface(Typeface.DEFAULT_BOLD);
                    break;
            }
        }
    }
}
