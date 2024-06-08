package com.example.carrentalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CustomerScreen extends AppCompatActivity {

    private Button btnCustomerLogin;
    private Button btnCustomerSignUp;
    private EditText edtTxtPhone;
    private EditText edtTxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_screen);
        setupViews();
        GoToSignUpScreen();
    }

    private void setupViews() {
        btnCustomerLogin = findViewById(R.id.btnCustomerLogin);
        btnCustomerSignUp = findViewById(R.id.btncustomerSignUp);
        edtTxtPhone = findViewById(R.id.edtTxtPhone);
        edtTxtPassword = findViewById(R.id.edtTxtPassword);
    }


    public void GoToSignUpScreen(){
        btnCustomerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerScreen.this, CustomerSignUp.class);
                startActivity(intent);
            }
        });
    }



}