package uk.co.ribot.androidboilerplate.ui.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Thing;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private static final String TAG = "SEARCH FRAGMENT";

    @Bind(R.id.editText)
    EditText searchBar;

    @Bind(R.id.searchResult)
    ListView searchResultListView;

    private RequestQueue queue;

    private List<Thing> searchThings = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        queue = Volley.newRequestQueue(view.getContext());
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) (getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                    String word = textView.getText().toString();
                    new fetchDiscountTask().execute(word);
                    return true;
                }
                return false;
            }
        });

        searchResultListView.setAdapter(new ThingListAdapter(searchThings));

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class fetchDiscountTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                JSONObject searchObj = new JSONObject("{\n" +
                        "                \"query\": {\n" +
                        "                    \"function_score\": {\n" +
                        "                        \"score_mode\": \"first\",\n" +
                        "                        \"query\": {\n" +
                        "                            \"match\": {\n" +
                        "                                \"title\": " + strings[0] + "\n" +
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
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, "http://es.misscatandzuozuo.info/mongoindex/thing/_search", searchObj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONObject("hits").getJSONArray("hits");
                            searchThings.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (jsonArray.getJSONObject(i).getDouble("_score") > 4.0) {
                                    JSONObject _thing = jsonArray.getJSONObject(i).getJSONObject("_source");
                                    Thing thing = Thing.valueOf(_thing);
                                    searchThings.add(thing);
                                }

                                // Log.d(TAG, thing.toString());
                            }

                            ((ThingListAdapter) searchResultListView.getAdapter()).notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        String creds = String.format("%s:%s", R.string.es_username, R.string.es_password);
                        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                        params.put("Authorization", auth);
                        return params;
                    }
                };

                queue.add(req);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class ThingListAdapter extends ArrayAdapter<Thing> {
        TextView crimeTitle;
        TextView crimeDate;

        public ThingListAdapter(List<Thing> things) {
            super(getActivity(), 0, things);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_thing, null);
            }

            crimeTitle = (TextView) convertView.findViewById(R.id.title);
            crimeDate = (TextView) convertView.findViewById(R.id.time);

            Thing c = (Thing) searchResultListView.getAdapter().getItem(position);
            crimeTitle.setText(c.getTitle());
            crimeDate.setText(c.getUrl());
            return convertView;
        }
    }
}
