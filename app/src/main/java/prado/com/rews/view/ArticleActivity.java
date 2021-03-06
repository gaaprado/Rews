package prado.com.rews.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import prado.com.rews.R;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        getSupportActionBar().hide();
        final WebView webView = (WebView) findViewById(R.id.article_webview);
        webView.setWebViewClient(new WebViewClient());

        if (getIntent().getExtras().getString("URL") != null) {
            webView.loadUrl(getIntent().getExtras().getString("URL"));
        } else {
            webView.loadUrl("https://www.reddit.com/login");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        final int ACTIVITY_RESULT = 0;
        setResult(ACTIVITY_RESULT);
        finish();
    }
}
