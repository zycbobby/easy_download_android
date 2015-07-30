package uk.co.ribot.androidboilerplate.event;

import uk.co.ribot.androidboilerplate.data.model.User;

/**
 * Created by zuo on 15-7-30.
 */
public class UserUpdateEvent {

    private User user;

    public UserUpdateEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
