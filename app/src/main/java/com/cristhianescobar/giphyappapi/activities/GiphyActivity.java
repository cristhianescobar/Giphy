package com.cristhianescobar.giphyappapi.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;

import com.cristhianescobar.giphyappapi.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GiphyActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.list_icon)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giphy);
        ButterKnife.bind(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getColor(R.color.colorCalm));
        }

        String url = getIntent().getStringExtra("url");
        toolbar.setTitle(url);
        setSupportActionBar(toolbar);
        Picasso.with(this).load(url)
                .error(android.R.drawable.stat_notify_error)
                .into(imageView);
        webView.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        webView.setVisibility(View.GONE);
        super.onBackPressed();
    }

}
