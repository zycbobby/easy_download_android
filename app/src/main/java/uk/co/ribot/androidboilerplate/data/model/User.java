package uk.co.ribot.androidboilerplate.data.model;

import java.util.Arrays;

/**
 * Created by zuo on 15-7-29.
 */
public class User {

    private String name;
    private String registerId;
    private String[] tags;

    public User(String name, String registerId, String[] tags) {
        this.name = name;
        this.registerId = registerId;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", registerId='" + registerId + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
