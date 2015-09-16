package take2.c4q.nyc.accessfoodvendor;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {

    private Button mButtonLogOut;
    private Button mButtonProfile;
    private Button mButtonPush;
    private TextView mTitle;
    private String truckName;

    private ParseObject mTruck;

    String truckId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        if(isNetworkAvailable()){
            ParseUser user = ParseUser.getCurrentUser();
            mTruck = user.getParseObject("truck");
            mTruck.fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject truck, ParseException e) {
                    mTruck = truck;
                    truckName = truck.getString("name");
                    mTitle.setText(truckName);
                    truckId = truck.getObjectId();
                }
            });
        }

            mTitle = (TextView) findViewById(R.id.title);

            mButtonProfile = (Button) findViewById(R.id.button_profile);
            mButtonProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isNetworkAvailable()){

                        Toast.makeText(getApplicationContext(), "Sorry there is no internet, please try again later", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        intent.putExtra("truckName", truckName);
                        startActivity(intent);
                    }
                }
            });



            mButtonLogOut = (Button) findViewById(R.id.log_out);
            mButtonLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    logOut();
                }
            });

            mButtonPush = (Button) findViewById(R.id.push);
            mButtonPush.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isNetworkAvailable()){

                        Toast.makeText(getApplicationContext(), "Sorry there is no internet, please try again later", Toast.LENGTH_SHORT).show();
                    }else {
                        PushDialogFragment dialog = new PushDialogFragment();
                        dialog.show(getSupportFragmentManager(), "Push");
                    }

                }
            });

//




            Button addPicBtn = (Button)findViewById(R.id.pic_button);
            addPicBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!isNetworkAvailable()){

                        Toast.makeText(getApplicationContext(), "Sorry there is no internet, please try again later", Toast.LENGTH_SHORT).show();
                    }else {
                        PicDialog picDialog = new PicDialog();

                        Bundle bundle = new Bundle();
                        bundle.putString("truckId", truckId);
                        picDialog.setArguments(bundle);

                        picDialog.show(getSupportFragmentManager(), "picD");
                    }
                }
            });





    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//        switch (item.getItemId()) {
//            case R.id.action_logout:
//                logOut();
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public void toUpdateLocation (View v){
        if(!isNetworkAvailable()){

            Toast.makeText(getApplicationContext(), "Sorry there is no internet, please try again later", Toast.LENGTH_SHORT).show();
        }else {

            Intent intent = new Intent(MainActivity.this, LocationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("truckId", truckId);
            startActivity(intent);
//        finish();
        }
    }
    public void toUpdateHours (View v){
        if(!isNetworkAvailable()){

            Toast.makeText(getApplicationContext(), "Sorry there is no internet, please try again later", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(MainActivity.this, HoursActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("truckId", truckId);
            startActivity(intent);
        }

    }

//    private void toPic(View v) {
//        Intent intent = new Intent(getApplicationContext(), PicturesActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtra("truckId", truckId);
//        startActivity(intent);
//
//    }

    private void logOut() {
        if(!isNetworkAvailable()){

            Toast.makeText(getApplicationContext(), "Sorry there is no internet, please try again later", Toast.LENGTH_SHORT).show();
        }else {
            ParseUser.logOut();
            Toast.makeText(getApplicationContext(), "Successfully logged out!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
