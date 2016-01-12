package com.cristhianescobar.giphyappapi.activities;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

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

    private String url;

    View.OnLongClickListener imageClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(url);
            Toast.makeText(GiphyActivity.this, "Copied image url to clipboard", Toast.LENGTH_SHORT).show();
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giphy);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra("url");
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
