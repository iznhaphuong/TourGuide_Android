package vn.edu.tdc.tourguide.model;

public class Comments {

    private String user_name;
    private int user_rating;
    private int user_image;
    private String date;
    private String another_cmt;

    // Constructor
    public Comments(String user_name, int user_image, String date, String another_cmt, int user_rating) {
        this.user_name = user_name;
        this.user_image = user_image;
        this.date = date;
        this.another_cmt = another_cmt;
        this.user_rating = user_rating;
    }

    public Comments(){

    }

    //Getter and Setter
    public String getAnother_cmt() {
        return another_cmt;
    }

    public void setAnother_cmt(String another_cmt) {
        this.another_cmt = another_cmt;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(int user_rating) {
        this.user_rating = user_rating;
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