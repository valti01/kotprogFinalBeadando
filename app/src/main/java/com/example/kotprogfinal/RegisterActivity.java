package com.example.kotprogfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordRepeatEditText;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameEditText = findViewById(R.id.ipAddressEditText);
        passwordEditText = findViewById(R.id.maxHostsEditText);
        emailEditText = findViewById(R.id.netmaskEditText);
        passwordRepeatEditText = findViewById(R.id.passwordRepeatEditText);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Router.class));
        }


    }

    public void register(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordRepeat = passwordRepeatEditText.getText().toString();
        String email = emailEditText.getText().toString();

        if(TextUtils.isEmpty(email)){
            emailEditText.setError("Email is Required.");
            return;
        }

        if(!password.equals(passwordRepeat))
        {
            passwordRepeatEditText.setError("Nem egyezik a két jelszó");
        }

        if(TextUtils.isEmpty(password)){
            passwordEditText.setError("Jelszó kötelező.");
            return;
        }

        if(password.length() < 6){
            passwordEditText.setError("Legalább 6 karakterből kell állnia a jelszónak.");
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Sikeres regisztráció.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }else{
                    Toast.makeText(RegisterActivity.this,"Hiba történt.",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });


    }




    public void cancel(View view) {
        finish();
    }
}