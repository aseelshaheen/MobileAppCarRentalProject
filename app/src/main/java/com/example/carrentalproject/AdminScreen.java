package com.example.carrentalproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminScreen extends AppCompatActivity {


    private Button btnAdminLogin;
    private Button btnAdminSignUp;
    private EditText edtTxtPhone;
    private EditText edtTxtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);
        setupViews();

    }

    private void setupViews() {
        btnAdminLogin = findViewById(R.id.btnAdminLogin);
        btnAdminSignUp = findViewById(R.id.btnAdminSignUp);
        edtTxtPhone = findViewById(R.id.edtTxtPhone);
        edtTxtPassword = findViewById(R.id.edtTxtPassword);
    }
}