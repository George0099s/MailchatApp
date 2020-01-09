package com.example.mailchat.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class User implements Parcelable {

    public static HashMap<Object, String> userInfo = new HashMap<>();
    public static HashMap<Object, String> businessUserInfo = new HashMap<>();

    public static HashMap<Object, String> businessCompanyInfo = new HashMap<>();

    private String name;
    private String gender;
    private String MailchatID;
    private String dateOfBirth;
    private String Email;
    private String phone;
    private String lastName;
    private String userId;
    private String imageURL;
    private String city;
    public User(){

    }

    protected User(Parcel in) {
        name = in.readString();
        gender = in.readString();
        MailchatID = in.readString();
        dateOfBirth = in.readString();
        Email = in.readString();
        phone = in.readString();
        lastName = in.readString();
        userId = in.readString();
        imageURL = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public User(String name, String lastName, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;

    }

    public User(String name, String lastName, String gender, String mailchatID, String email, String phone, String imageURL) {
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        MailchatID = mailchatID;
        Email = email;
        this.phone = phone;
        this.imageURL = imageURL;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMailchatID() {
        return MailchatID;
    }

    public void setMailchatID(String mailchatID) {
        MailchatID = mailchatID;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(MailchatID);
        dest.writeString(dateOfBirth);
        dest.writeString(Email);
        dest.writeString(phone);
        dest.writeString(lastName);
        dest.writeString(userId);
        dest.writeString(imageURL);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

