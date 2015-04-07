package cs3773.com.eventplanner.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cs3773.com.eventplanner.R;


public class AccountInfoActivity extends BaseActivity {

    private String fullName = "Matthew";
    private String phoneNumber = "124-222-3333";
    private String email = "dummy@email.com";
    private String accountId = "23";

    private TextView mTextViewAccountId;
    private TextView mTextViewFullName;
    private TextView mTextViewPhoneNumber;
    private TextView mTextViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        mTextViewFullName = (TextView) findViewById(R.id.textViewAccountName);
        mTextViewEmail = (TextView) findViewById(R.id.textViewAccountEmail);
        mTextViewPhoneNumber = (TextView) findViewById(R.id.textViewAccountNumber);
        mTextViewAccountId = (TextView) findViewById(R.id.textViewAccountUUID);

        Button mUpdateAccountInfoButton = (Button) findViewById(R.id.buttonUpdateAccountInfo);
        mUpdateAccountInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAccountInfo();
            }
        });


    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_ACCOUNT_INFO;
    }

    private void dummyCredentialSet() {
        mTextViewFullName.setText(fullName);
        mTextViewEmail.setText(email);
        mTextViewPhoneNumber.setText(phoneNumber);
        mTextViewAccountId.setText(accountId);
    }

    private void updateAccountInfo() {
        new AlertDialog.Builder(this)
                .setTitle("Updated Account Info")
                .setMessage("Your account info has been updated!")
                .setPositiveButton("Okay", null)
                .show();
    }
}
