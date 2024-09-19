package com.syber.ssspltd.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.syber.ssspltd.R;

public class BaseLoaderActivity extends AppCompatActivity {
    RelativeLayout showLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_loader);
        showLoader=findViewById(R.id.showLoader);
    }
}