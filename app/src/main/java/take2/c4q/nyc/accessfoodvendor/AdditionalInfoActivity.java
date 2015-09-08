package take2.c4q.nyc.accessfoodvendor;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Hoshiko on 9/6/15.
 */
public class AdditionalInfoActivity extends FragmentActivity {
//    static EditText DateEdit;
    EditText monOpen;
    EditText monClose;
    EditText tueOpen;
    EditText tueClose;
    EditText wedOpen;
    EditText wedClose;
    EditText thuOpen;
    EditText thuClose;
    EditText friOpen;
    EditText friClose;
    EditText satOpen;
    EditText satClose;
    EditText sunOpen;
    EditText sunClose;
    String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);

        Intent intent = getIntent();
        objectId= intent.getStringExtra("objectId");

        monOpen = (EditText) findViewById(R.id.mon_open);
        monOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });

        monClose = (EditText) findViewById(R.id.mon_close);
        monClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });

        tueOpen = (EditText) findViewById(R.id.tue_open);
        tueOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });


        tueClose = (EditText) findViewById(R.id.tue_close);
        tueClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });

        wedOpen = (EditText) findViewById(R.id.wed_open);
        wedOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });


        wedClose = (EditText) findViewById(R.id.wed_close);
        wedClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });

        thuOpen = (EditText) findViewById(R.id.thu_open);
        thuOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });


        thuClose = (EditText) findViewById(R.id.thu_close);
        thuClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });

        friOpen = (EditText) findViewById(R.id.fri_open);
        friOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });


        friClose = (EditText) findViewById(R.id.fri_close);
        friClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });

        satOpen = (EditText) findViewById(R.id.sat_open);
        satOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });


        satClose = (EditText) findViewById(R.id.sat_close);
        satClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });

        sunOpen = (EditText) findViewById(R.id.sun_open);
        sunOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });


        sunClose = (EditText) findViewById(R.id.sun_close);
        sunClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog(v);

            }
        });

        Button saveHoursBtn = (Button)findViewById(R.id.save_hours_btn);
        saveHoursBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Vendor");
                query.getInBackground(objectId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject currentVendor, ParseException e) {
                        if (e == null) {
                            currentVendor.put("day1",
                                    toJasonString((String.valueOf(monOpen.getText())),
                                            (String.valueOf((monClose.getText())))));

                            currentVendor.put("day2",
                                    toJasonString((String.valueOf(tueOpen.getText())),
                                            (String.valueOf((tueClose.getText())))));

                            currentVendor.put("day3",
                                    toJasonString((String.valueOf(wedOpen.getText())),
                                            (String.valueOf((wedClose.getText())))));

                            currentVendor.put("day4",
                                    toJasonString((String.valueOf(thuOpen.getText())),
                                            (String.valueOf((thuClose.getText())))));

                            currentVendor.put("day5",
                                    toJasonString((String.valueOf(friOpen.getText())),
                                            (String.valueOf((friClose.getText())))));

                            currentVendor.put("day6",
                                    toJasonString((String.valueOf(satOpen.getText())),
                                            (String.valueOf((satClose.getText())))));

                            currentVendor.put("day7",
                                    toJasonString((String.valueOf(sunOpen.getText())),
                                            (String.valueOf((sunClose.getText())))));


                            currentVendor.saveInBackground();

                            Intent intent = new Intent(AdditionalInfoActivity.this, LocationActivity.class);
                            intent.putExtra("objectId", objectId);
                            startActivity(intent);

                        }
                    }

                });

            }
        });

        Button skipToLocationBtn = (Button)findViewById(R.id.to_location_btn);
        skipToLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdditionalInfoActivity.this, LocationActivity.class);
                intent.putExtra("objectId", objectId);
                startActivity(intent);
            }
        });
    }

    public String toJasonString(String time1, String time2){

        String time1tofeed = time1.replaceAll(":", "");
        String time2tofeed = time2.replaceAll(":", "");

        String result = "{   \n" +
                "    \"openAt\" : \""+ time1tofeed +"\",\n" +
                "    \"closeAt\" : \""+ time2tofeed +"\"\n" +
                "}";

        return result;

    }



    public void showTimePickerDialog(View v) {
      EditText editText= (EditText)v;
        DialogFragment newFragment = new TimePickerFragment(editText);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        EditText editText;

        public TimePickerFragment(EditText editText) {
            this.editText = editText;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
//            final Calendar c = Calendar.getInstance();
//            int hour = c.get(Calendar.HOUR_OF_DAY);
//            int minute = c.get(Calendar.MINUTE);

            int hour;
            int minute;
            String timeStr = String.valueOf(editText.getText());

            if(!timeStr.equals("")){
                String[] parts = timeStr.split(":");
                String hourstr = parts[0];
                String minstr = parts[1];

                hour = Integer.parseInt(hourstr);
                minute = Integer.parseInt(minstr);

            } else {

                hour = 12;
                minute = 00;

            }

//             Create a new instance of TimePickerDialog and return it
//            return new TimePickerDialog(
//                    new ContextThemeWrapper(getActivity(),R.style.Dialog), this, hour, minute,
//                    DateFormat.is24HourFormat(getActivity()));

             return new TimePickerDialog(
                    getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));

        }

        public void setEditText(EditText editText){
            this.editText = editText;
        }

        public EditText getEditText(){
           return editText;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            editText.setText("");
            // Do something with the time chosen by the user
            String minStr = "00";
            if (minute != 0){
                minStr = String.valueOf(minute);
            }

            editText.setText(editText.getText()+"" + hourOfDay + ":" + minStr);
        }


    }

}
