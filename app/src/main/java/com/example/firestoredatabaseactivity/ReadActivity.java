package com.example.firestoredatabaseactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {
    boolean failed = true;
    static String KEY_NAME = "KEY_NAME",
                    KEY_AGE = "KEY_AGE",
                    KEY_EMAIL = "KEY_EMAIL",
                    KEY_PASSWORD = "KEY_PASSWORD";

     Button readDataBTN;
     EditText emailReadDataETV,passwordReadDataETV;
     String email,password;
     String sendName,sendAge,sendEmail,sendPassword;

     ArrayList<userModel> allDataList;

//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        readedData();

        readDataBTN = findViewById(R.id.readDataBTN);
        readDataBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataEmailPassword();
                verifyUser();
                sendDataAndOpenActivity();
            }
        });
    }

    public void getDataEmailPassword(){
        emailReadDataETV = findViewById(R.id.emailReadDataETV);
        passwordReadDataETV = findViewById(R.id.passwordReadDataETV);
        email = emailReadDataETV.getText().toString();
        password = passwordReadDataETV.getText().toString();
    }

    public void verifyUser(){
        if (email.length()==0 || password.length()==0){
            Toast.makeText(ReadActivity.this, "ReadVerify Fill Email Password Field", Toast.LENGTH_SHORT).show();
        }else {
            for (int i=0; i<allDataList.size(); i++) {
                String testEmail = (allDataList.get(i).email).toString();
                String testpassword = (allDataList.get(i).password).toString();
                if (email.equals(testEmail) && password.equals(testpassword)) {
                    Toast.makeText(ReadActivity.this, "Successful reading", Toast.LENGTH_SHORT).show();
                    sendName = (allDataList.get(i).name).toString();
                    sendAge = (allDataList.get(i).age).toString();
                    sendEmail = (allDataList.get(i).email).toString();
                    sendPassword = (allDataList.get(i).password).toString();
                    failed = false;
                    break;
                }
            }
        }
    }
    public void readedData(){
        allDataList = new ArrayList<>();
        allDataList.clear();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null){
                    List<userModel> data = value.toObjects(userModel.class);
                    allDataList.addAll(data);
                }else {
                    Toast.makeText(ReadActivity.this, "ReadActivity Failed To get Data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendDataAndOpenActivity(){
        if (email.length()==0 || password.length()==0){
            Toast.makeText(ReadActivity.this, " ReadActivity Fill Email Password Field", Toast.LENGTH_SHORT).show();
        }else {
            if (failed) {
                Toast.makeText(this, "Wrong Email And Password", Toast.LENGTH_SHORT).show();
            }else {
                Intent readed_data = new Intent(ReadActivity.this, ReadedDataActivity.class);
                readed_data.putExtra(KEY_NAME, sendName);
                readed_data.putExtra(KEY_AGE, sendAge);
                readed_data.putExtra(KEY_EMAIL, sendEmail);
                readed_data.putExtra(KEY_PASSWORD, sendPassword);
                startActivity(readed_data);
            }
        }
    }

}