package com.blablacar4v;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapActivity extends AppCompatActivity {
    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationClient;
    private AtomicReference<LatLng> firstClickLocation = new AtomicReference<>();
    private AtomicReference<LatLng> secondClickLocation = new AtomicReference<>();
    private AtomicInteger clickCount = new AtomicInteger();
    FloatingActionButton buttonBack;
    Button buttonAcept;
    EditText editArrivalPlace;
    EditText editDeparturePlace;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        createFragment();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        editArrivalPlace = findViewById(R.id.editArrivalPlace);
        editDeparturePlace = findViewById(R.id.editDeparturePlace);
        buttonBack = findViewById(R.id.buttonBack);
        buttonAcept = findViewById(R.id.buttonAcept);
        buttonAcept.setOnClickListener(v -> {
            if (clickCount.get() == 2) {
                Intent intent = new Intent(this, SetUpRideActivity.class);
                intent.putExtra("departurePlace", editDeparturePlace.getText().toString());
                intent.putExtra("arrivalPlace", editArrivalPlace.getText().toString());
                Log.d("MapActivity", "onCreate: " + editDeparturePlace.getText() + " " + editArrivalPlace.getText());
                setResult(RESULT_OK, intent);
                finish();
            } else {
                finish();
            }
        });
        buttonBack.setOnClickListener(v -> finish());
    }

    private void createFragment() {
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.map, fragment).commit();
        } else {
            fragment.getMapAsync(this::onMapReady);
        }

    }

    private void onMapReady(GoogleMap googleMap) {

        this.map = googleMap;
        Geocoder geocoder = new Geocoder(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Si no se tienen permisos, solicitarlos
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        map.setMyLocationEnabled(true);

        //Unir los dos puntos marcados
        map.setOnMapClickListener(latLng -> {

            if (clickCount.getAndIncrement() == 0) {
                firstClickLocation.set(latLng);
                map.addMarker(new MarkerOptions().position(latLng).title("Lugar de salida"));
                try {
                    // Obtener la lista de direcciones a partir de las coordenadas
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    if (addresses != null && addresses.size() > 0) {
                        // Obtener la primera dirección de la lista (la más relevante)
                        Address address = addresses.get(0);

                        // Obtener el nombre de la ubicación
                        editDeparturePlace.setText(address.getThoroughfare() + ", " + address.getLocality());

                        // Imprimir el nombre de la ubicación

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (clickCount.get() == 2) {
                secondClickLocation.set(latLng);
                map.addMarker(new MarkerOptions().position(latLng).title("Lugar de llegada"));
                try {
                    // Obtener la lista de direcciones a partir de las coordenadas
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    if (addresses != null && addresses.size() > 0) {
                        // Obtener la primera dirección de la lista (la más relevante)
                        Address address = addresses.get(0);

                        // Obtener el nombre de la ubicación
                        editArrivalPlace.setText(address.getThoroughfare() + ", " + address.getLocality());

                        // Imprimir el nombre de la ubicación

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (clickCount.get() == 3) {
                map.clear();
                editArrivalPlace.setText("");
                editDeparturePlace.setText("");
                clickCount.set(0);
            }


        });
        // Habilitar los puntos de interés en el mapa
        map.setIndoorEnabled(true);

// Registrar un OnPoiClickListener para manejar los clics en los puntos de interés
        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {


            @Override
            public void onPoiClick(PointOfInterest poi) {
                // Obtener la información del punto de interés
                if (clickCount.getAndIncrement() == 0){
                    map.addMarker(new MarkerOptions().position(poi.latLng).title("Lugar de salida"));
                    try {
                        // Obtener la lista de direcciones a partir de las coordenadas
                        List<Address> addresses = geocoder.getFromLocation(poi.latLng.latitude, poi.latLng.longitude, 1);

                        if (addresses != null && addresses.size() > 0) {
                            // Obtener la primera dirección de la lista (la más relevante)
                            Address address = addresses.get(0);

                            // Obtener el nombre de la ubicación
                            editDeparturePlace.setText(poi.name + ", " + address.getLocality());

                            // Imprimir el nombre de la ubicación

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if (clickCount.get() == 2) {
                    map.addMarker(new MarkerOptions().position(poi.latLng).title("Lugar de llegada"));
                    try {
                        // Obtener la lista de direcciones a partir de las coordenadas
                        List<Address> addresses = geocoder.getFromLocation(poi.latLng.latitude, poi.latLng.longitude, 1);

                        if (addresses != null && addresses.size() > 0) {
                            // Obtener la primera dirección de la lista (la más relevante)
                            Address address = addresses.get(0);

                            // Obtener el nombre de la ubicación
                            editArrivalPlace.setText(poi.name + ", " + address.getLocality());

                            // Imprimir el nombre de la ubicación

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if (clickCount.get() == 3) {
                    map.clear();
                    editArrivalPlace.setText("");
                    editDeparturePlace.setText("");
                    clickCount.set(0);
                }


                // Mostrar la información del punto de interés como desees
            }
        });


        getAndCenterCurrentLocation();

    }

    private void getAndCenterCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Obtener coordenadas de la ubicación actual
                            LatLng ubicacionActual = new LatLng(location.getLatitude(), location.getLongitude());

                            // Mover la cámara al lugar actual con un nivel de zoom
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual, 15));
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Si se otorgan permisos, iniciar el mapa
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync((OnMapReadyCallback) this);
            } else {
                // Si se deniegan los permisos, manejar la situación según sea necesario
            }
        }
    }

}