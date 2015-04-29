package cs3773.com.eventplanner.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.UUID;

import cs3773.com.eventplanner.R;
import cs3773.com.eventplanner.controller.Tools;
import cs3773.com.eventplanner.model.Role;
import cs3773.com.eventplanner.model.Session;
import cs3773.com.eventplanner.server.ServerLink;
import cs3773.com.eventplanner.server.ServerRequest;
import cs3773.com.eventplanner.server.ServerRequestException;

public class CreateAccountActivity extends BaseActivity {

    // components
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private EditText mEditTextValidatePassword;
    private Spinner mSpinnerRole;

    // data
    private String username;
    private String password;
    private String validatePassword;
    private String spinnerSelection;

    private CreateAccountTask mCreateAccountTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mEditTextUsername = (EditText) findViewById(R.id.editTextUsername);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mEditTextValidatePassword = (EditText) findViewById(R.id.editValidatePassword);
        mSpinnerRole = (Spinner) findViewById(R.id.spinner);

        mSpinnerRole.setAdapter(new ArrayAdapter<Role>(this, android.R.layout.simple_spinner_item, Role.values()));

        mSpinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
                mSpinnerRole.setSelection(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        Button mCreateAccountButton = (Button) findViewById(R.id.buttonCreateAccountInfo);
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewAccountInfo();
                createAccount();
            }
        });

    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_ACCOUNT_CREATE;
    }

    public void getNewAccountInfo() {
        username = mEditTextUsername.getText().toString();
        password = mEditTextPassword.getText().toString();
        validatePassword = mEditTextValidatePassword.getText().toString();
        spinnerSelection = mSpinnerRole.getSelectedItem().toString();
    }

    public void createAccount() {
        if (mCreateAccountTask != null) return;
        getNewAccountInfo();

        // validate username
        if (username.length() < 5) {
            errorDialog("Username must be at least 5 characters long.");
            mEditTextUsername.requestFocus();
            return;
        }

        // validate password
        if (password.length() < 8) {
            errorDialog("Password must be at least 8 character long.");
            mEditTextPassword.requestFocus();
            return;
        } else if (!password.matches(".*[a-zA-z].*") || !password.matches(".*[0-9].*")) {
            errorDialog("Password must contain at least 1 character and at least 1 number.");
            mEditTextPassword.requestFocus();
            return;
        }

        if (!password.equals(validatePassword)) {
            errorDialog("Passwords do not match.");
            mEditTextPassword.setText("");
            mEditTextValidatePassword.setText("");
            mEditTextPassword.requestFocus();
            return;
        }

        mCreateAccountTask = new CreateAccountTask();
        mCreateAccountTask.execute(username, password);
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class CreateAccountTask extends AsyncTask<String, Void, String> {

        CreateAccountTask() {
            getNewAccountInfo();
        }

        @Override
        protected String doInBackground(String... params) {
            ServerRequest request = new ServerRequest(ServerLink.CREATE_ACCOUNT);
            request.put("username", username);
            request.put("password", Tools.sha256Base64(password));
            request.put("account_id", UUID.randomUUID().toString());
            request.put("phone_number", "");
            request.put("email", "");
            request.put("full_name", "");
            request.put("role", spinnerSelection);

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
            mCreateAccountTask = null;

            if (result.equals("")) {
                // store the username locally
                Session.getAccountNames().add(username);

                showDialog("Created Account", "Your account has been created!");
                mEditTextUsername.setText("");
                mEditTextPassword.setText("");
                mEditTextValidatePassword.setText("");
                mSpinnerRole.setSelection(0, true);
                mEditTextUsername.requestFocus();
            } else if (result.contains("Duplicate entry") && result.contains("for key 'username'")) {
                errorDialog("That username already exists.");
                mEditTextUsername.requestFocus();
                mEditTextUsername.selectAll();
            } else {
                errorDialog("Unable to create account.");
            }
        }

        @Override
        protected void onCancelled() {
            mCreateAccountTask = null;
        }
    }

}
