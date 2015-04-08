package cs3773.com.eventplanner.activities;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cs3773.com.eventplanner.R;

public class CreateAccountActivity extends ActionBarActivity {

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

    public void getNewAccountInfo(){
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
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
