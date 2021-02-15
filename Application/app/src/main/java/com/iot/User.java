package com.iot;

public class User {

    public String username;
    public String email;
    public String password;
    public String phone;
    public String devices;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email,String password,String phone,String devices) {
        this.username = username;
        this.email = email;
        this.password=password;
        this.phone=phone;
        this.devices=devices;
    }

}