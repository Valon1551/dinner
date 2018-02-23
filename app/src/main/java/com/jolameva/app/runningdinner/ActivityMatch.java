package com.jolameva.app.runningdinner;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityMatch extends AppCompatActivity {

    private GroupAdapter groupAdapter;
    private List<GroupObject> groupList;
    private RecyclerView rv_Groups;

    private Button btn_matchMe, btn_newGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        btn_matchMe = findViewById(R.id.btn_matchMe);
        btn_newGroup = findViewById(R.id.btn_newGroup);

        btn_matchMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMatch.this, "Match Me", Toast.LENGTH_SHORT).show();
            }
        });

        btn_newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMatch.this, "New Group", Toast.LENGTH_SHORT).show();
            }
        });

        rv_Groups = findViewById(R.id.recycler_view_groups);
        groupList = new ArrayList<>();
        groupAdapter = new GroupAdapter(this, groupList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rv_Groups.setLayoutManager(mLayoutManager);
        rv_Groups.setAdapter(groupAdapter);

        fillGroups();
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);


        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
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
        groupObject = new GroupObject("3/4");
        groupList.add(groupObject);
        groupObject = new GroupObject("3/4");
        groupList.add(groupObject);
        groupObject = new GroupObject("3/4");
        groupList.add(groupObject);
        groupObject = new GroupObject("3/4");
        groupList.add(groupObject);
        groupObject = new GroupObject("3/4");
        groupList.add(groupObject);
        groupObject = new GroupObject("3/4");
        groupList.add(groupObject);
        groupObject = new GroupObject("3/4");
        groupList.add(groupObject);
        groupObject = new GroupObject("3/4");
        groupList.add(groupObject);
        groupObject = new GroupObject("3/4");
        groupList.add(groupObject);

        groupAdapter.notifyDataSetChanged();

    }
}
