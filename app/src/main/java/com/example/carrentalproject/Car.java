package com.example.carrentalproject;

public class Car {
    private int carID;
    private String carBrand;
    private String carModel;
    private int price;
    private String color;
    private String status;
    private String imagePath;

    public Car(String carBrand, String carModel, int price, String color, String status, String imagePath) {
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.price = price;
        this.color = color;
        this.status = status;
        this.imagePath = imagePath;
    }

    public Car(int carID, String carBrand, String carModel, int price, String color, String status, String imagePath) {
        this.carID = carID;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.price = price;
        this.color = color;
        this.status = status;
        this.imagePath = imagePath;
    }

    public Car(){

    }

    public Car(String brand, String model, String imageUrl) {
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.imagePath = imagePath;
    }


    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    @Override
    public String toString() {
        return  "Car ID: "+carID +"\n"+
                "Brand: " + carBrand + "\n" +
                "Model: " + carModel + "\n" +
                "Price: " + price + "\n" +
                "Color: " + color + "\n" +
                "Status: " + status;
    }
}
