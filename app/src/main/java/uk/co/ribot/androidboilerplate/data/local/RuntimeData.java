package uk.co.ribot.androidboilerplate.data.local;

import uk.co.ribot.androidboilerplate.data.model.User;

/**
 * Created by zuo on 15-7-30.
 */
public enum RuntimeData {

    INSTANCE;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
