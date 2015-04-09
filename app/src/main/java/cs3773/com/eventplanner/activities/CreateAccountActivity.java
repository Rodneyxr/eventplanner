package cs3773.com.eventplanner.activities;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import cs3773.com.eventplanner.R;
import cs3773.com.eventplanner.controller.Tools;
import cs3773.com.eventplanner.server.ServerLink;
import cs3773.com.eventplanner.server.ServerRequest;
import cs3773.com.eventplanner.server.ServerRequestException;

public class CreateAccountActivity extends BaseActivity {

    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private EditText mEditTextId;
    private String username;
    private String password;
    private String id;

    private CreateAccountTask mCreateAccountTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mEditTextUsername = (EditText) findViewById(R.id.editTextUsername);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mEditTextId = (EditText) findViewById(R.id.editTextId);

        Button mCreateAccountButton = (Button) findViewById(R.id.buttonCreateAccountInfo);
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        id = mEditTextId.getText().toString();
    }

    public void createAccount() {
        getNewAccountInfo();
        if (mCreateAccountTask != null) return;
        mCreateAccountTask = new CreateAccountTask();
        mCreateAccountTask.execute(username, password);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class CreateAccountTask extends AsyncTask<String, Void, Boolean> {

        CreateAccountTask() {
            getNewAccountInfo();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            ServerRequest request = new ServerRequest(ServerLink.CREATE_ACCOUNT);
            request.put("username", username);
            request.put("password", Tools.sha256Base64(password));
            request.put("account_id", UUID.randomUUID().toString());
            request.put("phone_number", "");
            request.put("email", "");
            request.put("full_name", "");

            try {
                request.send();
            } catch (ServerRequestException sre) {
                System.err.println(sre.getMessage());
                return false;
            }

            String result = request.getResponse();
            System.out.println("** result: " + result);
            // TODO: check if the account was actually created instead of just returning true

            if (result.contains("Duplicate entry") && result.contains("for key 'username'")) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mCreateAccountTask = null;

            if (success) {
                new AlertDialog.Builder(CreateAccountActivity.this)
                        .setTitle("Created Account")
                        .setMessage("Your account has been created!")
                        .setPositiveButton("Okay", null)
                        .show();
            } else {
                new AlertDialog.Builder(CreateAccountActivity.this)
                        .setTitle("Error")
                        .setMessage("Unable to create account.")
                        .setPositiveButton("Okay", null)
                        .show();
            }
        }

        @Override
        protected void onCancelled() {
            mCreateAccountTask = null;
        }
    }

}
