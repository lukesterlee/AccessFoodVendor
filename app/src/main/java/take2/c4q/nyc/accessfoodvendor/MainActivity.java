package take2.c4q.nyc.accessfoodvendor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SendCallback;


public class MainActivity extends AppCompatActivity {

    private Button mButtonLogOut;
    private Button mButtonProfile;
    private Button mButtonPush;
    private TextView mTitle;

    private ParseObject mTruck;

    String truckId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = getIntent();
//        truckId= intent.getStringExtra("truck");

        ParseUser user = ParseUser.getCurrentUser();
        ParseObject truck = user.getParseObject("truck");
        truckId = truck.getObjectId();

        mTitle = (TextView) findViewById(R.id.title);

        mButtonProfile = (Button) findViewById(R.id.button_profile);
        mButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
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
                ParsePush push = new ParsePush();
                push.setChannel(mTruck.getObjectId());
                push.setMessage("This is Test! 10% off today!!!");
                push.sendInBackground(new SendCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(getApplicationContext(), "Sent!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//        ParseUser user = ParseUser.getCurrentUser();

        mTruck = user.getParseObject("truck");
        mTruck.fetchIfNeededInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject truck, ParseException e) {
                mTruck = truck;
                mTitle.setText(truck.getString("name"));
            }
        });
//        mTruck.fetchInBackground(new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject truck, ParseException e) {
//
//            }
//        });




        Button addPicBtn = (Button)findViewById(R.id.pic_button);
        addPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicDialog picDialog = new PicDialog();

                Bundle bundle = new Bundle();
                bundle.putString("truckId", truckId);
                picDialog.setArguments(bundle);
                picDialog.show(getSupportFragmentManager(), "picD");
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
        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("truckId", truckId);
        startActivity(intent);
//        finish();
    }
    public void toUpdateHours (View v){
        Intent intent = new Intent(MainActivity.this, HoursActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("truckId", truckId);
        startActivity(intent);

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

        ParseUser.logOut();
        Toast.makeText(getApplicationContext(), "Successfully logged out!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
