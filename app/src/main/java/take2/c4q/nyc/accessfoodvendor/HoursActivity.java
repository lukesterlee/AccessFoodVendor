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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hoshiko on 9/6/15.
 */
public class HoursActivity extends FragmentActivity {
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

    CheckBox rbMon;
    CheckBox rbTue;
    CheckBox rbWed;
    CheckBox rbThu;
    CheckBox rbFri;
    CheckBox rbSat;
    CheckBox rbSun;


    String truckId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours);

        Intent intent = getIntent();
        truckId= intent.getStringExtra("truckId");

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


        rbMon = (CheckBox) findViewById(R.id.rb_mon);
        rbTue = (CheckBox) findViewById(R.id.rb_tue);
        rbWed = (CheckBox) findViewById(R.id.rb_wed);
        rbThu = (CheckBox)findViewById(R.id.rb_thu);
        rbFri = (CheckBox) findViewById(R.id.rb_fri);
        rbSat = (CheckBox) findViewById(R.id.rb_sat);
        rbSun = (CheckBox) findViewById(R.id.rb_sun);



        loadSchedulefromParse();




        Button saveHoursBtn = (Button)findViewById(R.id.save_hours_btn);
        saveHoursBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Vendor");
                query.getInBackground(truckId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject currentVendor, ParseException e) {
                        if (e == null) {
                            if(rbMon.isChecked()){
                                currentVendor.put("day1", "Closed");
                            }else if (monOpen.getText().equals("") || monClose.getText().equals("")) {
                                currentVendor.put("day1", null);
                            } else {
                                currentVendor.put("day1",
                                        toJasonString((String.valueOf(monOpen.getText())),
                                                (String.valueOf((monClose.getText())))));
                            }


                            if(rbTue.isChecked()){
                                currentVendor.put("day2", "Closed");

                            }else if (tueOpen.getText().equals("") || tueClose.getText().equals("")) {
                                currentVendor.put("day2", null);
                            } else {
                                currentVendor.put("day2",
                                        toJasonString((String.valueOf(tueOpen.getText())),
                                                (String.valueOf((tueClose.getText())))));
                            }

                            if(rbWed.isChecked()){
                                currentVendor.put("day3", "Closed");

                            }else if (wedOpen.getText().equals("") || wedClose.getText().equals("")) {
                                currentVendor.put("day3", null);
                            } else {
                                currentVendor.put("day3",
                                        toJasonString((String.valueOf(wedOpen.getText())),
                                                (String.valueOf((wedClose.getText())))));
                            }


                            if(rbThu.isChecked()){
                                currentVendor.put("day4", "Closed");

                            }else if (thuOpen.getText().equals("") || thuClose.getText().equals("")) {
                                currentVendor.put("day4", null);
                            } else {
                                currentVendor.put("day4",
                                        toJasonString((String.valueOf(thuOpen.getText())),
                                                (String.valueOf((thuClose.getText())))));
                            }


                            if(rbFri.isChecked()){
                                currentVendor.put("day5", "Closed");

                            }else if (friOpen.getText().equals("") || friClose.getText().equals("")) {
                                currentVendor.put("day5", null);
                            } else {
                                currentVendor.put("day5",
                                        toJasonString((String.valueOf(friOpen.getText())),
                                                (String.valueOf((friClose.getText())))));
                            }


                            if(rbSat.isChecked()){
                                currentVendor.put("day6", "Closed");

                            }else if (satOpen.getText().equals("") || satClose.getText().equals("")) {
                                currentVendor.put("day6", null);
                            } else {
                                currentVendor.put("day6",
                                        toJasonString((String.valueOf(satOpen.getText())),
                                                (String.valueOf((satClose.getText())))));
                            }



                            if(rbSun.isChecked()){
                                currentVendor.put("day7", "Closed");

                            }if (sunOpen.getText().equals("") || sunClose.getText().equals("")) {
                                currentVendor.put("day7", null);
                            } else {
                                currentVendor.put("day7",
                                        toJasonString((String.valueOf(sunOpen.getText())),
                                                (String.valueOf((sunClose.getText())))));
                            }


                            currentVendor.saveInBackground();
                            Toast.makeText(HoursActivity.this, "Hours are saved!", Toast.LENGTH_SHORT).show();

                        }

                    }

                });

            }
        });

        Button skipToLocationBtn = (Button)findViewById(R.id.to_location_btn);
        skipToLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoursActivity.this, LocationActivity.class);
                intent.putExtra("truckId", truckId);
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

            if(timeStr==null && !timeStr.equals("")){
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
                if(minute<10) {
                    minStr = "0" + String.valueOf(minute);
                }else {
                    //TODO make sure to make 2 digits for minute all the time

                    minStr = String.valueOf(minute);
                }
            }

            String hourStr= "00";
            if (hourOfDay != 0) {
                if (hourOfDay < 10) {
                    hourStr = "0" + String.valueOf(hourOfDay);
                }
                else {

                    hourStr = String.valueOf(minute);
                }
            }

            editText.setText(editText.getText()+"" + hourStr + ":" + minStr);
        }


    }

    public void loadSchedulefromParse(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Vendor");
        query.getInBackground(truckId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject vendor, ParseException e) {
                if (e == null) {

                    String monHours = vendor.getString("day1");
                    setHours(monHours, monOpen, monClose, rbMon);

                    String tueHours = vendor.getString("day2");
                    setHours(tueHours, tueOpen, tueClose, rbTue);

                    String wedHours = vendor.getString("day3");
                    setHours(wedHours, wedOpen, wedClose, rbWed);

                    String thuHours = vendor.getString("day4");
                    setHours(thuHours, thuOpen, thuClose, rbThu);

                    String friHours = vendor.getString("day5");
                    setHours(friHours, friOpen, friClose, rbFri);

                    String satHours = vendor.getString("day6");
                    setHours(satHours, satOpen, satClose, rbSat);

                    String sunHours = vendor.getString("day7");
                    setHours(sunHours, sunOpen, sunClose, rbSun);

                }
            }

        });
    }

    public void setHours(String hour, TextView holder, TextView holder2, CheckBox checkBox){

            try {
                if (hour == null) {
                    return;
                }else if(hour.equals("Closed")){
                    checkBox.setChecked(!checkBox.isChecked());
                }else {

                    JSONObject info = new JSONObject(hour);

                    if (info != null && !info.equals("closed")) {
                        String opening = formatUsingColon(info.getString("openAt"));
                        String closing = formatUsingColon(info.getString("closeAt"));

                        holder.setText(opening);
                        holder2.setText(closing);
                    }
                }

            } catch (JSONException e1) {
                e1.printStackTrace();
            }


    }

    private String formatUsingColon(String hourMin) {
        if (hourMin == null || hourMin.length() < 4){

            return null;
        }
        String hours = hourMin.substring(0, 2);
        String min = hourMin.substring(2, 4);
        return hours + ":" + min;
    }

}
