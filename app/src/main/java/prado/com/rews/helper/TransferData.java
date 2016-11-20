package prado.com.rews.helper;

import net.dean.jraw.RedditClient;

/**
 * Created by Gabriel on 19/11/2016.
 */

public interface TransferData {

    RedditClient getRedditClient();
    void setRedditClient(RedditClient redditClient);
}
