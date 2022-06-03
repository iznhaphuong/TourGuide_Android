package vn.edu.tdc.tourguide.modle;

import java.util.Date;

public class EventSchedule {


    public EventSchedule(int id, String tenDiaDiem, String gioSuKien, String ngaySuKien, String ghiChu) {
        this.id = id;
        this.tenDiaDiem = tenDiaDiem;
        this.gioSuKien = gioSuKien;
        this.ngaySuKien = ngaySuKien;
        this.ghiChu = ghiChu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDiaDiem() {
        return tenDiaDiem;
    }

    public void setTenDiaDiem(String tenDiaDiem) {
        this.tenDiaDiem = tenDiaDiem;
    }

    public String getGioSuKien() {
        return gioSuKien;
    }

    public void setGioSuKien(String gioSuKien) {
        this.gioSuKien = gioSuKien;
    }

    public String getNgaySuKien() {
        return ngaySuKien;
    }

    public void setNgaySuKien(String ngaySuKien) {
        this.ngaySuKien = ngaySuKien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    private int id;
    private String tenDiaDiem;
    private String gioSuKien;
    private String ngaySuKien;
    private String ghiChu;
}
