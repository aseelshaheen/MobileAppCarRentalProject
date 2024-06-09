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

public class AddCarScreen extends AppCompatActivity {

    private TextView textViewTitle;
    private Spinner spinnerCarBrand;
    private Spinner spinnerStatus;
    private EditText editTextCarModel;
    private EditText editTextPrice;
    private EditText editTextColor;
    private Button buttonInsertImage;
    private Button buttonInsertCar;
    private EditText imageName;
    private String imagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_screen);

        Log.d("AddCarScreen", "onCreate called");

        setupViews();
        populateSpinners();

        buttonInsertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        buttonInsertCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCar();
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
        buttonInsertImage = findViewById(R.id.buttonInsertImage);
        buttonInsertCar = findViewById(R.id.buttonInsertCar);
        imageName = findViewById(R.id.imageName);

        // Verify if views are properly initialized
        if (textViewTitle == null || spinnerCarBrand == null || spinnerStatus == null ||
                editTextCarModel == null || editTextPrice == null || editTextColor == null ||
                buttonInsertImage == null || buttonInsertCar == null || imageName == null) {
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
                        String imgName = getImageNameFromUri(uri);
                        imageName.setText(imgName);
                        Toast.makeText(AddCarScreen.this, "Image selected successfully", Toast.LENGTH_SHORT).show();
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

    private String getImageNameFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            String imageName = cursor.getString(columnIndex);
            cursor.close();
            return imageName;
        }
        return null;
    }

    public void addCar() {
        // Get user input and create Car object
        String carModel = editTextCarModel.getText().toString();
        int price = Integer.parseInt(editTextPrice.getText().toString().trim());
        String color = editTextColor.getText().toString();
        String carBrand = spinnerCarBrand.getSelectedItem().toString();
        String status = spinnerStatus.getSelectedItem().toString();
        String image = imageName.getText().toString();

        String url = "http://192.168.1.3:80/CarRental/AddNewCar.php";

        // Create request queue for Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<>();
        params.put("carBrand", carBrand);
        params.put("carModel", carModel);
        params.put("price", String.valueOf(price));
        params.put("color", color);
        params.put("status", status);
        params.put("image", image);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddCarScreen.this, "Car inserted successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddCarScreen.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        // Add the request to the request queue
        requestQueue.add(stringRequest);
    }
}


