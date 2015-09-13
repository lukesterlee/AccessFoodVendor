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

    private String objectId;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        objectId = getArguments().getString(Constant.EXTRA_KEY_OBJECT_ID);
//        isYelp = getArguments().getBoolean(Constants.EXTRA_KEY_IS_YELP);


        builder.setTitle("Pics")
                .setPositiveButton("Take Pic", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        Intent intent = new Intent(getActivity(), PhotoActivity.class);
                        intent.putExtra(Constant.EXTRA_PICTIRE, Constant.FLAG_CAMERA);
                        intent.putExtra(Constant.EXTRA_KEY_OBJECT_ID, objectId);
//                        intent.putExtra(Constants.EXTRA_KEY_IS_YELP, isYelp);
                        startActivity(intent);
                    }
                })
        .setNegativeButton("Camera Roll", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), PhotoActivity.class);
                intent.putExtra(Constant.EXTRA_PICTIRE, Constant.FLAG_GALLERY);
                intent.putExtra(Constant.EXTRA_KEY_OBJECT_ID, objectId);
//                        intent.putExtra(Constants.EXTRA_KEY_IS_YELP, isYelp);
                startActivity(intent);
            }
        });
        return builder.create();
    }
}

