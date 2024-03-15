package com.blablacar4v;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blablacar4v.models.Travel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RideActivity extends AppCompatActivity {
    ImageView perfilView;
    TextView texDeparturePlace;
    TextView textDepartureDate;
    TextView textDepartureHour;
    TextView textArrivalPlace;
    TextView textDescription;
    FloatingActionButton floatingHome;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        Travel travel = getIntent().getParcelableExtra("travel");
        if (travel == null) {

            Log.d("TAG", "///////////////Error getting documents: ");
        }
        floatingHome = findViewById(R.id.floatingHome);
        perfilView = findViewById(R.id.perfilView);
        texDeparturePlace = findViewById(R.id.textDeparturePlace);
        textDepartureDate = findViewById(R.id.textDepartureDate);
        textDepartureHour = findViewById(R.id.textDepartureHour);
        textArrivalPlace = findViewById(R.id.textArrivalPlace);
        perfilView.setImageResource(R.drawable.user);
        texDeparturePlace.setText("🏠 " + travel.getDeparturePlace());
        textDepartureDate.setText("📅 " + travel.getDepartureDate());
        textDepartureHour.setText("🕒 " + travel.getDepartureHour());
        textArrivalPlace.setText("📍 " + travel.getArrivalPlace());
        textDescription = findViewById(R.id.textDescription);
        Log.d("TAG", "////////////" + travel.getDescription());
        if(travel.getDescription().isEmpty()){
            textDescription.setText("No hay descripción");
        }else {
            textDescription.setText(travel.getDescription());
        }
        floatingHome.setOnClickListener(v -> {
            finish();
        });



    }



}