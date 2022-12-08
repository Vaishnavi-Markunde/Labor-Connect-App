package com.example.laborconnect.models;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class User  implements Serializable {

    @DocumentId
    private String docId;
    private String Name;
    private String Phone_No;
    private String Email;
    private String Gender;
    private String Profession;
    private String UID;
    String add1,add2,pincode,city,state;

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public User(String docId, String name, String phone_No, String email, String gender, String profession, String UID) {
        this.docId = docId;
        Name = name;
        Phone_No = phone_No;
        Email = email;
        Gender = gender;
        Profession = profession;
        this.UID = UID;
    }

    public User() {
    }

    public User(String docId, String name, String phone, String gender, String email, String prof) {
        this.docId = docId;
        this.Name = name;
        this.Phone_No = phone;

        this.Gender = gender;
        this.Email = email;
        this.Profession = prof;

    }

//    public Double getCurrentPositionLatitude() {
//        return currentPositionLatitude;
//    }

//    public void setCurrentPositionLatitude(Double currentPositionLatitude) {
//        this.currentPositionLatitude = currentPositionLatitude;
//    }
//
//    public Double getCurrentPositionLongitude() {
//        return currentPositionLongitude;
//    }

//    public void setCurrentPositionLongitude(Double currentPositionLongitude) {
//        this.currentPositionLongitude = currentPositionLongitude;
//    }

//    public boolean isCustomer(){
//        return this.role.equals("Customer");
//    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPhone_No() {
        return Phone_No;
    }

    public void setPhone_No(String phone) {
        this.Phone_No = phone;
    }

//    public Date getBirthDate() {
//        return birthDate;
//    }

//    public void setBirthDate(Date birthDate) {
//        this.birthDate = birthDate;
//    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        this.Profession = profession;
    }

}

//    public String getTransportationType() {
//        return transportationType;
//    }
//
//    public void setTransportationType(String transportationType) {
//        this.transportationType = transportationType;
//    }
//
//    public void setRating(List<Integer> rating) {
//        this.rating = rating;
//    }
//
//    public String getVehiclePlateNumber() {
//        return vehiclePlateNumber;
//    }
//
//    public void setVehiclePlateNumber(String vehiclePlateNumber) {
//        this.vehiclePlateNumber = vehiclePlateNumber;
//    }

