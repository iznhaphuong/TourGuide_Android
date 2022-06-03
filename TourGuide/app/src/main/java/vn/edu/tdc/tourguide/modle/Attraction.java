package vn.edu.tdc.tourguide.modle;

import android.widget.ImageView;

public class Attraction {
    private int id;
    private int city_id;
    private String title;
    private ImageView img;
    private float ratingValue;
    private int types;
    private String address;

    public Attraction(int id, int city_id, String title, float ratingValue, int types, String address) {
        this.id = id;
        this.city_id = city_id;
        this.title = title;
        this.ratingValue = ratingValue;
        this.types = types;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
