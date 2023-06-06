package com.example.sensor.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sensor.Activity.HumidityActivity;
import com.example.sensor.Activity.TemperatureActivity;
import com.example.sensor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TemperatureFragment extends Fragment {

    private ImageButton btnNext;
    private TextView progressTextTemp;
    private ProgressBar progressTemp;
    private DatabaseReference databaseReference;
    private RelativeLayout progressLayoutTemp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_temperature, container, false);
        btnNext = view.findViewById(R.id.btnnext);
        progressTextTemp = view.findViewById(R.id.progress_texttemp);
        progressTemp = view.findViewById(R.id.progress_temp);
        progressLayoutTemp = view.findViewById(R.id.progress_layouttemp);

        // Kết nối với Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("temperature1/value");

        // Lắng nghe sự thay đổi trong Firebase Realtime Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xử lý dữ liệu từ Firebase Realtime Database

                // Hiển thị ProgressBar
                progressLayoutTemp.setVisibility(View.VISIBLE);

                // Đọc dữ liệu từ Firebase Realtime Database
                Double value = dataSnapshot.getValue(Double.class);
                if (value != null) {
                    String temperatureValue = String.valueOf(value);
                    progressTextTemp.setText(temperatureValue);
                }

                // Ẩn ProgressBar sau khi hoàn thành xử lý
                progressLayoutTemp.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình lấy dữ liệu từ Firebase Realtime Database

                // Ẩn ProgressBar nếu có lỗi xảy ra
                progressLayoutTemp.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nhấn nút btnMain
                // Ví dụ: Chuyển đến MainActivity
                Intent intent = new Intent(getContext(), HumidityActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
