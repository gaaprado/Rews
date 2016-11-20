package prado.com.rews.helper;

import net.dean.jraw.RedditClient;

/**
 * Created by Gabriel on 19/11/2016.
 */

public interface RedditAccountResult {

    void onSuccess(RedditClient redditClient);

    void onFailed();
}
