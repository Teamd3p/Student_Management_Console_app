package com.tss.model;

public class Profile {
    private int id;
    private String phoneNumber;
    private String email;
    private String address;
    private int age;
    private String userType;
    private int userId;

    public Profile() {
    }

    public Profile(int id, String phoneNumber, String email, String address, int age, String userType, int userId) {
        setId(id);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setAddress(address);
        setAge(age);
        setUserType(userType);
        setUserId(userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType.trim().toLowerCase();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Profile [ID=" + id +
               ", Phone=" + phoneNumber +
               ", Email=" + email +
               ", Address=" + address +
               ", Age=" + age +
               ", UserType=" + userType +
               ", UserID=" + userId + "]";
    }
}
