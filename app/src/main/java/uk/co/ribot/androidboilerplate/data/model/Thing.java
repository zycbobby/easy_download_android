package uk.co.ribot.androidboilerplate.data.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by zuo on 15-7-24.
 */
public class Thing {

    private String title;
    private String url;
    private String[] images;

    public Thing(String title, String url, String[] images) {
        this.title = title;
        this.url = url;
        this.images = images;
    }

    public static Thing valueOf(JSONObject source) {
        try {
            JSONArray jsonArray = source.getJSONObject("info").getJSONArray("images");
            String[] images = new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++){
                images[i] = jsonArray.getJSONObject(i).getString("url");
            }
            return new Thing(source.getString("title"), source.getString("source"), images);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String[] getImages() {
        return images;
    }

    @Override
    public String toString() {
        return "Thing{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", images=" + Arrays.toString(images) +
                '}';
    }
}
