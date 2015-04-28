package cs3773.com.eventplanner.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Matthew on 4/25/2015.
 */
public class Event {

    private String eventName;
    private Date date;
    private String time;
    private String location;
    private String description;
    private String targetAudience;
    private ArrayList<Account> accountList = new ArrayList<Account>();

    public Event() {
    }

    public Event(String eventName, Date date, String time, String location, String description, String targetAudience, ArrayList<Account> team) {
        this.eventName = eventName;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
        this.accountList = team;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public void addTeam(Account account) {
        accountList.add(account);
    }

    public void removeTeam(Account account) {
        accountList.remove(account);
    }

    public ArrayList<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(ArrayList<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public String toString() {
        return getEventName();
    }
}

