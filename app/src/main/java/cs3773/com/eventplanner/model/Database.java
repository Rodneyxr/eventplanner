package cs3773.com.eventplanner.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

//            System.out.println(result);

            // parse the result
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
                    continue;
                }
                event.setDate(date);

                event.setTime(page.getString("time"));
                event.setLocation(page.getString("location"));
                event.setDescription(page.getString("description"));
//                event.setTeamList();
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

}