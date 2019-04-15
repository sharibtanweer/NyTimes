package com.NyTimes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class NewsDetailActivity extends AppCompatActivity {

    ImageView back_btn;
    WebView item_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_activity);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        item_detail = (WebView) findViewById(R.id.item_detail);
        item_detail.loadUrl(Settings.URLVALUE);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
