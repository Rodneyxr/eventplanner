package cs3773.com.eventplanner.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import cs3773.com.eventplanner.R;
import cs3773.com.eventplanner.controller.Tools;
import cs3773.com.eventplanner.model.Message;
import cs3773.com.eventplanner.model.Session;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    //Parse Keys
    public static final String APP_KEY_ID = "rcOmi6CtnvAiirDXzxycyvpV9286NQFzLGpCdE8L";
    public static final String APP_CLIENT_ID = "jJna9Mx1osBR8chefTVg4fzOpTGFjAIUI0DAYf7W";
    //parse Login
    private String userName;
    private String password;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this, APP_KEY_ID, APP_CLIENT_ID);

        //parse
        userName = "ParseUser";
        password = "wg498nodpf228hg";

        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                parseCreateUser();
                parseLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void parseCreateUser() {
        //CREATE PARSE USER
        ParseUser user = new ParseUser();
        user.setUsername("ParseUser");
        user.setPassword("wg498nodpf228hg");
        user.setEmail("BobTheBuilder@PBS.com");

        //Add Parse USER
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //we are good!
                    Toast.makeText(getApplicationContext(), "Parse is up and Running", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Parse Connected.(ERROR !)", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public void parseLogin() {

        String uName = userName;
        String pWord = password;

        if (!uName.equals("") || !pWord.equals("")) {
            ParseUser.logInInBackground(uName, pWord, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e == null) {

                        Toast.makeText(getApplicationContext(), "Login Successfully!"
                                , Toast.LENGTH_LONG).show();

                        //startActivity(new Intent(LoginActivity.this, ChatActivity.class));

                    } else {

                        Toast.makeText(getApplicationContext(), "Not logged in",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        String message;
        if ((message = isPasswordValid(password)) != null) {
            mPasswordView.setError(message);
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if ((message = isUsernameValid(username)) != null) {
            mUsernameView.setError(message);
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask();
            mAuthTask.execute(username, password);
        }
    }

    private String isUsernameValid(String username) {
        if (username.length() < 5)
            return "This username must be at least 5 characters";
        if (username.length() > 20)
            return "This username must be less than 20 characters";
        if (!username.matches("[a-zA-Z0-9_]{5,20}"))
            return "This username is invalid";
        return null;
    }

    private String isPasswordValid(String password) {
        if (password.length() < 5)
            return "This password must be at least 5 characters";
        if (password.length() > 20)
            return "This password must be less than 20 characters";
        return null;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<String, Void, Boolean> {

        UserLoginTask() {
        }

        @Override
        protected Boolean doInBackground(String... params) {
//            if (true) return true; // temporary to avoid accessing server

            String username = params[0];
            String password = params[1];

            password = Tools.sha256Base64(password);
            return Session.setAccount(username, password);

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Intent menuIntent = new Intent(LoginActivity.this, CalendarActivity.class);
                startActivity(menuIntent);
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
