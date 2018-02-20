package com.bank.vlun.runningdinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityRating extends AppCompatActivity {

    ImageView iv_ProfilePic;
    TextView tv_ProfileName;
    RatingBar rb_Taste, rb_Kindness, rb_Location, rb_Placeholder1, rb_Placeholder2, rb_SummaryRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        iv_ProfilePic = findViewById(R.id.iv_profilePicRating);
        tv_ProfileName = findViewById(R.id.tv_profileNameRating);
        rb_Taste = findViewById(R.id.rb_Geschmack);
        rb_Kindness = findViewById(R.id.rb_Freundlichkeit);
        rb_Location = findViewById(R.id.rb_Location);
        rb_Placeholder1 = findViewById(R.id.rb_placeholder1);
        rb_Placeholder2 = findViewById(R.id.rb_placeholder2);
        rb_SummaryRating = findViewById(R.id.rb_summaryRating);

        rb_SummaryRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(ActivityRating.this, ""+ratingBar.getRating(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}
