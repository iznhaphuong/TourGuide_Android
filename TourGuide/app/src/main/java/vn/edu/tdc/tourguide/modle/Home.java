package vn.edu.tdc.tourguide.modle;

import android.widget.ImageView;

public class Home {
    private int id;
    private String name;
    private ImageView img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public Home(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
