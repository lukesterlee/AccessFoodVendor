//package take2.c4q.nyc.accessfoodvendor;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//
//import com.parse.FindCallback;
//import com.parse.ParseException;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;
//import com.parse.ParseUser;
//
//import java.util.Calendar;
//import java.util.List;
//
///**
// * Created by Hoshiko on 9/14/15.
// */
//public class UserReviewActivity extends AppCompatActivity {
//
//    private RecyclerView mRecyclerView;
//    private UserReviewAdapter mAdapter;
//    private Toolbar mToolbar;
//    String truckId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_review);
////        mToolbar = (Toolbar) findViewById(R.id.toolbar_user_reviews);
////        setSupportActionBar(mToolbar);
//        ParseUser me = ParseUser.getCurrentUser();
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ParseObject truck = me.getParseObject("truck");
//        truckId = truck.getObjectId();
////        getSupportActionBar().setTitle(truck.getString("name"));
//
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_user_reviews);
//        mRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager lm = new LinearLayoutManager(this);
//        lm.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(lm);
//
//        mAdapter = new UserReviewAdapter(getApplicationContext());
//        mRecyclerView.setAdapter(mAdapter);
//
//        Calendar calendar = Calendar.getInstance();
//        int day = calendar.get(Calendar.DAY_OF_WEEK);
//        final String today = "day" + Integer.toString(day);
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Review");
//
//        query.whereEqualTo("vendor", truck).findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, ParseException e) {
//                if (e == null) {
////                if(list.size()!=0){
//                    for (final ParseObject review : list) {
//
////                        final ParseObject vendor = review.getParseObject("vendor");
////
////                        HashMap<String, Object> params = new HashMap<String, Object>();
////                        params.put("vendor", vendor);
////                        ParseCloud.callFunctionInBackground("averageRatings", params, new FunctionCallback<Float>() {
////                            @Override
////                            public void done(Float rate, ParseException e) {
////                                if (rate == null) {
////                                    rate = 4.0f;
////                                }
//
//                                final Review item = new Review();
//                                item.setTitle(review.getString("title"));
//                                item.setDescription(review.getString("description"));
//                                item.setRating(review.getInt("rating"));
//                                item.setDate(review.getCreatedAt());
//                                ParseUser reviewer = review.getParseUser("writer");
//                        String username = null;
//                        String userPicUrl =null;
//                        try {
//                            String userFirstname = reviewer.fetchIfNeeded().getString("first_name");
//                            String userLasttname = reviewer.fetchIfNeeded().getString("last_name");
//
//                            if (userFirstname != null && !userFirstname.equals("")){
//                                username = userFirstname + " " +userLasttname;
//                            }else{
//                                username = reviewer.getString("username");
//                            }
//
//                            userPicUrl = reviewer.fetchIfNeeded().getString("profile_url");
//
//
//                        } catch (ParseException e1) {
//                            e1.printStackTrace();
//
//                        }
//                        item.setWriter(username);
//
////                        if(userPicUrl != null && !userPicUrl.equals("")){
////                            item.setUserPicUrl(userPicUrl);
////                        }else{
////                            item.setUserPicUrl(null);
////                        }
//                        item.setUserPicUrl(userPicUrl);
//
//
////                                item.setVendor(truck);
//                                mAdapter.addReview(item);
//
////                            }
////                        });
//
//                    }
//                }
//
//
//            }
//        });
//
//    }
//
//
//}
