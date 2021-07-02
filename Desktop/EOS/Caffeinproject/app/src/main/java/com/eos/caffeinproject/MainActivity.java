package com.eos.caffeinproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;

    HomeActivity homeFragment = new HomeActivity();
    ZzimActivity zzimFragment = new ZzimActivity();
    SearchActivity searchFragment = new SearchActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.nav_view);
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,homeFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.logo_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeActivity()).commit();
                        break;
                    case R.id.logo_zzim:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ZzimActivity()).commit();
                        break;
                    case R.id.logo_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SearchActivity()).commit();
                        break;
                }
                return true;
            }
        });




    }


}