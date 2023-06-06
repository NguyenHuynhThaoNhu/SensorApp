package com.example.sensor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sensor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HumidityActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private ImageButton btnNext;
    private TextView progressTextHumi;
    private ProgressBar progressHumi;
    private DatabaseReference databaseReference;
    private RelativeLayout progressLayoutHumi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);

        btnBack = findViewById(R.id.btnback);
        btnNext = findViewById(R.id.btnnext);
        progressTextHumi = findViewById(R.id.progress_texthumi);
        progressHumi = findViewById(R.id.progress_humi);
        progressLayoutHumi = findViewById(R.id.progress_layouthumi);

        // Kết nối với Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("humidity1/value");

        // Lắng nghe sự thay đổi trong Firebase Realtime Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xử lý dữ liệu từ Firebase Realtime Database

                // Hiển thị ProgressBar
                progressLayoutHumi.setVisibility(View.VISIBLE);

                // Đọc dữ liệu từ Firebase Realtime Database
                Double value = dataSnapshot.getValue(Double.class);
                if (value != null) {
                    String temperatureValue = String.valueOf(value);
                    progressTextHumi.setText(temperatureValue);
                }

//               Ẩn ProgressBar sau khi hoàn thành xử lý
//               progressLayoutTemp.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình lấy dữ liệu từ Firebase Realtime Database

                // Ẩn ProgressBar nếu có lỗi xảy ra
                progressLayoutHumi.setVisibility(View.GONE);
                Toast.makeText(HumidityActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nhấn nút btnMain
                // Ví dụ: Chuyển đến MainActivity
                Intent intent = new Intent(HumidityActivity.this, LightActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nhấn nút btnMain
                // Ví dụ: Chuyển đến MainActivity
                Intent intent = new Intent(HumidityActivity.this, TemperatureActivity.class);
                startActivity(intent);
            }
        });
    }
}