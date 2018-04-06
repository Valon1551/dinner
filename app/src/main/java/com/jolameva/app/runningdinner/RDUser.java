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

    public RDUser(String displayName, String firebasePhotoUrl, String firebaseId) {
        this.firebaseId = firebaseId;
        this.largePicture = firebasePhotoUrl;
        this.name = displayName;
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

        myRef = database.getReference("users").child(getFirebaseId()).child("name");
        if (getName() != null) {
            myRef.setValue(getName());
        } else {
            myRef.setValue("null");
        }
        myRef = database.getReference("users").child(getFirebaseId()).child("email");
        if (getEmail() != null) {
            myRef.setValue(getEmail());
        } else {
            myRef.setValue("null");
        }
        myRef = database.getReference("users").child(getFirebaseId()).child("facebookUID");
        if (getFacebookId() != null) {
            myRef.setValue(getFacebookId());
        } else {
            myRef.setValue("null");
        }
        myRef = database.getReference("users").child(getFirebaseId()).child("googleUID");
        if (getGoogleId() != null) {
            myRef.setValue(getGoogleId());
        } else {
            myRef.setValue("null");
        }
        myRef = database.getReference("users").child(getFirebaseId()).child("gender");
        if (getGender() != null) {
            myRef.setValue(getGender());
        } else {
            myRef.setValue("null");
        }
        myRef = database.getReference("users").child(getFirebaseId()).child("picture");
        if (getPicture() != null) {
            myRef.setValue(getPicture().toString());
        } else {
            myRef.setValue("null");
        }
        myRef = database.getReference("users").child(getFirebaseId()).child("largePicture");
        if (getLargePicture() != null) {
            myRef.setValue(getLargePicture());
        } else {
            myRef.setValue("null");
        }
        myRef = database.getReference("users").child(getFirebaseId()).child("signInProvider");
        if (signInProvider != null) {

            myRef.setValue(getSignInProvider());
        } else {
            myRef.setValue("null");
        }
    }

}
