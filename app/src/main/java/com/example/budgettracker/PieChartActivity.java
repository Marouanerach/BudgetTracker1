
package com.example.budgettracker;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Map;

public class PieChartActivity extends AppCompatActivity {
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> expens = new ArrayList<>();

        DB = new DBHelper(this);
        Map<String,Double> sumCat = DB.expensCat();
        for(Map.Entry m:sumCat.entrySet()){
            Log.w("jesssssssssssss",  " test  "+ m.getKey()+ " "+m.getValue() );
            String  cat = (String) m.getKey();
            Double d= (Double) m.getValue();
            int i=d.intValue();
            expens.add(new PieEntry(i,cat));
        }

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(PieChartActivity.this,"Category: " + ((PieEntry) e).getLabel() + "\n" + "Spent: " + ((PieEntry) e).getValue() + " €", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


        PieDataSet pieDataSet = new PieDataSet(expens,"Expenses");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setCenterText("Expenses");
        pieChart.animate();


    }
}