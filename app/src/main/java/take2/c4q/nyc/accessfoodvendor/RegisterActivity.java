package take2.c4q.nyc.accessfoodvendor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class RegisterActivity extends AppCompatActivity {
    private EditText truckName;
    private EditText phoneNumber;
    private EditText email;
    private EditText website;
    private EditText twtterHandle;
    String truckNameString;
    String phoneNumberString;
    String emailString;
    String websiteString;
    String twitterString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        truckName = (EditText) findViewById(R.id.truckNameID);
        phoneNumber = (EditText) findViewById(R.id.phoneNumberID);
        email = (EditText) findViewById(R.id.emailFieldID);
        website = (EditText) findViewById(R.id.websiteId);
        twtterHandle = (EditText) findViewById(R.id.twitterID);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
    public void toPic (View v){
        truckNameString = truckName.getText().toString();
        phoneNumberString = phoneNumber.getText().toString();
        emailString = email.getText().toString();
        twitterString = twtterHandle.getText().toString();
        websiteString = website.getText().toString() + " ";


        if (!truckNameString.equals("") && (!phoneNumberString.equals("") && (!emailString.equals("")))){

            final ParseObject newVendor = new ParseObject("Vendor");
            newVendor.put("name", truckNameString);
            newVendor.put("phone", phoneNumberString);
            newVendor.put("email", emailString);
            newVendor.put("twitter", twitterString);
            newVendor.put("website", websiteString);

            newVendor.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if (e==null) {
                        ParseUser user = ParseUser.getCurrentUser();
                        user.put("truck", newVendor);
                        user.saveInBackground();

                        String objectId = newVendor.getObjectId();
                        Intent intent = new Intent(RegisterActivity.this, HoursActivity.class);
                        intent.putExtra("objectId", objectId);
                        startActivity(intent);
                    }
                }
            });



        }
        else {
            Toast.makeText(getApplicationContext(), "Missing Fields", Toast.LENGTH_SHORT).show();

        }
    }
}
