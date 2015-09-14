package take2.c4q.nyc.accessfoodvendor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageViewProfile;

    private Toolbar mToolbar;

    private LinearLayout mButtonFriends;
    private LinearLayout mButtonReviews;
    private LinearLayout mButtonFavorite;
    private TextView mTextViewFriends;
    private TextView mTextViewReviews;
    private TextView mTextViewFavorite;

    private RecyclerView mRecyclerView;
//    private UserReviewAdapter mAdapter;

    private CollapsingToolbarLayout mToolbarLayout;
    private RecyclerView mRecyclerViewPictures;
    private PicturesAdapter mPicturesAdapter;

    String truckId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final ParseUser me = ParseUser.getCurrentUser();
        ParseObject truck = me.getParseObject("truck");
        truckId = truck.getObjectId();


        mToolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String name = truck.getString("name");

        getSupportActionBar().setTitle(name + "'s Profile");
        mToolbarLayout.setTitle(name + "'s Profile");
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);



        //  mImageViewProfile = (ImageView) findViewById(R.id.imageView_profile);
//        mImageViewProfile = (ImageView)findViewById(R.id.imageView_profile);

        mButtonFriends = (LinearLayout) findViewById(R.id.button_friends_list);
        mButtonReviews = (LinearLayout) findViewById(R.id.button_user_reviews);
        mButtonFavorite = (LinearLayout) findViewById(R.id.button_profile_favorite);


        // Font path
//        String fontPath = "fonts/Quicksand-Regular.ttf";

        mTextViewFavorite = (TextView) findViewById(R.id.profile_number_favorite);
        mTextViewFriends = (TextView) findViewById(R.id.profile_number_followers);
        mTextViewReviews = (TextView) findViewById(R.id.profile_number_reviews);


        mRecyclerViewPictures = (RecyclerView) findViewById(R.id.recyclerView_details_pictures);
        LinearLayoutManager lm2 = new LinearLayoutManager(ProfileActivity.this);
        lm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewPictures.setLayoutManager(lm2);
        mPicturesAdapter = new PicturesAdapter(ProfileActivity.this);
        mRecyclerViewPictures.setAdapter(mPicturesAdapter);



        ParseQuery<ParseObject> pictures = ParseQuery.getQuery("Picture");
        pictures.whereEqualTo("vendor", truck).findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list.size() != 0) {
                    mPicturesAdapter = new PicturesAdapter(ProfileActivity.this, list);
                    mRecyclerViewPictures.setAdapter(mPicturesAdapter);
//                    mVendorPicImage.setVisibility(View.GONE);
                    mRecyclerViewPictures.setVisibility(View.VISIBLE);


                }
            }
        });


    }






    @Override
    protected void onResume() {
        super.onResume();
        setUpListeners(true);

        ParseUser me = ParseUser.getCurrentUser();
        ParseObject truck = me.getParseObject("truck");

        ParseRelation<ParseUser> followerRelation = truck.getRelation("followers");
        followerRelation.getQuery().countInBackground(new CountCallback() {
            @Override
            public void done(int i, ParseException e) {
                mTextViewFriends.setText(i + "");
            }
        });

        ParseQuery<ParseObject> favorites = ParseQuery.getQuery("Favorite");
        favorites.whereEqualTo("vendor", truck).findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                mTextViewFavorite.setText(list.size() + "");
            }
        });


        ParseQuery<ParseObject> reviewQuery = ParseQuery.getQuery("Review");
        reviewQuery.whereEqualTo("vendor", truck).countInBackground(new CountCallback() {
            @Override
            public void done(int i, ParseException e) {
                mTextViewReviews.setText(i + "");
            }
        });

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        setUpListeners(false);
//    }

    @Override
    public void onClick(View v) {

    }

    private void setUpListeners(boolean isResumed) {
        if (isResumed) {
            mButtonFriends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
            mButtonReviews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
            mButtonFavorite.setOnClickListener(this);
        } else {

            mButtonFriends.setOnClickListener(null);
            mButtonReviews.setOnClickListener(null);
            mButtonFavorite.setOnClickListener(null);
        }
    }


//    private void logOut() {
//        LoginManager.getInstance().logOut(); // facebook logout
//        ParseUser.logOut();
//        Toast.makeText(getApplicationContext(), "Successfully logged out!", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.button_profile_favorite:
//                Intent intent = new Intent(getApplicationContext(), UserFavoriteActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }




}
