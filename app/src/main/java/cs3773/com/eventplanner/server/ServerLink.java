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
     * Creates and adds a user in the database given the user's account information
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
    UPDATE_ACCOUNT("https://rodneyxr.com/ep/ep_update_account.php"),

    /**
     * Creates the user's team information in the database given the account_id
     * <p/>
     * $team_name
     * $team_supervisor
     * $event_manager
     * $event_manager_assistant
     * $member1
     * $member2
     * $member3
     * $team_description
     */
    CREATE_TEAM("https://rodneyxr.com/ep/ep_create_team.php"),

    /**
     * Creates the event information in the database
     * <p/>
     * $event_name
     * $date
     * $time
     * $location
     * $description
     * $target_audience
     * $team_list
     */
    CREATE_EVENT("https://rodneyxr.com/ep/ep_create_event.php"),

    /**
     * Gets all account names in the database
     * <p/>
     */
    GET_ACCOUNT_NAMES("https://rodneyxr.com/ep/ep_get_account_names.php"),

    /**
     * Gets all events in the database
     * <p/>
     */
    GET_EVENTS("https://rodneyxr.com/ep/ep_get_events.php"),

    /**
     * Deletes an event given the event_name
     * <p/>
     * $event_name
     */
    DELETE_EVENT("https://rodneyxr.com/ep/ep_delete_event.php"),

    /**
     * Deletes a team given the team_name
     * <p/>
     * $team_name
     */
    DELETE_TEAM("https://rodneyxr.com/ep/ep_delete_team.php");


    String url;

    ServerLink(String url) {
        this.url = url;
    }

}
