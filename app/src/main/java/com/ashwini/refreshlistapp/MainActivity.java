package com.ashwini.refreshlistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<DataModel> imageModelArrayList;
    private DataListAdapter adapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    public String url= "https://picsum.photos/v2/list?page=2&limit=20";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        try {
            run("https://picsum.photos/v2/list?page=2&limit=20");

        } catch (IOException e) {
            e.printStackTrace();
        }



        mSwipeRefreshLayout.setColorSchemeResources(R.color.black);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    run("https://picsum.photos/v2/list?page=2&limit=20");
                    mSwipeRefreshLayout.setRefreshing(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }
    void run(String loadurl) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(loadurl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject object=null;
                imageModelArrayList= new ArrayList<DataModel>();

                try {
                    JSONArray myResponse = new JSONArray(response.body().string());

                    if(myResponse != null && myResponse.length() > 0) {

                        for (int i = 0; i < myResponse.length(); i++) {

                            DataModel model = new DataModel();
                            object = myResponse.getJSONObject(i);

                            model.id = object.getString("id");
                            model.author = object.getString("author");
                            model.width = object.getInt("width");
                            model.height = object.getInt("height");
                            model.url = object.getString("url");
                            model.download_url = object.getString("download_url");
                            imageModelArrayList.add(model);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("list size", imageModelArrayList.size() + "");
                        adapter = new DataListAdapter(MainActivity.this,imageModelArrayList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

                    }
                });

            }
        });

    }

}