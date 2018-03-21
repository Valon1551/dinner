package com.jolameva.app.runningdinner.helperclass;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jolameva.app.runningdinner.RDUser;

import java.io.Serializable;

/**
 * Created by guemuesm on 12.03.2018.
 */

public class MatchRooms implements Serializable {



//    private String roomId;
//    private String emptyPlaces;
//    private String location;
    private String rdUserId;
    private String title;
    private String description;

    public MatchRooms(/**String roomId, String emptyPlaces, String location,*/ String rdUserId,  String description, String title) {
//        this.roomId = roomId;
//        this.emptyPlaces = emptyPlaces;
//        this.location = location;
        this.rdUserId = rdUserId;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getEmptyPlaces() {
//        return emptyPlaces;
//    }
//
//    public void setEmptyPlaces(String emptyPlaces) {
//        this.emptyPlaces = emptyPlaces;
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
    public String getRdUserId() {
        return rdUserId;
    }

    public void setRdUserId(String rdUserId) {
        this.rdUserId = rdUserId;
    }

    public void saveRoom(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("rooms").child(getRdUserId());

        myRef = database.getReference("rooms").child(getRdUserId()).child("title");
        myRef.setValue(getTitle());
    }
}
