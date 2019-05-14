package com.example.dungaa;

public class Category {
    public String image;
    public String names;

    public Category() {
    }

    public Category(String image, String names) {
        this.image = image;
        this.names = names;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
