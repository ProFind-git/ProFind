package com.example.profind;

public class Professionals {

    public String firstName,category;

    public Professionals() {
    }

    public Professionals(String firstName, String category) {
        this.firstName = firstName;
        this.category = category;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
