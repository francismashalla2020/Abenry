package com.thinkbold.abenry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thinkbold.abenry.Retrofit.ZungukaAfricaAPI;
import com.thinkbold.abenry.Utils.Common;
import com.thinkbold.abenry.model.NewsAdapter;
import com.thinkbold.abenry.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class News extends AppCompatActivity {

    ProgressBar my_progressBar;
    SwipeRefreshLayout swipeContainer;
    RecyclerView recyclerNews;
    NewsAdapter newsAdapter;
    CompositeDisposable compositeDisposable;
    ZungukaAfricaAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerNews = findViewById(R.id.recyclerNews);
        recyclerNews.setHasFixedSize(true);

        compositeDisposable = new CompositeDisposable();
        mService = Common.getAPIs();
        fetchJSON();

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                my_progressBar.setVisibility(View.VISIBLE);
                fetchJSON();
                swipeContainer.setRefreshing(false);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_nav);
        //setting the Home selected
        bottomNavigationView.setSelectedItemId(R.id.news_navigation);
        //performing item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.news_navigation:
                        return true;
                    case R.id.praatice_navigation:
                        startActivity(new Intent(getApplicationContext(), Services.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home_navigation:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.contact_navigation:
                        startActivity(new Intent(getApplicationContext(), Contact.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.about_navigation:
                        startActivity(new Intent(getApplicationContext(), AboutUs.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        ImageView imageBack=findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Retrofit Implementation
    private void fetchJSON(){

        my_progressBar = findViewById(R.id.my_progressBar);
        my_progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Common.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ZungukaAfricaAPI api = retrofit.create(ZungukaAfricaAPI.class);
        Call<String> call = api.getString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i("Responsestring", response.toString());
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        writeRecycler(jsonresponse);
                    }else {
                        Log.i("onEmptyResponse", "Returned Empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void writeRecycler(String response){
        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);

            if (obj.has("data")){
                ArrayList<NewsModel> models = new ArrayList<>();

                JSONArray dataArray = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    NewsModel newsModels = new NewsModel();
                    JSONObject dataObj = dataArray.getJSONObject(i);

                    newsModels.setImage(dataObj.getString("bookImage"));
                    newsModels.setTitle(dataObj.getString("title"));
                    newsModels.setDesc(dataObj.getString("description"));
                    newsModels.setCategory(dataObj.getString("category"));
                    newsModels.setDate(dataObj.getString("DATE_FORMAT(uploaded_on, '%D %M, %Y' )"));
                    newsModels.setId(dataObj.getInt("id"));

                    models.add(newsModels);
                }
                newsAdapter = new NewsAdapter(this, models);
                recyclerNews.setAdapter(newsAdapter);
                recyclerNews.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

                my_progressBar.setIndeterminate(false);
                my_progressBar.setVisibility(View.GONE);
            }else {
                Log.i("failed", obj.optString("place"));
                Toast.makeText(this, obj.optString("place")+"", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}