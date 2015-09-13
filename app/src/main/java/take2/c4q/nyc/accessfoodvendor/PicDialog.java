package take2.c4q.nyc.accessfoodvendor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Hoshiko on 9/8/15.
 */
public class PicDialog extends DialogFragment {

    private String truckId;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Base_Theme_AppCompat_Light_Dialog);
        truckId = getArguments().getString("truckId");
//        isYelp = getArguments().getBoolean(Constants.EXTRA_KEY_IS_YELP);


        builder.setTitle("Pics")
                .setPositiveButton("Take Pic", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        Intent intent = new Intent(getActivity(), PhotoActivity.class);
                        intent.putExtra(Constant.EXTRA_PICTIRE, Constant.FLAG_CAMERA);
                        intent.putExtra("truckId", truckId);
//                        intent.putExtra(Constants.EXTRA_KEY_IS_YELP, isYelp);
                        startActivity(intent);
                    }
                })
        .setNegativeButton("Camera Roll", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), PhotoActivity.class);
                intent.putExtra(Constant.EXTRA_PICTIRE, Constant.FLAG_GALLERY);
                intent.putExtra("truckId", truckId);
//                        intent.putExtra(Constants.EXTRA_KEY_IS_YELP, isYelp);
                startActivity(intent);
            }
        });
        return builder.create();
    }
}

