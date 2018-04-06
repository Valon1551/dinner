package com.jolameva.app.runningdinner.model;

import com.jolameva.app.runningdinner.chat.AbstractChat;

/**
 * Created by guemuesm on 06.04.2018.
 */

public class ChatModel extends AbstractChat{
    private String mAvatarUrl;
    private String mMessage;
    private String mUid;

    public ChatModel() {
        // Needed for Firebase
    }

    public ChatModel(String avatarUrl, String message, String uid) {
        mAvatarUrl = avatarUrl;
        mMessage = message;
        mUid = uid;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }


    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatModel chat = (ChatModel) o;

        return mUid.equals(chat.mUid)
                && (mAvatarUrl == null ? chat.mAvatarUrl == null : mAvatarUrl.equals(chat.mAvatarUrl))
                && (mMessage == null ? chat.mMessage == null : mMessage.equals(chat.mMessage));
    }

    @Override
    public int hashCode() {
        int result = mAvatarUrl == null ? 0 : mAvatarUrl.hashCode();
        result = 31 * result + (mMessage == null ? 0 : mMessage.hashCode());
        result = 31 * result + mUid.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "mName='" + mAvatarUrl + '\'' +
                ", mMessage='" + mMessage + '\'' +
                ", mUid='" + mUid + '\'' +
                '}';
    }
}
