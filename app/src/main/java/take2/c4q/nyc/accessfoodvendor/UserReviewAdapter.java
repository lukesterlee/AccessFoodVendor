package take2.c4q.nyc.accessfoodvendor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoshiko on 9/14/15.
 */
public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewAdapter.UserReviewHolder> {

    private Context mContext;
    private List<Review> mList;

    public UserReviewAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    @Override
    public UserReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.list_item_user_review, parent, false);
        return new UserReviewHolder(row);
    }

    @Override
    public void onBindViewHolder(UserReviewHolder holder, int position) {

        DecimalFormat df = new DecimalFormat("#0.0");
        Review review = getItem(position);

        final ParseUser me = ParseUser.getCurrentUser();
        ParseObject vendor = me.getParseObject("truck");


//        holder.vendorName.setText(vendor.getString("name"));
//        holder.address.setText(vendor.getString("address"));
//        Picasso.with(mContext).load(vendor.getPicture()).into(holder.vendorPicture);
//
//        double rate = vendor.getInt("rating");
//        String rating = df.format(rate);
//        holder.rating.setText(rating);





//        holder.yelpLogo.setVisibility(View.GONE);



        holder.title.setText(review.getTitle());
        holder.description.setText(review.getDescription());
        holder.userRatings.setText(String.valueOf(review.getRating()));
        holder.writerName.setText(review.getWriter());

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(review.getDate());
        holder.date.setText(date);

        String userpicurl = review.getUserPicUrl();

        try {
            if(userpicurl==null || userpicurl.equals(""))
                Picasso.with(mContext).load(R.drawable.default_profile).into(holder.writerPicture);
            else
            Picasso.with(mContext).load(userpicurl).into(holder.writerPicture);
        } catch (Exception e) {
//            Picasso.with(mContext).load(R.drawable.default_profile).into(holder.writerPicture);
        }


    }

    public void addReview(Review review) {
        mList.add(review);
        notifyItemInserted(mList.size() - 1);
        notifyItemChanged(mList.size() - 1);
    }

    public Review getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class UserReviewHolder extends RecyclerView.ViewHolder {

        protected ImageView vendorPicture;
        protected TextView vendorName;
        protected TextView address;
        protected TextView rating;
        protected TextView hour;
        protected TextView userRatings;
        protected ImageView writerPicture;
        protected TextView date;
        protected TextView title;
        protected TextView description;
        protected TextView writerName;
        protected ImageView yelpLogo;

        public UserReviewHolder(View itemView) {
            super(itemView);

            userRatings = (TextView) itemView.findViewById(R.id.user_review_how_many);
            date = (TextView) itemView.findViewById(R.id.user_review_date);
            title = (TextView) itemView.findViewById(R.id.user_review_title);
            description = (TextView) itemView.findViewById(R.id.user_review_description);
            writerPicture = (ImageView) itemView.findViewById(R.id.user_review_user_picture);
            writerName = (TextView)itemView.findViewById(R.id.user_review_writer);


//            vendorName = (TextView) itemView.findViewById(R.id.vendor_name);

//            address = (TextView) itemView.findViewById(R.id.textView_address);
//            rating = (TextView) itemView.findViewById(R.id.vendor_rating);
//            hour = (TextView) itemView.findViewById(R.id.textView_hour);
//            vendorPicture = (ImageView) itemView.findViewById(R.id.imageView_vendor);

        }
    }
}

