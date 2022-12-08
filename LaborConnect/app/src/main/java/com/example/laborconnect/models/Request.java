package com.example.laborconnect.models;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import com.google.firebase.database.ServerValue;

import java.io.Serializable;

public class Request implements Serializable {

    String name;
    String timespan;
    String addline1;
    String addline2;
    String city;
    String state;
    String pincode;
    String desc_title;
    String description;
    String worker_docid;
    String phone_No;
    String service;
    String uid;
    String address;
    String wid;
    String status;
    String worker_name;
    String worker_phnNo;
  //  Timestamp timestamp;

//    public Timestamp getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(Timestamp timestamp) {
//        this.timestamp = timestamp;
//    }

//    Timestamp acceptedTimestamp;
    //req doc id
    String id;
    // user doc id
    String user_docid;

    String UserReqId;

//    public Timestamp getAcceptedTimestamp() {
//        return acceptedTimestamp;
//    }
//
//    public void setAcceptedTimestamp(Timestamp acceptedTimestamp) {
//        this.acceptedTimestamp = acceptedTimestamp;
//    }


    public Request(String name, String timespan, String addline1, String addline2, String city, String state, String pincode, String desc_title, String description, String worker_docid, String phone_No, String service, String uid, String address, String wid, String status, String worker_name, String worker_phnNo, String id, String user_docid, String userReqId) {
        this.name = name;
        this.timespan = timespan;
        this.addline1 = addline1;
        this.addline2 = addline2;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.desc_title = desc_title;
        this.description = description;
        this.worker_docid = worker_docid;
        this.phone_No = phone_No;
        this.service = service;
        this.uid = uid;
        this.address = address;
        this.wid = wid;
        this.status = status;
        this.worker_name = worker_name;
        this.worker_phnNo = worker_phnNo;
        this.id = id;
        this.user_docid = user_docid;
        UserReqId = userReqId;
    }

//   public Request(String name, Timestamp timestamp, String timespan, String addline1, String addline2, String city, String state, String pincode, String desc_title, String description, String worker_docid, String phone_No, String service, String uid, String address, String wid, String status, String worker_name, String worker_phnNo, Timestamp acceptedTimestamp, String id, String user_docid, String userReqId) {
//        this.name = name;
//      //  this.timestamp = timestamp;
//        this.timespan = timespan;
//        this.addline1 = addline1;
//        this.addline2 = addline2;
//        this.city = city;
//        this.state = state;
//        this.pincode = pincode;
//        this.desc_title = desc_title;
//        this.description = description;
//        this.worker_docid = worker_docid;
//        this.phone_No = phone_No;
//        this.service = service;
//        this.uid = uid;
//        this.address = address;
//        this.wid = wid;
//        this.status = status;
//        this.worker_name = worker_name;
//        this.worker_phnNo = worker_phnNo;
//        //this.acceptedTimestamp = acceptedTimestamp;
//        this.id = id;
//        this.user_docid = user_docid;
//        UserReqId = userReqId;
//    }

    public String getWorker_phnNo() {
        return worker_phnNo;
    }

    public void setWorker_phnNo(String worker_phnNo) {
        this.worker_phnNo = worker_phnNo;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public String getWorker_docid() {
        return worker_docid;
    }

    public void setWorker_docid(String worker_docid) {
        this.worker_docid = worker_docid;
    }



    public String getDesc_title() {
        return desc_title;
    }

    public void setDesc_title(String desc_title) {
        this.desc_title = desc_title;
    }




    public String getUserReqId() {
        return UserReqId;
    }

    public void setUserReqId(String userReqId) {
        UserReqId = userReqId;
    }

    public String getUser_docid() {
        return user_docid;
    }

    public void setUser_docid(String user_docid) {
        this.user_docid = user_docid;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public Request() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimespan() {
        return timespan;
    }

    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }

    public String getAddline1() {
        return addline1;
    }

    public void setAddline1(String addline1) {
        this.addline1 = addline1;
    }

    public String getAddline2() {
        return addline2;
    }

    public void setAddline2(String addline2) {
        this.addline2 = addline2;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone_No() {
        return phone_No;
    }

    public void setPhone_No(String phone_No) {
        this.phone_No = phone_No;
    }
}
