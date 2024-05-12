package com.example.reservationrestaurant;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    private EditText foodname;
    private EditText foodDesc;
    private EditText price;
    private EditText imgUrl;
    private Spinner categories;
    private FirebaseDatabase categoriesDb;
    private List<String> CategoryNames;
    private ArrayAdapter<String> categoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        categoriesDb = FirebaseDatabase.getInstance("https://reservation-restaurant-24668-default-rtdb.europe-west1.firebasedatabase.app");
        foodname=(MaterialEditText)findViewById(R.id.editTextFoodName);
        foodDesc = (MaterialEditText) findViewById(R.id.editTextDescription);
        imgUrl = (MaterialEditText) findViewById(R.id.editTextImageUrl);
        price = (MaterialEditText) findViewById(R.id.editTextPrice);
        categories = (Spinner) findViewById(R.id.spinnerFoodCategory);
        DatabaseReference dbref = categoriesDb.getReference("Category");
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> categoryNames = new ArrayList<>();
                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                    String categoryName = categorySnapshot.child("Name").getValue(String.class);
                    categoryNames.add(categoryName);
                }
                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(Admin.this, android.R.layout.simple_spinner_item, categoryNames);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categories.setAdapter(categoryAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void AddFood(View v){
        String selectedCatgoryName = (String) categories.getSelectedItem();
        DatabaseReference dbcatkeyref = categoriesDb.getReference("Category");
        dbcatkeyref.orderByChild("Name").equalTo(selectedCatgoryName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String catKey = snapshot.getChildren().iterator().next().getKey();
                    String foodName = foodname.getText().toString();
                    String foodDescription = foodDesc.getText().toString();
                    String foodPrice = price.getText().toString();
                    String img = imgUrl.getText().toString();

                    DatabaseReference foodref=categoriesDb.getReference("Food").push();
                    foodref.child("Name").setValue(foodName);
                    foodref.child("Price").setValue(foodPrice);
                    foodref.child("MenuId").setValue(catKey);
                    foodref.child("Description").setValue(foodDescription);
                    foodref.child("image").setValue(img);
                    foodname.setText("");
                    foodDesc.setText("");
                    imgUrl.setText("");
                    price.setText("");
                    Toast.makeText(Admin.this, "Food added successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}

