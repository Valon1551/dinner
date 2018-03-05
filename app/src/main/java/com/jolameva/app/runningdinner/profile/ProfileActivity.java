package com.jolameva.app.runningdinner.profile;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jolameva.app.runningdinner.R;
import com.jolameva.app.runningdinner.settings.SettingsActivity;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private ImageView ivProfilePic;
    private TextView tvProfileName, tvProfileEmail;
    private Button btnGotoSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfilePic = findViewById(R.id.iv_profilePic);
        tvProfileName = findViewById(R.id.tv_profileName);
        tvProfileEmail = findViewById(R.id.tv_placeholder1);
        btnGotoSettings = findViewById(R.id.btn_gotoSettings);
        populateProfile();

        btnGotoSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ruft die Settingsactivity auf
                // Außerdem wird dafür gesorgt dass die Header übersprungen werden, es wird also nur GeneralPreferenceFragment angezeigt.
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                intent.putExtra( PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.GeneralPreferenceFragment.class.getName() );
                intent.putExtra( PreferenceActivity.EXTRA_NO_HEADERS, true );
                startActivity(intent);
            }
        });
    }

    private void populateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // So bekommt man einige Provider Infos. Also ob der user von facebook oder google kommt
        List test = user.getProviderData();



        if (user.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivProfilePic);
        }

        tvProfileEmail.setText(
                TextUtils.isEmpty(user.getEmail()) ? "No email" : "Placeholder1 "+user.getEmail());
        tvProfileName.setText(
                TextUtils.isEmpty(user.getDisplayName()) ? "No display name" : user.getDisplayName());

    }


}
