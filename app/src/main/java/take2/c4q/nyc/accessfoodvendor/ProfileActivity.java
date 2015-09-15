package take2.c4q.nyc.accessfoodvendor;

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
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageViewProfile;

//    private Toolbar mToolbar;

    private LinearLayout mButtonRatings;
    private LinearLayout mButtonReviews;
    private LinearLayout mButtonFollowers;
    private TextView mTextViewRating;
    private TextView mTextViewReviews;
    private TextView mTextViewFavorite;

    private RecyclerView mRecyclerView;
//    private UserReviewAdapter mAdapter;

    private CollapsingToolbarLayout mToolbarLayout;
    private RecyclerView mRecyclerViewPictures;
    private PicturesAdapter mPicturesAdapter;

//    String truckId;

    private RecyclerView mRecyclerView2;
    private UserReviewAdapter mAdapter;
    private Toolbar mToolbar;
    String truckId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final ParseUser me = ParseUser.getCurrentUser();
        ParseObject truck = me.getParseObject("truck");
        truckId = truck.getObjectId();

        String name = truck.getString("name");

        TextView truckNameTop = (TextView)findViewById(R.id.truck_name_top);
        truckNameTop.setText(name);
        TextView truckAddress = (TextView)findViewById(R.id.truck_address);
        truckAddress.setText("Address: " + truck.getString("address"));

        mToolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        mToolbar.setTitle(name);
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(name + " - Reviews");
        mToolbarLayout.setTitle(name + " - Reviews");
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);



//        mImageViewProfile = (ImageView) findViewById(R.id.imageView_profile);
        mImageViewProfile = (ImageView)findViewById(R.id.imageView_profile);
        String truckPic = truck.getString("profile_url");
        if(truckPic!=null && !truckPic.equals("")) {
            Picasso.with(getApplicationContext()).load(truckPic).into(mImageViewProfile);
        }
        mButtonRatings = (LinearLayout) findViewById(R.id.button_rating);
        mButtonReviews = (LinearLayout) findViewById(R.id.button_user_reviews);
        mButtonFollowers = (LinearLayout) findViewById(R.id.button_profile_favorite);


        // Font path
//        String fontPath = "fonts/Quicksand-Regular.ttf";

        mTextViewFavorite = (TextView) findViewById(R.id.profile_number_favorite);
        mTextViewRating = (TextView) findViewById(R.id.profile_number_rating);
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




        mRecyclerView2 = (RecyclerView) findViewById(R.id.recyclerView_user_reviews);
        mRecyclerView2.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView2.setLayoutManager(lm);

        mAdapter = new UserReviewAdapter(getApplicationContext());
        mRecyclerView2.setAdapter(mAdapter);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        final String today = "day" + Integer.toString(day);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Review");

        query.whereEqualTo("vendor", truck).findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
//                if(list.size()!=0){
                    for (final ParseObject review : list) {

                        final Review item = new Review();
                        item.setTitle(review.getString("title"));
                        item.setDescription(review.getString("description"));
                        item.setRating(review.getInt("rating"));
                        item.setDate(review.getCreatedAt());
                        ParseUser reviewer = review.getParseUser("writer");
                        String username = null;
                        String userPicUrl =null;
                        try {
                            String userFirstname = reviewer.fetchIfNeeded().getString("first_name");
                            String userLasttname = reviewer.fetchIfNeeded().getString("last_name");

                            if (userFirstname != null && !userFirstname.equals("")){
                                username = userFirstname + " " +userLasttname;
                            }else{
                                username = reviewer.getString("username");
                            }

                            userPicUrl = reviewer.fetchIfNeeded().getString("profile_url");


                        } catch (ParseException e1) {
                            e1.printStackTrace();

                        }
                        item.setWriter(username);

                        item.setUserPicUrl(userPicUrl);

                        mAdapter.addReview(item);

                    }
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



        ParseQuery<ParseObject> favorites = ParseQuery.getQuery("Favorite");
        favorites.whereEqualTo("vendor", truck).findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                mTextViewFavorite.setText(list.size() + "");
            }
        });

        DecimalFormat df = new DecimalFormat("#0.0");
        double rate = truck.getInt("rating");
        String rating = df.format(rate);

        mTextViewRating.setText(rating);

        ParseQuery<ParseObject> reviewQuery = ParseQuery.getQuery("Review");
        reviewQuery.whereEqualTo("vendor", truck).countInBackground(new CountCallback() {
            @Override
            public void done(int i, ParseException e) {
                mTextViewReviews.setText(i + "");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        setUpListeners(false);
    }

    @Override
    public void onClick(View v) {

    }

    private void setUpListeners(boolean isResumed) {
        if (isResumed) {
//            mButtonRatings.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                }
//            });
//            mButtonReviews.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getApplicationContext(), UserReviewActivity.class);
//                    startActivity(intent);
//                }
//            });
//            mButtonFollowers.setOnClickListener(this);
        } else {

            mButtonRatings.setOnClickListener(null);
            mButtonReviews.setOnClickListener(null);
            mButtonFollowers.setOnClickListener(null);
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
