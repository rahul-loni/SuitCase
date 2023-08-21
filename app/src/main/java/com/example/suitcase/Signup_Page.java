package com.example.suitcase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.suitcase.databinding.ActivitySignupPageBinding;

public class Signup_Page extends AppCompatActivity {
ActivitySignupPageBinding binding;
DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignupPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper=new DatabaseHelper(this);

         getSupportActionBar().hide();
        //Click to signup button
        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Text Method
                String email=binding.txtSignupEmail.getText().toString().trim();
                String password=binding.txtSignupPassword.getText().toString().trim();
                String Cpassword=binding.txtSignupCpassword.getText().toString().trim();

                //Form Validation

                if (email.equals("")  || password.equals("") || Cpassword.equals("")){
                    Toast.makeText(Signup_Page.this, "All field are mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                      if (password.equals(Cpassword)){
                         Boolean checkEmail=databaseHelper.checkEmail(email);
                         if (checkEmail==false){
                             Boolean insert=databaseHelper.insertUsers(email,password);
                             if (insert==true){
                                 Toast.makeText(Signup_Page.this, "Signup Comp", Toast.LENGTH_SHORT).show();
                                 Intent intent=new Intent(getApplicationContext(),Login_Page.class);
                                 startActivity(intent);
                             }else {
                                 Toast.makeText(Signup_Page.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                             }
                         }
                         else {
                             Toast.makeText(Signup_Page.this, "Users Already exists", Toast.LENGTH_SHORT).show();
                         }
                      }
                      else {
                          Toast.makeText(Signup_Page.this, "Invalid Password ", Toast.LENGTH_SHORT).show();
                      }

                }
                //Signup Method
            }
        });
        //Just click to move login page
        binding.txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Login_Page.class) ;
                startActivity(intent);
            }
        });
    }
}