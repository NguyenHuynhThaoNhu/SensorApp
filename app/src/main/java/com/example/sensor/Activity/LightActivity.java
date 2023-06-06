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

public class LightActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView progressTextLight;
    private ProgressBar progressLight;
    private DatabaseReference databaseReference;
    private RelativeLayout progressLayoutLight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        btnBack = findViewById(R.id.btnback);
        progressTextLight = findViewById(R.id.progress_textlight);
        progressLight = findViewById(R.id.progress_light);
        progressLayoutLight = findViewById(R.id.progress_layoutlight);

        // Kết nối với Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("light1/value");

        // Lắng nghe sự thay đổi trong Firebase Realtime Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xử lý dữ liệu từ Firebase Realtime Database

                // Hiển thị ProgressBar
                progressLayoutLight.setVisibility(View.VISIBLE);

                // Đọc dữ liệu từ Firebase Realtime Database
                Double value = dataSnapshot.getValue(Double.class);
                if (value != null) {
                    String temperatureValue = String.valueOf(value);
                    progressTextLight.setText(temperatureValue);
                }

//               Ẩn ProgressBar sau khi hoàn thành xử lý
//               progressLayoutTemp.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình lấy dữ liệu từ Firebase Realtime Database

                // Ẩn ProgressBar nếu có lỗi xảy ra
                progressLayoutLight.setVisibility(View.GONE);
                Toast.makeText(LightActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nhấn nút btnMain
                // Ví dụ: Chuyển đến MainActivity
                Intent intent = new Intent(LightActivity.this, HumidityActivity.class);
                startActivity(intent);
            }
        });
    }
}