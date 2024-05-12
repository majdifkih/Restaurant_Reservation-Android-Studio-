package com.example.reservationrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reservationrestaurant.Common.Common;
import com.example.reservationrestaurant.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
    EditText edtPhone,edtPassword;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtPassword=(MaterialEditText)findViewById(R.id.edtPassword);
        edtPhone=(MaterialEditText)findViewById(R.id.edtPhone);
        btnSignIn=(Button) findViewById(R.id.btnSignIn);
        FirebaseDatabase database= FirebaseDatabase.getInstance("https://reservation-restaurant-24668-default-rtdb.europe-west1.firebasedatabase.app");
        final DatabaseReference table_user = database.getReference("user");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SignIn", "Button clicked");
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("SignIn", "Received data from Firebase");
                       //check if user not exist in database
                        if (snapshot.child(edtPhone.getText().toString()).exists()){
                            //get user infos
                            User user = snapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            user.setPhone(edtPhone.getText().toString());
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                String role = user.getRole();
                                if(role != null && role.equals("Admin")){
                                    Intent adminIntent = new Intent(SignIn.this,OrdersList.class);
                                    adminIntent.putExtra("userRole", role);
                                    Common.currentUser=user;
                                    startActivity(adminIntent);
                                }else if (role.equals("Client")) {
                                    Intent homeIntent = new Intent(SignIn.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                }

                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Wrong Password !!!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignIn.this,"User not exist in database",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}