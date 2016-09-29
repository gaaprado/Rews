package prado.com.rews.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import prado.com.rews.R;

public class FragmentWeb extends Fragment {

    private String URL;
    private View view;

    public FragmentWeb(String URL) {
        this.URL = URL;
    }

    public FragmentWeb(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_web, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        WebView webView = (WebView) view.findViewById(R.id.webView);
        webView.loadUrl(URL);

        return view;
    }

}
