package cs3773.com.eventplanner.activities;

import android.os.Bundle;
import android.widget.EditText;
import java.util.Date;
import cs3773.com.eventplanner.R;
import cs3773.com.eventplanner.model.Account;
import cs3773.com.eventplanner.model.Event;
public class ViewEventActivity extends BaseActivity {

    private EditText mEditTextEventName;
    private EditText mEditTextEventDescription;
    private EditText mEditTextEventLocation;
    private EditText mEditTextEventTime;
    private EditText mEditTextEventDate;
    private EditText mEditTextEventUsers;
    private EditText mEditTextEventAudience;

    public static Event eventToLoad;
    private Date date;
    private String stringDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        mEditTextEventName = (EditText) findViewById(R.id.editTextEventName);
        mEditTextEventDescription = (EditText) findViewById(R.id.editTextEventDescription);
        mEditTextEventLocation = (EditText) findViewById(R.id.editTextEventLocation);
        mEditTextEventTime = (EditText) findViewById(R.id.editTextEventTime);
        mEditTextEventDate = (EditText) findViewById(R.id.editTextEventDate);
        mEditTextEventUsers = (EditText) findViewById(R.id.editTextEventUsers);
        mEditTextEventAudience = (EditText) findViewById(R.id.editTextEventAudience);
        getUpdatedEventInfo();
    }


    private void getUpdatedEventInfo() {
        if(eventToLoad == null){
            errorDialog("No event");
            return;
        }
        mEditTextEventName.setText(eventToLoad.getEventName());
        mEditTextEventDescription.setText(eventToLoad.getDescription());
        mEditTextEventLocation.setText(eventToLoad.getLocation());
        mEditTextEventTime.setText(eventToLoad.getTime());
        date = eventToLoad.getDate();
        stringDate = date.toString();
        mEditTextEventDate.setText(stringDate);

        StringBuilder sb = new StringBuilder();
        for (Account account : eventToLoad.getAccountList()) {
            sb.append(account.getUsername() + " ");
        }
        String accounts = sb.toString();
        mEditTextEventUsers.setText(accounts);
        mEditTextEventAudience.setText(eventToLoad.getTargetAudience());

    }

}
