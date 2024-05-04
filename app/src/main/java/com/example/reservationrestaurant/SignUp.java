package com.example.reservationrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reservationrestaurant.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {
    EditText edtPhone, edtPassword, edtName;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        edtName = (MaterialEditText) findViewById(R.id.edtName);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://reservation-restaurant-24668-default-rtdb.europe-west1.firebasedatabase.app");
        final DatabaseReference table_user = database.getReference("user");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SignUp", "Button clicked");

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("SignIn", "Received data from Firebase");

                        // Vérifier si l'utilisateur n'existe pas dans la base de données
                        if (snapshot.child(edtPhone.getText().toString()).exists()) {
                            Toast.makeText(SignUp.this, "Phone Number already register", Toast.LENGTH_SHORT).show();
                        } else {
                            User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Gestion des erreurs d'annulation
                    }
                });
            }
        });
    }
}
