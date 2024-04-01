package com.blablacar4v;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blablacar4v.models.Travel;
import com.blablacar4v.models.User;
import com.blablacar4v.models.UserTravel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RideActivity extends AppCompatActivity {
    ImageView perfilView;
    TextView texDeparturePlace;
    TextView textDepartureDate;
    TextView textDepartureHour;
    TextView textArrivalPlace;
    TextView textDescription;
    TextView textNumberSeats;
    TextView textDriver;
    FloatingActionButton floatingHome;
    Button buttonAdd;
    private FirebaseFirestore db;
    String email;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        Travel travel = getIntent().getParcelableExtra("travel");
        email = getIntent().getStringExtra("email");
        db = FirebaseFirestore.getInstance();

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
        buttonAdd = findViewById(R.id.buttonAdd);
        textNumberSeats = findViewById(R.id.textNumberSeats);
        textDriver = findViewById(R.id.textDriver);
        texDeparturePlace.setText("🏠 " + travel.getDeparturePlace());
        textDepartureDate.setText("📅 " + travel.getDepartureDate());
        textDepartureHour.setText("🕒 " + travel.getDepartureHour());
        textArrivalPlace.setText("📍 " + travel.getArrivalPlace());
        textDescription = findViewById(R.id.textDescription);
        textNumberSeats.setText(travel.getSeats() + " asientos disponibles");
        Program.management.getUser(travel.getUserPublicated()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<User> user = task.getResult();
                textDriver.setText(user.get(0).getName());
            }
        });
        Log.d("TAG", "////////////" + travel.getDescription());
        if(travel.getDescription().isEmpty()){
            textDescription.setText("No hay descripción");
        }else {
            textDescription.setText(travel.getDescription());
        }
        floatingHome.setOnClickListener(v -> {
            finish();
        });
        buttonAdd.setOnClickListener(v -> {

            db.collection("user-travel").document(travel.getId() + travel.getUserPublicated()).set(new UserTravel(
                    travel.getId(), travel.getUserPublicated()
            )).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    //Log.d("TAG", "///////////////DocumentSnapshot added with ID: " + travel.getId());
                    finish();
                } else {
                    showAlert();
                }
            });
        });
        }

    private void showAlert(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("Se ha producido un error añadiendote al viaje");
            builder.setPositiveButton("Aceptar", null);
            AlertDialog dialog = builder.create();
            dialog.show();

    }






}