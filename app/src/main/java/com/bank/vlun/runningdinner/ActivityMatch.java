package com.bank.vlun.runningdinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityMatch extends AppCompatActivity {

    private GroupAdapter groupAdapter;
    private List<GroupObject> groupList;
    private RecyclerView rv_Groups;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);


        rv_Groups = findViewById(R.id.recycler_view_groups);
        groupList = new ArrayList<>();
        groupAdapter = new GroupAdapter(this, groupList);

        rv_Groups.setAdapter(groupAdapter);

        fillGroups();
    }

    private void fillGroups(){

        GroupObject groupObject = new GroupObject("2/4");
        groupList.add(groupObject);

        groupObject = new GroupObject("3/4");
        groupList.add(groupObject);

        groupObject = new GroupObject("3/4");
        groupList.add(groupObject);
        groupObject = new GroupObject("1/4");
        groupList.add(groupObject);
        groupObject = new GroupObject("1/4");
        groupList.add(groupObject);
        groupObject = new GroupObject("2/4");
        groupList.add(groupObject);
        groupObject = new GroupObject("3/4");
        groupList.add(groupObject);

        groupAdapter.notifyDataSetChanged();

    }
}
