package prado.com.rews.helper;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.Sorting;
import net.dean.jraw.paginators.SubredditPaginator;
import net.dean.jraw.paginators.TimePeriod;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import prado.com.rews.R;
import prado.com.rews.interfaces.AsyncResponseResult;


/**
 * Created by Prado on 25/08/2016.
 */

    public class LoadSubmissions extends AsyncTask<Void, Void, Listing<Submission>> {

    private UserAgent user;
    private RedditClient redditClient;
    private Credentials CREDENTIALS;
    private Listing<Submission> myData;
    public AsyncResponseResult delegate = null;
    private String USERNAME = "gaaprado";
    private String PASSWORD = "gameteoroa1";
    private String CLIENT_ID = "9CmpIrj9-g_7kQ";
    private String SECRET = "5hO3cFwOLXRng7j4q_BNgvTYA94";
    private ProgressBar progressBar;
    private List<String> urls;


    public LoadSubmissions(AsyncResponseResult delegate, View view){
        this.delegate = delegate;
        this.user = UserAgent.of("Guest");
        this.redditClient = new RedditClient(user);
        this.CREDENTIALS = Credentials.script(USERNAME, PASSWORD, CLIENT_ID, SECRET);
        this.progressBar = (ProgressBar) view.findViewById(R.id.ProgressBar);
    }

    @Override
    protected Listing<Submission> doInBackground(Void... params) {
        OAuthData authData = null;
        try {
            authData = redditClient.getOAuthHelper().easyAuth(CREDENTIALS);
            redditClient.authenticate(authData);

        } catch (OAuthException e) {
            e.printStackTrace();
        }

        SubredditPaginator subreddit = new SubredditPaginator(redditClient);

        subreddit.setSubreddit("worldnews");
        subreddit.setLimit(8);
        subreddit.setTimePeriod(TimePeriod.DAY);
        subreddit.setSorting(Sorting.HOT);

        myData = subreddit.next();

        urls = new ArrayList<>();

        /*for(int i=0; i<myData.size(); i++) {
            Document doc = null;
            try {
                String url = "http://newspaper-demo.herokuapp.com/articles/show?url_to_clean=" + myData.get(i).getUrl();
                doc = Jsoup.connect(url).get();
                Elements img = doc.getElementsByTag("img");
                for (Element el : img) {
                    String src = el.absUrl("src");
                    urls.add(src);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */
        return myData;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Listing<Submission> submissions) {
        delegate.processFinish(submissions, urls);
        progressBar.setVisibility(View.GONE);
    }
}