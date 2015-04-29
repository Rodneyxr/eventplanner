package cs3773.com.eventplanner.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;
import android.content.Context;
import android.content.Intent;
import cs3773.com.eventplanner.R;
import cs3773.com.eventplanner.server.ServerLink;
import cs3773.com.eventplanner.server.ServerRequest;
import cs3773.com.eventplanner.server.ServerRequestException;
import android.app.TimePickerDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class CreateEventActivity extends BaseActivity  {
    //components
    private EditText mEditTextEvntNm;
    private EditText mEditTextEvntDesrptn;
    private EditText mEditTextEvntLctn;
    private EditText mEditTextEvntTim;
    private EditText mEditTextEvntDt;
    private EditText mEditTextEvntTm;
    private EditText mEditTextEvntHst;
    private EditText mEditTextEvntAduinc;
    private TextView txtTime;
    public Button btnTimePicker;
    final Context context = this;
    public Button button;
    public EditText result;

    //data


    // Variable for storing current time
    public int  mHour, mMinute;

    private String EvntNm;
    private String EvntDesrptn;
    private String EvntLctn;
    private String EvntTim;
    private String EvntDt;
    private String EvntTm;
    private String EvntHst;
    private String EvntAduinc;
    //event
    private CreateEventTask mCreateEventTask;

    //app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // components from xml
        button = (Button) findViewById(R.id.buttonPrompt);
        result = (EditText) findViewById(R.id.editTextResult);

        txtTime = (EditText) findViewById(R.id.txtTime);

        mEditTextEvntNm = (EditText) findViewById(R.id.editTextEvntNm);
        mEditTextEvntDesrptn = (EditText) findViewById(R.id.editTextEvntDesrptn);
        mEditTextEvntLctn = (EditText) findViewById(R.id.editTextEvntLctn);
        mEditTextEvntTim = (EditText) findViewById(R.id.editTextEvntTim);
        mEditTextEvntDt = (EditText) findViewById(R.id.editTextEvntDt);
        mEditTextEvntDt = (EditText) findViewById(R.id.editTextEvntDt);
        mEditTextEvntAduinc = (EditText) findViewById(R.id.editTextEvntAduinc);
        mEditTextEvntTm = (EditText) findViewById(R.id.editTextEvntTm);
        btnTimePicker = (Button) findViewById(R.id.btnTimePicker);
        Button button = (Button) findViewById(R.id.buttonPrompt);

        Button mTimePickerButton = (Button) findViewById(R.id.btnTimePicker);

        button.setOnClickListener(new View.OnClickListener(){
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


                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                       // result.setText(userInput.getText());
                                        String Description = userInput.getText().toString();
                                        Intent intent = new Intent(CreateEventActivity.this, Schedule.class);
                                        intent.putExtra("Description", Description);
                                        startActivity(intent);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }

        });


        mTimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePicker();
            }

        });

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
        EvntNm = mEditTextEvntNm.getText().toString();
        EvntDesrptn = mEditTextEvntDesrptn.getText().toString();
        EvntLctn = mEditTextEvntLctn.getText().toString();
        EvntTim = mEditTextEvntTim.getText().toString();
        EvntDt = mEditTextEvntDt.getText().toString();
        EvntHst = mEditTextEvntHst.getText().toString();
        EvntAduinc = mEditTextEvntAduinc.getText().toString();
        EvntTm = mEditTextEvntTm.getText().toString();
    }

    public void TimePicker() {

            // Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            // Display Selected time in textbox
                            //TODO Add final so not visible in create event (edittext)
                             txtTime.setText(hourOfDay + ":" + minute);
                            String Times = txtTime.getText().toString();
                            Intent intent = new Intent(CreateEventActivity.this, Schedule.class);
                            intent.putExtra("Times", Times);
                            startActivity(intent);
                        }
                    }, mHour, mMinute, false);
            tpd.show();
        }





        public void createTeam() {
        if (EvntNm.isEmpty()) {
            errorDialog("Event name cannot be empty.");
            mEditTextEvntNm.requestFocus();
            return;
        }
        if (EvntDesrptn.isEmpty()) {
            errorDialog("Event Description cannot be empty.");
            mEditTextEvntDesrptn.requestFocus();
            return;
        }
        if (EvntLctn.isEmpty()) {
            errorDialog("Event Location cannot be empty.");
            mEditTextEvntLctn.requestFocus();
            return;
        }
        if (EvntTim.isEmpty()) {
            errorDialog("Event time  cannot be empty.");
            mEditTextEvntTim.requestFocus();
            return;
        }
        if (EvntDt.isEmpty()) {

            errorDialog("Event Date cannot be empty.");
            mEditTextEvntDt.requestFocus();
            return;
        }
        if (EvntHst.isEmpty()) {
            errorDialog("Event host cannot be empty.");
            mEditTextEvntHst.requestFocus();
            return;
        }
        if (EvntAduinc.isEmpty()) {
            errorDialog("Event audience cannot be empty.");
            mEditTextEvntAduinc.requestFocus();
            return;
        }
        if (EvntTm.isEmpty()) {
            errorDialog("Event team[s] cannot be empty.");
            mEditTextEvntTm.requestFocus();
            return;
        }

        mCreateEventTask = new CreateEventTask();
        mCreateEventTask.execute(EvntNm, EvntDesrptn, EvntLctn, EvntTim, EvntDt, EvntTm, EvntHst, EvntAduinc);

    }

    public class CreateEventTask extends AsyncTask<String, Void, String> {

        CreateEventTask() {
            getNewEvntInfo();
        }

        @Override
        protected String doInBackground(String... params) {
            ServerRequest request = new ServerRequest(ServerLink.CREATE_EVENT);

            request.put("event_name", EvntNm);
            request.put("date", EvntDt);
            request.put("time", EvntTim);
            request.put("location", EvntLctn);
            request.put("description", EvntDesrptn);
            request.put("target_audience", EvntAduinc);
//            request.put("event_host", EvntHst);
            request.put("team_list", EvntTm);

            try {
                request.send();
            } catch (ServerRequestException sre) {
                System.err.println(sre.getMessage());
                return sre.getMessage();
            }

            String result = request.getResponse();
            System.out.println("** result: " + result);

            return result;
        }

        @Override
        protected void onPostExecute(final String result) {
            mCreateEventTask = null;

            if (result.equals("")) {
                showDialog("Created Event", "Your event has been created!");
                mEditTextEvntNm.setText("");
                mEditTextEvntDt.setText("");
                mEditTextEvntTim.setText("");
                mEditTextEvntLctn.setText("");
                mEditTextEvntDesrptn.setText("");
                mEditTextEvntAduinc.setText("");
                mEditTextEvntTm.setText("");

                mEditTextEvntHst.setText("");
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
