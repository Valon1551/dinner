package com.jolameva.app.runningdinner.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

/**
 * Created by guemuesm on 12.03.2018.
 */

public class MatchRooms implements Serializable {



//    private String roomId;
//    private String numberOfUsers;
//    private String location;
    private String rduserid;
    private String title;
    private String roomid;

    public MatchRooms(/**String roomId, String numberOfUsers, String location,*/String rdUserId, String roomid, String title) {
//        this.roomId = roomId;
//        this.numberOfUsers = numberOfUsers;
//        this.location = location;
        this.rduserid = rdUserId;
        this.roomid = roomid;
        this.title = title;
    }

    public MatchRooms() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getRduserid() {
        return rduserid;
    }

    public void setRduserid(String rdUserId) {
        this.rduserid = rdUserId;
    }
//    public String getRoomId() {
//        return roomId;
//    }
//
//    public void setRoomId(String roomId) {
//        this.roomId = roomId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getRoomid() {
//        return roomid;
//    }
//
//    public void setRoomid(String roomid) {
//        this.roomid = roomid;
//    }
//
//    public String getNumberOfUsers() {
//        return numberOfUsers;
//    }
//
//    public void setNumberOfUsers(String emptyPlaces) {
//        this.numberOfUsers = numberOfUsers;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//


    public void saveRoom(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rooms").child(getRduserid());

        myRef = database.getReference("rooms").child(getRduserid()).child("title");
        myRef.setValue(getTitle());
        myRef = database.getReference("rooms").child(getRduserid()).child("roomid");
        myRef.setValue(getRoomid());
    }

    public void removeMatchRoom(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rooms").child(getRduserid());
        myRef.removeValue();
    }
}
