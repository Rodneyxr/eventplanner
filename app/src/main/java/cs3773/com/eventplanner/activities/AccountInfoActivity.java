package cs3773.com.eventplanner.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cs3773.com.eventplanner.R;


public class AccountInfoActivity extends BaseActivity {

    private String fullName;
    private String phoneNumber;
    private String email;
    // private String accountId; ****Not needed since account ID is system Generated.

    // private TextView mTextViewAccountId;
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
        // mTextViewAccountId = (TextView) findViewById(R.id.textViewAccountUUID);

        Button mUpdateAccountInfoButton = (Button) findViewById(R.id.buttonUpdateAccountInfo);
        mUpdateAccountInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUpdatedAccountInfo();
                updateAccountInfo();
            }
        });


    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_ACCOUNT_INFO;
    }

    private void getUpdatedAccountInfo() {
        fullName = mEditTextFullName.getText().toString();
        email = mEditTextEmail.getText().toString();
        phoneNumber = mEditTextPhoneNumber.getText().toString();
        // ID doesnt need to be set because it is System generated.
    }

    private void updateAccountInfo() {
        new AlertDialog.Builder(this)
                .setTitle("Updated Account Info")
                .setMessage("Your account info has been updated!")
                .setPositiveButton("Okay", null)
                .show();
    }
}
