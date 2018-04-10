package com.jolameva.app.runningdinner.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jolameva.app.runningdinner.R;
import com.jolameva.app.runningdinner.model.ChatModel;
import com.jolameva.app.runningdinner.profile.FriendProfileActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by guemuesm on 06.04.2018.
 */

public class ChatHolder extends RecyclerView.ViewHolder {
    private final TextView mTextField, mMessageTimestamp;
    public CircleImageView avatarUser;
    private Context context;
    public View mView;

    // Binded die Views
    public ChatHolder(View itemView) {
        super(itemView);
        mView = itemView;
        context = itemView.getContext();
        mTextField = itemView.findViewById(R.id.textContent);
        mMessageTimestamp = itemView.findViewById(R.id.tv_messageTimestamp);
        avatarUser = itemView.findViewById(R.id.ivChatAvatar);

    }

    // Holt sich die Daten aus der POJO Klasse
    public void bind(AbstractChat chat) {
        setAvatar(chat.getAvatarUrl());
        setText(chat.getMessage());
        setMessageTimestamp(chat.getTimestamp());

    }

    public void openFriendProfile(AbstractChat chat) {
        int position = getAdapterPosition();

        String friendUserId = chat.getUid();
        Toast.makeText(context, friendUserId, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, FriendProfileActivity.class);
        intent.putExtra("frienduserid",friendUserId);
        context.startActivity(intent);


    }

    private void setAvatar(String avatarUrl) {

        // Falls keine URL verfügbar wird ein Default Avatar benutzt.
        if (avatarUrl == null || avatarUrl.equals("")) {

        } else {
            Glide.with(context)
                    .load(avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatarUser);
        }
    }

    // Setzt den Inhalt von text in die Chatblase
    private void setText(String text) {
        mTextField.setText(text);
    }

    private void setMessageTimestamp(long timestamp) {

        // Zeigt den Timestamp wie bei Youtube an
        CharSequence elapsedTime = DateUtils.getRelativeTimeSpanString(timestamp, System.currentTimeMillis(), 0);
        mMessageTimestamp.setText(elapsedTime);
    }

}
