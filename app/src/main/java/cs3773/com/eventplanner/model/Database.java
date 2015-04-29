package cs3773.com.eventplanner.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cs3773.com.eventplanner.server.ServerLink;
import cs3773.com.eventplanner.server.ServerRequest;
import cs3773.com.eventplanner.server.ServerRequestException;

/**
 * This class will provide static methods for conveniently fetch data from the database
 * and return them as usable objects.
 * <p/>
 * Created by Rodney on 4/27/2015.
 */
public class Database {

    public static ArrayList<Event> getEvents() {
        ArrayList<Event> eventsList = new ArrayList<Event>();
        try {
            ServerRequest request = new ServerRequest(ServerLink.GET_EVENTS);
            request.send();

            String result = request.getResponse();
            if (result.equals("false")) {
                return null;
            }

            // parse the result
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            JSONArray pages = new JSONArray(result);
            for (int i = 0; i < pages.length(); i++) {
                JSONObject page = pages.getJSONObject(i);
                Event event = new Event();
                event.setEventName(page.getString("event_name"));
                String dateString = page.getString("date");

                // set the date
                Date date;
                try {
                    date = sdf.parse(dateString);
                } catch (ParseException pe) {
                    pe.printStackTrace();
                    continue; // skip this event (it is invalid or something went wrong)
                }
                event.setDate(date);

                event.setTime(page.getString("time"));
                event.setLocation(page.getString("location"));
                event.setDescription(page.getString("description"));
//                event.setAccountList(); // TODO: set account list
                eventsList.add(event);
            }

        } catch (ServerRequestException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (JSONException je) {
            je.printStackTrace();
            return null;
        }
        return eventsList;
    }

    public static ArrayList<String> getAccountNames() {
        ArrayList<String> accountList = new ArrayList<>();
        try {
            ServerRequest request = new ServerRequest(ServerLink.GET_ACCOUNT_NAMES);
            request.send();

            String result = request.getResponse();
            if (result.equals("false")) {
                return null;
            }

            // parse the result
            JSONArray pages = new JSONArray(result);
            for (int i = 0; i < pages.length(); i++) {
                JSONObject page = pages.getJSONObject(i);
                String name = page.getString("username");
                accountList.add(name);
            }

        } catch (ServerRequestException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (JSONException je) {
            je.printStackTrace();
            return null;
        }
        return accountList;
    }

}