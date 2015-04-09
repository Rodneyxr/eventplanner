package cs3773.com.eventplanner.server;

/**
 * Created by Rodney on 4/4/2015.
 * <p/>
 * Enumerator that contains all server links.
 */
public enum ServerLink {

    GET_ACCOUNT("https://rodneyxr.com/ep_get_account.php"),
    CREATE_ACCOUNT("https://rodneyxr.com/ep_create_account.php");

    String url;

    ServerLink(String url) {
        this.url = url;
    }

}
