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
        if (id < 0) {
            throw new IllegalArgumentException("Profile ID cannot be negative.");
        }
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }

        String trimmed = phoneNumber.trim();
        if (!trimmed.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }

        this.phoneNumber = trimmed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
        this.address = address.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 1 || age > 120) {
            throw new IllegalArgumentException("Age must be between 1 and 120.");
        }
        this.age = age;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        if (userType == null || userType.trim().isEmpty()) {
            throw new IllegalArgumentException("User type cannot be null or empty.");
        }
        this.userType = userType.trim().toLowerCase();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than zero.");
        }
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
