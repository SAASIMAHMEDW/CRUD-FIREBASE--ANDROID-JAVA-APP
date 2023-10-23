package com.example.firestoredatabaseactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class updatedDataEntryActivity extends AppCompatActivity {

    String name,age,email,password,uid;

    String updatedName,updatedAge,updatedEmail,updatedPassword;
    EditText editUpdateNameETV,editUpdateAgeETV,editUpdateEmailETV,editUpdatePasswordETV;
    Button UpdatedBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated_data_entry);
        setUpdateData();

        UpdatedBTN = findViewById(R.id.UpdatedBTN);
        UpdatedBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedData();
                setUpdatedData(updatedName,updatedAge,updatedEmail,updatedPassword,uid);
                goHomeUpdate();
//                Log.e("GotUID", "onClick: uid"+uid);
            }
        });
    }

    public void setUpdateData(){
        editUpdateNameETV = findViewById(R.id.editUpdateNameETV);
        editUpdateAgeETV = findViewById(R.id.editUpdateAgeETV);
        editUpdateEmailETV = findViewById(R.id.editUpdateEmailETV);
        editUpdatePasswordETV = findViewById(R.id.editUpdatePasswordETV);

        Intent dataIntent = getIntent();
        name = dataIntent.getStringExtra(ReadActivity.KEY_NAME);
        age = dataIntent.getStringExtra(ReadActivity.KEY_AGE);
        email = dataIntent.getStringExtra(ReadActivity.KEY_EMAIL);
        password = dataIntent.getStringExtra(ReadActivity.KEY_PASSWORD);
        uid = dataIntent.getStringExtra("uid");

//        editUpdateNameETV.setText(name);
//        editUpdateAgeETV.setText(age);
//        editUpdateEmailETV.setText(email);
//        editUpdatePasswordETV.setText(password);

        editUpdateNameETV.setHint(name);
        editUpdateAgeETV.setHint(age);
        editUpdateEmailETV.setHint(email);
        editUpdatePasswordETV.setHint(password);


    }

    public void goHomeUpdate(){
        Intent updateHome = new Intent(updatedDataEntryActivity.this,MainActivity.class);
        startActivity(updateHome);
    }

    public void updatedData(){

        if((editUpdateNameETV.getText().toString().length())==0){
            updatedName = editUpdateNameETV.getHint().toString();
        }else {
            updatedName = editUpdateNameETV.getText().toString();
        }
         if((editUpdateAgeETV.getText().toString().length())==0){
            updatedAge = editUpdateAgeETV.getHint().toString();
        }else {
             updatedAge = editUpdateAgeETV.getText().toString();
        }
         if((editUpdateEmailETV.getText().toString().length())==0){
            updatedEmail = editUpdateEmailETV.getHint().toString();
        }else {
            updatedEmail = editUpdateEmailETV.getText().toString();
        }
         if((editUpdatePasswordETV.getText().toString().length())==0){
            updatedPassword = editUpdatePasswordETV.getHint().toString();
        }else {
            updatedPassword = editUpdatePasswordETV.getText().toString();
        }
    }

    public void setUpdatedData(String name, String age, String email, String password,String uid){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(uid)
                    .update("name",name, "age",age, "email",email, "password",password)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(updatedDataEntryActivity.this, "Updated Successful", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(updatedDataEntryActivity.this, "Failed To Update", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}