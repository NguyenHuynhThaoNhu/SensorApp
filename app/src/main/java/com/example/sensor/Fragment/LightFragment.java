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
import com.example.sensor.Activity.LightActivity;
import com.example.sensor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LightFragment extends Fragment {
    private ImageButton btnBack;
    private TextView progressTextLight;
    private ProgressBar progressLight;
    private DatabaseReference databaseReference;
    private RelativeLayout progressLayoutLight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_light, container, false);

        btnBack = view.findViewById(R.id.btnback);
        progressTextLight = view.findViewById(R.id.progress_textlight);
        progressLight = view.findViewById(R.id.progress_light);
        progressLayoutLight = view.findViewById(R.id.progress_layoutlight);

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
                    String lightValue = String.valueOf(value);
                    progressTextLight.setText(lightValue);
                }

                // Ẩn ProgressBar sau khi hoàn thành xử lý
                progressLayoutLight.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình lấy dữ liệu từ Firebase Realtime Database

                // Ẩn ProgressBar nếu có lỗi xảy ra
                progressLayoutLight.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nhấn nút btnBack
                // Ví dụ: Chuyển đến HumidityActivity
                Intent intent = new Intent(getContext(), HumidityActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
