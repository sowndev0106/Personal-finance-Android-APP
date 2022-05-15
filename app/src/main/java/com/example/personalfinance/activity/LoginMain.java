package com.example.personalfinance.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.personalfinance.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginMain extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText txtEmail,txtPassword;
    private Button btnLogin;
    private TextView txtRegisterLogin;
    private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        txtEmail = findViewById(R.id.txtEmail_Login);
        txtPassword = findViewById(R.id.txtPassword_Login);
        btnLogin = findViewById(R.id.btnSignIn_Login);
        txtRegisterLogin = findViewById(R.id.txtSignUp_Login);

        imageButton = findViewById(R.id.btnBack_Login);
        auth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email  = txtEmail.getText().toString().trim();
                String passWord = txtPassword.getText().toString().trim();
                auth.signInWithEmailAndPassword(email,passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginMain.this,HomeActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                        else{
                            Intent intent = new Intent(LoginMain.this,RegisterMain.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    }
                });
            }
        });
        txtRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginMain.this,RegisterMain.class);
                startActivity(intent);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginMain.this,StartActivity.class);
                startActivity(intent);
            }
        });
    }


}