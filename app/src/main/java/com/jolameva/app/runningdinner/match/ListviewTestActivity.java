package com.jolameva.app.runningdinner.match;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jolameva.app.runningdinner.GroupObject;
import com.jolameva.app.runningdinner.R;

public class ListviewTestActivity extends AppCompatActivity {

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_test);

        mListView = findViewById(R.id.testListview);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dinner-c3ee4.firebaseio.com/rooms");

        Query query = databaseReference;

        FirebaseListOptions<String> options = new FirebaseListOptions.Builder<String>()
                .setQuery(query, String.class)
                .setLayout(android.R.layout.simple_list_item_1)
                .build();

        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(options) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView test = v.findViewById(android.R.id.text1);
                test.setText(model);
            }
        };

        mListView.setAdapter(firebaseListAdapter);

    }
}
