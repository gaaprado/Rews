package prado.com.rews.model;

/**
 * Created by Prado on 08/09/2016.
 */

public class UrlDownloaded {

    private String url;
    private String id;

    public UrlDownloaded(){
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UrlDownloaded{" +
                "url='" + url + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
