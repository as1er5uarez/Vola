package com.blablacar4v;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blablacar4v.models.Travel;
import com.blablacar4v.models.User;
import com.blablacar4v.recycler.UserAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

enum ProviderType {
    BASIC;
}
public class MainActivity extends AppCompatActivity {
    Bundle bundle;
    TextView tx;
    Button logoutButton;
    RecyclerView recyclerView;
    UserAdapter adapter;
    List<User> users;
    List<Travel> travels;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bundle = getIntent().getExtras();
        tx = findViewById(R.id.textView5);
        logoutButton = findViewById(R.id.logoutButton);
        String email = bundle.getString("email");
        String provider = bundle.getString("provider");
        recyclerView = findViewById(R.id.recyclerView);
        users = Program.management.getUsers();
        Program.management.getTravels().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                travels = task.getResult();
                adapter = new UserAdapter(travels, travel -> {
                    Intent intent = new Intent(MainActivity.this, RideActivity.class);
                    intent.putExtra("travel", (CharSequence) travel);
                    startActivity(intent);
                });
                recyclerView.setAdapter(adapter);
            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
        Log.d("TAG", "Hola");
        tabLayout = findViewById(R.id.tabLayout);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        //Poner el recyclerview desde el inicio
                        recyclerView.smoothScrollToPosition(0);
                        break;
                    case 1:
                        Intent intent = new Intent(MainActivity.this, SetUpRideActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
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


        setUp(email, ProviderType.valueOf(provider));
        onResume();

    }
    private void setUp(String email, ProviderType provider){
        tx.setText(email);
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            onBackPressed();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restablecer la selección del tab
        tabLayout.getTabAt(0).select(); // Cambia 0 por el índice del tab que quieras seleccionar por defecto
    }



}