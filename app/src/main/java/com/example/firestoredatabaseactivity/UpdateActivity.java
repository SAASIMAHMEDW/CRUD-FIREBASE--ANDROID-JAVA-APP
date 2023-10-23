package com.example.firestoredatabaseactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class UpdateActivity extends AppCompatActivity {

    String sendName,sendAge,sendEmail,sendPassword,sendUid;
    String email,password;
    Button updateDataBTN;
    boolean failed = true;
    ArrayList<userModel> allDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        UsereData();

        updateDataBTN = findViewById(R.id.updateDataBTN);
        updateDataBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmailPassword();
                verifyUserEmailPassword(email,password);
//                VerifiedUserData();

            }
        });
    }

    public void UsereData(){
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
                }
            }
        });
    }

    public void getEmailPassword(){
        EditText updateEmailETV,updatePasswordETV;
        updateEmailETV = findViewById(R.id.updateEmailETV);
        updatePasswordETV = findViewById(R.id.updatePasswordETV);
        email = updateEmailETV.getText().toString();
        password = updatePasswordETV.getText().toString();
    }

    public void verifyUserEmailPassword(String email, String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(UpdateActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            sendDataAndOpenUpdateActivity();
//                            Toast.makeText(UpdateActivity.this, "Verified", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(UpdateActivity.this, " Update->User Not Verified", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void sendDataAndOpenUpdateActivity(){
        VerifiedUserData();

        if (email.length()==0 || password.length()==0){
            Toast.makeText(UpdateActivity.this, "UpdateActivity Fill Email Password Field", Toast.LENGTH_SHORT).show();
        }else {
            if (failed) {
                Toast.makeText(this, "Wrong Email And Password", Toast.LENGTH_SHORT).show();
            }else {
                Intent updated_data = new Intent(UpdateActivity.this,updatedDataEntryActivity.class);
                updated_data.putExtra(ReadActivity.KEY_NAME, sendName);
                updated_data.putExtra(ReadActivity.KEY_AGE, sendAge);
                updated_data.putExtra(ReadActivity.KEY_EMAIL, sendEmail);
                updated_data.putExtra(ReadActivity.KEY_PASSWORD, sendPassword);
                updated_data.putExtra("uid",sendUid);
                startActivity(updated_data);
            }
        }
    }

    public void VerifiedUserData(){
        if (email.length()==0 || password.length()==0){
            Toast.makeText(UpdateActivity.this, "UpdateActivity->Fill Email Password Field", Toast.LENGTH_SHORT).show();
        }else {
            for (int i=0; i<allDataList.size(); i++) {
                String testEmail = (allDataList.get(i).email).toString();
                String testpassord = (allDataList.get(i).password).toString();
                if (email.equals(testEmail) && password.equals(testpassord)) {
                    sendName = (allDataList.get(i).name).toString();
                    sendAge = (allDataList.get(i).age).toString();
                    sendEmail = (allDataList.get(i).email).toString();
                    sendPassword = (allDataList.get(i).password).toString();
                    sendUid = (allDataList.get(i).uid.toString());
                    failed = false;
                    break;
                }
            }
        }
    }

}