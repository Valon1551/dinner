package com.jolameva.app.runningdinner.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jolameva.app.runningdinner.R;
import com.jolameva.app.runningdinner.model.RDUser;


public class FriendProfileActivity extends AppCompatActivity {

    private ImageView ivProfilePic;
    private TextView tvProfileName, tvProfileEmail;
    private String friendUserID;
    private String flargePictureUrl;
    private String femail;
    private String fname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        ivProfilePic = findViewById(R.id.iv_profilePic);
        tvProfileName = findViewById(R.id.tv_profileName);
        tvProfileEmail = findViewById(R.id.tv_placeholder1);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle == null) {
            finish();
        } else {
            friendUserID = bundle.getString("frienduserid");
        }

        populateProfile(friendUserID);

    }

    private void populateProfile(String fUserId) {

        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(fUserId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // DataSnapshot holt sich die Daten aus dem Node. Schreibweise der Attribute in der DB
                // müssen mit den Attributen in RDUser übereinstimmen
                RDUser rdUser = dataSnapshot.getValue(RDUser.class);
                fname = rdUser.getName();
                femail = rdUser.getEmail();
                flargePictureUrl = rdUser.getLargePicture();

                // Im folgenden Block wird die FriendProfileAct. mit den Daten aus der DB befüllt.
                // Die Methode muss in onDataChange sein, weil sonst versucht wird die Daten zu setzen
                // bevor das auslesen überhaupt funktioniert hat.
                if (flargePictureUrl != null) {
                    Glide.with(getBaseContext())
                            .load(flargePictureUrl)
                            .apply(RequestOptions.circleCropTransform())
                            .into(ivProfilePic);
                }

                tvProfileEmail.setText(
                        TextUtils.isEmpty(femail) ? "No email" : femail);
                tvProfileName.setText(
                        TextUtils.isEmpty(fname) ? "No display name" : fname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
