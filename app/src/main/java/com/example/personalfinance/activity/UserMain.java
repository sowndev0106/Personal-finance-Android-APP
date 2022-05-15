package com.example.personalfinance.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalfinance.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserMain extends AppCompatActivity {
    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogOut;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        txtName = findViewById(R.id.txtUserNameMain);
        txtEmail = findViewById(R.id.txtEmailUserMain);
        btnLogOut = findViewById(R.id.btnLogOut);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        txtEmail.setText(mFirebaseUser.getEmail());
        database = FirebaseDatabase.getInstance();
        String name  = database.getReference("users").child(mAuth.getUid()).child("userName").toString();
        txtName.setText(name);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(UserMain.this,StartActivity.class);
                startActivity(intent);
            }
        });

    }
}