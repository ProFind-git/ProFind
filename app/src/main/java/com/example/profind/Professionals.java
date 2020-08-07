package com.example.profind;

public class Professionals {

    public String firstName,category,mobileNo,city;

    public Professionals() {
    }



    public Professionals(String firstName, String mobileNo, String category,String city) {
        this.firstName = firstName;
        this.category = category;
        this.mobileNo= mobileNo;
        this.city= city;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCategory() {
        category=category.toLowerCase();
        return category;
    }

    public void setCategory(String category) {

        this.category = category.toLowerCase();
    }
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
