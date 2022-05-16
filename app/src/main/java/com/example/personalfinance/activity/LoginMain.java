package com.example.personalfinance.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.personalfinance.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginMain extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText txtEmail,txtPassword;
    private Button btnLogin;
    private TextView txtRegisterLogin;
    private ImageButton imageButton;
    private TextView txtForgotPass;
    private ProgressDialog  progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        txtEmail = findViewById(R.id.txtEmail_Login);
        txtPassword = findViewById(R.id.txtPassword_Login);
        btnLogin = findViewById(R.id.btnSignIn_Login);
        txtRegisterLogin = findViewById(R.id.txtSignUp_Login);
        txtForgotPass = findViewById(R.id.txtForgot_Password_Login);
        imageButton = findViewById(R.id.btnBack_Login);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginMain.this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email  = txtEmail.getText().toString().trim();
                String passWord = txtPassword.getText().toString().trim();
                progressDialog.setMessage("Waiting....!");
                progressDialog.setTitle("Loading");
                progressDialog.show();
                progressDialog.getWindow().setBackgroundDrawableResource(R.color.black);
                auth.signInWithEmailAndPassword(email,passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginMain.this,HomeActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                        else{
                            Intent intent = new Intent(LoginMain.this,LoginMain.class);
                            startActivity(intent);
                            Toast.makeText(LoginMain.this, "Email or password invalid", Toast.LENGTH_SHORT).show();
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
        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder  passwordResertDialog = new AlertDialog.Builder(view.getContext());
                passwordResertDialog.setTitle("Resert Password ?");
                passwordResertDialog.setMessage("Enter Your Email To Received Reset Link");
                passwordResertDialog.setView(resetMail);
                passwordResertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = resetMail.getText().toString();
                        auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginMain.this, " Reset Link is sent to your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginMain.this, "Error ! Reset Link is not Sent "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                passwordResertDialog.create().show();
            }
        });
    }


}