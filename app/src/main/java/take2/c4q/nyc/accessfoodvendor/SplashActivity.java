package take2.c4q.nyc.accessfoodvendor;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.VideoView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class SplashActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    String truckId = null;
    String userId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(new GifView(this));

        setContentView(R.layout.activity_splash);

//        accessIM = (ImageView) findViewById(R.id.accessID);
//        foodIM = (ImageView) findViewById(R.id.foodID);
//        NYCim = (ImageView) findViewById(R.id.NYCID);

        try{
//            VideoView videoHolder = new VideoView(this);
//            setContentView(videoHolder);
//            Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
//                    + R.raw.accessfood2);
//            videoHolder.setVideoURI(video);

            VideoView videoHolder = (VideoView) findViewById(R.id.truckvideo);
//            MediaController mediaController = new MediaController(this);
//            mediaController.setAnchorView(videoHolder);
            Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
                    + R.raw.accessfood_vendor);
//            videoHolder.setMediaController(mediaController);
//            videoHolder.setMediaController(new MediaController(this));
            videoHolder.setVideoURI(video);
            videoHolder.requestFocus();
            videoHolder.start();

            videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer mp) {
                    jump();
                }

            });
            videoHolder.start();
        } catch(Exception ex) {
            jump();
        }


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        if (isUserLoggedIn()) {
            ParseUser user = ParseUser.getCurrentUser();
            ParseObject truck = user.getParseObject("truck");

            if(truck==null){
                goToRegisterActivity();
            }else{
                user.getParseObject("truck");
                truckId = truck.getObjectId();
                goToMainActivity(truckId);
            }

        } else {
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, 6000);
        }


    }

    private boolean isUserLoggedIn() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            return true;
        } else {
            AccessToken currentToken = AccessToken.getCurrentAccessToken();
            if (currentToken != null && !currentToken.isExpired()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void goToMainActivity(String userId) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("truck", truckId);

        startActivity(intent);
        finish();
    }

    private void goToRegisterActivity() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    private void jump() {
    //it is safe to use this code even if you
    //do not intend to allow users to skip the splash
        if(isFinishing())
            return;
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
