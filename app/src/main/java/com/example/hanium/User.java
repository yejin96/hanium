package com.example.hanium;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;

public class User implements Serializable {
    String id;
    String name;
    String cellphone;
    String mail;

    FirebaseFirestore db= FirebaseFirestore.getInstance();
    private static final String TAG = "User";
    public User(String id) {
        this.id = id;
        db.collection("User")
                .whereEqualTo("ID",id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                name=document.get("NAME").toString();
                                cellphone=document.get("Phone").toString();
                                mail=document.get("Email").toString();
                            }
                        }

                    }
                });

    }
}
