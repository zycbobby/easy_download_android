package uk.co.ribot.androidboilerplate.data.model;

import java.util.Arrays;

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

    public void addTags(String tag) {
        boolean b = true;
        String[] newTags = new String[tags.length + 1];
        for (int i = 0; i < tags.length; i++) {
            String t = tags[i];
            if (tag.equals(t)) {
                b = false;
            }
            newTags[i] = t;
        }
        if (b) {
            newTags[newTags.length - 1] = tag;
            tags = newTags;
        }
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
