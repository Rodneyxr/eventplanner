package cs3773.com.eventplanner.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cs3773.com.eventplanner.R;
import cs3773.com.eventplanner.controller.BackgroundTask;
import cs3773.com.eventplanner.model.Account;
import cs3773.com.eventplanner.model.Session;


public class AccountInfoActivity extends BaseActivity {

    private TextView mTextViewAccountId;
    private EditText mEditTextFullName;
    private EditText mEditTextPhoneNumber;
    private EditText mEditTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        mEditTextFullName = (EditText) findViewById(R.id.editTextAccountName);
        mEditTextEmail = (EditText) findViewById(R.id.editTextAccountEmail);
        mEditTextPhoneNumber = (EditText) findViewById(R.id.editTextAccountPhoneNumber);
        mTextViewAccountId = (TextView) findViewById(R.id.textViewAccountUUID);

        Button mUpdateAccountInfoButton = (Button) findViewById(R.id.buttonUpdateAccountInfo);
        mUpdateAccountInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUpdatedAccountInfo();
                updateAccountInfo();
            }
        });

        // refresh the view to display the user's account info
        loadAccountInfo();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_ACCOUNT_INFO;
    }

    /**
     * refreshes the view with the user's account information
     */
    private void loadAccountInfo() {
        Account account = Session.getAccount();
        mEditTextFullName.setText(account.getFullName());
        mEditTextEmail.setText(account.getEmail());
        mEditTextPhoneNumber.setText(account.getPhoneNumber());
        mTextViewAccountId.setText(account.getAccountNumber().toString());
    }

    private void getUpdatedAccountInfo() {
        Session.getAccount().setFullName(mEditTextFullName.getText().toString());
        Session.getAccount().setEmail(mEditTextEmail.getText().toString());
        Session.getAccount().setPhoneNumber(mEditTextPhoneNumber.getText().toString());
    }

    private void updateAccountInfo() {
        BackgroundTask bg = new BackgroundTask() {
            @Override
            protected Boolean doInBackground(String... params) {
                return Session.getAccount().saveAccount();
            }

            @Override
            protected void onPostExecute(final Boolean success) {
                if (success) {
                    showDialog("Updated Account Info", "Your account information has been updated!");
                } else {
                    errorDialog("There was a problem saving your account information!");
                }
            }
        };
        bg.execute();

//        if (Session.getAccount().saveAccount()) {
//            showDialog("Updated Account Info", "Your account information has been updated!");
//        } else {
//            errorDialog("There was a problem saving your account information!");
//        }
    }
}
