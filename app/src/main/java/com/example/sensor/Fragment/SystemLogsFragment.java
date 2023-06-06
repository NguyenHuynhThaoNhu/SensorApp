package com.example.sensor.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sensor.Activity.SystemActivity;
import com.example.sensor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SystemLogsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_system, container, false);
        // Kết nối với Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference temperatureRef = firebaseDatabase.getReference("temperature1/value");
        DatabaseReference humidityRef = firebaseDatabase.getReference("humidity1/value");
        DatabaseReference lightRef = firebaseDatabase.getReference("light1/value");

        TextView temperatureTextView = view.findViewById(R.id.progress_texttemp);
        TextView humidityTextView = view.findViewById(R.id.progress_texthumi);
        TextView lightTextView = view.findViewById(R.id.progress_textlight);

        temperatureRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double temperatureValue = dataSnapshot.getValue(Double.class);
                if (temperatureValue != null) {
                    temperatureTextView.setText(String.valueOf(temperatureValue));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Lắng nghe sự thay đổi trong Firebase Realtime Database cho độ ẩm
        humidityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double humidityValue = dataSnapshot.getValue(Double.class);
                if (humidityValue != null) {
                    humidityTextView.setText(String.valueOf(humidityValue));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Lắng nghe sự thay đổi trong Firebase Realtime Database cho ánh sáng
        lightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double lightValue = dataSnapshot.getValue(Double.class);
                if (lightValue != null) {
                    lightTextView.setText(String.valueOf(lightValue));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
