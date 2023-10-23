package com.example.firestoredatabaseactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DeleteActivity extends AppCompatActivity {

    EditText delEmailETV,delPasswordETV;
    String email,password;
    Button delDataBTN;
    boolean dialogStatus = true;
    String Uid;
    ArrayList<userModel> allDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        UsereData();

        delDataBTN = findViewById(R.id.delDataBTN);
        delDataBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDelData();
                VerifiedUserDataDeletion();
//                Toast.makeText(DeleteActivity.this, Uid, Toast.LENGTH_SHORT).show();
                deletionDialog(Uid,dialogStatus);
                ClearDelData();
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

    public void getDelData(){
        delEmailETV = findViewById(R.id.delEmailETV);
        delPasswordETV = findViewById(R.id.delPasswordETV);
        email = delEmailETV.getText().toString();
        password = delPasswordETV.getText().toString();
    }

    public void deletionDialog(String uid, boolean dialogStatus){
        if(dialogStatus){
            AlertDialog.Builder buider = new AlertDialog.Builder(DeleteActivity.this);
            buider.setMessage("Confirm the Deletion");
            buider.setCancelable(false);

            buider.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    delDeletionData(uid);
                    dialog.dismiss();
                }
            });

            buider.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Toast.makeText(DeleteActivity.this, "Deletion Canceled", Toast.LENGTH_SHORT).show();
                }
            });

            buider.show();
        }

    }

    public void delDeletionData(String Uid){

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("users").document(Uid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(DeleteActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DeleteActivity.this, "Failed To Delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void VerifiedUserDataDeletion(){
        if (email.length()==0 || password.length()==0){
            Toast.makeText(DeleteActivity.this, "DeleteActivity->Fill Email Password Field", Toast.LENGTH_SHORT).show();
            dialogStatus = false;
        }else {
            for (int i=0; i<allDataList.size(); i++) {
                String testEmail = (allDataList.get(i).email).toString();
                String testpassord = (allDataList.get(i).password).toString();
                if (email.equals(testEmail) && password.equals(testpassord)) {
                    Uid = (allDataList.get(i).uid.toString());
                    break;
                }
            }
        }
    }

    public void ClearDelData(){
        delEmailETV = findViewById(R.id.delEmailETV);
        delPasswordETV = findViewById(R.id.delPasswordETV);
        delEmailETV.setText("");
        delPasswordETV.setText("");
    }

}