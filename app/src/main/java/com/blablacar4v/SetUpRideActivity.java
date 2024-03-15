package com.blablacar4v;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.blablacar4v.models.Travel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetUpRideActivity extends AppCompatActivity {
    FloatingActionButton homeButton;
    TimePickerDialog timePickerDialog;
    EditText editArrivalTime;
    EditText editDepartureTime;
    EditText editDate;
    EditText editDeperaturePlace;
    EditText editArrivalPlace;
    EditText editSeats;
    EditText editDepartureDate;

    private FirebaseFirestore db;
    Button setUpButton;
    Bundle bundle;
    String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_ride);
        homeButton = findViewById(R.id.floatingHome);
        setUpButton = findViewById(R.id.buttonAdd);
        editDeperaturePlace = findViewById(R.id.editDeparturePlace);
        editArrivalPlace = findViewById(R.id.editArrivalPlace);
        bundle = getIntent().getExtras();
        email = bundle.getString("email");
        db = FirebaseFirestore.getInstance();
        editSeats = findViewById(R.id.editSeats);
        editDeperaturePlace.setOnClickListener(v -> {
            Intent intent = new Intent(SetUpRideActivity.this, MapActivity.class);
            intent.putExtra("type", "departure");
            startActivity(intent);
        });


        homeButton.setOnClickListener(v -> finish());
        setUp();
    }

    private void setUp() {
        setUpButton.setOnClickListener(v -> {
            if (!editArrivalTime.getText().toString().isEmpty() || !editDepartureTime.getText().toString().isEmpty() || !editDate.getText().toString().isEmpty() || !editDeperaturePlace.getText().toString().isEmpty() || !editArrivalPlace.getText().toString().isEmpty() || !editSeats.getText().toString().isEmpty()) {
                db.collection("travels").document().set(new Travel(
                        editArrivalTime.getText().toString(),
                        editArrivalPlace.getText().toString(),
                        editDate.getText().toString(),
                        editDepartureTime.getText().toString(),
                        editDeperaturePlace.getText().toString(),
                        "",
                        Integer.parseInt(editSeats.getText().toString()),
                        email
                ));
            }
        });
    }

}