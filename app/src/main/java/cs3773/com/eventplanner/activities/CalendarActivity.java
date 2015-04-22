package cs3773.com.eventplanner.activities;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import cs3773.com.eventplanner.R;

public class CalendarActivity extends BaseActivity {

    private CalendarView calendar;

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
                // TODO: open up events on click
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }
}
