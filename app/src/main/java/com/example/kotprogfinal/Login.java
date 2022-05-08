package com.example.kotprogfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();

        if(TextUtils.isEmpty(email)){
            emailEditText.setError("Email is Required.");
            return;
        }

        if(TextUtils.isEmpty(password)){
            passwordEditText.setError("Jelszó kötelező.");
            return;
        }

        if(password.length() < 6){
            passwordEditText.setError("Legalább 6 karakterből kell állnia a jelszónak.");
            return;
        }

        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this,"Sikeres belépés.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Router.class));
                }else {
                    Toast.makeText(Login.this, "Hiba történt.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void register(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }





}