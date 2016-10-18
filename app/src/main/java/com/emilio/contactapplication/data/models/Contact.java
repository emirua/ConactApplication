package com.emilio.contactapplication.data.models;

/**
 * Created by Emilio on 17/10/2016.
 */

public class Contact {
    private String name;
    private long number;
    private String image;

    public Contact(String name, long number, String image) {
        this.name = name;
        this.number = number;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public long getNumber() {
        return number;
    }

    public String getImage() {
        return image;
    }
}
