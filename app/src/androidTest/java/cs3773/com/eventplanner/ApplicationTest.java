package cs3773.com.eventplanner;

import android.app.Application;
import android.os.StrictMode;
import android.test.ApplicationTestCase;

import java.util.ArrayList;

import cs3773.com.eventplanner.controller.Tools;
import cs3773.com.eventplanner.model.Account;
import cs3773.com.eventplanner.model.Database;
import cs3773.com.eventplanner.model.EmployeeAccount;
import cs3773.com.eventplanner.model.Event;
import cs3773.com.eventplanner.model.Role;
import cs3773.com.eventplanner.model.Team;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        // use this to send server requests on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Test_Serialization();
        Test_GetEvents();
    }

    public void Test_GetEvents() {
        System.out.println("getting events...");
        ArrayList<Event> events = Database.getEvents();
        for (Event event : events)
            System.out.println(event);
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

        Account account1 = new EmployeeAccount();
        account1.setFullName("Test account 1");
        account1.setRole(Role.employee);

        Account account2 = new EmployeeAccount();
        account2.setFullName("Test account 2");
        account2.setRole(Role.event_manager);

        event.addTeam(account1);
        event.addTeam(account2);

        String blob = Tools.blobify(event.getAccountList());
        System.out.println(blob);
        ArrayList<Account> accountsFromBlob = (ArrayList<Account>) Tools.deblobify(blob);
        System.out.println(accountsFromBlob.get(0).getFullName());
    }

}