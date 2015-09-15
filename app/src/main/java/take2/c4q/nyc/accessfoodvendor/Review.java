package take2.c4q.nyc.accessfoodvendor;

import java.util.Date;

/**
 * Created by Hoshiko on 9/14/15.
 */
public class Review {


    private String title;
    private String description;
    private Date date;
    private int rating;
    private String writer;
    private String userPicUrl;

    public String getUserPicUrl() {
        return userPicUrl;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }



    public Review() {
    }

    public Review(String title, String description, Date date, int rating, String writer, String userPicUrl) {

        this.title = title;
        this.description = description;
        this.date = date;
        this.rating = rating;
        this.writer = writer;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

}
