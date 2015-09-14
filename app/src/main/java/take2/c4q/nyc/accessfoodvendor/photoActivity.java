package take2.c4q.nyc.accessfoodvendor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Hoshiko on 9/8/15.
 */
public class PhotoActivity extends AppCompatActivity {
    private Uri imageUri;
    private ImageView imageView;
    private Bitmap bitmap;
    private String truckId;



    private ProgressDialog mProgressDialog;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //progressBar.setVisibility(View.GONE);

        Intent intent = getIntent();
        truckId = intent.getStringExtra(Constant.EXTRA_KEY_OBJECT_ID);

        int flag = getIntent().getIntExtra(Constant.EXTRA_PICTIRE, -1);


        switch (flag) {
            case 1:
                takePic();
                break;
            case 2:
                usePic();
                break;
        }



        setContentView(R.layout.activity_photo);
        progressBar = (ProgressBar) findViewById(R.id.pgid);
        imageView = (ImageView) findViewById(R.id.imageID);


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

        Button backBtn = (Button)findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.menu_pic, menu);
////        return true;
//    }

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
    public void takePic() {

        String mediaStorageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).getPath();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File mediaFile = new File(mediaStorageDir + File.separator + "IMG_" + timeStamp + ".jpg");
        imageUri = Uri.fromFile(mediaFile);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mediaFile));
        this.startActivityForResult(takePictureIntent, Constant.FLAG_CAMERA);



//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
    }
    public void usePic() {
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(choosePictureIntent,Constant.FLAG_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constant.FLAG_CAMERA && resultCode == RESULT_OK) {

            //Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);


        }
        else if(requestCode == Constant.FLAG_GALLERY && resultCode == RESULT_OK) {

            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(imagePath, options);
            imageView.setImageBitmap(bitmap);

            cursor.close();
        }
    }
    public void save (View v){

        progressBar.setVisibility(View.VISIBLE);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();

        final ParseFile file = new ParseFile("picture.jpg", byteArray);

        file.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                final ParseObject picture = new ParseObject("Picture");
                picture.put("data", file);

                picture.put("uploader", ParseUser.getCurrentUser());


                ParseUser user = ParseUser.getCurrentUser();
                ParseObject truck = user.getParseObject("truck");
                truck.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject truck, ParseException e) {
                        picture.put("vendor", truck);
                        picture.saveInBackground();
                    }
                });


                Toast.makeText(getApplicationContext(), "uploaded", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);


            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer integer) {
                // progressBar.getProgress(integer);

            }
        });
    }



}