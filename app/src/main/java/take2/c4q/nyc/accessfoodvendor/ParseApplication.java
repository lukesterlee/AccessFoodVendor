package take2.c4q.nyc.accessfoodvendor;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
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
		/*
		 * Add Parse initialization code here
		 */

        //Twitter
        Parse.initialize(this);
        ParseTwitterUtils.initialize("eaL1xnjYnll6Sofg1eutFHrRn", "L60RTqqiYw3iBL2vTNxuAu2NFers0KtLYbJdImgcvCHJHnL5nt");

        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);


    }
}


