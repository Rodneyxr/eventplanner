package cs3773.com.eventplanner.activities;

import android.os.Bundle;

import cs3773.com.eventplanner.R;

public class ChatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_CHAT;
    }
}
