package com.appstone.jobportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText phoneNo;
    private EditText signUpEmail;
    private EditText signUpPassword;
    private EditText signUpCnfrmPassword;
    private Button btnSignUp;


    private String userID;


    private FirebaseAuth auth;
    private FirebaseFirestore fireStore;
//    private FirebaseDatabase database;
//    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.et_firstName);
        lastName = findViewById(R.id.et_lastName);
        phoneNo = findViewById(R.id.et_phoneNo);
        signUpEmail = findViewById(R.id.et_signUpEmail);
        signUpPassword = findViewById(R.id.et_signUpPassword);
        signUpCnfrmPassword = findViewById(R.id.et_cnfrmPassword);

        auth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("users");
//        DbHelper dbHelper = new DbHelper();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(SignUp.this, RegisterPage.class));
            finish();

        }


        btnSignUp = findViewById(R.id.btn_signUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String txt_firstName = firstName.getText().toString();
                final String txt_LastName = lastName.getText().toString();
                final String txt_PhoneNo = phoneNo.getText().toString();
                final String txt_email = signUpEmail.getText().toString().trim();
                String txt_password = signUpPassword.getText().toString().trim();
                String txt_cnfrmPassword = signUpCnfrmPassword.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_firstName)
                        || TextUtils.isEmpty(txt_LastName) || TextUtils.isEmpty(txt_PhoneNo)
                        || TextUtils.isEmpty(txt_cnfrmPassword)) {
                    Toast.makeText(SignUp.this, "fill all the blanks", Toast.LENGTH_LONG).show();
                } else if (txt_password.length() < 6) {
                    signUpPassword.setError("password must be greater than 6");
                } else {

                    auth.createUserWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                finish();

                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignUp.this,"Email verification link is sent" +
                                                "tp your Emasil",Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("log","OnFailure: Email is not sent"+e.getMessage());

                                    }
                                });

// cuurrent user details store in firestore
                                Toast.makeText(SignUp.this, "successfully registered", Toast.LENGTH_LONG).show();
                                userID = auth.getCurrentUser().getUid();
                                DocumentReference documentReference = fireStore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("firstName", txt_firstName);
                                user.put("LastName", txt_LastName);
                                user.put("Email", txt_email);
                                user.put("PhoneNo", txt_PhoneNo);

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("tag", "onSuccess: user profile is created");
                                    }
                                });



                                startActivity(new Intent(SignUp.this, RegisterPage.class));
                            } else {
                                Toast.makeText(SignUp.this, "registration failed", Toast.LENGTH_LONG).show();
                            }

                        }
                    });


                }


            }
        });


    }

    public void onClickBackToLoginPage(View view) {
        startActivity(new Intent(SignUp.this, LoginPage.class));
    }
}