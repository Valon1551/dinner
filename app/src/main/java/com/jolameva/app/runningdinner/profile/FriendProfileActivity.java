package com.jolameva.app.runningdinner.profile;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jolameva.app.runningdinner.R;
import com.jolameva.app.runningdinner.model.RDUser;

import java.util.List;

public class FriendProfileActivity extends AppCompatActivity {

    private ImageView ivProfilePic;
    private TextView tvProfileName, tvProfileEmail;
    private String friendUserID;
    private DatabaseReference myRef;
    private String fuserID;
    private String flargePictureUrl;
    private String femail;
    private String fname;
    private RDUser rdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        ivProfilePic = findViewById(R.id.iv_profilePic);
        tvProfileName = findViewById(R.id.tv_profileName);
        tvProfileEmail = findViewById(R.id.tv_placeholder1);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle == null){
            finish();
        } else {
            friendUserID = bundle.getString("frienduserid");
        }

        populateProfile(friendUserID);

    }

    private void populateProfile(String fUserId) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(fUserId);
        Query queryRef = myRef;
        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(fUserId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 RDUser rdUser = dataSnapshot.getValue(RDUser.class);
                 fname = rdUser.getName();
                 femail = rdUser.getEmail();
                 flargePictureUrl = rdUser.getLargePicture();

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
