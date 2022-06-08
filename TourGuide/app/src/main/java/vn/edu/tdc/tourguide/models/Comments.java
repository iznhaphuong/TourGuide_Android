package vn.edu.tdc.tourguide.models;

public class Comments {

    private String user_name;


    public float getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(float user_rating) {
        this.user_rating = user_rating;
    }

    private float user_rating;
    private int user_image;
    private String date;
    private String reviews;

    public Comments(String user_name, float user_rating, String date, String reviews) {
        this.user_name = user_name;
        this.user_rating = user_rating;
        this.date = date;
        this.reviews = reviews;
    }

    // Constructor
    public Comments(String user_name, int user_image, String date, String reviews, float user_rating) {
        this.user_name = user_name;
        this.user_image = user_image;
        this.date = date;
        this.reviews = reviews;
        this.user_rating = user_rating;
    }

    public Comments(){

    }

    //Getter and Setter
    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public int getUser_image() {
        return user_image;
    }

    public void setUser_image(int user_image) {
        this.user_image = user_image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}