package cs3773.com.eventplanner.model;

/**
 * Created by Matthew on 4/9/2015.\
 * .
 */
public enum Role {
    admin("admin"),
    event_manager("event_manager"),
    event_manager_assistant("event_manager_assistant"),
    team_supervisor("team_supervisor"),
    team_member("team_member"),
    employee("employee");

    public String name;

    Role(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}
