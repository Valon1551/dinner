package com.bank.vlun.runningdinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityMatch extends AppCompatActivity {

    ListView lv_Groups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        lv_Groups = findViewById(R.id.lv_Groups);

        String [] values = new String[] {"skl","slkdjf","alskdj","alksdjlkj","lkajsdl"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.list_element_group, values);

        lv_Groups.setAdapter(adapter);
        lv_Groups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int itemPosition = i;
               // String itemValue = (String) lv_Groups.getItemAtPosition(i);

                Toast.makeText(ActivityMatch.this, "Item geklickt ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
