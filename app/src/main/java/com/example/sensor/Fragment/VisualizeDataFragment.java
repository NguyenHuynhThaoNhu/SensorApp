package com.example.sensor.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sensor.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VisualizeDataFragment extends Fragment {

    private LineChart temperatureChart;
    private BarChart humidityChart;
    private CandleStickChart lightChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_visualize, container, false);
        temperatureChart = view.findViewById(R.id.temperatureChart);
        humidityChart = view.findViewById(R.id.humidityChart);
        lightChart = view.findViewById(R.id.lightChart);

        // Kết nối với Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference temperatureRef = database.getReference("temperature");
        DatabaseReference humidityRef = database.getReference("humidity");
        DatabaseReference lightRef = database.getReference("light");

        // Lắng nghe sự thay đổi trong Firebase Realtime Database
        temperatureRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Lấy danh sách các phần tử trong mảng "data"
                Iterable<DataSnapshot> temperatureDataList = dataSnapshot.child("data").getChildren();


                // Tạo danh sách các Entry để lưu trữ dữ liệu nhiệt độ trên biểu đồ
                List<Entry> temperatureEntries = new ArrayList<>();

                // Lặp qua từng phần tử trong danh sách
                int index = 0;
                for (DataSnapshot temperatureData : temperatureDataList) {
                    // Lấy giá trị nhiệt độ từ phần tử
                    float temperatureValue = temperatureData.child("value").getValue(Float.class);

                    // Thêm Entry mới vào danh sách
                    temperatureEntries.add(new Entry(index, temperatureValue));
                    index++;
                }

                // Tạo LineDataSet và LineData từ danh sách các Entry
                LineDataSet temperatureDataSet = new LineDataSet(temperatureEntries, "Temperature");
                LineData lineData = new LineData(temperatureDataSet);
                temperatureDataSet.setColor(Color.RED); // Đặt màu đỏ cho đường nhiệt độ
                temperatureDataSet.setCircleColor(Color.RED); // Đặt màu đỏ cho điểm nhiệt độ

                // Đặt LineData cho biểu đồ nhiệt độ
                temperatureChart.setData(lineData);

                // Cập nhật biểu đồ nhiệt độ
                temperatureChart.notifyDataSetChanged();
                temperatureChart.setVisibleXRangeMaximum(5);
                temperatureChart.moveViewToX(lineData.getEntryCount());

                // Vẽ lại biểu đồ nhiệt độ trên giao diện
                temperatureChart.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình lấy dữ liệu từ Firebase Realtime Database
                Log.e("VisualizeDataFragment", "Failed to read temperature value.", databaseError.toException());
                Toast.makeText(getContext(), "Failed to read temperature value.", Toast.LENGTH_SHORT).show();
            }
        });

        humidityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Xử lý dữ liệu độ ẩm
                // dataSnapshot chứa dữ liệu mới nhất từ Firebase Realtime Database

                // Lấy danh sách các phần tử trong mảng "data"
                Iterable<DataSnapshot> humidityDataList = dataSnapshot.child("data").getChildren();

                // Tạo danh sách các Entry để lưu trữ dữ liệu độ ẩm trên biểu đồ BarChart
                List<BarEntry> humidityEntries = new ArrayList<>();

                // Lặp qua từng phần tử trong danh sách
                int index = 0;
                for (DataSnapshot humidityData : humidityDataList) {
                    // Lấy giá trị độ ẩm từ phần tử
                    float humidityValue = humidityData.child("value").getValue(Float.class);

                    // Thêm BarEntry mới vào danh sách
                    humidityEntries.add(new BarEntry(index, humidityValue));
                    index++;
                }

                // Tạo BarDataSet và BarData từ danh sách các Entry
                BarDataSet humidityDataSet = new BarDataSet(humidityEntries, "Humidity");
                BarData barData = new BarData(humidityDataSet);
                humidityDataSet.setColors(new int[]{Color.GREEN, Color.YELLOW, Color.RED}); // Đặt màu cho các cột theo một danh sách màu

                // Đặt BarData cho biểu đồ độ ẩm
                humidityChart.setData(barData);

                // Cập nhật biểu đồ độ ẩm
                humidityChart.notifyDataSetChanged();
                humidityChart.setVisibleXRangeMaximum(5);
                humidityChart.moveViewToX(barData.getEntryCount());

                // Vẽ lại biểu đồ độ ẩm trên giao diện
                humidityChart.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình lấy dữ liệu từ Firebase Realtime Database
                Log.e("VisualizeDataFragment", "Failed to read humidity value.", databaseError.toException());
                Toast.makeText(getContext(), "Failed to read humidity value.", Toast.LENGTH_SHORT).show();
            }
        });

        lightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Xử lý dữ liệu ánh sáng
                // dataSnapshot chứa dữ liệu mới nhất từ Firebase Realtime Database

                // Lấy danh sách các phần tử trong mảng "data"
                Iterable<DataSnapshot> lightDataList = dataSnapshot.child("data").getChildren();

                // Tạo danh sách các Entry để lưu trữ dữ liệu ánh sáng trên biểu đồ CandleStickChart
                List<CandleEntry> lightEntries = new ArrayList<>();

                // Lặp qua từng phần tử trong danh sách
                int index = 0;
                for (DataSnapshot lightData : lightDataList) {
                    // Lấy giá trị ánh sáng từ phần tử
                    float lightValue = lightData.child("value").getValue(Float.class);

                    // Thêm CandleEntry mới vào danh sách
                    lightEntries.add(new CandleEntry(index, lightValue, lightValue, lightValue, lightValue));
                    index++;
                }

                // Tạo CandleDataSet và CandleData từ danh sách các Entry
                CandleDataSet lightDataSet = new CandleDataSet(lightEntries, "Light");
                CandleData candleData = new CandleData(lightDataSet);
                lightDataSet.setDecreasingColor(Color.GREEN); // Đặt màu xanh lá cho thanh giảm giá trị ánh sáng
                lightDataSet.setIncreasingColor(Color.RED); // Đặt màu đỏ cho thanh tăng giá trị ánh sáng
                lightDataSet.setNeutralColor(Color.GRAY); // Đặt màu xám cho thanh không thay đổi giá trị ánh sáng

                // Đặt CandleData cho biểu đồ ánh sáng
                lightChart.setData(candleData);

                // Cập nhật biểu đồ ánh sáng
                lightChart.notifyDataSetChanged();
                lightChart.setVisibleXRangeMaximum(5);
                lightChart.moveViewToX(candleData.getEntryCount());

                // Vẽ lại biểu đồ ánh sáng trên giao diện
                lightChart.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình lấy dữ liệu từ Firebase Realtime Database
                Log.e("VisualizeDataFragment", "Failed to read light value.", databaseError.toException());
                Toast.makeText(getContext(), "Failed to read light value.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
