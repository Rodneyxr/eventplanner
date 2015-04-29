package cs3773.com.eventplanner.model;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class will hold all information about the schedule for an event.
 * <p/>
 * Created by Rodney on 4/28/2015.
 */
public class TimeSlot implements Comparable<Time>, Serializable {

    private Time time;
    private String description;

    public TimeSlot(String time, String description) throws Exception {
        Date date = null;
        try {
            date = new SimpleDateFormat("hh:mm", Locale.US).parse(time);
        } catch (ParseException e) {
            throw new Exception("Invalid time format. Must be in the format 'hh:mm'");
        }

        if (date != null) {
            this.time = new Time(date.getTime());
        } else {
            this.time = new Time(0);
        }

    }

    public TimeSlot(Time time, String description) {
        this.time = time;
        this.description = description;
    }

    public Time getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Time other) {
        return time.compareTo(other);
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("h:mm a", Locale.US).format(new Date(time.getTime()));
    }

}
