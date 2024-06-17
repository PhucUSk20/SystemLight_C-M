package com.project.SLCAM;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.CircularGauge;
import com.anychart.core.gauge.pointers.Bar;
import com.anychart.core.gauge.pointers.Marker;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.SolidFill;
import com.anychart.data.Set;
import com.anychart.data.Mapping;
import com.anychart.core.cartesian.series.Line;
import com.anychart.graphics.vector.Stroke;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.triggertrap.seekarc.SeekArc;

import java.util.ArrayList;
import java.util.List;

public class TrafficLightDetailActivity extends AppCompatActivity {

    private TextView brightnessValue;
    private TextView environmentBrightnessValue;
    private SeekArc seekArc;
    private AnyChartView chartView;
    private AnyChartView gaugeEnvironment;

    private DatabaseReference lightLevelReference;
    private DatabaseReference lightEnvironmentReference;
    private int progress = 0;
    private int environmentProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_brightness_layout);

        brightnessValue = findViewById(R.id.brightness_value);
        environmentBrightnessValue = findViewById(R.id.environment_brightness_value);
        seekArc = findViewById(R.id.seekArc);
        chartView = findViewById(R.id.chartView);
        gaugeEnvironment = findViewById(R.id.gaugeEnvironment);

        setupSeekArc();
        setupFirebase();
        setupChart();
        setupGauge();
    }

    private void setupSeekArc() {
        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int progress2, boolean fromUser) {
                if (fromUser) {
                    brightnessValue.setText("Brightness: " + progress2 + "%");
                    progress = progress2;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
                // Handle touch start event if needed
            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
                updateLedBrightness(progress);
            }
        });
    }

    private void setupFirebase() {
        // Initialize Firebase Database references
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("led1");
        lightLevelReference = databaseReference.child("DimmingLevel");
        lightEnvironmentReference = databaseReference.child("LightEnvironment");

        // Add listeners to read data from Firebase
        lightLevelReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer levelLight = dataSnapshot.getValue(Integer.class);
                if (levelLight != null) {
                    progress = levelLight;
                    seekArc.setProgress(progress);
                    brightnessValue.setText("Brightness: " + progress + "%");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error if needed
            }
        });

        lightEnvironmentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer environmentLight = dataSnapshot.getValue(Integer.class);
                if (environmentLight != null) {
                    environmentProgress = environmentLight;
                    environmentBrightnessValue.setText("Cường độ sáng môi trường: " + environmentProgress + " lux");
                    updateGauge(environmentProgress);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error if needed
            }
        });
    }

    private void updateLedBrightness(int brightnessPercent) {
        lightLevelReference.setValue(brightnessPercent);
    }

    private void setupChart() {
        AnyChartView anyChartView = findViewById(R.id.chartView);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Trend of Sales of the Most Popular Products of ACME Corp.");

        cartesian.yAxis(0).title("Number of Bottles Sold (thousands)");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new CustomDataEntry("1986", 3.6, 2.3, 2.8));
        seriesData.add(new CustomDataEntry("1987", 7.1, 4.0, 4.1));
        seriesData.add(new CustomDataEntry("1988", 8.5, 6.2, 5.1));
        seriesData.add(new CustomDataEntry("1989", 9.2, 11.8, 6.5));
        seriesData.add(new CustomDataEntry("1990", 10.1, 13.0, 12.5));
        seriesData.add(new CustomDataEntry("1991", 11.6, 13.9, 18.0));
        seriesData.add(new CustomDataEntry("1992", 16.4, 18.0, 21.0));
        seriesData.add(new CustomDataEntry("1993", 18.0, 23.3, 20.3));
        seriesData.add(new CustomDataEntry("1994", 13.2, 24.7, 19.2));
        seriesData.add(new CustomDataEntry("1995", 12.0, 18.0, 14.4));
        seriesData.add(new CustomDataEntry("1996", 3.2, 15.1, 9.2));
        seriesData.add(new CustomDataEntry("1997", 4.1, 11.3, 5.9));
        seriesData.add(new CustomDataEntry("1998", 6.3, 14.2, 5.2));
        seriesData.add(new CustomDataEntry("1999", 9.4, 13.7, 4.7));
        seriesData.add(new CustomDataEntry("2000", 11.5, 9.9, 4.2));
        seriesData.add(new CustomDataEntry("2001", 13.5, 12.1, 1.2));
        seriesData.add(new CustomDataEntry("2002", 14.8, 13.5, 5.4));
        seriesData.add(new CustomDataEntry("2003", 16.6, 15.1, 6.3));
        seriesData.add(new CustomDataEntry("2004", 18.1, 17.9, 8.9));
        seriesData.add(new CustomDataEntry("2005", 17.0, 18.9, 10.1));
        seriesData.add(new CustomDataEntry("2006", 16.6, 20.3, 11.5));
        seriesData.add(new CustomDataEntry("2007", 14.1, 20.7, 12.2));
        seriesData.add(new CustomDataEntry("2008", 15.7, 21.6, 10));
        seriesData.add(new CustomDataEntry("2009", 12.0, 22.5, 8.9));

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Brandy");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Whiskey");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("Tequila");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);

}
    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }

    private void setupGauge() {
        CircularGauge circularGauge = AnyChart.circular();

        circularGauge.data(new SingleValueDataSet(new String[] { "0" }));

        circularGauge.fill("#fff")
                .stroke(null)
                .padding(0d, 0d, 0d, 0d)
                .margin(100d, 100d, 100d, 100d);
        circularGauge.startAngle(270d);
        circularGauge.sweepAngle(180d);

        Bar bar0 = circularGauge.bar(0);
        bar0.dataIndex(0);
        bar0.radius(100d);
        bar0.width(17d);
        bar0.fill(new SolidFill("#64b5f6", 1d));
        bar0.stroke(null);
        bar0.zIndex(5d);

        Marker marker = circularGauge.marker(0);
        marker.dataIndex(0);
        marker.radius(100d);
        marker.fill(new SolidFill("#64b5f6", 1d));
        marker.stroke("1 #64b5f6");
        marker.zIndex(10d);

        circularGauge.label(0d)
                .text("Lux")
                .anchor(Anchor.CENTER)
                .padding(0d, 0d, 10d, 0d)
                .height(17d)
                .offsetY(-20d);

        gaugeEnvironment.setChart(circularGauge);
    }

    private void updateGauge(int value) {
      //  CircularGauge gauge = (CircularGauge) gaugeEnvironment.getChart();
      //  gauge.data(new SingleValueDataSet(new String[] { String.valueOf(value) }));
    }
}
