package com.omaressam.browslyze2.Logic;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PieCharting {


   public Authentication auth = new Authentication();
   public  RealtimeDb db = new RealtimeDb();
     HashMap<String , Long> newHash;
     HashMap hash ;

    public PieCharting(HashMap hash , PieChart pieChart) {

               newHash=(HashMap<String, Long>) hash ;
               setChart(pieChart);
     }

   public void setChart (PieChart pieChart)
     {
         PieData pieData;
         PieDataSet pieDataSet;
         ArrayList pieEntries  = new ArrayList<>();
         ArrayList PieEntryLabels;


         for (Map.Entry<String, Long> entry : newHash.entrySet()) {
             if (newHash.get(entry.getKey()) != 0) {
                 pieEntries.add(new PieEntry(((float) newHash.get(entry.getKey())), entry.getKey() ));
             }
         }
         pieDataSet = new PieDataSet(pieEntries, "  ");
         pieData = new PieData(pieDataSet);
         pieData.setValueFormatter(new PercentFormatter());

         pieChart.setData(pieData);

         pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
         pieDataSet.setSliceSpace(2f);
         pieDataSet.setValueTextColor(Color.WHITE);
         pieDataSet.setValueTextSize(10f);
         pieDataSet.setSliceSpace(5f);



     }
}
