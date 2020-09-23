package com.appstone.jobportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    private EditText etUserId;
    private EditText etPassword;


    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        etUserId = findViewById(R.id.et_LogInUserId);
        etPassword = findViewById(R.id.et_LogInPassword);

        auth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
           startActivity(new Intent(LoginPage.this,RegisterPage.class));
        }



    }

    public void onclickLogIn(View view) {
        String txt_email = etUserId.getText().toString();
        String txt_password = etPassword.getText().toString();
        if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
            Toast.makeText(LoginPage.this, "fill all the blanks", Toast.LENGTH_LONG).show();

        } else if (txt_password.length() < 6) {
            etPassword.setError("password must be greater than 6");
        } else {

            auth.signInWithEmailAndPassword(txt_email, txt_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginPage.this, "login successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginPage.this, RegisterPage.class));
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginPage.this, "invalid Email And Password", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void onClickSignUp(View view) {
        startActivity(new Intent(LoginPage.this, SignUp.class));
    }


    public void resetPassword(View view) {
        final EditText resetEmail = new EditText(view.getContext());
        AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Reset Password");
        passwordResetDialog.setMessage("Enter your Email");
        passwordResetDialog.setView(resetEmail);

        passwordResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String mail = resetEmail.getText().toString();
                auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(LoginPage.this, "Reset Link Sent to your email", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginPage.this, "Failed to Sent Link", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

        passwordResetDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        passwordResetDialog.create().show();


    }
}