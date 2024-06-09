package com.example.carrentalproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarViewScreen extends AppCompatActivity {

    private Spinner spinnerCarBrand;
    private EditText editTextCarModel;
    private RecyclerView rcViewCars;
    private List<Car> items = new ArrayList<>();
    private static final String BASE_URL = "http://10.0.2.2:84/rest2/get_items.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_view_screen);
        rcViewCars.setLayoutManager(new LinearLayoutManager(this));
        loadItems();
        setupViews();
        populateSpinner();

    }

    private void setupViews(){
        spinnerCarBrand = findViewById(R.id.spinnerCarBrand);
        editTextCarModel = findViewById(R.id.editTextCarModel);
        rcViewCars = findViewById(R.id.rcViewCars);
    }

    public void populateSpinner() {
        Log.d("AddCarScreen", "populateSpinners called");

        List<String> carBrands = Arrays.asList(
                "Toyota", "Honda", "Ford", "BMW", "Audi", "Tesla", "Chevrolet", "Hyundai",
                "Nissan", "Kia", "Mercedes-Benz", "Volkswagen", "Subaru", "Lexus", "Jeep",
                "Mazda", "Volvo", "Porsche", "Ferrari", "Jaguar", "Land Rover", "Maserati"
        );
        ArrayAdapter<String> carBrandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carBrands);
        carBrandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarBrand.setAdapter(carBrandAdapter);
    }
    private void loadItems() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, BASE_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                String brand = object.getString("brand");
                                String model = object.getString("model");
                                String imageUrl = object.getString("image");

                                Car car = new Car(brand, model, imageUrl);
                                items.add(car);
                            }

                            CarAdapter adapter = new CarAdapter(CarViewScreen.this, items);
                            rcViewCars.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CarViewScreen.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CarViewScreen.this, "Error loading data", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}