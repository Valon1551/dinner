package com.bank.vlun.runningdinner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by guemuesm on 16.02.2018.
 * Der Adapter dient dazu Daten von der Cardview zu bearbeiten und auf Click Events zu reagieren.
 * Außerdem braucht man den Adapter zum Rendern der Cardview
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {

    // Context ist wichtig weil der Button wissen muss von welcher Activity er gerufen wird
    private Context mContext;
    private List<GroupObject> groupList;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public Button btn_Room, btn_Mo, btn_Di, btn_Mi, btn_Do, btn_Fr, btn_Sa, btn_So;

        public MyViewHolder(View view) {
            super(view);

            // Alle Buttons in der Cardview werden hier referenziert
            btn_Room = view.findViewById(R.id.btn_Room);
            btn_Mo = view.findViewById(R.id.btn_Mo);
            btn_Di = view.findViewById(R.id.btn_Di);
            btn_Mi = view.findViewById(R.id.btn_Mi);
            btn_Do = view.findViewById(R.id.btn_Do);
            btn_Fr = view.findViewById(R.id.btn_Fr);
            btn_Sa =  view.findViewById(R.id.btn_Sa);
            btn_So = view.findViewById(R.id.btn_So);
        }
    }


    public GroupAdapter(Context mContext, List<GroupObject> groupList) {
        this.mContext = mContext;
        this.groupList = groupList;
    }

    /**
     * Hier wird die einzelne Listrow inflated
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element_group, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        GroupObject groupObject = groupList.get(position);


        holder.btn_Room.setText(groupObject.getName());

        holder.btn_Room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Raum "+position+" angeklickt", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
