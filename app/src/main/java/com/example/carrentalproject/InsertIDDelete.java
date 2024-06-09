package com.example.carrentalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class InsertIDDelete extends AppCompatActivity {


    private EditText editTextCarId;
    private Button buttonDeleteCar;
    private static final String DELETE_URL = "http://192.168.1.3:80/CarRental/DeleteCar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_iddelete);

        editTextCarId = findViewById(R.id.editTextCarId);
        buttonDeleteCar = findViewById(R.id.buttonDeleteCar);

        buttonDeleteCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCar();
            }
        });
    }

    private void deleteCar() {
        final String carId = editTextCarId.getText().toString().trim();
        if (carId.isEmpty()) {
            Toast.makeText(this, "Please enter Car ID", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = DELETE_URL + "?id=" + carId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(InsertIDDelete.this, "Car deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(InsertIDDelete.this, "Failed to delete car", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InsertIDDelete.this, "Error deleting car", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}