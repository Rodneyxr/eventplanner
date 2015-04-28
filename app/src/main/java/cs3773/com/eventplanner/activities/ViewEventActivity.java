package cs3773.com.eventplanner.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import cs3773.com.eventplanner.R;

public class ViewEventActivity extends BaseActivity {

    private EditText mEditTextEventName;
    private EditText mEditTextEventDescription;
    private EditText mEditTextEventLocation;
    private EditText mEditTextEventTime;
    private EditText mEditTextEventDate;
    private EditText mEditTextEventTeam;
    private EditText mEditTextEventHost;
    private EditText mEditTextEventAudience;
    //data
    private String eventName;
    private String eventDescription;
    private String eventLocation;
    private String eventTime;
    private String eventDate;
    private String eventTeam;
    private String eventHost;
    private String eventAudience;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        mEditTextEventName = (EditText) findViewById(R.id.editTextEventName);
        mEditTextEventDescription = (EditText) findViewById(R.id.editTextEventDescription);
        mEditTextEventLocation = (EditText) findViewById(R.id.editTextEventLocation);
        mEditTextEventTime = (EditText) findViewById(R.id.editTextEventTime);
        mEditTextEventDate = (EditText) findViewById(R.id.editTextEventDate);
        mEditTextEventTeam = (EditText) findViewById(R.id.editTextEventTeam);
        mEditTextEventHost = (EditText) findViewById(R.id.editTextEventHost);
        mEditTextEventAudience = (EditText) findViewById(R.id.editTextEventAudience);
    }


}
