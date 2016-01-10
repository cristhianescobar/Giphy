package com.cristhianescobar.giphyappapi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.recyclerview_list)
    RecyclerView mRecyclerView;

    ImageDataAdapter adapter;

    public static final String API_BASE_URL = "http://api.giphy.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


//        adapter = new ImageDataAdapter(this, new ArrayList<DataUnit>());
//        mRecyclerView.setAdapter(adapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
    List result;

    @NonNull
    private List getSourceData() {

        result = new ArrayList<DataUnit>();
        for (int i = 0; i < 20; i++) {
            DataUnit d = getDataUnit("Giphy : " + i);
            result.add(d);
        }
        return result;
    }

    @NonNull
    private DataUnit getDataUnit(String name) {
        DataUnit d = new DataUnit();
        d.iconId = R.drawable.ic_cast_dark;
        d.title = name;
        return d;
    }

    @OnClick(R.id.fab)
    public void submit(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyGiphyAPIService service = retrofit.create(MyGiphyAPIService.class);

        Call<MyGiphyAPIService.ResponseData> call = service.getTrendingGiphys();
        call.enqueue(new Callback<MyGiphyAPIService.ResponseData>() {
            @Override
            public void onResponse(final Response<MyGiphyAPIService.ResponseData> response, Retrofit retrofit) {



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Good Response", Toast.LENGTH_LONG).show();
                        MyGiphyAPIService.ResponseData dResponse = response.body();
                        List<DataUnit> giphyList = new ArrayList<DataUnit>();

                        for(MyGiphyAPIService.Data singleGiphy : dResponse.data){
                            giphyList.add(getDataUnit(singleGiphy.images.downSampled.imageUrl));
                        }

                        adapter = new ImageDataAdapter(getBaseContext(), giphyList);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

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
