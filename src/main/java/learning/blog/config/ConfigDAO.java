package learning.blog.config;

//Only to pass and receive parameters between forms
public class ConfigDAO {
    private String url;
    private String quote;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
