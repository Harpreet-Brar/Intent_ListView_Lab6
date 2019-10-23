package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.list);
        OkHttpClient client = new OkHttpClient();
        ArrayList<String> headings = new ArrayList<>();
        ArrayList<String> links = new ArrayList<>();

        String url = " https://www.reddit.com/.json";
        Request request = new Request.Builder().url(url).build();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    Log.d("response", data);
                    JSONObject target = (JSONObject) new JSONTokener(data).nextValue();
                    JSONArray jsonArray = target.getJSONObject("data").getJSONArray("children");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String x =item.getJSONObject("data").getString("title");
                        String y = item.getJSONObject("data").getString("url");
                        headings.add(x);
                        links.add(y);
                    }
                    runOnUiThread(() -> {
                        headings.add(headings.toString());
                        listView.setAdapter(new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, headings));

                    });
                } catch (IOException | JSONException e) {
                    runOnUiThread(() -> {

                    });
                }
                ;
            }
        };

        thread.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String clickedlink = links.get(i);
                Intent intent = new Intent(getApplicationContext(), RedditActivity.class);
                intent.putExtra("link", clickedlink);
                startActivity(intent);
            }
        });

    }
}
