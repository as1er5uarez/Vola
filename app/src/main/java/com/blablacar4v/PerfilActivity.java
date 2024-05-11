package com.blablacar4v;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class PerfilActivity extends AppCompatActivity {

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        tabLayout = findViewById(R.id.tabLayout);
        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        //Poner el recyclerview desde el inicio
                        Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intentSet = new Intent(PerfilActivity.this, SetUpRideActivity.class);
                        intentSet.putExtra("email", email);
                        startActivity(intentSet);
                        break;
                    case 2:

                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restablecer la selección del tab
        tabLayout.getTabAt(2).select(); // Cambia 0 por el índice del tab que quieras seleccionar por defecto
    }
}

