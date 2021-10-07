package com.example.alldata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        bottomNavigation = findViewById(R.id.bottomnavigation);
        bottomNavigation.setOnItemSelectedListener(navlistn);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout , new texts()).commit();
    }


    private NavigationBarView.OnItemSelectedListener navlistn = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected_fragment = null;

            switch (item.getItemId()){
                case R.id.photos:
                    selected_fragment = new photosfragment();
                    break;
                case R.id.messages:
                    selected_fragment = new texts();
                    break;
                case R.id.pdfs:
                    selected_fragment = new pdfsfragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout , selected_fragment).commit();
            return true;
        }
    };
}