package cs3773.com.eventplanner.activities;

import android.app.AlertDialog;
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
//        ServerRequest request = new ServerRequest(ServerLink.GET_ACCOUNT);
//        request.put("username", "testusername");//username);
//        request.put("password", "password");//Tools.sha256Base64(password));
//        request.put("account_id", "testuuid");//UUID.randomUUID().toString());
//        request.put("phone_number", "2101231234");
//        request.put("email", "testemailcom");
//        request.put("full_name", "fake name");

        if (true) return; // below code causes crash
        ServerRequest request = new ServerRequest(ServerLink.GET_ACCOUNT);
        request.put("username", username);
        request.put("password", Tools.sha256Base64(password));
//        request.send();

        //String result = request.getResponse();

        try {
            request.send();
        } catch (ServerRequestException sre) {
            System.err.println(sre.getMessage());
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(sre.getMessage())
                    .setPositiveButton("Okay", null)
                    .show();
            return;
        }

        String result = request.getResponse();
        System.out.println("** result: " + result);

        new AlertDialog.Builder(this)
                .setTitle("Created Account")
                .setMessage("Your account has been created!")
                .setPositiveButton("Okay", null)
                .show();
    }

}
