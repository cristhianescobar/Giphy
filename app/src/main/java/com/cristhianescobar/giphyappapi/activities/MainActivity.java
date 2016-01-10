package com.cristhianescobar.giphyappapi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.cristhianescobar.giphyappapi.DataUnit;
import com.cristhianescobar.giphyappapi.R;
import com.cristhianescobar.giphyappapi.adapter.ImageDataAdapter;
import com.cristhianescobar.giphyappapi.data.Data;
import com.cristhianescobar.giphyappapi.data.ResponseData;
import com.cristhianescobar.giphyappapi.service.GiphyAPIService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {


    private ImageDataAdapter adapter;
    private String TAG = "SEARCH";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.giphy_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.search_view)
    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpToolbar();
        getPopularGiphys();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);
                searchGiphyQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: " + newText);
                return false;
            }
        });
    }

    private void getPopularGiphys() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GiphyAPIService.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GiphyAPIService service = retrofit.create(GiphyAPIService.class);

        Call<ResponseData> call = service.getTrendingGiphys();
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(final Response<ResponseData> response, Retrofit retrofit) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Good Response", Toast.LENGTH_LONG).show();
                        ResponseData dResponse = response.body();
                        List<DataUnit> giphyList = new ArrayList<>();

                        for (Data singleGiphy : dResponse.data) {
                            giphyList.add(DataUnit.getDataUnit(
                                    singleGiphy.images.downSampled.imageUrl));
                        }

                        adapter = new ImageDataAdapter(getBaseContext(), giphyList);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));

                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Bad Response", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }

    private void searchGiphyQuery(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GiphyAPIService.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GiphyAPIService service = retrofit.create(GiphyAPIService.class);

        Call<ResponseData> call = service.getQueryGiphy(query);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(final Response<ResponseData> response, Retrofit retrofit) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Good Response", Toast.LENGTH_LONG).show();
                        ResponseData dResponse = response.body();
                        List<DataUnit> giphyList = new ArrayList<>();

                        for(Data singleGiphy : dResponse.data){
                            giphyList.add(DataUnit.getDataUnit(
                                    singleGiphy.images.downSampled.imageUrl));
                        }

                        adapter = new ImageDataAdapter(getBaseContext(), giphyList);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));

                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Bad Response", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}