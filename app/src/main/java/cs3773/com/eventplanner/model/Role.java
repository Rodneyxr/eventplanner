package cs3773.com.eventplanner.model;

/**
 * Created by Matthew on 4/9/2015.\
 * .
 */
public enum Role {
    admin("admin"),
    user("user"),
    manager("manager");

    public String name;

    Role(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    }
