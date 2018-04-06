package com.jolameva.app.runningdinner.chat;

/**
 * Created by guemuesm on 06.04.2018.
 */

public abstract class AbstractChat {

    public abstract String getAvatarUrl();

    public abstract String getMessage();

    public abstract String getUid();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

}
