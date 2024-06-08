package com.example.carrentalproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;

public class AddCarScreen extends AppCompatActivity {

    private TextView textViewTitle, textViewPrice;
    private Spinner spinnerCarBrand, spinnerStatus;
    private EditText editTextCarModel, editTextPrice, editTextColor;
    private Button buttonInsertImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        populateSpinners();
    }


    public void setupViews(){
        textViewTitle = findViewById(R.id.textViewTitle);
        spinnerCarBrand = findViewById(R.id.spinnerCarBrand);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        editTextCarModel = findViewById(R.id.editTextCarModel);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextColor = findViewById(R.id.editTextColor);
        buttonInsertImage = findViewById(R.id.buttonInsertImage);
    }

    public  void populateSpinners(){
        // Populate the car brand spinner
        List<String> carBrands = Arrays.asList(
                "Toyota", "Honda", "Ford", "BMW", "Audi", "Tesla", "Chevrolet", "Hyundai",
                "Nissan", "Kia", "Mercedes-Benz", "Volkswagen", "Subaru", "Lexus", "Jeep",
                "Mazda", "Volvo", "Porsche", "Ferrari", "Jaguar", "Land Rover", "Maserati"
        );
        ArrayAdapter<String> carBrandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carBrands);
        carBrandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarBrand.setAdapter(carBrandAdapter);

        // Populate the status spinner
        List<String> statuses = Arrays.asList("Available", "Unavailable");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);
    }
}