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

public class SignUpAdminScreen extends AppCompatActivity {

    private EditText username;
    private EditText adminemail;
    private EditText edtTxtPassword;
    private Button btncustomerSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_admin_screen);

        // Call setupView to initialize the UI components
        setupView();
    }

    private void setupView() {
        username = findViewById(R.id.username);
        adminemail = findViewById(R.id.adminemail);
        edtTxtPassword = findViewById(R.id.edtTxtPassword);
        btncustomerSignUp = findViewById(R.id.btncustomerSignUp);
    }
}