package com.example.reservationrestaurant.Model;

public class User {
    private String Name;
    private String Password;
    private String Phone;

    private String Role;
    public User() {}

    public User(String name, String password) {
        Name = name;
        Password = password;
        Role = "Client";
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }



}
