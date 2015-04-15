package cs3773.com.eventplanner.controller;

import android.os.AsyncTask;

/**
 * This class will perform tasks in the background. This will mostly
 * be used to connect with the server.
 * <p/>
 * Created by Rodney on 4/14/2015.
 */
public class BackgroundTask extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... params) {
        return false;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
    }

    @Override
    protected void onCancelled() {
    }
}
