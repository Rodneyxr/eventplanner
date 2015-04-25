package cs3773.com.eventplanner.model;

import java.io.Serializable;

/**
 * Created by Matthew on 4/25/2015.
 */
public class Team implements Serializable {
    // Team Name
    // Team Supervisor
    // Event Manager
    // Event Manager assistant
    // General Member
    // Description

    private String teamName;
    private String teamSupervisor;
    private String eventManager;
    private String eventManagerAssistant;
    private String generalMember;
    private String description;

    public Team() {

    }

    public Team(String teamName,String teamSupervisor, String eventManager, String eventManagerAssistant, String generalMember, String description) {
        this.teamName = teamName;
        this.teamSupervisor = teamSupervisor;
        this.eventManager = eventManager;
        this.eventManagerAssistant = eventManagerAssistant;
        this.generalMember = generalMember;
        this.description = description;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getEventManager() {
        return eventManager;
    }

    public void setEventManager(String eventManager) {
        this.eventManager = eventManager;
    }

    public String getTeamSupervisor() {
        return teamSupervisor;
    }

    public void setTeamSupervisor(String teamSupervisor) {
        this.teamSupervisor = teamSupervisor;
    }

    public String getEventManagerAssistant() {
        return eventManagerAssistant;
    }

    public void setEventManagerAssistant(String eventManagerAssistant) {
        this.eventManagerAssistant = eventManagerAssistant;
    }

    public String getGeneralMember() {
        return generalMember;
    }

    public void setGeneralMember(String generalMember) {
        this.generalMember = generalMember;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
