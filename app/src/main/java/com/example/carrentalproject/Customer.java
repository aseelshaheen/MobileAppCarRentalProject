package com.example.carrentalproject;

public class Customer {
    private int idNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String licenseImage;

    // Constructor
    public Customer(int idNumber, String firstName, String lastName, String email, String phoneNumber, String password, String licenseImage) {
        this.idNumber = idNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.licenseImage = licenseImage;
    }

    // Getters and Setters
    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLicenseImage() {
        return licenseImage;
    }

    public void setLicenseImage(String licenseImage) {
        this.licenseImage = licenseImage;
    }

    // toString method
    @Override
    public String toString() {
        return "Customer{" +
                "idNumber=" + idNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", licenseImage='" + licenseImage + '\'' +
                '}';
    }
}
