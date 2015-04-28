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
    private ArrayList<Team> teamList = new ArrayList<Team>();

    public Event() {

    }

    public Event(String eventName, Date date, String time, String location, String description, String targetAudience, ArrayList<Team> team) {
        this.eventName = eventName;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
        this.teamList = team;
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

    public void addTeam(Team team) {
        teamList.add(team);
    }

    public void removeTeam(Team team) {
        teamList.remove(team);
    }

    public ArrayList<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(ArrayList<Team> teamList) {
        this.teamList = teamList;
    }

    @Override
    public String toString() {
        return getEventName();
    }
}

