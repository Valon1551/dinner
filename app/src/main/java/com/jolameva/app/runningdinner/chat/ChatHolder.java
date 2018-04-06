package com.jolameva.app.runningdinner.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jolameva.app.runningdinner.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by guemuesm on 06.04.2018.
 */

public class ChatHolder extends RecyclerView.ViewHolder {
    private final TextView mTextField;
    public CircleImageView avatarUser;
    private Context context;

    public ChatHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        mTextField = itemView.findViewById(R.id.textContent);
        avatarUser = itemView.findViewById(R.id.ivChatAvatar);
    }

    public void bind(AbstractChat chat) {
        setAvatar(chat.getAvatarUrl());
        setText(chat.getMessage());

    }

    private void setAvatar(String avatarUrl) {

        if (avatarUrl== null || avatarUrl.equals("")){

        } else {
            Glide.with(context)
                    .load(avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatarUser);
        }

    }

    private void setText(String text) {
        mTextField.setText(text);
    }

}
