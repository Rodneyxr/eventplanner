package cs3773.com.eventplanner.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import cs3773.com.eventplanner.R;
import cs3773.com.eventplanner.controller.Tools;
import cs3773.com.eventplanner.model.Event;
import cs3773.com.eventplanner.model.Session;
import cs3773.com.eventplanner.server.ServerLink;
import cs3773.com.eventplanner.server.ServerRequest;
import cs3773.com.eventplanner.server.ServerRequestException;


public class CreateEventActivity extends BaseActivity {

    //components
    private EditText mEditTextEvntNm;
    private EditText mEditTextEvntDesrptn;
    private EditText mEditTextEvntLctn;
    private EditText mEditTextEvntTim;
    private EditText mEditTextEvntDt;
    private EditText mEditTextEvntTm;
    private EditText mEditTextEvntAduinc;
    final Context context = this;
    //public Button button;

    //data
    private String eventName;
    private String eventDescription;
    private String eventLocation;
    private String eventTime;
    private String eventDate;
    private String eventAccountList;
    private String eventAudience;
    private Date date;

    //event
    private CreateEventTask mCreateEventTask;
    private ArrayList<String> accountList = new ArrayList<String>();
    Session session = new Session();

    //app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // components from xml
       // button = (Button) findViewById(R.id.buttonPrompt);


        mEditTextEvntNm = (EditText) findViewById(R.id.editTextEvntNm);
        mEditTextEvntDesrptn = (EditText) findViewById(R.id.editTextEvntDesrptn);
        mEditTextEvntLctn = (EditText) findViewById(R.id.editTextEvntLctn);
        mEditTextEvntTim = (EditText) findViewById(R.id.editTextEvntTim);
        mEditTextEvntDt = (EditText) findViewById(R.id.editTextEvntDt);
        mEditTextEvntDt = (EditText) findViewById(R.id.editTextEvntDt);
        mEditTextEvntAduinc = (EditText) findViewById(R.id.editTextEvntAduinc);
        mEditTextEvntTm = (EditText) findViewById(R.id.editTextEvntTm);
        //Button button = (Button) findViewById(R.id.buttonPrompt);


        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.description_prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                final EditText userInput2 = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput2);


                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it
                                        // edit text
                                        String Description = userInput2.getText().toString();
                                        String Time = userInput.getText().toString();
                                        Intent intent = new Intent(CreateEventActivity.this, Schedule.class);
                                        intent.putExtra("Description", Description);
                                        intent.putExtra("Time", Time);
                                        startActivity(intent);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }

        }); */

        Button mCreateEventButton = (Button) findViewById(R.id.buttonCreateEventInfo);
        mCreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewEvntInfo();
                createTeam();
            }
        });

    }


    public void getNewEvntInfo() {
        eventName = mEditTextEvntNm.getText().toString();
        eventDescription = mEditTextEvntDesrptn.getText().toString();
        eventLocation = mEditTextEvntLctn.getText().toString();
        eventTime = mEditTextEvntTim.getText().toString();
        eventDate = mEditTextEvntDt.getText().toString();
        eventAudience = mEditTextEvntAduinc.getText().toString();
        eventAccountList = mEditTextEvntTm.getText().toString();


    }

    public void createTeam() {
        if (eventName.isEmpty()) {
            errorDialog("Event name cannot be empty.");
            mEditTextEvntNm.requestFocus();
            return;
        }
        if (eventDescription.isEmpty()) {
            errorDialog("Event Description cannot be empty.");
            mEditTextEvntDesrptn.requestFocus();
            return;
        }
        if (eventLocation.isEmpty()) {
            errorDialog("Event Location cannot be empty.");
            mEditTextEvntLctn.requestFocus();
            return;
        }
        if (eventTime.isEmpty()) {
            errorDialog("Event time  cannot be empty.");
            mEditTextEvntTim.requestFocus();
            return;
        }
        if (eventDate.isEmpty()) {
            errorDialog("Event Date cannot be empty.");
            mEditTextEvntDt.requestFocus();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        // set the date
        try {
            date = sdf.parse(eventDate);
        } catch (ParseException pe) {
            errorDialog("Event Date should be in the format: 'yyyy-MM-dd'");
            return;
        }

        if (eventAudience.isEmpty()) {
            errorDialog("Event audience cannot be empty.");
            mEditTextEvntAduinc.requestFocus();
            return;
        }
        if (eventAccountList.isEmpty()) {
            errorDialog("Event team[s] cannot be empty.");
            mEditTextEvntTm.requestFocus();
            return;
        }

        accountList = new ArrayList<String>(Arrays.asList(eventAccountList.split("\\s*,\\s*")));
        for(String account : accountList){
            if(!Session.getAccountNames().contains(account)){
                errorDialog("User: " + account + " doesn't exist!");
                return;
            }
        }

        mCreateEventTask = new CreateEventTask();
        mCreateEventTask.execute(eventName, eventDescription, eventLocation, eventTime, eventDate, eventAccountList, eventAudience);

    }

    public class CreateEventTask extends AsyncTask<String, Void, String> {

        CreateEventTask() {
            getNewEvntInfo();
        }

        @Override
        protected String doInBackground(String... params) {
            ServerRequest request = new ServerRequest(ServerLink.CREATE_EVENT);

            request.put("event_name", eventName);
            request.put("date", eventDate);
            request.put("time", eventTime);
            request.put("location", eventLocation);
            request.put("description", eventDescription);
            request.put("target_audience", eventAudience);
            request.put("team_list", Tools.blobify(accountList));

            try {
                request.send();
            } catch (ServerRequestException sre) {
                System.err.println(sre.getMessage());
                return sre.getMessage();
            }

            String result = request.getResponse();

            return result;
        }

        @Override
        protected void onPostExecute(final String result) {
            mCreateEventTask = null;

            if (result.equals("")) {
                Event event = new Event();
                event.setEventName(eventName);
                event.setDate(date);
                event.setTime(eventTime);
                event.setLocation(eventLocation);
                event.setTargetAudience(eventAudience);
                event.setDescription(eventDescription);
                event.setAccountList(accountList);

                // add this event to the local session
                Session.getEvents().add(event);

                showDialog("Created Event", "Your event has been created!");
                mEditTextEvntNm.setText("");
                mEditTextEvntDt.setText("");
                mEditTextEvntTim.setText("");
                mEditTextEvntLctn.setText("");
                mEditTextEvntDesrptn.setText("");
                mEditTextEvntAduinc.setText("");
                mEditTextEvntTm.setText("");
                mEditTextEvntNm.requestFocus();

            }
            //not sure what key is and if it is even needed.
            else if (result.contains("Duplicate entry") && result.contains("for key 'event'")) {
                errorDialog("That event name already exists.");
                mEditTextEvntNm.requestFocus();
                mEditTextEvntNm.selectAll();
            } else {
                errorDialog("Unable to create event.");
            }
        }

        @Override
        protected void onCancelled() {
            mCreateEventTask = null;
        }
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_EVENT_CREATE;
    }

}
