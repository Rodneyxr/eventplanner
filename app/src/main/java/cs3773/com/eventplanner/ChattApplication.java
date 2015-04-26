package cs3773.com.eventplanner;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import cs3773.com.eventplanner.model.Message;

/**
 * Created by Sohail on 4/10/15.
 */
public class ChattApplication extends Application {

    public static final String APP_KEY_ID = "rcOmi6CtnvAiirDXzxycyvpV9286NQFzLGpCdE8L";
    public static final String APP_CLIENT_ID = "jJna9Mx1osBR8chefTVg4fzOpTGFjAIUI0DAYf7W";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        ParseObject.registerSubclass(Message.class);

        Parse.initialize(this, APP_KEY_ID, APP_CLIENT_ID);

    }
}
