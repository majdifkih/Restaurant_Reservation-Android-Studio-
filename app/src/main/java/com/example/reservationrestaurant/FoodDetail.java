package com.example.reservationrestaurant;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reservationrestaurant.Model.Food;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    Button btn_plus;
    Button btn_minus;
    private int count = 0;
    TextView text_number;
    String foodId;
    FirebaseDatabase database;
    DatabaseReference foods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance("https://reservation-restaurant-24668-default-rtdb.europe-west1.firebasedatabase.app");
        foods =database.getReference("Food");
        text_number = (TextView) findViewById(R.id.text_number);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);


        food_description = (TextView) findViewById(R.id.food_description);
        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_image = (ImageView) findViewById(R.id.img_food);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if(getIntent() != null )
            foodId = getIntent().getStringExtra("FoodId");
        if(!foodId.isEmpty())
        {
            getDetailFood(foodId);
        }
    }

    private void getDetailFood(String foodId){
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Food food = snapshot.getValue(Food.class);
                Picasso.get().load(food.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(food.getName());
                food_price.setText(food.getPrice());

                food_name.setText(food.getName());
                food_description.setText(food.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Gérer les clics sur les boutons
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Augmenter le compteur
                count++;
                // Mettre à jour le TextView
                text_number.setText(String.valueOf(count));
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    // Diminuer le compteur si supérieur à zéro
                    count--;
                    // Mettre à jour le TextView
                    text_number.setText(String.valueOf(count));
                }
            }
        });
    }
}