package com.jolameva.app.runningdinner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
//import com.jolameva.app.runningdinner.chat.ActivityChat;
import com.jolameva.app.runningdinner.chat.ActivityChat2;
import com.jolameva.app.runningdinner.fbauth.AuthRunningActivity;
import com.jolameva.app.runningdinner.model.MatchRooms;

/*
    Hier werden die verschiedenen Räume aufgelistet die die User erstellen,
    damit andere beitreten können
 */
public class ActivityMatch extends AppCompatActivity {

    // RecyclerView die sich um die Listenansicht kümmert
    private RecyclerView rv_Groups;

    private Button btn_matchMe, btn_newGroup;

    // Firebase Datenbank Referenz
    private DatabaseReference myRef;

    // Firebase RecyclerAdapter mit MatchRooms als Model und dem MatchRoomViewHolder
    public FirebaseRecyclerAdapter<MatchRooms, MatchRoomViewHolder> recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialisiert die CollapsingToolbar
        initCollapsingToolbar();

        // Button referenzieren
        btn_matchMe = findViewById(R.id.btn_matchMe);
        btn_newGroup = findViewById(R.id.btn_newGroup);

        // onClick Listener
        btn_matchMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMatch.this, "Match Me", Toast.LENGTH_SHORT).show();
                removeMatchRoom(myRef);
            }
        });
        btn_newGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMatch.this, "New Group", Toast.LENGTH_SHORT).show();
                createMatchRoom();
            }
        });

        // Check ob der User noch eingeloggt ist
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            finish();
            return;
        }

        // RecyclerView referenzieren
        rv_Groups = findViewById(R.id.recycler_view_groups);
        // LayoutManager setzen. Muss immer gesetzt werden
        rv_Groups.setLayoutManager(new LinearLayoutManager(this));
        // Referenz auf welchen Node von der Datenbank wir zugreifen möchten
        myRef = FirebaseDatabase.getInstance().getReference().child("rooms");

        Query query = myRef;

        // Query mit der myRef Referenz auf den Node wird gesetzt.
        // Die Modell Klasse MatchRooms wird hier referenziert.
        // Es ist wichtig dass die Attribute von Matchrooms 100%ig mit den Attributen aus unserer DB übereinstimmen!
        FirebaseRecyclerOptions<MatchRooms> options =
                new FirebaseRecyclerOptions.Builder<MatchRooms>().setQuery(query, MatchRooms.class).build();

        recyclerAdapter = new FirebaseRecyclerAdapter<MatchRooms, MatchRoomViewHolder>(
                options
        ) {
            @Override
            public MatchRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Holt sich die einzelne Row und setzt diese als Layout damit später
                // in der MatchRoomViewHolder Klasse die einzelnen Attribute wie textview für Title etc. referenziert werden können
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element_group, parent, false);
                // Gibt die View weiter an die MatchRoomViewHolder Klasse
                return new MatchRoomViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull MatchRoomViewHolder holder, final int position, @NonNull MatchRooms model) {

                // Hier können die Werte aus der MatchRooms Modell Klasse ausgelesen werden
                // Mit der Methode setDetails werden dann die Werte mit Hilfe der ViewHolder Klasse in die ListRow gesetzt
                // TODO: Alle notwendigen Daten setzen
                holder.setDetails(model.getTitle().toString());

                // TODO: Hier kommt immer np exception.. die model variable hat lediglich Title als wert aber sonst nichts.. warum?!
//                holder.setRoomID(model.getRdUserId().toString());
                final String roomID = model.getTitle().toString();
                holder.btn_enterRoom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ActivityMatch.this, ActivityChat2.class);
                        intent.putExtra("roomid", roomID);
                        startActivity(intent);

                    }
                });

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ActivityMatch.this, "clicked pos: " + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        };
        // startListening() wird benötigt das auf Änderungen der DB autmatisch reagiert wird
        recyclerAdapter.startListening();
        // Zu guter Letzt den Adapter auf die RecyclerView setzen
        rv_Groups.setAdapter(recyclerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

    public class MatchRoomViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView tv_Mo, tv_Di, tv_Mi, tv_Do, tv_Fr, tv_Sa, tv_So;
        public Button btn_enterRoom;
        public String roomID;

        public MatchRoomViewHolder(final View itemView) {
            super(itemView);
            this.mView = itemView;
            btn_enterRoom = itemView.findViewById(R.id.btn_Room);
            tv_Mo = itemView.findViewById(R.id.tv_Mo);
            tv_Di = itemView.findViewById(R.id.tv_Di);
            //tv_Mi = itemView.findViewById(R.id.tv_Mi);
            //tv_Do = itemView.findViewById(R.id.tv_Do);
            //tv_Fr = itemView.findViewById(R.id.tv_Fr);
            //tv_Sa = itemView.findViewById(R.id.tv_Sa);
            //tv_So = itemView.findViewById(R.id.tv_So);

        }

        public void setDetails(String titlex) {
            btn_enterRoom.setText(titlex);
        }

        public void setRoomID(String roomIDx) {
            tv_Mo.setText(roomIDx);
        }
    }

    // WIP
    private void createMatchRoom() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            startActivity(AuthRunningActivity.createIntent(this));
            finish();
            return;
        }
        MatchRooms matchRooms = new MatchRooms();
        matchRooms.setRdUserId(currentUser.getUid());
        matchRooms.setTitle("FireID " + currentUser.getUid());
        matchRooms.saveRoom();
    }

    private void removeMatchRoom(DatabaseReference matchroomRef) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            startActivity(AuthRunningActivity.createIntent(this));
            finish();
            return;
        }

        MatchRooms matchRooms = new MatchRooms();
        matchRooms.setRdUserId(currentUser.getUid());
        matchRooms.removeMatchRoom();
    }

    // Die Methode kann ignoriert werden, die kümmert sich nur um die Collapsing Toolbar
    // der Code war so schon vorhanden.
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

}
