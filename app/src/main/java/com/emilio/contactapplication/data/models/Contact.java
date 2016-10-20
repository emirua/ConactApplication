package com.emilio.contactapplication.data.models;

/**
 * Created by Emilio on 17/10/2016.
 */

public class Contact {
    private String name;
    private String number;
    private String image;

    public Contact(String name, String number, String image) {
        this.name = name;
        this.number = number;
        this.image = image;
    }

    public Contact() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getImage() {
        return image;
    }
}
