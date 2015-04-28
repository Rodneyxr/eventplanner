package cs3773.com.eventplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cs3773.com.eventplanner.R;
import cs3773.com.eventplanner.model.Event;
import cs3773.com.eventplanner.model.Session;
import cs3773.com.eventplanner.model.Team;

public class CalendarActivity extends BaseActivity {

    private CalendarView calendar;
    private  Intent intent;
    private Event event;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Date date;
    private Date eventDate;
    private String eventName = "Rodney's Reindeer Party";
    private int testDay = 15;
    private int testMonth = 3;
    private int testYear = 2015;
    private String time = "11:00";
    private String location = "Rodney's Crib";
    private String description = "Rodney's great party";
    private String targetAudience = "little children";
    private ArrayList<Team> teamList = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initializeCalendar();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_CALENDAR;
    }

    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendarView);

        //sets the listener to be notified upon selected date change.
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {

                try{
                        date = sdf.parse((year) + "-" + (month + 1) + "-" + (day));
                        for(Event event : Session.getEvents()) {
                            //eventDate = sdf.parse(testYear + "-" + testMonth + "-" + testDay);
                            if (date.compareTo(event.getDate()) == 0) {
                                ViewEventActivity.eventToLoad = event;
                                intent = new Intent(CalendarActivity.this, ViewEventActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        //
                        //event = new Event(eventName, eventDate, time, location, description, targetAudience, teamList);


                } catch(ParseException ex){
                    System.out.println("Parse didn't work");
                }
            }
        });
    }
}