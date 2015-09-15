package take2.c4q.nyc.accessfoodvendor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PushDialogFragment extends DialogFragment {

    private View mDialogView;

    private Button mEditTextPercent;
    private Button mEditTextDollar;
    private EditText mEditTextAmount;
    private EditText mEditTextExpiration;

    private boolean isPercent;
    private ParseObject mTruck;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseUser user = ParseUser.getCurrentUser();
        ParseObject truck = user.getParseObject("truck");
        truck.fetchInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null)
                    mTruck = parseObject;
            }
        });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        mDialogView = inflater.inflate(R.layout.dialog_coupon, null);

        mEditTextPercent = (Button) mDialogView.findViewById(R.id.percent);
        mEditTextDollar = (Button) mDialogView.findViewById(R.id.dollar);
        mEditTextAmount = (EditText) mDialogView.findViewById(R.id.amount);
        mEditTextExpiration = (EditText) mDialogView.findViewById(R.id.expire);

        mEditTextPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPercent = true;
                mEditTextPercent.setBackgroundResource(R.drawable.button_fill);
                mEditTextPercent.setTextColor(getResources().getColor(R.color.white));
                mEditTextDollar.setBackgroundResource(R.drawable.rounded_corner);
                mEditTextDollar.setTextColor(getResources().getColor(R.color.primaryColor));
            }
        });

        mEditTextDollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPercent = false;
                mEditTextPercent.setBackgroundResource(R.drawable.rounded_corner);
                mEditTextPercent.setTextColor(getResources().getColor(R.color.primaryColor));
                mEditTextDollar.setBackgroundResource(R.drawable.button_fill);
                mEditTextDollar.setTextColor(getResources().getColor(R.color.white));
            }
        });

}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Dialog_Theme);
        builder.setView(mDialogView);
        builder.setTitle("Send Coupons");
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (mEditTextAmount.getText() != null && mEditTextExpiration.getText() != null) {
                    String message;
                    String amount = mEditTextAmount.getText().toString();
                    String type;
                    if (isPercent) {
                        type = "%";
                        message = amount + type;
                    } else {
                        type = "$";
                        message = type + amount;
                    }

                    int days = Integer.valueOf(mEditTextExpiration.getText().toString());
                    Calendar today = Calendar.getInstance();
                    today.add(Calendar.DATE, days);
                    Date date = today.getTime();
                    DateFormat dateFormat = new SimpleDateFormat("MMddyyyy", Locale.US);
                    String expire = dateFormat.format(date);
                    String objectId = mTruck.getObjectId();
                    message += " off! Expires in " + days + " days";

                    JSONObject data = null;
                    try {
                        data = new JSONObject("{\"title\": \"" + mTruck.getString("name") + " sent a coupon!\"," +
                                "\"alert\": \"" + message + "\"," +
                                "\"type\": \"" + type + "\"," +
                                "\"amount\": \"" + amount + "\"," +
                                "\"vendor\": \"" + objectId + "\"," +
                                "\"expiration\": \"" + expire + "\"}");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ParsePush push = new ParsePush();
                    push.setChannel(objectId);
                    push.setData(data);
                    push.sendInBackground(new SendCallback() {
                        @Override
                        public void done(ParseException e) {
                            dialog.cancel();
                        }
                    });
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        return builder.create();
    }
}
