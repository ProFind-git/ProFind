package com.example.profind;


public class profDataHelperClass
{
    String firstName,lastName,place,mobileNo,experience,about,category;


    public profDataHelperClass() {

    }

    public profDataHelperClass(String firstName, String lastName,String place ,String mobileNo, String experience, String about,String category) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.place=place;
        this.mobileNo = mobileNo;
        this.experience = experience;
        this.about = about;
        this.category=category.toLowerCase();

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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
    public String getCategory() {
        category=category.toLowerCase();
        return category;
    }

    public void setCategory(String category) {
        this.category = category.toLowerCase();
    }
}
