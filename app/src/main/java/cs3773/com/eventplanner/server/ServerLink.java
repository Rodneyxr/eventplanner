package cs3773.com.eventplanner.server;

/**
 * Created by Rodney on 4/4/2015.
 * <p/>
 * Enumerator that contains all server links.
 */
public enum ServerLink {

    /**
     * Retrieves a user's account information from the database given the user's credentials
     * <p/>
     * $username, $password
     */
    GET_ACCOUNT("https://rodneyxr.com/ep/ep_get_account.php"),

    /**
     * Create's and adds a user in the database given the user's account information
     * <p/>
     * $username
     * $password
     * $account_id
     * $phone_number
     * $email
     * $full_name
     * $role
     */
    CREATE_ACCOUNT("https://rodneyxr.com/ep/ep_create_account.php"),

    /**
     * Updates the user's account information in the database given the account_id
     * <p/>
     * $account_id
     * $phone_number
     * $email
     * $full_name
     */
    UPDATE_ACCOUNT("https://rodneyxr.com/ep/ep_update_account.php");

    String url;

    ServerLink(String url) {
        this.url = url;
    }

}
