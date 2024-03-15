package com.blablacar4v;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blablacar4v.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignUpActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    EditText emailEditText;
    EditText passwordEditText;
    EditText directionEditText;
    EditText phoneEditText;
    EditText nameEditText;
    Button signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        directionEditText = findViewById(R.id.directionEdit);
        phoneEditText = findViewById(R.id.telephonEditText);
        nameEditText = findViewById(R.id.nameEditText);
        db = FirebaseFirestore.getInstance();
        setUp();
    }

    private  void setUp(){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailEditText.getText().toString().isEmpty() || !passwordEditText.getText().toString().isEmpty()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString());
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        db.collection("users").document(emailEditText.getText().toString()).set(new User(
                                directionEditText.getText().toString(),
                                emailEditText.getText().toString(),
                                nameEditText.getText().toString(),
                                phoneEditText.getText().toString(),
                                ""
                        ));
                        showHome(FirebaseAuth.getInstance().getCurrentUser().getEmail(), ProviderType.BASIC);
                    }
                } else {
                    showAlert();
                }
            }
        });
    }



    private void showHome(String name, ProviderType provider){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("provider", provider.toString());
        startActivity(intent);
    }

    private void showAlert(){
        Toast.makeText(this, "Se ha producido un error", Toast.LENGTH_SHORT).show();
    }
}