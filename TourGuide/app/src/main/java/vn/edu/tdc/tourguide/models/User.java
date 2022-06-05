package vn.edu.tdc.tourguide.models;

public class User {
    private String idUser, nameOfUser, email;

    public String getIdUser() {
        return idUser;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public User(String nameOfUser, String email) {
        this.nameOfUser = nameOfUser;
        this.email = email;
    }

    public User(String id, String nameOfUser, String email) {
        this.idUser = id;
        this.nameOfUser = nameOfUser;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser='" + idUser + '\'' +
                ", nameOfUser='" + nameOfUser + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
