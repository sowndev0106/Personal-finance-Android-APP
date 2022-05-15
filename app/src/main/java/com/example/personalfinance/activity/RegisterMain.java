package com.example.personalfinance.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.personalfinance.R;
import com.example.personalfinance.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterMain extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText txtEmail,txtPass,txtUser;
    private Button btnRegister;
    private TextView txtLogin;
    private ImageButton imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);
        txtEmail = findViewById(R.id.txtEmail_Register);
        txtPass = findViewById(R.id.txtPassword_Register);
        btnRegister = findViewById(R.id.btn_RegisterSignUp);
        txtLogin = findViewById(R.id.txt_RegisterSignIn);
        txtUser = findViewById(R.id.txtUserName);
        imgBack = findViewById(R.id.img_BackRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPass.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(RegisterMain.this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();

                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(RegisterMain.this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();

                }
                if (password.isEmpty()) {
                    Toast.makeText(RegisterMain.this, "Vui lòng nhập Password", Toast.LENGTH_SHORT).show();

                }

                if (password.length() < 8) {

                    Toast.makeText(RegisterMain.this, "Password của bạn phải trên 8 ký tự", Toast.LENGTH_SHORT).show();

                }
                else{
                    auth = FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegisterMain.this,LoginMain.class);
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                User user = new User();
                                String username = txtUser.getText().toString().trim();
                                user.setUserName(username);
                                database.getReference("users").child(auth.getUid()).setValue(user);
                                startActivity(intent);
                                finishAffinity();
                            }
                        }
                    });
                }
            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterMain.this,LoginMain.class);
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterMain.this,StartActivity.class);
                startActivity(intent);
            }
        });
    }
}