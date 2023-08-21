package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.suitcase.databinding.ActivityLoginPageBinding;

public class Login_Page extends AppCompatActivity {
    ActivityLoginPageBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        databaseHelper=new DatabaseHelper(this);

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.txtLoginEmail.getText().toString().trim();
                String password=binding.txtLoginPassword.getText().toString().trim();

                if(email.equals("") || password.equals("")){
                    Toast.makeText(Login_Page.this, "All field are mandatory", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkCredential=databaseHelper.checkEmailPassword(email,password);
                    if (checkCredential==true){
                        Toast.makeText(Login_Page.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Login_Page.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ForgotPassword_Page.class);
                startActivity(intent);
            }
        });
        binding.txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Signup_Page.class);
                startActivity(intent);
            }
        });

    }
}