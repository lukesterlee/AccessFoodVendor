package take2.c4q.nyc.accessfoodvendor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Hoshiko on 9/6/15.
 */
public class VendorRegistrationActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText nameInput = (EditText) findViewById(R.id.name_input);
        final String businessName = nameInput.getText().toString();

        EditText phoneInput = (EditText) findViewById(R.id.phone_input);
        final String businessPhone = phoneInput.getText().toString();

        EditText emailInput = (EditText) findViewById(R.id.email_input);
        final String businessEmail = emailInput.getText().toString();

        EditText twitterInput = (EditText) findViewById(R.id.twitter_input);
        final String businessTwitter = twitterInput.getText().toString();

        EditText categoryInput = (EditText) findViewById(R.id.category);
        final String category = categoryInput.getText().toString();

        Button submitButton = (Button)findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!businessName.equals("")) && (!businessPhone.equals("")) && (!businessEmail.equals(""))){

                    final ParseObject newVendor = new ParseObject("Vendor");
                    newVendor.put("name", businessName);
                    newVendor.put("phone", businessPhone);
                    newVendor.put("email", businessEmail);
                    newVendor.put("twitter", businessTwitter);
                    newVendor.put("description", category);
                    newVendor.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            ParseUser user = ParseUser.getCurrentUser();
                            user.put("truck", newVendor);
                            user.saveInBackground();

                            String objectId = newVendor.getObjectId();
                            Intent intent = new Intent(VendorRegistrationActivity.this, PicturesActivity.class);
                            intent.putExtra("objectId", objectId);
                            startActivity(intent);
                        }
                    });



                }
                else {
                    Toast.makeText(getApplicationContext(), "Missing Fields", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
