package com.example.sensor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sensor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SystemActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        // Kết nối với Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference temperatureRef = firebaseDatabase.getReference("temperature1/value");
        DatabaseReference humidityRef = firebaseDatabase.getReference("humidity1/value");
        DatabaseReference lightRef = firebaseDatabase.getReference("light1/value");

        // Lắng nghe sự thay đổi trong Firebase Realtime Database cho nhiệt độ
        temperatureRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double temperatureValue = dataSnapshot.getValue(Double.class);
                if (temperatureValue != null) {
                    TextView temperatureTextView = findViewById(R.id.progress_texttemp);
                    temperatureTextView.setText(String.valueOf(temperatureValue));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SystemActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Lắng nghe sự thay đổi trong Firebase Realtime Database cho độ ẩm
        humidityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double humidityValue = dataSnapshot.getValue(Double.class);
                if (humidityValue != null) {
                    TextView humidityTextView = findViewById(R.id.progress_texthumi);
                    humidityTextView.setText(String.valueOf(humidityValue));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SystemActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Lắng nghe sự thay đổi trong Firebase Realtime Database cho ánh sáng
        lightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double lightValue = dataSnapshot.getValue(Double.class);
                if (lightValue != null) {
                    TextView lightTextView = findViewById(R.id.progress_textlight);
                    lightTextView.setText(String.valueOf(lightValue));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SystemActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}