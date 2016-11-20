package prado.com.rews.helper;

import android.os.AsyncTask;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;

/**
 * Created by Prado on 25/08/2016.
 */

public class RedditAccount extends AsyncTask<Void, Void, RedditClient> {

    private RedditAccountResult delegate;
    private String username;
    private String password;

    public RedditAccount(RedditAccountResult delegate, String username, String password) {
        this.delegate = delegate;
        this.username = username;
        this.password = password;
    }

    @Override
    protected RedditClient doInBackground(Void... params) {
        UserAgent myUserAgent = UserAgent.of("app", "prado.com.rews", "v0.1", "RewsApp");
        final RedditClient redditClient = new RedditClient(myUserAgent);
        final Credentials credentials =
                Credentials.script(username, password, "9CmpIrj9-g_7kQ", "5hO3cFwOLXRng7j4q_BNgvTYA94");
        try {
            OAuthData oauth = redditClient.getOAuthHelper().easyAuth(credentials);
            redditClient.authenticate(oauth);
            return redditClient;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(RedditClient redditClient) {
        if (redditClient != null) {
            delegate.onSuccess(redditClient);
        } else {
            delegate.onFailed();
        }
    }
}
