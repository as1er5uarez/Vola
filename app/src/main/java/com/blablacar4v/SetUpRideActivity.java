package com.blablacar4v;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blablacar4v.models.Travel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class SetUpRideActivity extends AppCompatActivity {
    FloatingActionButton homeButton;

    EditText editArrivalTime;
    EditText editDepartureTime;
    EditText editDate;
    Button buttonMap;
    EditText editArrivalPlace;
    EditText editSeats;
    Button buttonDepartureDate;
    Button buttonDepartureTime;
    Button buttonArrivalTime;
    EditText editDescription;



    private FirebaseFirestore db;
    private GoogleMap map;
    Button setUpButton;
    Bundle bundle;
    String email;
    String departurePlace;
    String arrivalPlace;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_ride);
        homeButton = findViewById(R.id.floatingHome);
        setUpButton = findViewById(R.id.buttonAdd);
        buttonMap = findViewById(R.id.buttonMap);
        editArrivalPlace = findViewById(R.id.editArrivalPlace);
        buttonDepartureDate = findViewById(R.id.buttonDepartureDate);
        buttonDepartureTime = findViewById(R.id.buttonDepartureTime);
        editDescription = findViewById(R.id.editDescription);
        buttonArrivalTime = findViewById(R.id.buttonArrivalTime);
        bundle = getIntent().getExtras();
        email = bundle.getString("email");
        db = FirebaseFirestore.getInstance();
        editSeats = findViewById(R.id.editSeats);
        buttonMap.setOnClickListener(v -> {
            Intent intent = new Intent(SetUpRideActivity.this, MapActivity.class);
            intent.putExtra("type", "departure");
            startActivityForResult(intent, 1);
        });
        homeButton.setOnClickListener(v -> {
            finish();
        });
        setUp();

    }

    private void setUp() {
        setUpButton.setOnClickListener(v -> {
            Bundle bun = getIntent().getExtras();

            if (!buttonArrivalTime.getText().toString().isEmpty() || !buttonDepartureTime.getText().toString().isEmpty() || !buttonDepartureDate.getText().toString().isEmpty() || departurePlace.isEmpty() || !arrivalPlace.isEmpty() || !editSeats.getText().toString().isEmpty() || !editDescription.getText().toString().isEmpty()) {
                Program.management.getMaxId().thenAccept(id -> {
                    // Aquí puedes usar el ID obtenido
                    System.out.println("El ID máximo es: " + id);


                db.collection("travels").document(String.valueOf((id))).set(new Travel(
                        buttonDepartureTime.getText().toString(),
                        arrivalPlace,
                        buttonDepartureDate.getText().toString(),
                        buttonArrivalTime.getText().toString(),
                        departurePlace,
                        editDescription.getText().toString(),
                        Integer.parseInt(editSeats.getText().toString()),
                        email,
                        id
                ));
                });
            }
        });
    }
    public void openCalendar(View view) {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear un DatePickerDialog y mostrarlo
        DatePickerDialog datePickerDialog = new DatePickerDialog(SetUpRideActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Cuando el usuario selecciona una fecha, actualizar el TextView con la fecha seleccionada
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        buttonDepartureDate.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    public void openTimePicker(View view) {
        // Obtener la hora actual
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int buttonId = view.getId();
        // Crear un TimePickerDialog y mostrarlo
        TimePickerDialog timePickerDialog = new TimePickerDialog(SetUpRideActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        // Cuando el usuario selecciona una hora, actualizar el TextView con la hora seleccionada
                        String selectedTime = selectedHour + ":" + selectedMinute;
                        //buttonDepartureTime.setText(selectedTime);
                        if (buttonId == R.id.buttonDepartureTime) {
                            buttonDepartureTime.setText(selectedTime);
                        } else if (buttonId == R.id.buttonArrivalTime) {
                            buttonArrivalTime.setText(selectedTime);
                        }
                    }
                }, hour, minute, true); // true para usar formato de 24 horas, false para AM/PM
        timePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {
            // Verificar si los datos están presentes en el Intent
            if (data != null) {
                arrivalPlace = data.getStringExtra("arrivalPlace");
                departurePlace = data.getStringExtra("departurePlace");
                // Usa el dato recibido como desees
            }
        }
    }





}