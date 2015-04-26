package cs3773.com.eventplanner.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cs3773.com.eventplanner.R;
import cs3773.com.eventplanner.server.ServerLink;
import cs3773.com.eventplanner.server.ServerRequest;
import cs3773.com.eventplanner.server.ServerRequestException;

public class CreateTeamActivity extends BaseActivity {
    //components
    private EditText mEditTextTmNm;
    private EditText mEditTextTmGenMmbr1;
    private EditText mEditTextTmGenMmbr2;
    private EditText mEditTextTmGenMmbr3;
    private EditText mEditTextTmSprvsr;
    private EditText mEditTextTmEvntMngr;
    private EditText mEditTextTmEvntMngrAsstnt;
    private EditText mEditTextTmDscrptn;

    //data
    private String TmNm;
    private String TmGenMmbr1;
    private String TmGenMmbr2;
    private String TmGenMmbr3;
    private String TmSprvsr;
    private String TmEvntMngr;
    private String TmEvntMngrAsstnt;
    private String TmDscrptn;

    private CreateTeamTask mCreateTeamTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        mEditTextTmNm = (EditText) findViewById(R.id.editTextTmNm);
        mEditTextTmGenMmbr1 = (EditText) findViewById(R.id.editTextTmGenMmbr1);
        mEditTextTmGenMmbr2 = (EditText) findViewById(R.id.editTextTmGenMmbr2);
        mEditTextTmGenMmbr3 = (EditText) findViewById(R.id.editTextTmGenMmbr3);
        mEditTextTmSprvsr = (EditText) findViewById(R.id.editTextTmSuprvsr);
        mEditTextTmEvntMngr = (EditText) findViewById(R.id.editTextTmEvntMngr);
        mEditTextTmEvntMngrAsstnt = (EditText) findViewById(R.id.editTextTmEvntMngrAsstnt);
        mEditTextTmDscrptn = (EditText) findViewById(R.id.editTextTmDscrptn);

        Button mCreateTeamButton = (Button) findViewById(R.id.buttonCreateTeamInfo);
        mCreateTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewTeamInfo();
                createTeam();
            }
        });


    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_TEAM_CREATE;
    }

    public void getNewTeamInfo() {
        TmNm = mEditTextTmNm.getText().toString();
        TmEvntMngr = mEditTextTmEvntMngr.getText().toString();
        TmEvntMngrAsstnt = mEditTextTmEvntMngrAsstnt.getText().toString();
        TmSprvsr = mEditTextTmSprvsr.getText().toString();
        TmGenMmbr1 = mEditTextTmGenMmbr1.getText().toString();
        TmGenMmbr2 = mEditTextTmGenMmbr2.getText().toString();
        TmGenMmbr3 = mEditTextTmGenMmbr3.getText().toString();
        TmDscrptn = mEditTextTmDscrptn.getText().toString();

    }

    public void createTeam() {
        if (TmNm.isEmpty()) {
            errorDialog("Team name cannot be empty.");
            mEditTextTmNm.requestFocus();
            return;
        }
        // check if empty
        if (TmEvntMngr.isEmpty()) {
            errorDialog("Event manager field cannot be empty.");
            mEditTextTmEvntMngr.requestFocus();
            return;
        }
        if (TmEvntMngrAsstnt.isEmpty()) {
            errorDialog("Event manager assisstant field cannot be empty.");
            mEditTextTmEvntMngrAsstnt.requestFocus();
            return;
        }
        if (TmSprvsr.isEmpty()) {
            errorDialog("Supervisior field cannot be empty.");
            mEditTextTmSprvsr.requestFocus();
            return;
        }
        if (TmDscrptn.isEmpty()) {
            errorDialog("Team description field cannot be empty.");
            mEditTextTmDscrptn.requestFocus();
            return;
        }

        mCreateTeamTask = new CreateTeamTask();
        mCreateTeamTask.execute(TmNm, TmEvntMngr, TmEvntMngrAsstnt, TmSprvsr, TmGenMmbr1, TmGenMmbr2, TmGenMmbr3, TmDscrptn);
    }

    public class CreateTeamTask extends AsyncTask<String, Void, String> {

        CreateTeamTask() {
            getNewTeamInfo();
        }

        @Override
        protected String doInBackground(String... params) {
            ServerRequest request = new ServerRequest(ServerLink.CREATE_TEAM);
            request.put("team_name", TmNm);
            request.put("event_manager", TmEvntMngr);
            request.put("event_manager_assistant", TmEvntMngrAsstnt);
            request.put("team_supervisor", TmSprvsr);
            request.put("member1", TmGenMmbr1);
            request.put("member2", TmGenMmbr2);
            request.put("member3", TmGenMmbr3);
            request.put("team_description", TmDscrptn);

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
            mCreateTeamTask = null;

            if (result.equals("")) {
                showDialog("Created Team", "Your team has been created!");
                mEditTextTmNm.setText("");
                mEditTextTmGenMmbr1.setText("");
                mEditTextTmGenMmbr2.setText("");
                mEditTextTmGenMmbr3.setText("");
                mEditTextTmSprvsr.setText("");
                mEditTextTmEvntMngr.setText("");
                mEditTextTmEvntMngrAsstnt.setText("");
                mEditTextTmDscrptn.setText("");
                mEditTextTmNm.requestFocus();

            }
            //not sure what key is and if it is even needed.
            else if (result.contains("Duplicate entry") && result.contains("for key 'team'")) {
                errorDialog("That team name already exists.");
                mEditTextTmNm.requestFocus();
                mEditTextTmNm.selectAll();
            } else {
                errorDialog("Unable to create team.");
            }
        }

        @Override
        protected void onCancelled() {
            mCreateTeamTask = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_team, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
