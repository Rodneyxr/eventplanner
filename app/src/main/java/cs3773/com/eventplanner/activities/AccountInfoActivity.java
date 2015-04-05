package cs3773.com.eventplanner.activities;

import android.os.Bundle;

import cs3773.com.eventplanner.R;


public class AccountInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_ACCOUNT_INFO;
    }
}
