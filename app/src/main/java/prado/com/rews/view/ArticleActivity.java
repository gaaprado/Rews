package prado.com.rews.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import prado.com.rews.R;

public class ArticleActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        webView = (WebView) findViewById(R.id.webView);

        webView.loadUrl(getIntent().getExtras().get("url").toString());
    }
}
