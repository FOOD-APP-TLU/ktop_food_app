package com.example.ktop_food_app.App.model.Entity;

public class User {
    private int id; // Them truong id de dinh danh user
    private int img; // Luu resource id cua anh
    private String name;
    private String email;
    private String number;
    private String address;

    // Cap nhat constructor de them truong id
    public User(int id, int img, String name, String email, String number, String address) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.email = email;
        this.number = number;
        this.address = address;
    }

    // Getters & Setters
    // Getter va Setter cho truong id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}