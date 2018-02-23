package com.jolameva.app.runningdinner;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ActivityRating extends AppCompatActivity {

    Button btn_sendRating;
    ImageView iv_ProfilePic;
    TextView tv_ProfileName;
    RatingBar rb_Taste, rb_Kindness, rb_Location, rb_Placeholder1, rb_Placeholder2, rb_SummaryRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        btn_sendRating = findViewById(R.id.btn_sendRating);
        iv_ProfilePic = findViewById(R.id.iv_profilePicRating);
        tv_ProfileName = findViewById(R.id.tv_profileNameRating);
        rb_Taste = findViewById(R.id.rb_Geschmack);
        rb_Kindness = findViewById(R.id.rb_Freundlichkeit);
        rb_Location = findViewById(R.id.rb_Location);
        rb_Placeholder1 = findViewById(R.id.rb_placeholder1);
        rb_Placeholder2 = findViewById(R.id.rb_placeholder2);
        rb_SummaryRating = findViewById(R.id.rb_summaryRating);

//        rb_SummaryRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//            }
//        });

        Glide.with(this).load(R.drawable.profile_pic_zacke).into(iv_ProfilePic);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String profileName = sharedPreferences.getString("example_text","Name nicht gefunden");
        tv_ProfileName.setText(profileName);

        rb_Taste.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v<1.0f)
                    ratingBar.setRating(1.0f);
                calculateSummary();
                rb_SummaryRating.setRating(calculateSummary());

            }
        });

        rb_Kindness.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v<1.0f)
                    ratingBar.setRating(1.0f);
                calculateSummary();
                rb_SummaryRating.setRating(calculateSummary());
            }
        });

        rb_Location.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v<1.0f)
                    ratingBar.setRating(1.0f);
                calculateSummary();
                rb_SummaryRating.setRating(calculateSummary());
            }
        });

        rb_Placeholder1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v<1.0f)
                    ratingBar.setRating(1.0f);
                calculateSummary();
                rb_SummaryRating.setRating(calculateSummary());
            }
        });

        rb_Placeholder2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(v<1.0f)
                    ratingBar.setRating(1.0f);
                calculateSummary();
                rb_SummaryRating.setRating(calculateSummary());
            }
        });

        btn_sendRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityRating.this, "Danke für deine Bewertung Cyka Blyat", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private float calculateSummary(){
        float result;

        result = (rb_Taste.getRating() + rb_Kindness.getRating() + rb_Placeholder1.getRating() + rb_Location.getRating() + rb_Placeholder2.getRating()) / 5;


        return result;
    }
}
