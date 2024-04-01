package com.blablacar4v;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.blablacar4v.models.Travel;
import com.blablacar4v.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Management {
    public FirebaseFirestore db;
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Travel> travels = new ArrayList<>();
    User user = new User();

    public Management(){
        db = FirebaseFirestore.getInstance();
    }

    /*public Task<User> getUser(String email){
        TaskCompletionSource<User> source = new TaskCompletionSource<>();
        db.collection("users")
                .document(email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document.exists()){
                            user = document.toObject(User.class);
                            source.setResult(user);
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }

                });
        return source.getTask();
    }*/

    public Task<List<User>> getUser(String email){
        TaskCompletionSource<List<User>> source = new TaskCompletionSource<>();
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        this.users.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.toObject(User.class).getEmail().equals(email)) {
                                this.users.add(document.toObject(User.class));
                                Log.d(TAG, users.get(0).getName()+ " users");
                            }
                            Log.d(TAG, users.size() + " users");
                        }
                        source.setResult(users);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
        return source.getTask();
    }


    public Task<List<Travel>> getTravels(){
        TaskCompletionSource<List<Travel>> source = new TaskCompletionSource<>();
        db.collection("travels")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        this.travels.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            this.travels.add(document.toObject(Travel.class));
                            Log.d(TAG, travels.size() + " travels");
                        }
                        source.setResult(travels);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
        return source.getTask();
    }

    public List<User> getUsers(){
                db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            users.add(document.toObject(User.class));
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
        return users;
    }

    public int getMaxId(){
        final int[] max = {-1};
        getTravels().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Travel> travels = task.getResult();
                for (Travel travel : travels) {
                    if (travel.getId() >= max[0]) {
                        max[0] = travel.getId();
                    }
                }
            }
        });
        return max[0];
    }
}
