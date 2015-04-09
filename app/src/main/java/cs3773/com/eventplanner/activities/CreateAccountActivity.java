package cs3773.com.eventplanner.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cs3773.com.eventplanner.R;

public class CreateAccountActivity extends BaseActivity {

    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private EditText mEditTextId;
    private String username;
    private String password;
    private String id;

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
        id = mEditTextId.getText().toString();
    }

    public void createAccount() {
        new AlertDialog.Builder(this)
                .setTitle("Created Account")
                .setMessage("Your account has been created!")
                .setPositiveButton("Okay", null)
                .show();
    }

}
