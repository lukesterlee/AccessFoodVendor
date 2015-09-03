package take2.c4q.nyc.accessfoodvendor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

public class HoursActivity extends AppCompatActivity {
    RadioButton monOpeningAM;
    RadioButton monOpeningPM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours);

        monOpeningAM = (RadioButton) findViewById(R.id.monOpenAMid);
        monOpeningPM = (RadioButton) findViewById(R.id.monOpenPmid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hours, menu);
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
    public void checkAM (View v){
        if (monOpeningPM.isChecked()) {
            monOpeningPM.setChecked(false);
            monOpeningAM.setChecked(true);
        }
    }
    public void checkPM (View v){
        if (monOpeningAM.isChecked()) {
            monOpeningAM.setChecked(false);
            monOpeningPM.setChecked(true);
        }
    }
}
