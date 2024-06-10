package com.example.carrentalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class CustomerSignUp extends AppCompatActivity {

    private EditText edtTxtFirstName;
    private EditText edtTxtLastName;
    private EditText edtTxtPhoneNum;
    private EditText edtTxtEmail;
    private EditText edtTxtPassword;
    private EditText edtTxtIDNumber;
    private Button btnCustomerSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);
        setupViews();

        btnCustomerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupCustomer();
            }
        });
    }

    private void setupViews() {
        edtTxtFirstName = findViewById(R.id.FirstName);
        edtTxtLastName = findViewById(R.id.LastName);
        edtTxtPhoneNum = findViewById(R.id.PhoneNum);
        edtTxtEmail = findViewById(R.id.adminemail);
        edtTxtPassword = findViewById(R.id.edtTxtPassword);
        btnCustomerSignUp = findViewById(R.id.btncustomerSignUp);
        edtTxtIDNumber = findViewById(R.id.IDNumber);
    }

    public void signupCustomer() {
        final String id = edtTxtIDNumber.getText().toString().trim();
        final String firstName = edtTxtFirstName.getText().toString().trim();
        final String lastName = edtTxtLastName.getText().toString().trim();
        final String phoneNumber = edtTxtPhoneNum.getText().toString().trim();
        final String email = edtTxtEmail.getText().toString().trim();
        final String password = edtTxtPassword.getText().toString().trim();

        // Check if any field is empty
        if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(CustomerSignUp.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create Customer object
        Customer customer = new Customer(Integer.parseInt(id), firstName, lastName, email, phoneNumber, password, "");

        String url = "http://192.168.1.3:80/CarRental/CustomerSignup.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    Toast.makeText(CustomerSignUp.this, message, Toast.LENGTH_SHORT).show();

                    if (status.equals("success")) {
                        // Registration successful, navigate to login screen or main activity
                        Intent intent = new Intent(CustomerSignUp.this, CustomerScreen.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CustomerSignUp.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(CustomerSignUp.this, "Error connecting to server", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idNumber", String.valueOf(customer.getIdNumber()));
                params.put("firstName", customer.getFirstName());
                params.put("lastName", customer.getLastName());
                params.put("phoneNumber", customer.getPhoneNumber());
                params.put("email", customer.getEmail());
                params.put("password", customer.getPassword());
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(CustomerSignUp.this);
        requestQueue.add(stringRequest);
    }
}
