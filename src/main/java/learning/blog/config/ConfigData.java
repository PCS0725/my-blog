package learning.blog.config;

public class ConfigData {
    //set a default image so that it does not search too much.
    public static String BACKGROUND_URL = "../assets/Background-image.png";
//    public static String style = "color:dodgerblue;
    public static String HOME_QUOTE = "I'd rather choose failing over regret";

    public ConfigData(){

    }

    public static String getBackgroundUrl() {
        return BACKGROUND_URL;
    }

    public static void setBackgroundUrl(String backgroundUrl) {
        BACKGROUND_URL = backgroundUrl;
    }

    public static String getHomeQuote() {
        return HOME_QUOTE;
    }

    public static void setHomeQuote(String homeQuote) {
        HOME_QUOTE = homeQuote;
    }
}
