package cs3773.com.eventplanner.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs3773.com.eventplanner.R;


public class AccountInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

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

    private void updateAccountInfo() {
        new AlertDialog.Builder(this)
                .setTitle("Updated Account Info")
                .setMessage("Your account info has been updated!")
                .setPositiveButton("Okay", null)
                .show();
    }
}
