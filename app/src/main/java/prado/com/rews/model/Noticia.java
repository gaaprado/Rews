package prado.com.rews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Noticia {

    @SerializedName("reddit_link")
    @Expose
    private String redditLink;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("rank")
    @Expose
    private int rank;
    @SerializedName("imgur")
    @Expose
    private String imgur;
    @SerializedName("article")
    @Expose
    private String article;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * @return The redditLink
     */
    public String getRedditLink() {
        return redditLink;
    }

    /**
     * @param redditLink
     *         The reddit_link
     */
    public void setRedditLink(String redditLink) {
        this.redditLink = redditLink;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *         The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *         The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * @param rank
     *         The rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * @return The imgur
     */
    public String getImgur() {
        return imgur;
    }

    /**
     * @param imgur
     *         The imgur
     */
    public void setImgur(String imgur) {
        this.imgur = imgur;
    }

    /**
     * @return The article
     */
    public String getArticle() {
        return article;
    }

    /**
     * @param article
     *         The article
     */
    public void setArticle(String article) {
        this.article = article;
    }

    /**
     * @return The imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * @param imgUrl
     *         The img_url
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *         The id
     */
    public void setId(String id) {
        this.id = id;
    }

}