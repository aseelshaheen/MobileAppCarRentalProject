package com.example.carrentalproject;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCarScreen extends AppCompatActivity {

    private TextView textViewTitle;
    private Spinner spinnerCarBrand;
    private Spinner spinnerStatus;
    private EditText editTextCarModel;
    private EditText editTextPrice;
    private EditText editTextColor;
    private Button buttonUpdateImage;
    private Button buttonUpdateCar;
    private String imagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_screen);

        Log.d("AddCarScreen", "onCreate called");

        setupViews();
        populateSpinners();

        buttonUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        buttonUpdateCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCarBrand = spinnerCarBrand.getSelectedItem().toString();
                String newCarModel = editTextCarModel.getText().toString();
                int newPrice = Integer.parseInt(editTextPrice.getText().toString().trim());
                String newColor = editTextColor.getText().toString();
                String newStatus = spinnerStatus.getSelectedItem().toString();
                String newImagePath = imagePath;

                Car car = new Car(newCarBrand, newCarModel, newPrice, newColor, newStatus,newImagePath);
                updateCar(car);
            }
        });
    }

    public void setupViews() {
        Log.d("AddCarScreen", "setupViews called");

        textViewTitle = findViewById(R.id.textViewTitle);
        spinnerCarBrand = findViewById(R.id.spinnerCarBrand);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        editTextCarModel = findViewById(R.id.editTextCarModel);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextColor = findViewById(R.id.editTextColor);
        buttonUpdateImage = findViewById(R.id.buttonUpdateImage);
        buttonUpdateCar = findViewById(R.id.buttonUpdateCar);

        // Verify if views are properly initialized
        if (textViewTitle == null || spinnerCarBrand == null || spinnerStatus == null ||
                editTextCarModel == null || editTextPrice == null || editTextColor == null ||
                buttonUpdateImage == null || buttonUpdateCar == null) {
            Log.e("AddCarScreen", "One or more views are not properly initialized");
        }
    }

    public void populateSpinners() {
        Log.d("AddCarScreen", "populateSpinners called");

        List<String> carBrands = Arrays.asList(
                "Toyota", "Honda", "Ford", "BMW", "Audi", "Tesla", "Chevrolet", "Hyundai",
                "Nissan", "Kia", "Mercedes-Benz", "Volkswagen", "Subaru", "Lexus", "Jeep",
                "Mazda", "Volvo", "Porsche", "Ferrari", "Jaguar", "Land Rover", "Maserati"
        );
        ArrayAdapter<String> carBrandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carBrands);
        carBrandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarBrand.setAdapter(carBrandAdapter);

        List<String> statuses = Arrays.asList("Available", "Unavailable");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);
    }

    public void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        mGetContentLauncher.launch("image/*");
    }



    private ActivityResultLauncher<String> mGetContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the selected image URI
                    if (uri != null) {
                        imagePath = getPathFromUri(uri);
                    }
                }
            });

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return uri.getPath();
    }

    public void updateCar(Car car) {
        String url = "http://192.168.1.3:80/CarRental/AddNewCar.php";

        // Create request queue for Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Create JSON object with car data
        JSONObject jsonCar = new JSONObject();
        try {
            jsonCar.put("carID", car.getCarID());
            jsonCar.put("carBrand", car.getCarBrand());
            jsonCar.put("carModel", car.getCarModel());
            jsonCar.put("price", car.getPrice());
            jsonCar.put("color", car.getColor());
            jsonCar.put("status", car.getStatus());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a JSON object request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonCar,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle response
                        Toast.makeText(UpdateCarScreen.this, "Car record updated successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateCarScreen.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the request queue
        requestQueue.add(jsonObjectRequest);
    }


}
