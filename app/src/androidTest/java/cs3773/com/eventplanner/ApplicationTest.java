package cs3773.com.eventplanner;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.ArrayList;

import cs3773.com.eventplanner.controller.Tools;
import cs3773.com.eventplanner.model.Event;
import cs3773.com.eventplanner.model.Team;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        Test_Serialization();
    }

    public void Test_Serialization() {
        Event event = new Event();

        // create team 1
        Team testTeam1 = new Team();
        testTeam1.setTeamName("teamname1");
        testTeam1.setDescription("description1");
        testTeam1.setEventManager("eventmanager1");
        testTeam1.setEventManagerAssistant("assistant1");
        testTeam1.setGeneralMember("generalmember1");
        testTeam1.setTeamSupervisor("supervisor1");

        // create team 2
        Team testTeam2 = new Team();
        testTeam2.setTeamName("teamname2");
        testTeam2.setDescription("description2");
        testTeam2.setEventManager("eventmanager2");
        testTeam2.setEventManagerAssistant("assistant2");
        testTeam2.setGeneralMember("generalmember2");
        testTeam2.setTeamSupervisor("supervisor2");

        event.addTeam(testTeam1);
        event.addTeam(testTeam2);

        String blob = Tools.blobify(event.getTeamList());
        System.out.println("APP_TEST: " + blob);
        ArrayList<Team> teamFromBlob = (ArrayList<Team>) Tools.deblobify(blob);
        System.out.println("APP_TEST: " + teamFromBlob.get(0).getTeamName());
    }


}