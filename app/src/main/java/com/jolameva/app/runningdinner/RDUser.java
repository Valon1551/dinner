package com.jolameva.app.runningdinner;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

/**
 * Created by guemuesm on 06.03.2018.
 */

public class RDUser implements Serializable {


    private String firebaseId;
    private Uri picture;
    private String largePicture;
    private String name;
    private String facebookId;
    private String googleId;
    private String email;
    private String signInProvider;
    private String gender;


    public RDUser(String firebaseId, Uri picture, String largePicture, String name,
                  String facebookId, String googleId, String email, String signInProvider) {

        this.firebaseId = firebaseId;
        this.picture = picture;
        this.largePicture = largePicture;
        this.name = name;
        this.facebookId = facebookId;
        this.googleId = googleId;
        this.email = email;
        this.signInProvider = signInProvider;
    }

    public RDUser() {

    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public Uri getPicture() {
        return picture;
    }

    public String getLargePicture() {
        return largePicture;
    }

    public String getName() {
        return name;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public void setPicture(Uri picture) {
        this.picture = picture;
    }

    public void setLargePicture(String largePicture) {
        this.largePicture = largePicture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(String signInProvider) {
        this.signInProvider = signInProvider;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void saveUser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(getFirebaseId());
//        myRef.setValue(getId());
        myRef = database.getReference("users").child(getFirebaseId()).child("name");
        myRef.setValue(getName());
        myRef = database.getReference("users").child(getFirebaseId()).child("email");
        myRef.setValue(getEmail());
        myRef = database.getReference("users").child(getFirebaseId()).child("facebookUID");
        myRef.setValue(getFacebookId());
        myRef = database.getReference("users").child(getFirebaseId()).child("googleUID");
        myRef.setValue(getGoogleId());
        myRef = database.getReference("users").child(getFirebaseId()).child("gender");
        myRef.setValue(getGender());
        myRef = database.getReference("users").child(getFirebaseId()).child("picture");
        myRef.setValue(getPicture().toString());
        myRef = database.getReference("users").child(getFirebaseId()).child("largePicture");
        myRef.setValue(getLargePicture());
        myRef = database.getReference("users").child(getFirebaseId()).child("signInProvider");
        myRef.setValue(getSignInProvider());
    }

}
