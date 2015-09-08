package take2.c4q.nyc.accessfoodvendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PicturesActivity extends AppCompatActivity {
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");

        Button addpicBtn = (Button)findViewById(R.id.add_pics);
        addpicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicDialog picDialog = new PicDialog();

                Bundle bundle = new Bundle();
                bundle.putString(Constant.EXTRA_KEY_OBJECT_ID, objectId);


                picDialog.show(getSupportFragmentManager(), "picD");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pictures, menu);
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
    public void toR (View v){
        Intent intent = new Intent(PicturesActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    public void toMenu (View v){
        Intent intent = new Intent(PicturesActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
