package com.appstone.jobportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class UserDetails extends AppCompatActivity {

    private TextView firstName,lastName,email,phoneNo;

    private String userID;


    private FirebaseAuth auth;
    private FirebaseFirestore fireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

         firstName = findViewById(R.id.tv_firstName);
         lastName = findViewById(R.id.tv_lastName);
         phoneNo = findViewById(R.id.tv_phoneNO);
         email = findViewById(R.id.tv_email);

         fireStore = FirebaseFirestore.getInstance();
         auth = FirebaseAuth.getInstance();
         userID = auth.getCurrentUser().getUid();

        DocumentReference documentReference = fireStore.collection("users").document(userID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                phoneNo.setText(value.getString("PhoneNo"));
                firstName.setText(value.getString("firstName"));
                lastName.setText(value.getString("LastName"));
                email.setText(value.getString("Email"));
            }
        });















    }
}