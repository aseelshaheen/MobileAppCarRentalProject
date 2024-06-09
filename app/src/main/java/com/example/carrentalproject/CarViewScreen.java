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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
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

    private Button btnSearch;
    private Button btnAdd;
    private Button btnUpdate;
    private Button btnDelete;

    private List<Car> items = new ArrayList<>();
    private Car selectedCar = null;
    private static final String BASE_URL = "http://192.168.1.3:80/CarRental/getItems.php";
    private static final String BASE_URL_FILTER = "http://192.168.1.3:80/CarRental/ApplyFilter.php";
    private static final String DELETE_URL = "http://192.168.1.3:80/CarRental/DeleteCar.php";
    private CarDetails adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_view_screen);
        setupViews();
        populateSpinner();
        loadItems();
        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String brand = spinnerCarBrand.getSelectedItem().toString();
                String model = editTextCarModel.getText().toString();
                loadFilteredItems(brand, model);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCar != null) {
                    deleteItem(selectedCar);
                } else {
                    Toast.makeText(CarViewScreen.this, "No car selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarViewScreen.this, AddCarScreen.class);
                startActivity(intent);
            }
        });

    }

    private void setupViews() {
        spinnerCarBrand = findViewById(R.id.spinnerCarBrand);
        editTextCarModel = findViewById(R.id.editTextCarModel);
        rcViewCars = findViewById(R.id.rcViewCars);
        btnSearch = findViewById(R.id.btnSearch);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        rcViewCars.setLayoutManager(new LinearLayoutManager(this));
    }

    public void populateSpinner() {
        List<String> carBrands = Arrays.asList(
                "Select Brand","Toyota", "Honda", "Ford", "BMW", "Audi", "Tesla", "Chevrolet", "Hyundai",
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
                                String brand = object.getString("carBrand");
                                String model = object.getString("carModel");
                                String imageUrl = object.getString("image");
                                int price = object.getInt("price");
                                String color = object.getString("color");
                                String status = object.getString("status");

                                Car car = new Car(brand, model, price, color, status, imageUrl);
                                items.add(car);
                            }

                            // Set up the adapter
                            CarDetails adapter = new CarDetails(CarViewScreen.this, items);
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

    private void loadFilteredItems(String brand, String model) {
        // Construct the URL with query parameters for filtering
        Uri.Builder builder = Uri.parse(BASE_URL_FILTER).buildUpon();
        if (brand != null && !brand.isEmpty()) {
            builder.appendQueryParameter("brand", brand);
        }
        if (model != null && !model.isEmpty()) {
            builder.appendQueryParameter("model", model);
        }
        String url = builder.toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            items.clear(); // Clear the previous items
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                String brand = object.getString("carBrand");
                                String model = object.getString("carModel");
                                String imageUrl = object.getString("image");

                                Car car = new Car(brand, model, imageUrl);
                                items.add(car);
                            }

                            adapter = new CarDetails(CarViewScreen.this, items);
                            rcViewCars.setAdapter(adapter);

                            adapter.notifyDataSetChanged();

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

    private void deleteItem(Car car) {
        String url = DELETE_URL + "?id=" + car.getCarID();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            items.remove(car);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(CarViewScreen.this, "Car deleted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CarViewScreen.this, "Failed to delete car", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CarViewScreen.this, "Error deleting car", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

}
