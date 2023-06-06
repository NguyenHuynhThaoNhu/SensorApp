package com.example.sensor.Activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sensor.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LinearActivity extends AppCompatActivity {
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();

        // ScatterChart cho Training Data
        ScatterChart trainingChart = findViewById(R.id.TrainingChart);
        retrieveAndPlotScatterData(databaseRef.child("x_train"), databaseRef.child("y_train"), trainingChart, Color.BLUE, "Dữ liệu huấn luyện");

        // ScatterChart cho Testing Data
        ScatterChart testingChart = findViewById(R.id.TestingChart);
        retrieveAndPlotScatterData(databaseRef.child("x_test"), databaseRef.child("y_test"), testingChart, Color.RED, "Dữ liệu kiểm tra");

        // LineChart cho Linear Regression
        LineChart linearChart = findViewById(R.id.LinearChart);
        retrieveAndPlotLineData(databaseRef.child("x_train"), databaseRef.child("y_train"), linearChart, Color.GREEN, "Linear Regression");

        // CombinedChart cho Data Visualization and Linearity
        CombinedChart combinedChart = findViewById(R.id.DataChart);
        retrieveAndPlotCombinedData(databaseRef.child("x_train"), databaseRef.child("y_train"), databaseRef.child("x_test"), databaseRef.child("y_test"), combinedChart);
    }

    private void retrieveAndPlotCombinedData(DatabaseReference xTrainRef, DatabaseReference yTrainRef, DatabaseReference xTestRef, DatabaseReference yTestRef, final CombinedChart chart) {
        xTrainRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Entry> trainingEntries = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String xKey = snapshot.getKey();
                    double x = Double.parseDouble(xKey.replace(",", ".")); // Thay thế dấu phẩy bằng dấu chấm

                    double y = snapshot.getValue(Double.class);

                    trainingEntries.add(new Entry((float) x, (float) y));
                }

                ScatterDataSet trainingDataSet = new ScatterDataSet(trainingEntries, "Dữ liệu huấn luyện");
                trainingDataSet.setColor(Color.BLUE);

                xTestRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Entry> testingEntries = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String xKey = snapshot.getKey();
                            double x = Double.parseDouble(xKey.replace(",", ".")); // Thay thế dấu phẩy bằng dấu chấm

                            double y = snapshot.getValue(Double.class);

                            testingEntries.add(new Entry((float) x, (float) y));
                        }

                        ScatterDataSet testingDataSet = new ScatterDataSet(testingEntries, "Dữ liệu kiểm tra");
                        testingDataSet.setColor(Color.RED);

                        List<IScatterDataSet> scatterDataSets = new ArrayList<>();
                        scatterDataSets.add(trainingDataSet);
                        scatterDataSets.add(testingDataSet);

                        ScatterData scatterData = new ScatterData(scatterDataSets);

                        CombinedData combinedData = new CombinedData();
                        combinedData.setData(scatterData);

                        chart.setData(combinedData);
                        chart.invalidate();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý sự kiện onCancelled nếu cần
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý sự kiện onCancelled nếu cần
            }
        });
    }

    private void retrieveAndPlotScatterData(DatabaseReference xRef, DatabaseReference yRef, final ScatterChart chart, final int color, final String label) {
        xRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Entry> entries = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String xKey = snapshot.getKey();
                    double x = Double.parseDouble(xKey.replace(",", ".")); // Thay thế dấu phẩy bằng dấu chấm

                    double y = snapshot.getValue(Double.class);

                    entries.add(new Entry((float) x, (float) y));
                }

                ScatterDataSet dataSet = new ScatterDataSet(entries, label);
                dataSet.setColor(color);

                ScatterData scatterData = new ScatterData(dataSet);

                chart.setData(scatterData);
                chart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý sự kiện onCancelled nếu cần
            }
        });
    }

    private void retrieveAndPlotLineData(DatabaseReference xRef, DatabaseReference yRef, final LineChart chart, final int color, final String label) {
        xRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Entry> entries = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String xKey = snapshot.getKey();
                    double x = Double.parseDouble(xKey.replace(",", ".")); // Thay thế dấu phẩy bằng dấu chấm

                    double y = snapshot.getValue(Double.class);

                    entries.add(new Entry((float) x, (float) y));
                }

                LineDataSet dataSet = new LineDataSet(entries, label);
                dataSet.setColor(color);
                dataSet.setDrawCircles(false);

                List<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(dataSet);

                LineData lineData = new LineData(dataSets);

                chart.setData(lineData);
                chart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý sự kiện onCancelled nếu cần
            }
        });
    }
}
