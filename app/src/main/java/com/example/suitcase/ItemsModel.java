package com.example.suitcase;

import android.net.Uri;

public class ItemsModel {
    private int id;
    private String name;
    private String description;
    private double price;
    private Uri image;
    private boolean purchased;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased)  {
        this.purchased = purchased;
    }
        public String toString () {
            return "Item{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", image=" + image +
                    ", price=" + price +
                    ", purchased=" + purchased +
                    '}';
        }
    }

