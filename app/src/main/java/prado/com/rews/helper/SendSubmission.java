package prado.com.rews.helper;

import android.os.AsyncTask;

import net.dean.jraw.ApiException;
import net.dean.jraw.RedditClient;
import net.dean.jraw.managers.AccountManager;
import net.dean.jraw.models.Contribution;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.VoteDirection;
import net.dean.jraw.paginators.UserContributionPaginator;

import java.util.HashMap;
import java.util.Map;

import prado.com.rews.model.Noticia;

/**
 * Created by Gabriel on 20/11/2016.
 */

public class SendSubmission extends AsyncTask<Void, Void, Map<String, Listing<Contribution>>> {

    private RedditClient redditClient;
    private Noticia noticia;
    private String type;
    private LoadSubmissionsResult loadSubmissionsResult;

    public SendSubmission(RedditClient redditClient, Noticia noticia, String type) {
        this.redditClient = redditClient;
        this.noticia = noticia;
        this.type = type;
    }

    public SendSubmission(RedditClient redditClient, LoadSubmissionsResult loadSubmissionsResult, String type) {
        this.redditClient = redditClient;
        this.loadSubmissionsResult = loadSubmissionsResult;
        this.type = type;
    }

    @Override
    protected Map<String, Listing<Contribution>> doInBackground(final Void... params) {
        UserContributionPaginator saved;
        Map<String, Listing<Contribution>> map = new HashMap<>();
        if (type.equals("all")) {
            saved = new UserContributionPaginator(redditClient, "saved", redditClient.me().getFullName());
            map.put("saved", saved.next());
            saved = new UserContributionPaginator(redditClient, "liked", redditClient.me().getFullName());
            map.put("upvoted", saved.next());
            saved = new UserContributionPaginator(redditClient, "disliked", redditClient.me().getFullName());
            map.put("downvoted", saved.next());
            return map;
        } else {
            AccountManager accountManager = new AccountManager(redditClient);
            Submission submission = redditClient.getSubmission(noticia.getId());
            if (submission != null) {
                try {
                    switch (type) {
                        case "0":
                            accountManager.unsave(submission);
                            break;
                        case "1":
                        case "-1":
                            accountManager.vote(submission,
                                    type.equals("1") ? VoteDirection.UPVOTE : VoteDirection.DOWNVOTE);
                            break;
                        case "2":
                            accountManager.save(submission);
                            break;
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(final Map<String, Listing<Contribution>> listing) {
        super.onPostExecute(listing);
        if (listing != null) {
            loadSubmissionsResult.onSuccess(listing);
        }
    }
}
