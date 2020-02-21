package com.codebrewers.loc;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class BottomNavigationMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LandingFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment tempFragment = null;

                switch(item.getItemId()){
                    case R.id.weather:
                        tempFragment=new WeatherFragment();
                        break;
                    case R.id.profile:
                        tempFragment = new ProfileFragment();
                        break;
                    case R.id.landing:
                        tempFragment = new LandingFragment();
                        break;

                    case R.id.chat:
                        tempFragment = new ChatFragment();
                        break;


                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, tempFragment).commit();
                return true;
            }
        });
    }
}
