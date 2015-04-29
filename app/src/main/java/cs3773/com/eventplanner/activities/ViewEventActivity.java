package cs3773.com.eventplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import cs3773.com.eventplanner.R;
import cs3773.com.eventplanner.controller.BackgroundTask;
import cs3773.com.eventplanner.model.Account;
import cs3773.com.eventplanner.model.Event;
import cs3773.com.eventplanner.model.Session;
import cs3773.com.eventplanner.server.ServerLink;
import cs3773.com.eventplanner.server.ServerRequest;
import cs3773.com.eventplanner.server.ServerRequestException;

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

        Button mDeleteEventButton = (Button) findViewById(R.id.buttonDeleteEvent);
        mDeleteEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent();
            }
        });
    }

    private void getUpdatedEventInfo() {
        if (eventToLoad == null) {
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
        for (String account : eventToLoad.getAccountList()) {
            sb.append(account + ", ");
        }
        String accounts = sb.toString();
        mEditTextEventUsers.setText(accounts);
        mEditTextEventAudience.setText(eventToLoad.getTargetAudience());
    }

    public void deleteEvent() {
        BackgroundTask bg = new BackgroundTask() {
            @Override
            protected Boolean doInBackground(String... params) {
                ServerRequest request = new ServerRequest(ServerLink.DELETE_EVENT);
                request.put("event_name", eventToLoad.getEventName());

                try {
                    request.send();

                } catch (ServerRequestException sre) {
                    System.err.println(sre.getMessage());
                    return false;
                }

                String result = request.getResponse();
                System.out.println("** result: " + result);

                if (result.equals(""))
                    return true;
                return false;
            }

            @Override
            protected void onPostExecute(final Boolean success) {
                if (success) {
                    Session session = new Session();
                    session.getEvents().remove(eventToLoad);
                    mEditTextEventName.setText("");
                    mEditTextEventDescription.setText("");
                    mEditTextEventLocation.setText("");
                    mEditTextEventTime.setText("");
                    mEditTextEventDate.setText("");
                    mEditTextEventUsers.setText("");
                    mEditTextEventAudience.setText("");
                    showDialog("Deleted Event", "Your event has been deleted!");
                } else {
                    errorDialog("There was a problem deleting your event!");
                }
            }
        };
        bg.execute();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewEventActivity.this, CalendarActivity.class);
        startActivity(intent);
        finish();
    }
}
