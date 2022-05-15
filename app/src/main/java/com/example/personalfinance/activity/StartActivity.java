package com.example.personalfinance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.personalfinance.R;
import com.google.firebase.auth.FirebaseAuth;


public class StartActivity extends AppCompatActivity {
    private Button btnRegister, btnLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        btnRegister= findViewById(R.id.btn_start_register);
        btnLogin= findViewById(R.id.btn_start_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null && !firebaseAuth.getCurrentUser().isAnonymous()){
            startActivity(new Intent(StartActivity.this,HomeActivity.class));
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,RegisterMain.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, LoginMain.class);
                startActivity(intent);
            }
        });
    }
}