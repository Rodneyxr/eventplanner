package cs3773.com.eventplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.app.AlertDialog;
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
    private String WhatsNewTitle = "Welcome To Centrium Events!";
    final Context context = this;


    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // second argument is the default to use if the preference can't be found
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);

        if (!welcomeScreenShown) {
            // here you can launch another activity if you like
            // the code below will display a popup

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.login_prompt, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
            context);
        alertDialogBuilder.setView(promptsView);

         // set dialog message

            alertDialogBuilder
            .setCancelable(false)
            .setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.commit(); // Very important to save the preference
        }
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