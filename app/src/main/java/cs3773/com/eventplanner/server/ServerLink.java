package cs3773.com.eventplanner.server;

/**
 * Created by Rodney on 4/4/2015.
 * <p/>
 * Enumerator that contains all server links.
 */
public enum ServerLink {

    LOGIN("https://rodneyxr.com/ep_login.php");

    String url;

    ServerLink(String url) {
        this.url = url;
    }

}
