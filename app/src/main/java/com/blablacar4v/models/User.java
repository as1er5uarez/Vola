package com.blablacar4v.models;

public class User {
    private String email;
    private String password;
    private String direction;
    private String phone;
    private String name;
    private String photo;

    public User() {
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String direction, String password, String email, String name, String  phone, String photo)  {
        this.email = email;
        this.password = password;
        this.direction = direction;
        this.phone = phone;
        this.name = name;
        this.photo = photo;
    }

    public User(String direction, String email, String name, String  phone, String photo)  {
        this.email = email;
        this.direction = direction;
        this.phone = phone;
        this.name = name;
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public String getDirection() {
        return direction;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", direction='" + direction + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }


}
