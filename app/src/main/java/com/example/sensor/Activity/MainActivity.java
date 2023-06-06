package com.example.sensor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sensor.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

        private ViewPager2 viewPager;
        private BottomNavigationView bottomNavigationView;
        private MenuItem prevMenuItem;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            viewPager = findViewById(R.id.vpg_main);
            bottomNavigationView = findViewById(R.id.bottom_menu);

            // Thiết lập adapter cho ViewPager2
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
            viewPager.setAdapter(viewPagerAdapter);

            // Bắt sự kiện khi người dùng chọn một item trong BottomNavigationView
            bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            viewPager.setCurrentItem(0); // Chuyển đến trang thứ 0
                            break;
                        case R.id.navigation_control:
                            viewPager.setCurrentItem(1); // Chuyển đến trang thứ 1
                            break;
                        case R.id.navigation_lab6:
                            viewPager.setCurrentItem(2); // Chuyển đến trang thứ 2
                            break;
                        case R.id.navigation_analyst:
                            viewPager.setCurrentItem(3); // Chuyển đến trang thứ 3
                            break;
                        case R.id.navigation_system:
                            viewPager.setCurrentItem(4); // Chuyển đến trang thứ 4
                            break;
                    }
                    return false;
                }
            });

            // Bắt sự kiện khi trang được chuyển đổi trong ViewPager2
            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    if (prevMenuItem != null) {
                        prevMenuItem.setChecked(false);
                    } else {
                        bottomNavigationView.getMenu().getItem(0).setChecked(false);
                    }
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                    prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                }
            });
        }
    }
