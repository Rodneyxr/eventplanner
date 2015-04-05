package cs3773.com.eventplanner.activities;

import android.os.Bundle;

import cs3773.com.eventplanner.R;

public class CalendarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_CALENDAR;
    }
}
