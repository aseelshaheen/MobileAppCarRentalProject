package com.example.carrentalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ResponseCache;
import java.util.HashMap;
import java.util.Map;

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

    private void signUpAdmin() {
        final String usernameStr = username.getText().toString().trim();
        final String emailStr = adminemail.getText().toString().trim();
        final String passwordStr = edtTxtPassword.getText().toString().trim();

        // Check if any field is empty
        if (usernameStr.isEmpty() || emailStr.isEmpty() || passwordStr.isEmpty()) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            Toast.makeText(SignUpAdminScreen.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://192.168.1.3:80/CarRental/AdminSignUp.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    Toast.makeText(SignUpAdminScreen.this, message, Toast.LENGTH_SHORT).show();

                    if (status.equals("success")) {
                        // Registration successful, navigate to login screen or main activity
                        Intent intent = new Intent(SignUpAdminScreen.this, AdminScreen.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpAdminScreen.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(SignUpAdminScreen.this, "Error connecting to server", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", usernameStr);
                params.put("email", emailStr);
                params.put("password", passwordStr);
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}