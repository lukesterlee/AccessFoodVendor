package take2.c4q.nyc.accessfoodvendor;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    protected EditText usernameEditText;
    protected EditText passwordEditText;
    protected EditText usernameEditText2;
    protected EditText passwordEditText2;
    protected EditText emailField;
    protected Button loginButton;
    protected Button signUpButton;
    protected Button backButton;
    protected Button continueButton;
    protected LinearLayout layout;
    protected ParseApplication app;

    private LoginButton mButtonFacebookLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // facebook stuff
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        FacebookSdk.clearLoggingBehaviors();

        app = new ParseApplication();

        signUpButton = (Button)findViewById(R.id.signupButtonID);
        usernameEditText = (EditText)findViewById(R.id.usernameField);
        passwordEditText = (EditText)findViewById(R.id.passwordField);
        usernameEditText2 = (EditText)findViewById(R.id.usernameField2);
        passwordEditText2 = (EditText)findViewById(R.id.passwordField2);
        loginButton = (Button)findViewById(R.id.loginButton);
        emailField = (EditText) findViewById(R.id.emailFieldID);
        backButton = (Button) findViewById(R.id.BackButtonID);
        continueButton = (Button) findViewById(R.id.ContinueButtonID);
        layout = (LinearLayout) findViewById(R.id.layoutID);
        mButtonFacebookLogin = (LoginButton) findViewById(R.id.login_button);

        mButtonFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Toast.makeText(getApplicationContext(), "Uh oh. The user cancelled the Facebook login.", Toast.LENGTH_SHORT).show();
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Profile profile = Profile.getCurrentProfile();
                            user.put("first_name", profile.getFirstName());
                            user.put("last_name", profile.getLastName());
                            user.put("profile_url", profile.getProfilePictureUri(300, 300).toString());
                            user.saveInBackground();

                            Toast.makeText(getApplicationContext(), "User signed up and logged in through Facebook!", Toast.LENGTH_SHORT).show();
                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                            goToMainActivity();
                        } else {
                            Profile profile = Profile.getCurrentProfile();
                            Toast.makeText(getApplicationContext(), "User logged in through Facebook!", Toast.LENGTH_SHORT).show();
                            Log.d("MyApp", "User logged in through Facebook!");
                            goToMainActivity();
                        }
                    }
                });
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                username = username.trim();
                password = password.trim();

                if (username.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(R.string.login_error_message)
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    setProgressBarIndeterminateVisibility(true);

                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            setProgressBarIndeterminateVisibility(false);

                            if (e == null) {
                                // Success!
                                goToMainActivity();
                                //finish();
                            } else {
                                // Fail
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle(R.string.login_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText2.getText().toString();
                String password = passwordEditText2.getText().toString();
                String email = emailField.getText().toString();

                username = username.trim();
                password = password.trim();
                email = email.trim();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    setProgressBarIndeterminateVisibility(true);

                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setEmail(email);
                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            setProgressBarIndeterminateVisibility(false);

                            if (e == null) {
                                // Success!
                                goToMainActivity();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle(R.string.signup_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void setUpListeners(boolean isResumed) {
        if (isResumed) {

        } else {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpListeners(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setUpListeners(false);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
    public void showSignUpFields (View v){
        mButtonFacebookLogin.setVisibility(View.GONE);
        usernameEditText.setVisibility(View.GONE);
        passwordEditText.setVisibility(View.GONE);
        usernameEditText2.setVisibility(View.VISIBLE);
        passwordEditText2.setVisibility(View.VISIBLE);
        emailField.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
        signUpButton.setVisibility(View.GONE);
        backButton.setVisibility(View.VISIBLE);
        continueButton.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
    }

    public void back (View v){
        mButtonFacebookLogin.setVisibility(View.VISIBLE);
        usernameEditText.setVisibility(View.VISIBLE);
        passwordEditText.setVisibility(View.VISIBLE);
        usernameEditText2.setVisibility(View.GONE);
        passwordEditText2.setVisibility(View.GONE);
        emailField.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
        signUpButton.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.GONE);
        continueButton.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
