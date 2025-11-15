package com.syber.ssspltd.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.anychart.graphics.vector.SolidFill;
import com.syber.ssspltd.databinding.ActivityGraphTestBinding;

import java.util.ArrayList;
import java.util.List;

public class GraphTestActivity extends AppCompatActivity {

    ActivityGraphTestBinding binding;
    Context mContext = this;
    Pie pie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGraphTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      //  binding.dashboardGraph.setProgressBar(findViewById(R.id.progress_bar));

         pie = AnyChart.pie();
        GetDashboardDetails();


    }

    private void GetDashboardDetails() {
        List<DataEntry> data = new ArrayList<>();

        data.add(new ValueDataEntry("Apples", 154646));
        data.add(new ValueDataEntry("Bananas",  6371664));
        data.add(new ValueDataEntry("Pears", 789622));
        data.add(new ValueDataEntry("Grapes", 1486621));
        data.add(new ValueDataEntry("Oranges", 1200000));

        pie.data(data);
        pie.palette().itemAt(0, new SolidFill("#000000",1));

        pie.title("Fruits imported in 2015 (in kg)");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Retail channels")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        binding.dashboardGraph.setChart(pie);
    }
}