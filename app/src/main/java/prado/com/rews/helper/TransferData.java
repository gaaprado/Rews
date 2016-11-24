package prado.com.rews.helper;

import net.dean.jraw.RedditClient;

import java.util.List;

import prado.com.rews.model.Noticia;

/**
 * Created by Gabriel on 19/11/2016.
 */

public interface TransferData {

    RedditClient getRedditClient();

    void setRedditClient(RedditClient redditClient);

    List<Noticia> getNoticiaList();

    void setNoticiaList(List<Noticia> list);
}
