package com.example.mailchat.Models;

import java.util.HashMap;

public class Users {

    public static HashMap<Object, String> userInfo = new HashMap<>();
    public static HashMap<Object, String> businessUserInfo = new HashMap<>();

    public static HashMap<Object, String> businessCompanyInfo = new HashMap<>();

    private String Name;
    private String Gender;
    private String MailchatID;
    private String DateOfBirth;
    private String Email;
    private String Phone;
    private String LastName;


    public Users(){

    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
    public Users(String name, String lastName, String phone) {
        Name = name;
        LastName = lastName;
        Phone = phone;

    }

    public Users(String name,String lastName, String gender, String mailchatID, String email, String phone) {
        Name = name;
        LastName = lastName;
        Gender = gender;
        MailchatID = mailchatID;
        Email = email;
        Phone = phone;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMailchatID() {
        return MailchatID;
    }

    public void setMailchatID(String mailchatID) {
        MailchatID = mailchatID;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}

