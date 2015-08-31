package take2.c4q.nyc.accessfoodvendor;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseTwitterUtils;

public class ParseApplication extends Application {

    private static ParseApplication sInstance;

    public static ParseApplication getInstance() {
        return sInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        Parse.initialize(this);
        ParseTwitterUtils.initialize("xlSQblNeZ1cR07HRZiGqPfa4t", "IsuirBTeqi3EWOs7kYGMyYzThaiba78sCXYLX3q5G4Ie6PdffJ");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }
}