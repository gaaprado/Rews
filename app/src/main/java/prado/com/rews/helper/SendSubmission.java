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

import prado.com.rews.model.Noticia;

/**
 * Created by Gabriel on 20/11/2016.
 */

public class SendSubmission extends AsyncTask<Void, Void, Listing<Contribution>> {

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
    protected Listing<Contribution> doInBackground(final Void... params) {

        if (type.equals("saved")) {
            UserContributionPaginator saved =
                    new UserContributionPaginator(redditClient, type, redditClient.me().getFullName());
            return saved.next();
        } else {
            AccountManager accountManager = new AccountManager(redditClient);
            Submission submission = redditClient.getSubmission(noticia.getId());
            if (submission != null) {
                try {
                    if (type.equals("1") || type.equals("-1")) {
                        accountManager.vote(submission,
                                type.equals("1") ? VoteDirection.UPVOTE : VoteDirection.DOWNVOTE);
                    } else {
                        accountManager.save(submission);
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(final Listing<Contribution> listing) {
        super.onPostExecute(listing);
        if (listing != null) {
            loadSubmissionsResult.onSuccess(listing);
        }
    }
}
