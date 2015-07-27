package uk.co.ribot.androidboilerplate.data.remote;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import uk.co.ribot.androidboilerplate.data.model.Thing;

/**
 * Created by zuo on 15-7-27.
 */
public class ElasticSearchHelper {

    public static Observable<List<Thing>> search(@NonNull String keyword) {
        final MediaType json = MediaType.parse("application/json; charset=utf-8");
        final OkHttpClient client = new OkHttpClient();
        final RequestBody body = RequestBody.create(json, "{\n" +
                "                \"query\": {\n" +
                "                    \"function_score\": {\n" +
                "                        \"score_mode\": \"first\",\n" +
                "                        \"query\": {\n" +
                "                            \"match\": {\n" +
                "                                \"title\": \"" + keyword + "\"\n" +
                "                            }\n" +
                "                        },\n" +
                "\n" +
                "                        \"functions\": [\n" +
                "                            {\n" +
                "                                \"filter\": {\n" +
                "                                    \"exists\": {\n" +
                "                                        \"field\": \"updatedAt\"\n" +
                "                                    }\n" +
                "                                },\n" +
                "                                \"gauss\": {\n" +
                "                                    \"updatedAt\": {\n" +
                "                                        \"scale\": \"1d\",\n" +
                "                                        \"offset\": \"0.2d\",\n" +
                "                                        \"decay\": 0.5\n" +
                "                                    }\n" +
                "                                }\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    }\n" +
                "                }\n" +
                "            }");
        String creds = String.format("%s:%s", "zuo", "22216785");
        final String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);

        return Observable.create(new Observable.OnSubscribe<List<Thing>>() {

            @Override
            public void call(Subscriber<? super List<Thing>> subscriber) {
                Request request = new Request.Builder()
                        .url("http://es.misscatandzuozuo.info/mongoindex/thing/_search")
                        .post(body)
                        .addHeader("Authorization", auth)
                        .build();

                try {
                    System.out.println(request.body().toString());
                    Response resp = client.newCall(request).execute();
                    if (resp.isSuccessful()) {
                        JSONArray jsonArray = new JSONObject(resp.body().string()).getJSONObject("hits").getJSONArray("hits");
                        List<Thing> things = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject _thing = jsonArray.getJSONObject(i).getJSONObject("_source");
                            Thing thing = Thing.valueOf(_thing);
                            things.add(thing);
                        }
                        subscriber.onNext(things);
                        subscriber.onCompleted();
                    } else {
                        throw new IOException("Unexpected code " + resp);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }
}
