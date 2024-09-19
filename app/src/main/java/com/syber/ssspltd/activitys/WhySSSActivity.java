package com.syber.ssspltd.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.adapter.WhySSSAdapter;
import com.syber.ssspltd.response.WhysssRespons.WhySSS_Response;

import java.util.ArrayList;
import java.util.List;

public class WhySSSActivity extends AppCompatActivity {
    Context mContext=this;
    FloatingActionButton supportFab;
    WhySSSAdapter whySSSAdapter;
   List<WhySSS_Response> whySSSResponseList;
   RecyclerView whySSS_recycler;
   String [] sn_num={"1 -","2 -","3 -","4 -","5 -","6 -","7 -","8 -","9 -","10 -","11 -","12 -","13 -","14 -","15 -","16 -","17 -","18 -","19 -","20 -"};
   String [] text_name={"क्या आप बाजार से भी सस्ता माल खरीदना चाहते हैं?"," क्या आप TOP MOST BRANDS का माल डायरेक्ट फैक्ट्री से लेनाचाहते हैं ?" +
           "क्या आप 5000+ SUPPLIERS से एक साथ जुड़ना चाहते हैं ?"
           ,"क्या आप WHATSAPP के द्वारा 14000+ BRANDS के SAMPLES देखना चाहते हैं?"
           ,"क्या आप REGULAR GST भरने वाले SUPPLIERS से काम करना चाहते हैं?"
           ,"क्या आप अपने व्यापार में FINANCE चाहते हैं?"
           ,"क्या आप PROBLEM FREE माल की पैकिंग करवाना चाहते हैं?"
           ,"क्या आप अनुभवी SALES MAN के साथ MARKETING करना चाहते हैं ?"
           ,"क्या आप ACCOUNTING की पुरी जानकारी ONE ROOF के निचे चाहते हैं?"
           ,"क्या आप MOBILE APP के THROUGH LEDGER, SALE BILL, PURCHASE BILL, BILTY, CURRIER,REPORT इत्यादि देखना चाहते हैं ?"
           ,"क्या आप ALL INDIA काम करके भी एक ही ACCOUNT में PAYMENT भेजना चाहते हैं ?"
           ,"क्या आप REAL TIME PAYMENT CONFIRMATION चाहते हैं?"
           ,"क्या आप बैंक से ज्यादा INTEREST पाना चाहते हैं?"
           ,"क्या आप व्यापार में TRANSPARENCY चाहते हैं?"
           ,"क्या आप PURCHASING करके EXTRA DISCOUNT सोना, चांदी, गाड़ी इत्यादि पाना चाहते हैं ?"
           ,"क्या आप व्यापार करने के साथ साथ देश विदेश घूमना चाहते हैं ?"
           ," क्या आप GARMENT FAIR में हिस्सा लेना चाहते हैं ?"
           ,"क्या आप FREE BUSINESS DEVELOPMENT SEMINAR में भाग लेना चाहते हैं ?"
           ,"क्या आप अपने से बड़े दुकानदार का साथ चाहते हैं ताकि आप भी अपनी दुकान बड़ी कर सकें ?"
           ,"क्या आप अपने घर से दूर रहकर भी घर जैसा खाना एवम् रहने की सुविधा चाहते हैं ?"};
   WhySSS_Response whySSSResponse_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_why_sssactivity);
        whySSSResponseList =new ArrayList<>();
        whySSS_recycler=findViewById(R.id.whySSS_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        whySSS_recycler.setLayoutManager(linearLayoutManager);

        for (int i =0; i<text_name.length;i++)
        {
            whySSSResponse_type = new WhySSS_Response( sn_num[i],text_name[i]);
            whySSSResponseList.add(whySSSResponse_type);
        }
        whySSSAdapter = new WhySSSAdapter(mContext, whySSSResponseList);
        whySSS_recycler.setAdapter(whySSSAdapter);

        supportFab= findViewById(R.id.support_fab);
        supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Why SSS");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}