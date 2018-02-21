package com.jolameva.app.runningdinner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

        public TextView tv_Room, tv_Mo, tv_Di, tv_Mi, tv_Do, tv_Fr, tv_Sa, tv_So;

        public MyViewHolder(View view) {
            super(view);

            // Alle TextViews in der Cardview werden hier referenziert
            tv_Room = view.findViewById(R.id.btn_Room);
            tv_Mo = view.findViewById(R.id.tv_Mo);
            tv_Di = view.findViewById(R.id.tv_Di);
            tv_Mi = view.findViewById(R.id.tv_Mi);
            tv_Do = view.findViewById(R.id.tv_Do);
            tv_Fr = view.findViewById(R.id.tv_Fr);
            tv_Sa =  view.findViewById(R.id.tv_Sa);
            tv_So = view.findViewById(R.id.tv_So);
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        GroupObject groupObject = groupList.get(position);

        holder.tv_Room.setText(groupObject.getName());

        holder.tv_Room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Raum "+position+" angeklickt", Toast.LENGTH_SHORT).show();
            }
        });

        // Damit kann man den Background der einzelnen Textviews ändern. Für später eventuell wichtig
//        holder.tv_Mo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.tv_Mo.setBackgroundResource(R.drawable.weekdaybutton_border_red);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
