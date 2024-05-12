package com.example.reservationrestaurant;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.reservationrestaurant.Model.Order;
import com.example.reservationrestaurant.Model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class OrdersList extends AppCompatActivity {

    private TextView name;
    private TextView adresse;
    private TextView phone;
    private TextView totalPrice;

    private TextView FoodList;
    FirebaseDatabase db;
    DatabaseReference reqRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders_list);
        db = FirebaseDatabase.getInstance("https://reservation-restaurant-24668-default-rtdb.europe-west1.firebasedatabase.app");
        reqRef = db.getReference("Requests");
        name=(TextView) findViewById(R.id.textView);
        adresse=(TextView) findViewById(R.id.textView2);
        phone=(TextView) findViewById(R.id.textView3);
        FoodList =(TextView) findViewById(R.id.textView5);
        totalPrice =(TextView) findViewById(R.id.textView4);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        reqRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Request request = dataSnapshot.getValue(Request.class);
                    if (request != null) {

                        String orderName = request.getName();
                        String orderAddress = request.getAddress();
                        String orderPhone = request.getPhone();
                        String OrdertotalPrice = request.getTotal();
                        List<Order> Foods = request.getFoods();
                        StringBuilder foodStringBuild = new StringBuilder();



                        name.setText("Name :" +orderName);
                        adresse.setText("Adresse :"+orderAddress);
                        phone.setText("Phone :"+orderPhone);
                        totalPrice.setText("Total Price :"+OrdertotalPrice);

                        for( Order order:Foods){
                            foodStringBuild.append(order.getProductName()).append("X").append(order.getQuantity()).append("\n");
                        }
                        FoodList.setText(foodStringBuild.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}
