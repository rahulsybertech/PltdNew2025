package com.syber.ssspltd.activitys;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;

import java.util.Objects;

public class FAQActivity extends AppCompatActivity {
    ImageView faqButton, faqButton1, faqButton2, faqButton3, faqButton4, faqButton6, faqButton7, faqButton8, faqButton9;
    TextView faqItem, faqItem1, faqItem2, faqItem3, faqItem4, faqItem6, faqItem7, faqItem8, faqItem9;
    boolean isUpOpen = false;
    Context mContext=this;
    FloatingActionButton support_flo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        View rootView = findViewById(android.R.id.content);

        ViewCompat.setOnApplyWindowInsetsListener(rootView, (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        support_flo = findViewById(R.id.support_fab);
        support_flo.setOnClickListener(v ->
                Lazy.openDialog(mContext));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("FAQ");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        faqButton = findViewById(R.id.faqButton);
        faqButton1 = findViewById(R.id.faqButton1);
        faqButton2 = findViewById(R.id.faqButton2);
        faqButton3 = findViewById(R.id.faqButton3);
        faqButton4 = findViewById(R.id.faqButton4);
        faqButton6 = findViewById(R.id.faqButton6);
        faqButton7 = findViewById(R.id.faqButton7);
        faqButton8 = findViewById(R.id.faqButton8);
        faqButton9 = findViewById(R.id.faqButton9);


        faqItem = findViewById(R.id.faqItem);
        faqItem1 = findViewById(R.id.faqItem1);
        faqItem2 = findViewById(R.id.faqItem2);
        faqItem3 = findViewById(R.id.faqItem3);
        faqItem4 = findViewById(R.id.faqItem4);
        faqItem6 = findViewById(R.id.faqItem6);
        faqItem7 = findViewById(R.id.faqItem7);
        faqItem8 = findViewById(R.id.faqItem8);
        faqItem9 = findViewById(R.id.faqItem9);

        faqItem.setVisibility(View.GONE);
        faqItem1.setVisibility(View.GONE);
        faqItem2.setVisibility(View.GONE);
        faqItem3.setVisibility(View.GONE);
        faqItem4.setVisibility(View.GONE);
        faqItem6.setVisibility(View.GONE);
        faqItem7.setVisibility(View.GONE);
        faqItem8.setVisibility(View.GONE);
        faqItem9.setVisibility(View.GONE);
        faqButton.setOnClickListener(v -> faqItem.setVisibility(faqItem.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
        faqButton1.setOnClickListener(v -> faqItem1.setVisibility(faqItem1.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
        faqButton2.setOnClickListener(v -> faqItem2.setVisibility(faqItem2.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
        faqButton3.setOnClickListener(v -> faqItem3.setVisibility(faqItem3.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));

        faqButton4.setOnClickListener(v -> faqItem4.setVisibility(faqItem4.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
        faqButton6.setOnClickListener(v -> faqItem6.setVisibility(faqItem6.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
        faqButton7.setOnClickListener(v -> faqItem7.setVisibility(faqItem7.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
        faqButton8.setOnClickListener(v -> faqItem8.setVisibility(faqItem8.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
        faqButton9.setOnClickListener(v -> faqItem9.setVisibility(faqItem9.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}