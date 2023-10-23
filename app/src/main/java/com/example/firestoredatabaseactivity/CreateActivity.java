package com.example.firestoredatabaseactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class CreateActivity extends AppCompatActivity {
    EditText nameAreaCreateETV,ageAreaCreateETV,emailAreaCreateETV,passwordAreaCreateETV;
    String name,age,email,password;
    Button createDataBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        createDataBTN = findViewById(R.id.createDataBTN);
        createDataBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                emailPasswordCreateAuth(email,password);
                createDataCollection(name,age,email,password);
                clearData();
            }
        });
    }

    public void getData(){
        nameAreaCreateETV = findViewById(R.id.nameAreaCreateETV);
        ageAreaCreateETV = findViewById(R.id.ageAreaCreateETV);
        emailAreaCreateETV = findViewById(R.id.emailAreaCreateETV);
        passwordAreaCreateETV = findViewById(R.id.passwordAreaCreateETV);

        name = nameAreaCreateETV.getText().toString();
        age = ageAreaCreateETV.getText().toString();
        email = emailAreaCreateETV.getText().toString();
        password = passwordAreaCreateETV.getText().toString();
    }

    public void emailPasswordCreateAuth(String email,String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(CreateActivity.this, "Failed To Create User Email And Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void createDataCollection(String name,String age, String email, String password){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = db.collection("users").document().getId();
        userModel user = new userModel(name,age,email,password,uid);

        db.collection("users")
                .document(uid)
                    .set(user)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()){
                                    Toast.makeText(CreateActivity.this, "Failed To Create User", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(CreateActivity.this, "Successfully Created User", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
        }

    public void clearData(){
        nameAreaCreateETV = findViewById(R.id.nameAreaCreateETV);
        ageAreaCreateETV = findViewById(R.id.ageAreaCreateETV);
        emailAreaCreateETV = findViewById(R.id.emailAreaCreateETV);
        passwordAreaCreateETV = findViewById(R.id.passwordAreaCreateETV);

        nameAreaCreateETV.setText("");
        ageAreaCreateETV.setText("");
        emailAreaCreateETV.setText("");
        passwordAreaCreateETV.setText("");
    }

}