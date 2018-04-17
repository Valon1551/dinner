package com.jolameva.app.runningdinner.chat;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jolameva.app.runningdinner.R;
import com.jolameva.app.runningdinner.model.ChatModel;

public class ActivityChat2 extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    private static final String TAG = "RTDB";
    public static final int VIEW_TYPE_USER_MESSAGE = 0;
    public static final int VIEW_TYPE_FRIEND_MESSAGE = 1;
    public static String roomID;
    protected static Query chatQuery = FirebaseDatabase.getInstance().getReference().child("chats");

    RecyclerView mRecyclerView;
    ImageButton btnSend;
    EditText etMessageEdit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        // Holt sich die Extras aus der MatchRoomsAct.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // Hier muss später wahrscheinlich shared preferences benutzt werden
        if (bundle != null){
            roomID = bundle.getString("roomid");
            chatQuery = FirebaseDatabase.getInstance().getReference().child("chats").child(roomID);
        }

        // Layout binden
        btnSend = findViewById(R.id.btnSend);
        etMessageEdit = (EditText) findViewById(R.id.editWriteMessage);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerChat);

        // Sollte auf true bleiben damit unnötige überprüfungen auf veränderungen der Größe eines Listenitems übersprungen werden
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // Sorgt dafür dass der Chat hochscrollt wenn die Tastatur geöffnet wird. Problem: wenn der chat nur eine nachricht hat
        // wird diese nachricht ebenfalls am unteren ende angezeigt anstatt oben
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isSignedIn()) { attachRecyclerViewAdapter(); }
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth auth) {

        // Nachrichten schreiben nur möglich wenn man eingeloggt ist
        btnSend.setEnabled(isSignedIn());
        etMessageEdit.setEnabled(isSignedIn());

        if (isSignedIn()) {
            attachRecyclerViewAdapter();
        } else {
            finish();
        }
    }

    // Prüft ob der Current User eingeloggt ist
    private boolean isSignedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    private void attachRecyclerViewAdapter() {
        final RecyclerView.Adapter adapter = newAdapter();

        // Scroll to bottom on new messages
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });

        mRecyclerView.setAdapter(adapter);
    }

    public void sendMessage(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String avatarUrl = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();

        long timestamp = System.currentTimeMillis();
        // Auf isEmpty prüfen sonst werden leere Messages auch zur DB hinzugefügt
        if (TextUtils.isEmpty(etMessageEdit.getText().toString().trim())){
            Log.d(TAG, "Empty Message will not be sent");
        } else {
            // Gibt die Daten weiter an die ChatModel Klasse
            onAddMessage(new ChatModel(avatarUrl, etMessageEdit.getText().toString(), uid,  timestamp, uid));
        }
        // Leert die EditTextbox nach jedem Button klick
        etMessageEdit.setText("");
    }

    protected RecyclerView.Adapter newAdapter() {
        FirebaseRecyclerOptions<ChatModel> options =
                new FirebaseRecyclerOptions.Builder<ChatModel>()
                        .setQuery(chatQuery, ChatModel.class)
                        .setLifecycleOwner(this)
                        .build();

        return new FirebaseRecyclerAdapter<ChatModel, ChatHolder>(options) {
            @Override
            public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                // Wähle das richtige Chatblasen Layout
                if (viewType == VIEW_TYPE_USER_MESSAGE){

                    return new ChatHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.rc_item_message_user, parent, false));
                } else {
                    return new ChatHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.rc_item_message_friend, parent, false));
                }
            }

            @Override
            protected void onBindViewHolder(@NonNull final ChatHolder holder, final int position, @NonNull ChatModel model) {
                holder.bind(model);

                // Dieser Code ist dazu da, dass man über den Avatar im Chat auf das Benutzerprofil kommt von dem die Nachricht
                // geschrieben wurde. Da es sich hier um eine inner class handelt muss das ChatModel in eine neue ChatModel
                // variable übertragen werden, die dann als final deklariert werden muss
                final ChatModel chat = model;
                holder.avatarUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Gib das Chatmodel an die openFirendProfile Methode weiter, um auf die UserId zugreifen zu können
                        holder.openFriendProfile(chat);
                    }
                });
            }

            @Override
            public ChatModel getItem(int position){
                return super.getItem(position);
            }
            @Override
            public int getItemViewType(int position){
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                // Checkt ob es sich um eine eigene Message handelt oder nicht
                ChatModel chatModel2 = getItem(position);
                if (chatModel2.getUid().equals(currentUser.getUid())){
                    return VIEW_TYPE_USER_MESSAGE;
                }
                else
                    return VIEW_TYPE_FRIEND_MESSAGE;
            }
        };
    }

    protected void onAddMessage(ChatModel chat) {
        // Mit push wird auf die DB geschrieben
        chatQuery.getRef().push().setValue(chat, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference reference) {
                if (error != null) {
                    Log.e(TAG, "Failed to write message", error.toException());
                }
            }
        });
    }
}