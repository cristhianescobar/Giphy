package com.cristhianescobar.giphyappapi.activities;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.cristhianescobar.giphyappapi.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GiphyActivity extends AppCompatActivity {

    public static String URL = "url";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.list_icon)
    ImageView imageView;

    private String url;

    View.OnLongClickListener imageClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(url);
            Snackbar.make(view, "Copied image url to clipboard", Snackbar.LENGTH_LONG).show();
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giphy);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra(URL);
        toolbar.setTitle(url);
        setSupportActionBar(toolbar);
        Picasso.with(this).load(url)
                .error(android.R.drawable.stat_notify_error)
                .into(imageView);
        webView.loadUrl(url);
        imageView.setOnLongClickListener(imageClickListener);
        webView.setOnLongClickListener(imageClickListener);
    }

    @Override
    public void onBackPressed() {
        webView.setVisibility(View.GONE);
        super.onBackPressed();
    }
}
