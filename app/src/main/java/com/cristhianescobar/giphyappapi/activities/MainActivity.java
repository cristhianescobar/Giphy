package com.cristhianescobar.giphyappapi.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.cristhianescobar.giphyappapi.R;
import com.cristhianescobar.giphyappapi.adapter.ImageDataAdapter;
import com.cristhianescobar.giphyappapi.data.Data;
import com.cristhianescobar.giphyappapi.data.ResponseData;
import com.cristhianescobar.giphyappapi.service.GiphyAPIService;
import com.cristhianescobar.giphyappapi.utils.DataUnit;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final String SAVED_LIST = "list";
    private final String TAG = "MainActivity";

    private int GRID_CELLS = 2;

    private ImageDataAdapter adapter;
    private Retrofit retrofit;
    private GiphyAPIService service;
    private ArrayList<DataUnit> giphyList;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.giphy_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.search_view)
    SearchView searchView;
    @Bind(R.id.progress_spinner)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpToolbar();
        setGiphyRecyclerView();
        setupRetrofit();

        if(savedInstanceState != null && savedInstanceState.containsKey(SAVED_LIST)){
            giphyList = savedInstanceState.getParcelableArrayList(SAVED_LIST);
            progressBar.setVisibility(View.GONE);
            adapter.setNewData(giphyList);

        }else {
            getPopularGiphys();
        }
    }

    private void setGiphyRecyclerView() {
        giphyList = new ArrayList<>();
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            GRID_CELLS = 3;
        }
        adapter = new ImageDataAdapter(MainActivity.this, giphyList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), GRID_CELLS));
    }

    private void setupRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(GiphyAPIService.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(GiphyAPIService.class);
    }

    protected void setupSearchView() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);
                searchGiphyQuery(query);
                progressBar.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: " + newText);
                if(!newText.isEmpty() && newText.charAt(newText.length()-1) == ' '){
                    searchGiphyQuery(newText);
                    progressBar.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        searchView.clearFocus();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SAVED_LIST, giphyList);
        super.onSaveInstanceState(outState);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setupSearchView();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void getPopularGiphys() {
        service.getTrendingGiphysRX()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseData>() {
                    @Override
                    public void call(ResponseData responseData) {
                        progressBar.setVisibility(View.GONE);
                        giphyList = getGiphyObjectsOutOfResponse(responseData);
                        adapter.setNewData(giphyList);
                    }
                });
    }

    @NonNull
    private ArrayList<DataUnit> getGiphyObjectsOutOfResponse(ResponseData responseData) {
        ArrayList<DataUnit> giphyList = new ArrayList<>();

        for (Data singleGiphy : responseData.data) {
            giphyList.add(DataUnit.getDataUnit(singleGiphy.images.downSampled.imageUrl));
        }
        return giphyList;
    }

    private void searchGiphyQuery(String query) {
        service.getQueryGiphyRX(query,GiphyAPIService.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseData>() {
                    @Override
                    public void call(ResponseData responseData) {
                        progressBar.setVisibility(View.GONE);
                        ArrayList<DataUnit> temp = getGiphyObjectsOutOfResponse(responseData);
                        Collections.reverse(temp);
                        for (DataUnit temData: temp){
                            giphyList.add(0, temData);
                        }
                        adapter.setNewData(giphyList);
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
            searchView.setQuery("", false);
            searchView.clearFocus();

            giphyList.clear();
            getPopularGiphys();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}